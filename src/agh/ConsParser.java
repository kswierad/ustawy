package agh;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 * Created by Student29 on 2017-12-01.
 */
public class ConsParser implements IParser {
    private ArrayList<String> toAdd = new ArrayList<>();

    public String[] parse(BufferedReader reader){
        try {
            ArrayList<String> rawText = new ArrayList<>();
            ArrayList<String> toAdd = new ArrayList<>();
            while (true) {
                String localLine = reader.readLine();
                if(localLine==null) break;

                if(Pattern.matches("Rozdział.*",localLine)){
                    rawText.add("S"+localLine);
                    rawText.add("T"+reader.readLine());
                } else rawText.add(this.manipulate(localLine));
            }
            return rawText.toArray(new String[0]);
        } catch (IOException ex){
            System.out.println("Problem IO: " + ex);
        }
        return null;

    }

    private char match(String localLine){
        if(Pattern.matches("©.*",localLine)) return 'X';
        if(Pattern.matches("2009-11-16.*",localLine)) return 'X';
        if(Pattern.matches("^Dz\\.U\\. .*$",localLine)) return 'X';
        if(Pattern.matches(".*?pominięt.*?",localLine)) return 'X';
        if(Pattern.matches(".*?uchylon.*?",localLine)) return 'X';
        if(Pattern.matches("Rozdział.*",localLine)) return 'S';
        if(Pattern.matches("Art.+",localLine)) return 'A';
        if(Pattern.matches("\\d+\\..*",localLine)) return 'R';
        if(Pattern.matches("\\d+\\).*",localLine)) return 'P';
        if(Pattern.matches("[a-z]\\).*",localLine)) return 'L';
        if(Pattern.matches("\\p{Lu}\\p{Lu}.*",localLine)) return 'B';
        if(localLine.length()<2) return 'X';

        return 'T';
    }

    private String manipulate(String localLine){
        if(localLine.endsWith("-")){
            localLine=localLine.substring(0,localLine.length()-2);
            if(toAdd.isEmpty()){
                localLine = this.match(localLine)+localLine;
            }
            toAdd.add(localLine);
            return "X";
        } else {
            if(toAdd.isEmpty()){
                return this.match(localLine)+localLine;
            } else {
                StringBuilder builder = new StringBuilder();
                for (String line : toAdd){
                    builder.append(line);
                }
                toAdd.clear();
                builder.append(localLine);
                return builder.toString();
            }
        }

    }
}
