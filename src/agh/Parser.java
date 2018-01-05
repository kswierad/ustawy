package agh;

import java.io.BufferedReader;

import org.apache.commons.cli.*;

/**
 * Created by Kamil on 2018-01-03.
 */
public class Parser {
    public AbstractContent parse(BufferedReader reader, CommandLine cmd) {
        boolean cons;
        IParser parser;
        if (cmd.getOptionValue("f").matches(".*konstytucja.*")) {
            parser = new ConsParser();
            cons = true;
        } else {
            parser = new UokikParser();
            cons = false;
        }
        String[] rawText = parser.parse(reader);
        ContentBuilder builder = new ContentBuilder(rawText, cons);

        return builder.buildContent();
    }
}
