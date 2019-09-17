package com.muda.utils.listenter;

import com.alibaba.druid.pool.DruidDataSource;
import com.muda.beans.platform.job.PlatformJob;
import com.muda.controller.platform.job.PlatformJobController;
import com.muda.druidSource.autoconfigure.DruidDataSourceBuilder;
import com.muda.druidSource.beans.DbInfo;
import com.muda.utils.http.Oauth;
import freemarker.core.AliasTemplateNumberFormatFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.util.*;

public class TestngListener extends TestListenerAdapter {
    private static Log logger = LogFactory.getLog(TestngListener.class);

    public static final String CONFIG = "config.properties";

    static int objCount = 0;
    private static DbInfo info;
    public static JdbcTemplate jt;
    public static Pair<String, JdbcTemplate> p;
    static {
        info = new DbInfo();
        info.setDriverClass("com.mysql.jdbc.Driver");
        info.setUrl("jdbc:mysql://10.10.28.5:3306/wj_test?useUnicode=true&characterEncoding=UTF-8&tinyInt1isBit=false");
        info.setDbNames("wj_test");
        info.setUsername("cifuser");
        info.setPassword("cifuser@123");
        info.setType("com.alibaba.druid.pool.DruidDataSource");

        Object[][] obj = PlatformJobController.getData();
        objCount = obj.length;
        objCount =100;
        p = createDbConnection(info, null, null, null);
        jt = p.getRight();
    }


    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        logger.info(tr.getName() + " Failure");
//        takeScreenShot(tr);
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
        logger.info(tr.getName() + " Skipped");
//        takeScreenShot(tr);
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
        logger.info(tr.getName() + " Success");
        PlatformJob platformJob = new PlatformJob(objCount + "",   "T");
        String sql = "insert into wj_test.platform_job(job_name,project_name) VALUES('" + platformJob.getJobName() + "'" +
                " , '" + platformJob.getProjectName() + "'" + ")";
        jt.update(sql);
    }

    @Override
    public void onTestStart(ITestResult tr) {
        super.onTestStart(tr);
        logger.info(tr.getName() + " Start");
    }


    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);
//
        ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
        Set<Integer> passedTestIds = new HashSet<Integer>();
        for (ITestResult passedTest : testContext.getPassedTests().getAllResults()) {
            logger.info("PassedTests = " + passedTest.getName());
            passedTestIds.add(getId(passedTest));
        }


        Set<Integer> failedTestIds = new HashSet<Integer>();
        for (ITestResult failedTest : testContext.getFailedTests().getAllResults()) {
            logger.info("failedTest = " + failedTest.getName());
            int failedTestId = getId(failedTest);
            if (failedTestIds.contains(failedTestId) || passedTestIds.contains(failedTestId)) {
                testsToBeRemoved.add(failedTest);
            } else {
                failedTestIds.add(failedTestId);
            }
            PlatformJob platformJob = new PlatformJob(objCount + "",   "F");
            String sql = "insert into wj_test.platform_job(job_name,project_name) VALUES('" + platformJob.getJobName() + "'" +
                    " , '" + platformJob.getProjectName() + "'" + ")";
            jt.update(sql);
        }

// finally delete all tests that are marked
        for (Iterator<ITestResult> iterator = testContext.getFailedTests().getAllResults().iterator(); iterator.hasNext(); ) {
            ITestResult testResult = iterator.next();
            if (testsToBeRemoved.contains(testResult)) {
                logger.info("Remove repeat Fail Test: " + testResult.getName());
                iterator.remove();
            }
        }

    }


    private int getId(ITestResult result) {
        int id = result.getTestClass().getName().hashCode();
        id = id + result.getMethod().getMethodName().hashCode();
        id = id + (result.getParameters() != null ? Arrays.hashCode(result.getParameters()) : 0);
        return id;
    }

    /**
     @Override public void onFinish(ITestContext testContext) {
     super.onFinish(testContext);
     Iterator<ITestResult> listOfFailedTests = testContext.getFailedTests().getAllResults().iterator();
     while (listOfFailedTests.hasNext()) {
     ITestResult failedTest = (ITestResult) listOfFailedTests.next();
     ITestNGMethod method = failedTest.getMethod();
     System.err.println(method.getMethodName());
     if (testContext.getFailedTests().getResults(method).size() > 1) {
     listOfFailedTests.remove();
     } else {
     if (testContext.getPassedTests().getResults(method).size() > 0) {
     listOfFailedTests.remove();
     }

     }

     }
     }
     */

    /**
     * 自动截图，保存图片到本地以及html结果文件中
     *
     * @param tr
     */
//    private void takeScreenShot(ITestResult tr) {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
//        String mDateTime = formatter.format(new Date());
//        String fileName = mDateTime + "_" + tr.getName();
//        String filePath = OrangeiOS.driver.getScreenshotAs(fileName);
//        Reporter.setCurrentTestResult(tr);
//        Reporter.log(filePath);
//
////这里实现把图片链接直接输出到结果文件中，通过邮件发送结果则可以直接显示图片
//        Reporter.log("<img src=\"../" + filePath + "\"/>");
//
//    }
    private static Pair<String, JdbcTemplate> createDbConnection(DbInfo dbInfo, String defaultDriverClass, String defaultDsTypeClass, Properties commonSettings) {
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
        return Pair.of(dbInfo.getDbNames(), jdbc);
    }
}