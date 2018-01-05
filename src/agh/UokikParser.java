package agh;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 * Created by Student29 on 2017-12-01.
 */
public class UokikParser implements IParser {
    private ArrayList<String> toAdd = new ArrayList<>();

    public String[] parse(BufferedReader reader) {
        try {
            ArrayList<String> rawText = new ArrayList<>();
            while (true) {
                String localLine = reader.readLine();
                if (localLine == null) break;
                if (Pattern.matches("Art\\. [0-9]\\..*", localLine)) {
                    rawText.add('A' + localLine.substring(0, 6));
                    rawText.add(this.manipulate(localLine.substring(8)));
                } else if (Pattern.matches("Art\\. [0-9][0-9a-z]\\..*", localLine)) {
                    rawText.add('A' + localLine.substring(0, 7));
                    rawText.add(this.manipulate(localLine.substring(9)));
                } else if (Pattern.matches("Art\\. [0-9][0-9][0-9a-z]\\..*", localLine)) {
                    rawText.add('A' + localLine.substring(0, 8));
                    rawText.add(this.manipulate(localLine.substring(10)));
                } else if (Pattern.matches("Art\\. [0-9][0-9][0-9][a-z]\\..*", localLine)) {
                    rawText.add('A' + localLine.substring(0, 9));
                    rawText.add(this.manipulate(localLine.substring(11)));
                } else
                    rawText.add(this.manipulate(localLine));


            }
            return rawText.toArray(new String[0]);
        } catch (IOException ex) {
            System.out.println("Problem IO: " + ex);
        }
        return null;

    }

    private char match(String localLine) {
        if (Pattern.matches("©.*", localLine)) return 'X';
        if (Pattern.matches("\\d{4}-\\d{2}-\\d{2}", localLine)) return 'X';
        if (Pattern.matches("^Dz\\.U\\. .*$", localLine)) return 'X';
        if (Pattern.matches(".*?uchylon.*?", localLine)) return 'X';
        if (Pattern.matches(".*?pominięt.*?", localLine)) return 'X';
        if (Pattern.matches("Rozdział.*", localLine)) return 'B';
        if (Pattern.matches(".*USTAWA.*", localLine)) return 'T';
        if (Pattern.matches("\\d+\\..*", localLine)) return 'R';
        if (Pattern.matches("\\d+\\).*", localLine)) return 'P';
        if (Pattern.matches("[a-z]\\).*", localLine)) return 'L';
        if (Pattern.matches("\\p{Lu}\\p{Lu}.*", localLine)) return 'S';
        return 'T';
    }

    private String manipulate(String localLine) {

        if (localLine.endsWith("-")) {
            localLine = localLine.substring(0, localLine.length() - 2);
            if (this.toAdd.isEmpty()) {
                localLine = this.match(localLine) + localLine;
            }
            this.toAdd.add(localLine);
            return "X";
        } else {
            if (this.toAdd.isEmpty()) {
                return this.match(localLine) + localLine;
            } else {
                StringBuilder builder = new StringBuilder();
                for (String line : this.toAdd) {
                    builder.append(line);
                }
                this.toAdd.clear();
                builder.append(localLine);
                return builder.toString();
            }
        }
    }
}
