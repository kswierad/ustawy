import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Student29 on 2017-12-01.
 */
public class TextSystem {

    public static void main(String args[]){
        try (BufferedReader reader = new BufferedReader(
                new FileReader(args[0]))){
                IParser parser =  new Parser();
                String[] rawText = parser.parse(reader);
                for(String str : rawText) System.out.println(str);


        } catch (IOException ex) {
            System.out.println("Problem IO: " + ex);
        }
    }
}
