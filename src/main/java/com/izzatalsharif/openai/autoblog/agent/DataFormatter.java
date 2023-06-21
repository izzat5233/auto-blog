package com.izzatalsharif.openai.autoblog.agent;


import com.izzatalsharif.openai.autoblog.agent.exception.OpenaiException;

public interface DataFormatter<I, O> {

    String formatInput(I input) throws OpenaiException;

    O parseOutput(String output) throws OpenaiException;

    DataFormatter<String, String> STRING_DATA_FORMATTER = new DataFormatter<>() {

        @Override
        public String formatInput(String input) {
            return input;
        }

        @Override
        public String parseOutput(String output) {
            return output;
        }

    };

}
