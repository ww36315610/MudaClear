package com.muda.druidSource.config;

import com.muda.druidSource.beans.DbInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

public class DSPropertiesLoadUtils {
    private static final Logger logger = LoggerFactory.getLogger(DSPropertiesLoadUtils.class);
    private static final String DefaultDbConnName = "cif_utcs";
    public static final String MysqlConfigItemPrefix = "spring.datasource.druid";
    public static final String AllConfigItemPrefix = "spring.datasource";
    public static final String DefaultDriverClassKey = "spring.datasource.druid.driverClass";
    public static final String DefaultDbTypeClassKey = "spring.datasource.druid.type";
    private static final String DBConfigItem[] = {"type", "url", "username", "password", "dbNames", "driverClass","dbType","ips"};
    public static DbConfig parseDbConfig(Properties config) {
        DbConfig dbConfig = new DbConfig();
        Map<String, DbInfo> dbInfoMap = new HashMap<>();
        Map<String, String> commonSettings = new HashMap<>();
        config.forEach((k, y) -> {
            DbInfo info = null;
            String key = k.toString();
            if (key.startsWith(MysqlConfigItemPrefix)) {
                String itemName = null;
                String dbConnName = null;
                for (String configItemName : DBConfigItem) {
                    if (key.endsWith(configItemName)) {
                        itemName = configItemName;
                        dbConnName = key.substring(MysqlConfigItemPrefix.length() + 1, key.indexOf(configItemName) - 1);
                        dbConnName = StringUtils.isBlank(dbConnName) ? DefaultDbConnName : dbConnName;
                        break;
                    }
                }

                if (!StringUtils.isEmpty(dbConnName))
                    if (dbInfoMap.containsKey(dbConnName)) {
                        info = dbInfoMap.get(dbConnName);
                    } else {
                        info = new DbInfo();
                        info.setDbNames(dbConnName);
                        dbInfoMap.put(dbConnName, info);
                    }
                if (!StringUtils.isEmpty(itemName)) {
                    try {
                        String setterName = "set"+itemName.substring(0,1).toUpperCase()+itemName.substring(1);
                        Method setterMethod  = info.getClass().getDeclaredMethod(setterName,new Class[]{String.class});
                        setterMethod.invoke(info,new Object[]{y});
                    } catch (Exception e) {
                        logger.error("Exception",e);
                    }
                } else {
                    commonSettings.put(k.toString(), y.toString());
                }
            } else {
                commonSettings.put(k.toString(), y.toString());
            }
        });


        List<DbInfo> list = new ArrayList<>();
        list.addAll(dbInfoMap.values());
        dbConfig.setList(list);
        dbConfig.setCommonsSettings(commonSettings);
        return dbConfig;
    }
}
