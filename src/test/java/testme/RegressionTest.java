package testme;

import data.TestResult;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import util.FaasbenchmarkUtil;
import util.PropUtil;

import java.util.Properties;
import java.util.logging.Logger;


public class RegressionTest {

    private static final Logger log = Logger.getLogger(RegressionTest.class.getName());

    private static final String TEST_PROPERTIES = "test.properties";
    private static final String TEST_NAME_KEY = "TEST_NAME";
    private static final String COMPARABLE_RESULT_FILE_PATH_KEY = "COMPARABLE_RESULT_FILE_PATH";
    private static final String FRAMEWORK_KEY = "FRAMEWORK";

    private TestResult previousResults;
    private TestResult currentResults;
    private String testName;
    private FaasbenchmarkUtil faasbenchmarkUtil;

    @BeforeClass
    public void runAndGetResults() {
        Properties prop = PropUtil.loadProperties(TEST_PROPERTIES);
        String previousResultsFilePath = prop.getProperty(COMPARABLE_RESULT_FILE_PATH_KEY);
        this.testName = prop.getProperty(TEST_NAME_KEY);
        this.faasbenchmarkUtil = new FaasbenchmarkUtil(TEST_PROPERTIES);
        this.faasbenchmarkUtil.runFaasbenchmark(prop.getProperty(FRAMEWORK_KEY), testName);
        String currentResultsFilePath = this.faasbenchmarkUtil.getLastFaasbenchmarkResultFilePath();
        this.previousResults = this.faasbenchmarkUtil.initTestResult(previousResultsFilePath);
        this.currentResults = this.faasbenchmarkUtil.initTestResult(currentResultsFilePath);
    }

    @Test
    public void testIsTestsTheSame() {
        Assert.assertTrue(previousResults.testName.equals(testName) && currentResults.testName.equals(testName));
    }

    @Test(description = "Test is current invocation overhead MIN is less than the previous tests")
    public void testOverheadMin() {
        double currentMin = currentResults.statistics.getMin();
        double prevMin = previousResults.statistics.getMin();
        log.info("currMin = " + currentMin + ", prevMin = " + prevMin);
        Assert.assertTrue(currentMin < prevMin);
    }

    @Test(description = "Test is current invocation overhead MAX is less than the previous tests")
    public void testOverheadMax() {
        double currMax = currentResults.statistics.getMax();
        double prevMax = previousResults.statistics.getMax();
        log.info("currMax = " + currMax + ", prevMax = " + prevMax);
        Assert.assertTrue(currMax < prevMax);
    }

    @Test(description = "Test is current invocation overhead AVERAGE is less than the previous tests")
    public void testOverheadAverage() {
        double currAverage = currentResults.statistics.getAverage();
        double prevAverage = previousResults.statistics.getAverage();
        log.info("currAverage = " + currAverage + ", prevAverage = " + prevAverage);
        Assert.assertTrue(currAverage < prevAverage);
    }

}