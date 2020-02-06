package util;

import com.google.gson.Gson;
import data.TestResult;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.DoubleSummaryStatistics;

public class FaasbenchmarkUtil {
    String dirToProjectFaasbenchmark;
    public FaasbenchmarkUtil(String dirToProjectFaasbenchmark) {
        this.dirToProjectFaasbenchmark = dirToProjectFaasbenchmark;
    }

    public String getLastFaasbenchmarkResultFilePath() {
        String getRelevantPathCommand = "ls -d results_*/*/*/**";
        String dirToRunFrom = this.dirToProjectFaasbenchmark;
        return dirToRunFrom + "/" + CLIUtils.executeCommand(getRelevantPathCommand, dirToRunFrom);
    }

    public void runFaasbenchmark(String framework, String testName) {
        moveOldResultsToOldDir();
        String runFaasbenchmarkCommand = "go run main.go run " + framework + " " + testName;
        CLIUtils.executeCommand(runFaasbenchmarkCommand, this.dirToProjectFaasbenchmark);
    }

    public void moveOldResultsToOldDir() {
        String moveOldResultsToOldDirCommand = "mkdir -p old; [ -r ./results_* ] && mv ./results_* ./old/ || true";
        CLIUtils.executeCommand(moveOldResultsToOldDirCommand, this.dirToProjectFaasbenchmark);
    }

    public DoubleSummaryStatistics getStatisticsOfInvocationOverhead(TestResult tr) {
        return tr.functions.stream().
                flatMapToDouble(r -> r.results.stream()
                        .mapToDouble(i -> i.invocationOverhead)).summaryStatistics();
    }

    public TestResult initTestResult(String resultsFilePath) {
        try (Reader reader = new FileReader(resultsFilePath)) {
            TestResult testResult = new Gson().fromJson(reader, TestResult.class);
            testResult.filePath = resultsFilePath;
            testResult.statistics = getStatisticsOfInvocationOverhead(testResult);
            return testResult;
        } catch (IOException e) {
            throw new RuntimeException("Cannot parse file " + resultsFilePath + "to ResultInvocationOverhead", e);
        }
    }

}
