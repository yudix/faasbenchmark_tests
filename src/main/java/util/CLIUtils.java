package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CLIUtils {
    public static String executeCommand(String command, String dirToRunFrom) {
        Process process = getProcess(command, dirToRunFrom);
        return readOutput(process);
    }

    private static Process getProcess(String command, String dirToRunFrom) {
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

    private static String readOutput(Process process) {
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

    private static String processOutput(Process process, StringBuilder output) throws InterruptedException {
        int exitVal = process.waitFor();
        if (exitVal == 0) {
            System.out.println("Execute command finished successfully!");
            return output.toString();
        } else {
            throw new RuntimeException("Execute command failed");
        }
    }
}
