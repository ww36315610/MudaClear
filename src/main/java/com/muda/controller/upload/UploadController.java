package com.muda.controller.upload;

import com.muda.druidSource.MysqlDynamicDSManager;
import com.muda.utils.file.ExcellOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.testng.collections.Lists;
import org.testng.collections.Maps;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class UploadController {


    public final static String UPLOAD_FILE_PATH = "/Users/apple/Desktop/uploadFile";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ResponseBody
    @RequestMapping(value = "/upload/uploadThings")
    public Map<String, String> uploadThings(@RequestParam("file") MultipartFile file) {
        //如果目录不存在，自动创建文件夹
        File dir = new File(UPLOAD_FILE_PATH);
        if (!dir.exists()) {
            dir.mkdir();
        }
        if (!file.isEmpty()) {
            Map<String, String> resObj = Maps.newHashMap();
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(UPLOAD_FILE_PATH, file.getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                resObj.put("msg", "error");
                resObj.put("code", "1");
                return resObj;
            }
            resObj.put("msg", "ok");
            resObj.put("code", "0");
            resObj.put("fileName", file.getOriginalFilename());
            resObj.put("dir", UPLOAD_FILE_PATH);
//            caseMysql.xlsx";
            String fileName = file.getOriginalFilename();
            addExcellList(UPLOAD_FILE_PATH + "/" + resObj.get("fileName"), fileName.substring(0, fileName.indexOf(".")));
            return resObj;
        } else {
            return null;
        }
    }



    //上传入库方法
    private String addExcellList(String excellPath, String excellSheet) {
        //获取到excell的所有实体
        List<Map<String, Object>> caseMysqlList = ExcellOperation.getExcellData(excellPath, excellSheet);
        //获取到excell的列名
        String keyExcell =ExcellOperation.getRowKeys();
        String keyArr[] = keyExcell.split(",");
        //组装sql
        String sqlStart = "insert into case_mysql(";
        String sqlEnd = ") VALUES(?,?,?,?)";
        String sqlCenter =keyExcell;
        String sql = sqlStart+sqlCenter+sqlEnd;

        List<Object[]> list = Lists.newArrayList();
        caseMysqlList.forEach(m -> {
            Object args[] = new Object[keyArr.length];
            for (int i = 0; i < keyArr.length; i++)
                args[i] = m.get(keyArr[i]);
            System.out.println("|||"+args.toString());
            list.add(args);
        });
        jdbcTemplate.batchUpdate(sql, list);
//        jdbcTemplate.batchUpdate(sql, (BatchPreparedStatementSetter) caseMysqlList);
        return "";
    }

    private String addExcellListBefore(String excellPath,String excellSheet) {
        List<Map<String, Object>> caseMysqlList = ExcellOperation.getExcellData(excellPath,excellSheet);
        String sql = "insert into case_mysql(case_name,case_code,status,sortNO) VALUES(?,?,?,?)";
        List<Object[]> list = Lists.newArrayList();
        caseMysqlList.forEach(m -> {
            Object args[] = {m.get("case_name"), m.get("case_code"), m.get("status"), m.get("sortNO")};
            list.add(args);
        });
        jdbcTemplate.batchUpdate(sql, list);
//        jdbcTemplate.batchUpdate(sql, (BatchPreparedStatementSetter) caseMysqlList);
        return "";
    }
}
