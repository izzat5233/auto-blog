package com.izzatalsharif.openai.autoblog.agent.formatter;

import com.izzatalsharif.openai.autoblog.agent.DataFormatter;
import org.springframework.stereotype.Component;

@Component
public class SimpleDataFormatter
        implements DataFormatter<String, String> {

    @Override
    public String formatInput(String input) {
        return input;
    }

    @Override
    public String parseOutput(String output) {
        return output;
    }

}
