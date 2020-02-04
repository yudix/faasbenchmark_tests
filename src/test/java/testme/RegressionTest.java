package testme;

import com.google.gson.Gson;
import data.ResultInvocationOverhead;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;

public class RegressionTest {

    public static final String PROJECTS_FAASBENCHMARK = "/home/yudix/IdeaProjects/faasbenchmark";

    @Test
    public void testIncreasingCPULoadLvl1Regression() {
        String previousResultsFilePath = "/home/yudix/IdeaProjects/faasbenchmark/old/results_20200204092216/IncreasingCPULoadLvl1/aws/result.json";
        String testName = "IncreasingCPULoadLvl1";
        runFaasbenchmark("aws", testName);
        String currentResultsFilePath = getLastFaasbenchmarkResultFilePath();
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
        ResultInvocationOverhead previousResults = initResultInvocationOverhead(previousResultsFilePath);
        ResultInvocationOverhead currentResults = initResultInvocationOverhead(currentResultsFilePath);
        Assert.assertTrue(isTestIsTheSame(testName, previousResults, currentResults));
//        Assert.assertTrue(getMinInvocationOverhead(previousResults)<=getMinInvocationOverhead(currentResults));
//        Assert.assertTrue(getMaxInvocationOverhead(previousResults)>=getMaxInvocationOverhead(currentResults));
//        Assert.assertTrue(getAverageInvocationOverhead(previousResults)<=getAverageInvocationOverhead(currentResults));

        return true;
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

    private ResultInvocationOverhead initResultInvocationOverhead(String resultsFilePath) {
        try (Reader reader = new FileReader(resultsFilePath)) {
            return new Gson().fromJson(reader, ResultInvocationOverhead.class);
        } catch (IOException e) {
            throw new RuntimeException("Cannot parse file " + resultsFilePath + "to ResultInvocationOverhead", e);
        }
    }

    private boolean isTestIsTheSame(String testName, ResultInvocationOverhead previous, ResultInvocationOverhead current) {
        return previous.testName.equals(testName) && current.testName.equals(testName);
    }
}