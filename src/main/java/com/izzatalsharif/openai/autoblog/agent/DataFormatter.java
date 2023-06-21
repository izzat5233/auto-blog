package com.izzatalsharif.openai.autoblog.agent;

import com.izzatalsharif.openai.autoblog.agent.exception.OpenaiException;

/**
 * The DataFormatter interface defines the contract for classes that handle the formatting of data sent to and received from the OpenAI API.
 *
 * <p>The type parameters I and O represent the input and output types respectively.
 * The input is the data that will be sent to the API, and the output is the data that will be received from the API.
 *
 * <p>Implementations of this interface are responsible for converting the input data into a format that can be sent to the API,
 * and for converting the data received from the API into a format that can be used by the application.
 * Both of which should be determined by the prompt of the chat completion.
 *
 * @param <I> the type of the input data
 * @param <O> the type of the output data
 */
public interface DataFormatter<I, O> {

    /**
     * Formats the input data into a string that can be sent to the OpenAI API.
     *
     * @param input the input data
     * @return a string representation of the input data
     * @throws OpenaiException if an error occurs during formatting
     */
    String formatInput(I input) throws OpenaiException;

    /**
     * Parses the output data received from the OpenAI API into an object of type O.
     *
     * @param output the output data as a string
     * @return an object of type O representing the output data
     * @throws OpenaiException if an error occurs during parsing
     */
    O parseOutput(String output) throws OpenaiException;

}
