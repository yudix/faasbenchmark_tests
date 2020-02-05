package testme;

import com.google.gson.Gson;
import com.relevantcodes.extentreports.ExtentReports;
import data.Function;
import data.Result;
import data.TestResult;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.io.*;

//import java.util.logging.Logger;

public class RegressionTest {

    public static final String PROJECTS_FAASBENCHMARK = "/home/yudix/IdeaProjects/faasbenchmark";
    static final Logger log = Logger.getLogger(RegressionTest.class);

    @Test
    public void testIncreasingCPULoadLvl1Regression() {
//        Logger.getLogger()
        String previousResultsFilePath = "/home/yudix/IdeaProjects/faasbenchmark/old/results_20200204092216/IncreasingCPULoadLvl1/aws/result.json";
        String testName = "IncreasingCPULoadLvl1";
        runFaasbenchmark("aws", testName);
        String currentResultsFilePath = getLastFaasbenchmarkResultFilePath();
        Assert.assertTrue(isInvocationOverheadIncrease(testName, currentResultsFilePath, previousResultsFilePath));
    }

    @Test
    public void testIncreasingCPULoadLvl1Regression2() {
        String previousResultsFilePath = "/home/yudix/IdeaProjects/faasbenchmark/old/results_20200204092216/IncreasingCPULoadLvl1/aws/result.json";
        String testName = "IncreasingCPULoadLvl1";
//        runFaasbenchmark("aws", testName);
        String currentResultsFilePath = "/home/yudix/IdeaProjects/faasbenchmark/results_20200205220414/IncreasingCPULoadLvl1/aws/result.json";
//                getLastFaasbenchmarkResultFilePath();
        Assert.assertTrue(isInvocationOverheadIncrease(testName, currentResultsFilePath, previousResultsFilePath));
    }

    private String getLastFaasbenchmarkResultFilePath() {
        String command = "ls -d results_*/*/*/**";
        String dirToRunFrom = PROJECTS_FAASBENCHMARK;
        return dirToRunFrom + "/" + executeAndGetResults(command, dirToRunFrom);
    }

    private void runFaasbenchmark(String framework, String testName) {
        String command = "mkdir -p old; [ -r ./results_* ] && mv ./results_* ./old/ || true";
        executeAndGetResults(command, PROJECTS_FAASBENCHMARK);
        command = "go run main.go run " + framework + " " + testName;
        executeAndGetResults(command, PROJECTS_FAASBENCHMARK);
    }

    public boolean isInvocationOverheadIncrease(String testName, String previousResultsFilePath, String currentResultsFilePath) {
        TestResult previousResults = initResultInvocationOverhead(previousResultsFilePath);
        TestResult currentResults = initResultInvocationOverhead(currentResultsFilePath);
        Assert.assertTrue(isTestIsTheSame(testName, previousResults, currentResults));
        Assert.assertTrue(compareMin(previousResults, currentResults));
//        Assert.assertTrue(getMaxInvocationOverhead(previousResults)>=getMaxInvocationOverhead(currentResults));
//        Assert.assertTrue(getAverageInvocationOverhead(previousResults)<=getAverageInvocationOverhead(currentResults));

        return true;
    }

    private boolean compareMin(TestResult previousResults, TestResult currentResults) {
        boolean isCurrentMinGreaterThanPrevious = getMinInvocationOverhead(previousResults) <= getMinInvocationOverhead(currentResults);
        return isCurrentMinGreaterThanPrevious;
    }

    private double getMinInvocationOverhead(TestResult results) {
        double minOverhead = -1;
        for (Function function : results.functions)
            for (Result result : function.results)
                minOverhead = minOverhead == -1 || result.invocationOverhead < minOverhead ? result.invocationOverhead : minOverhead;
        System.out.println("on file: '"+results.filePath+"'");
            System.out.println("min invocation overhead is: " + minOverhead);
        return minOverhead;
    }

    private String executeAndGetResults(String command, String dirToRunFrom) {
        Process process = getProcess(command, dirToRunFrom);
        return readOutput(process);
    }

    private Process getProcess(String command, String dirToRunFrom) {
        try {
            System.out.println("Start execute command: " + command);
            String[] commands = {"/bin/bash", "-c", command};
            ProcessBuilder pb = new ProcessBuilder(commands);
            pb.directory(new File(dirToRunFrom));
            pb.redirectErrorStream(true);
            return pb.start();
        } catch (IOException e) {
            throw new RuntimeException("Init Process with command '" + command + "' failed", e);
        }
    }

    private String readOutput(Process process) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
                System.out.println(line);
            }
            return processOutput(process, output);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String processOutput(Process process, StringBuilder output) throws InterruptedException {
        int exitVal = process.waitFor();
        if (exitVal == 0) {
            System.out.println("Execute command finished successfully!");
            return output.toString();
        } else {
            throw new RuntimeException("Execute command failed");
        }
    }

    private TestResult initResultInvocationOverhead(String resultsFilePath) {
        try (Reader reader = new FileReader(resultsFilePath)) {
            TestResult testResult = new Gson().fromJson(reader, TestResult.class);
            testResult.filePath = resultsFilePath;
            return testResult;
        } catch (IOException e) {
            throw new RuntimeException("Cannot parse file " + resultsFilePath + "to ResultInvocationOverhead", e);
        }
    }

    private boolean isTestIsTheSame(String testName, TestResult previous, TestResult current) {
        ExtentReports extentReports = new ExtentReports("");

        return previous.testName.equals(testName) && current.testName.equals(testName);
    }
}