package com.peregrine.admin.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

/**
 * Created by schaefa on 4/6/17.
 */
public class ProcessRunner {

    private final Logger log = LoggerFactory.getLogger(ProcessRunner.class);

    private File workingDirectory = new File(".");

    public ProcessRunner() {}

    public ProcessRunner(File workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public boolean isWindows() {
        String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        return OS.startsWith("win");
    }

    public ProcessContext execute(List<String> command)
        throws ExternalProcessException
    {
        log.trace("Execute Command: '{}'", command);
        Path dir = null;
        try {
            dir = Files.createTempDirectory(null);
        } catch(IOException e) {
            throw new ExternalProcessException("Failed to create temporary directory to catch output", e);
        }

        final File output = dir.resolve("output.txt").toFile();
        output.deleteOnExit();
        final File error = dir.resolve("error.txt").toFile();
        error.deleteOnExit();
        log.trace("Output File: '{}', Error File: '{}'", output.getPath(), error.getPath());
        ProcessContextReader answer = new ProcessContextReader().setOutputFile(output).setErrorFile(error);

        final ProcessBuilder pb = new ProcessBuilder(command);
        pb.directory(workingDirectory);
        pb.redirectOutput(Redirect.to(output));
        pb.redirectError(Redirect.to(error));

        int exitCode = ProcessContext.NO_EXIT_CODE;
        try {
            final Process p = pb.start();
            try {
                exitCode = p.waitFor();
                log.trace("Exit Code: '{}'", exitCode);
            } catch(final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch(IOException e) {
            log.trace("IO Exception", e);
            throw new ExternalProcessException("Failed to execute process", e).setCommand(command);
        }

        return answer.setExitCode(exitCode);
    }
}
