package com.muda.druidSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.muda.druidSource.beans.JdbcTemplateWrapper;
import com.muda.druidSource.autoconfigure.DruidDataSourceBuilder;
import com.muda.druidSource.beans.DbInfo;
import com.muda.druidSource.config.DSPropertiesLoadUtils;
import com.muda.druidSource.config.DbConfig;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.StreamSupport;

@Configuration
@PropertySource(value = "classpath:jdbc.properties")
@EnableConfigurationProperties
public class MysqlDynamicDSManager {
    private static final Logger logger = LoggerFactory.getLogger(MysqlDynamicDSManager.class);
    @Resource
    private org.springframework.core.env.Environment springEnv;

    private List<JdbcTemplateWrapper> JdbcTempList;

    private static final String showDatabase = "show databases";

    private JdbcTemplate utcJdbc = null;//utc-rest 数据库连接

    public void init(Properties props) throws Exception {
        JdbcTempList = getAllJdbcTemplateMapping(props);
    }

    @PostConstruct
    public void init() throws Exception {
        init(getProperties());
    }

    public Properties getProperties() {
        Properties props = new Properties();
        MutablePropertySources propSrcs = ((AbstractEnvironment) springEnv).getPropertySources();
        StreamSupport.stream(propSrcs.spliterator(), false)
                .filter(ps -> ps instanceof EnumerablePropertySource)
                .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
                .flatMap(Arrays::<String>stream)
                .forEach(propName -> {
                    if (System.getProperty(propName) == null && (!System.getenv().containsKey(propName))) {
                        props.setProperty(propName, springEnv.getProperty(propName));
                    }
                });
        return props;
    }

    private static Pair<String, JdbcTemplate> createDbConnection(DbInfo dbInfo, String defaultDriverClass, String defaultDsTypeClass, Properties commonSettings) {
        logger.info("createDbConnection.create({}),before", dbInfo.getUrl());
        if (StringUtils.isEmpty(dbInfo.getDriverClass())) {
            dbInfo.setDriverClass(defaultDsTypeClass);
        }
        if (StringUtils.isEmpty(dbInfo.getType())) {
            dbInfo.setType(defaultDriverClass);
        }
        DruidDataSource ds = DruidDataSourceBuilder.create().build();
        ds.setUrl(dbInfo.getUrl());
        ds.setUsername(dbInfo.getUsername());
        ds.setPassword(dbInfo.getPassword());
        ds.setDriverClassName(dbInfo.getDriverClass());
        ds.setDbType(dbInfo.getType());
        ds.setConnectProperties(commonSettings);
        JdbcTemplate jdbc = new JdbcTemplate(ds);
        logger.info("createDbConnection.create({}),after", dbInfo.getUrl());
        return Pair.of(dbInfo.getDbNames(), jdbc);
    }


    /**
     * @param config
     * @return
     */
    public static List<JdbcTemplateWrapper> getAllJdbcTemplateMapping(Properties config) throws Exception {
        DbConfig dbConfig = DSPropertiesLoadUtils.parseDbConfig(config);
        Properties commonSettings = new Properties();
        List<JdbcTemplateWrapper> JdbcTempList = new ArrayList<>();
        JdbcTemplateWrapper tidb = new JdbcTemplateWrapper();

        commonSettings.putAll(dbConfig.getCommonsSettings());

        if (config != null) {
            String defaultDriverClass = dbConfig.getCommonsSettings().get(DSPropertiesLoadUtils.DefaultDriverClassKey);
            String defaultDsTypeClass = dbConfig.getCommonsSettings().get(DSPropertiesLoadUtils.DefaultDbTypeClassKey);
            List<DbInfo> list = dbConfig.getList();
            for (DbInfo dbInfo : list) {
                if ("tidb".equals(dbInfo.getDbType())) {
                    List<JdbcTemplate> jdbcList = new ArrayList<>();
                    String[] ips = dbInfo.getIps().split(",");
                    Set<String> dataList = new HashSet<String>();
                    String url = dbInfo.getUrl();
                    for (String ip : ips) {
                        url = url.replace("@ip", ip);
                        dbInfo.setUrl(url);
                        logger.info("--------- 开始连接 ---1   " + "  ------  url ----" + dbInfo.getUrl() + " ------------");
                        Pair<String, JdbcTemplate> p = createDbConnection(dbInfo, defaultDriverClass, defaultDsTypeClass, commonSettings);
                        jdbcList.add(p.getValue());
                        logger.info("do(show database) for url:{}",dbInfo.getUrl());
                        dataList.addAll(getDatabaseList(p.getValue()));
                        logger.info("do(show database) for url:{}return",dbInfo.getUrl());
                    }
                    tidb.setCfgDbNames(new HashSet<>(Arrays.asList(dbInfo.getDbNames().split(","))));
                    tidb.setDbNames(dataList);
                    tidb.setType("tidb");
                    tidb.setUrl(dbInfo.getUrl());
                    tidb.setJdbcList(jdbcList);
                    logger.info("----------------  url " + dbInfo.getUrl() + "---- 连接成功");

                } else {
                    Pair<String, JdbcTemplate> p = createDbConnection(dbInfo, defaultDriverClass, defaultDsTypeClass, commonSettings);
                    Set<String> dataList = new HashSet<String>();
                    //logger.info("do(show database) for url:{}",dbInfo.getUrl());
                    logger.info("--------- 开始连接 ---2   " + "  ------  url ----" + dbInfo.getUrl() + " ------------");
                    dataList.addAll(getDatabaseList(p.getValue()));
                    //logger.info("do(show database) for url:{}return",dbInfo.getUrl());
                    JdbcTemplateWrapper bean = new JdbcTemplateWrapper();
                    bean.setDbNames(dataList);
                    bean.setType("mysql");
                    bean.setUrl(dbInfo.getUrl());
                    bean.setCfgDbNames(new HashSet<>(Arrays.asList(dbInfo.getDbNames().split(","))));
                    List<JdbcTemplate> jdbcList = new ArrayList<>();
                    jdbcList.add(p.getValue());
                    bean.setJdbcList(jdbcList);
                    JdbcTempList.add(bean);
                    logger.info("----------------  url " + dbInfo.getUrl() + "---- 连接成功");
                }
            }
        }
        // tidb 放在后边
        JdbcTempList.add(tidb);
        return JdbcTempList;
    }


    /**
     * 按照多个库名去匹配数据源
     * 优先匹配mysql
     *
     * @param nameSet
     * @return
     */
    public JdbcTemplate getJdbcTemplate(Set<String> nameSet) {

        if (CollectionUtils.isEmpty(nameSet)) {
            throw new IllegalArgumentException("could not getJdbcTemplate for empty  nameSet");
        }
        boolean flag = false;
        JdbcTemplate jdbc = null;
        for (JdbcTemplateWrapper bean : JdbcTempList) {
            flag = bean.matchCfgDBNames(nameSet);
            if (flag) {
                jdbc = bean.getJdbcTemplate();
                logger.info("===数据源类型：" + bean.getType() + "；数据源url：" + bean.getUrl());
                break;
            }
        }
        if (!flag)
            for (JdbcTemplateWrapper bean : JdbcTempList) {
                flag = bean.matchDBNames(nameSet);
                if (flag) {
                    jdbc = bean.getJdbcTemplate();
                    logger.info("===数据源类型：" + bean.getType() + "；数据源url：" + bean.getUrl());
                    break;
                }
            }
        if (!flag) {
            throw new IllegalArgumentException("DB Connection nameSet:" + nameSet + " not reserve in db dbNames set:");
        }
        return jdbc;
    }


    private static List<String> getDatabaseList(JdbcTemplate jdbc) throws Exception {
        List<String> list = new ArrayList<>();
        ResultSet set = jdbc.getDataSource().getConnection().getMetaData().getCatalogs();
        while (set.next()) {
            String s = set.getString(1);
            list.add(s);
            //logger.info("dbName:{}",s);
        }
        return list;
    }
}
