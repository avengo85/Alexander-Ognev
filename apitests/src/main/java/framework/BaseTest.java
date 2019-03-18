package framework;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@Listeners({BaseTest.FailListener.class})
public class BaseTest {
    private static final String REPORTS_PATH = "Reports\\";
    public static String time = new SimpleDateFormat("yyyy.MM.dd 'at' HHmm ss z").format(Calendar.getInstance().getTime());
    private static String filepath = REPORTS_PATH + time + "/";
    private static String testName;
    public static ExtentReports reports;
    public static ExtentTest test;

    @BeforeMethod(alwaysRun = true)
    public static void beforeSuite(Method method) {
        testName = method.getName();
        test = reports.startTest(testName, testName);
        test.log(LogStatus.INFO, testName + " is starting...");
    }

    @BeforeClass
    public synchronized void initialize() {
        reports = new ExtentReports(filepath + "/HtmlReport/index.html", true);
    }

    @AfterMethod
    public void tearDown() {
        reports.endTest(test);
        reports.flush();
    }


    public static class FailListener extends TestListenerAdapter {

        @Override
        public void onTestFailure(ITestResult result) {
            test.log(LogStatus.FAIL, "<span class='label failure'>" + result.getName() + "</span>",
                    "<pre>Error message : " + "\n" + result.getThrowable().getMessage() + "</pre>");
        }
    }
}