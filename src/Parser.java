import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.regex.Pattern;


/**
 * Created by Student29 on 2017-12-01.
 */
public class Parser implements  IParser{


    public String[] parse(BufferedReader reader){
        try {
            ArrayList<String> rawText = new ArrayList<>();
            while (true) {
                String localLine = reader.readLine();
                if(localLine==null) break;
                rawText.add(this.match(localLine)+localLine);
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
        if(Pattern.matches("Rozdział.*",localLine)) return 'S';
        if(Pattern.matches("Art.+",localLine)) return 'A';
        if(Pattern.matches("\\d+\\..*",localLine)) return 'P';
        if(Pattern.matches("\\d+\\).*",localLine)) return 'O';
        return 'T';
    }




}
