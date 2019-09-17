package com.muda.controller.platform.job;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.muda.beans.platform.job.PlatformJob;
import com.muda.component.PageInfo;
import com.muda.druidSource.autoconfigure.DruidDataSourceBuilder;
import com.muda.druidSource.beans.DbInfo;
import com.muda.service.platform.job.PlatformJobService;
import com.muda.utils.file.ConfigTools;
import com.muda.utils.file.FileOperation;
import com.muda.utils.http.HttpClientImp;
import com.muda.utils.http.Oauth;
import com.muda.utils.listenter.TestngRetry;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.testng.Assert;
import org.testng.TestNG;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.util.*;

@Controller
public class PlatformJobController extends ConfigTools {

    private static final Logger logger = LoggerFactory.getLogger(PlatformJobController.class);
    private static String swithMethod = "pre";
    private static final String fileCSV;
    private static final String httpUrl;
    private static final String oauthURL;
    private static final String clientId;
    private static final String cientSecret;
    HttpClientImp hci = new HttpClientImp();
    static OAuth2AccessToken token;
    static Map<String, Object> header;

    public static final String DefaultDriverClassKey = "spring.datasource.druid.driverClass";
    public static final String DefaultDbTypeClassKey = "spring.datasource.druid.type";
    public static Pair<String, JdbcTemplate> p;


    private static final DbInfo info;

    static {
        httpUrl = config.getString("cif_utc_rest." + swithMethod + ".url");
        swithMethod = swithMethod.equals("pre") ? "line" : swithMethod.equals("dev") ? "test" : swithMethod.equals("beta") ? "test" : swithMethod.equals("test") ? "test" : "line";
        System.out.println(swithMethod);
        oauthURL = config.getString("oauth_" + swithMethod + "_rest.oauth.url");
        clientId = config.getString("oauth_" + swithMethod + "_rest.oauth.clientId ");
        cientSecret = config.getString("oauth_" + swithMethod + "_rest.oauth.cientSecret");
        token = Oauth.getToken(oauthURL, clientId, cientSecret);
        header = Oauth.headerPut(token);
        fileCSV = "/Users/apple/Documents/case/all_method.txt";
        Set<String> nameSet = Collections.singleton("wj_test");

        info = new DbInfo();
        info.setDriverClass("com.mysql.jdbc.Driver");
        info.setUrl("jdbc:mysql://10.10.28.5:3306/wj_test?useUnicode=true&characterEncoding=UTF-8&tinyInt1isBit=false");
        info.setDbNames("wj_test");
        info.setUsername("cifuser");
        info.setPassword("cifuser@123");
        info.setType("com.alibaba.druid.pool.DruidDataSource");

        p = createDbConnection(info, DefaultDriverClassKey, DefaultDbTypeClassKey, null);
    }

    @Autowired
    PlatformJobService platformJobService;

    @ResponseBody
    @GetMapping("/patformJob/get")
    public String get(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @RequestParam(value = "caseName", defaultValue = "") String jobName) {
        List<PlatformJob> platformJobList;
        try {
            platformJobList = platformJobService.get(pageNum, pageSize, jobName);
            PageInfo<PlatformJob> pageInfo = new PageInfo<>(platformJobList);
            pageInfo.setCode(200);
            return JSON.toJSONString(pageInfo);
        } catch (Exception e) {
            List<PlatformJob> errorList = Lists.newArrayList();
            PlatformJob platformJob = new PlatformJob();
            errorList.add(platformJob);
            PageInfo<PlatformJob> pageInfo = new PageInfo<>();
            pageInfo.setCode(500);
            pageInfo.setData(errorList);
            return JSON.toJSONString(pageInfo);
        }
    }

    /**
     * 获取所有caseName集合
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/patformJob/findCount")
    public int findCount() {
        int con = 0;
        try {
            con = platformJobService.findCount();
        } catch (NullPointerException n) {
            con = 1;
        }
        return con;
    }


    @ResponseBody
    @PostMapping("/patformJob/start")
    public String start(@RequestBody PlatformJob platformJob) {
//        platformJobService.insert(platformJob);
        TestNG testNG = new TestNG();
        List<String> suites = com.beust.jcommander.internal.Lists.newArrayList();
        suites.add("./testNG.xml");
        testNG.setTestSuites(suites);
        testNG.run();
        return "";
    }


    //    @Test(dataProvider = "file_case_data", dataProviderClass = PlatformJobController.class, threadPoolSize = 1, invocationCount = 1)
    @Test(dataProvider = "file_case_data", dataProviderClass = PlatformJobController.class)
    public void testMethod(String requestBody) {

        HttpClient client = new DefaultHttpClient();
        String key = requestBody.split("#####")[0];
        String vaule = requestBody.split("#####")[1];
        String uurrll = httpUrl + key.substring(key.lastIndexOf("/") + 1);
        String valueR = vaule.replace("\"3001\"", "\"9999\"");
        JSONObject jsonPre = hci.postJsonArray(client, uurrll, header, valueR).getJSONObject(0);
        Assert.assertTrue(false);

//        JdbcTemplate jt = p.getRight();
//        PlatformJob platformJob = new PlatformJob("jj", "pp");
//        String sql = "insert into wj_test.platform_job(job_name,project_name) VALUES('" + platformJob.getJobName() + "'" +
//                " , '" + platformJob.getProjectName() + "'" + ")";
//        jt.update(sql);


    }

    @DataProvider(name = "file_case_data", parallel = true)
    public static Object[][] getData() {
        List<String> listFile = FileOperation.readFileByLineString(fileCSV);
//        List<String> listFile = getCase(fileCSV);
        Object[][] obj = new Object[listFile.size()][1];
        for (int i = 0; i < listFile.size(); i++) {
            obj[i][0] = listFile.get(i);
        }
        return obj;
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
}
