import com.muda.beans.cases.Case_Mysql;
import com.muda.controller.cases.CaseMysqlController;
import com.muda.controller.cases.CasesController;
import com.muda.utils.file.ExcellOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.testng.collections.Lists;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootGo.class)
public class SpringBootGoTest {
    private static final Logger logger = LoggerFactory.getLogger(SpringBootGoTest.class);
    @Autowired
    DataSource dataSource;
    @Autowired
    CaseMysqlController caseMysqlController;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    public void contextLoad() throws SQLException {
        System.out.println("=======)))" + dataSource.getClass().getName());
        //也可以自己实例一下
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * from wj_test.platform_job";
        System.out.println("--==]]]"+    jdbcTemplate.queryForList(sql));
        Connection connection = dataSource.getConnection();

        connection.close();
    }

    @Test
    public void addItemsTest() {
        Case_Mysql case_mysql = new Case_Mysql("AA", "aa", 1, 1);
        caseMysqlController.addItems(case_mysql);
    }

    @Test
    public void itemsListTest() {
        List<Map<String, Object>> list = caseMysqlController.itemsList();
        list.forEach(m -> {
            logger.info("===" + m.values());
        });
    }


    /**
     * 测试批量插入
     */
    @Test
    public void addBatchUpdateTest() {
        List<Map<String, Object>> list = caseMysqlController.itemsList();
        caseMysqlController.addBatchUpdate(list);
    }


    @Test
    public void detailTest() {
    }




    //测试切换数据源
    @Test
    public void druidSouceChangeTest() {
        List<Map<String, Object>> list = caseMysqlController.druidSouceChange();
        list.forEach(m -> {
            logger.info("===" + m.values());
        });
    }




    //测试excell读取并入库mysql
    @Test
    public void addExcellList() {
        String excellPath = "/Users/apple/Desktop/log/caseMysql.xlsx";
        String excellSheet = "caseMysql";

        List<Map<String, Object>> list = ExcellOperation.getExcellData(excellPath,excellSheet);
        caseMysqlController.addExcellList(list);

    }


}
