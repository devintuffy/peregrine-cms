package com.peregrine.admin.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.io.StringReader;

/**
 * Created by schaefa on 4/5/17.
 */
public class ProcessContextTracker
    implements ProcessContext
{
    private final Logger log = LoggerFactory.getLogger(ProcessContextTracker.class);

    private int exitCode = 0;
    private String output = "";
    private String error = "";

    @Override
    public int getExitCode() {
        return exitCode;
    }

    public ProcessContextTracker setExitCode(int exitCode) {
        this.exitCode = exitCode;
        return this;
    }

    @Override
    public String getOutput() {
        return output;
    }

    @Override
    public Reader getOutputReader() {
        return new StringReader(output);
    }

    public ProcessContextTracker appendOutput(String text) {
        output += text;
        return this;
    }

    @Override
    public String getError() {
        return error;
    }

    @Override
    public Reader getErrorReader() {
        return new StringReader(error);
    }

    public ProcessContextTracker appendError(String text) {
        if(text != null && !text.isEmpty()) {
            exitCode = 1;
        }
        error += text;
        return this;
    }

    @Override
    public void tearDown() {}
}
