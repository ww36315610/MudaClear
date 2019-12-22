package com.muda.controller.cases;

import com.muda.beans.cases.Case_Mysql;
import com.muda.druidSource.MysqlDynamicDSManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.testng.collections.Lists;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Controller
public class CaseMysqlController {
    private static final Logger logger = LoggerFactory.getLogger(CaseMysqlController.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    MysqlDynamicDSManager mysqlDynamicDSManager;

    /**
     * @param id
     * @return 根据ID查询单条信息
     */
    @RequestMapping("/detail/{id}")
    public Map<String, Object> detail(@PathVariable int id) {
        Map<String, Object> map = null;
        List<Map<String, Object>> list = itemsList();
        map = list.get(id);
        return map;
    }


    /**
     * @return 查询全部信息
     */
    @RequestMapping("/list")
    public List<Map<String, Object>> itemsList() {
        String sql = "select * from case_mysql";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }


    /**
     * 并插入数据库
     *
     * @param items
     * @return
     */
    @RequestMapping("/add")
    public @ResponseBody
    String addItems(Case_Mysql items) {
        Set<String> nameSet = Collections.singleton("agile_portal");
        String sql = "insert into case_mysql(case_name,case_code,status,sortNO) VALUES(?,?,?,?)";
        Object args[] = {items.getCaseName(), items.getCaseCode(), items.getStatus(), items.getSortNO()};
        int temp = jdbcTemplate.update(sql, args);
        if (temp > 0) {
            return "文章新增成功";
        }
        return "新增出现错误";
    }

    /**
     * 【批量】新增数据
     *
     * @param
     * @return
     */
    @RequestMapping("/addBat")
    public @ResponseBody
    String addBatchUpdate(List<Map<String, Object>> caseMysqlList) {
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


    /**
     * @param items
     * @return 删除数据
     */
    @RequestMapping("/del")
    public @ResponseBody
    String delItems(Case_Mysql items) {
        String sql = "delete from case_mysql where id = ?";
        Object args[] = {items.getId()};
        int temp = jdbcTemplate.update(sql, args);
        if (temp > 0) {
            return "文章删除成功";
        }
        return "删除出现错误";
    }

    /**
     * @param items
     * @return 更新操作
     */
    @RequestMapping("/upd")
    public @ResponseBody
    String updItems(Case_Mysql items) {
        String sql = "update case_mysql set case_name = ?,case_code = ? where id = ?";
        Object args[] = {items.getCaseName(), items.getCaseCode(), items.getId()};
        int temp = jdbcTemplate.update(sql, args);
        if (temp > 0) {
            return "文章修改成功";
        }
        return "修改出现错误";
    }






    //***************************************切换数据源***********************************************************************
    /**
     * @return 切换数据源--查询
     */
    @RequestMapping("/druidSouceChange")
    public List<Map<String, Object>> druidSouceChange() {
        logger.info("====This is my jdbcTemplate for{}=====", jdbcTemplate);
        String sql = "SELECT * from agile_portal.ap_deploy_daily LIMIT 1;";
        Set<String> nameSet = Collections.singleton("agile_portal");
        jdbcTemplate = mysqlDynamicDSManager.getJdbcTemplate(nameSet);
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    //***************************************读取excell插入***********************************************************************
    /**
     * 查询excell的数据，并插入数据库
     *
     * @param items
     * @return
     */
    @RequestMapping("/addExcellObject")
    public @ResponseBody
    String addExcellObject(Case_Mysql items) {
        String sql = "insert into case_mysql(case_name,case_code,status,sortNO) VALUES(?,?,?,?)";
        Object args[] = {items.getCaseName(), items.getCaseCode(), items.getStatus(), items.getSortNO()};
        int temp = jdbcTemplate.update(sql, args);
        if (temp > 0) {
            return "文章新增成功";
        }
        return "新增出现错误";
    }


    /**
     * 【批量】新增数据
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/addExcellList")
    public String addExcellList(List<Map<String, Object>> caseMysqlList) {
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
