package agh;

import org.apache.commons.cli.CommandLine;

import java.io.IOException;

/**
 * Created by Kamil on 2018-01-03.
 */
public class Printer {
    public void print(Contents parsedDocument, CommandLine cmd){
        String mode = cmd.getOptionValue("m");
        boolean showContent;
        switch (mode) {
            case "content":
                showContent = true;
                break;

            case "table":
                showContent = false;
                break;

            default:
                System.out.println("Unsupported mode!");
                return;
        }
        if(cmd.hasOption("w")) {
            parsedDocument.print(showContent);
            return;
        }
        try {
            String specificArticle = cmd.getOptionValue("a");
            if (specificArticle != null) {
                if (!showContent) {
                    System.out.println("Unsupported mode!");
                    return;
                }
                if (specificArticle.indexOf('-') == -1) {
                    System.out.println("\nPrinting: " + specificArticle);
                    String args[] = specificArticle.split(", ");
                    parsedDocument.printSpecificArticle(args[0], args[args.length - 1], showContent);
                } else {
                    System.out.println("\nPrinting range: " + specificArticle);
                    String[] range = specificArticle.split("-");
                    parsedDocument.printRange(range[0] + " ", range[1] + " ", showContent);
                }
                return;
            }

            String specificSection = cmd.getOptionValue("s");
            if (specificSection != null) {
                if (showContent) {
                    System.out.println("\nPrinting content of : " + specificSection);
                    parsedDocument.printSection(specificSection, showContent);
                } else {
                    System.out.println("\nPrinting table of : " + specificSection);
                    parsedDocument.printSection(specificSection, showContent);
                }


            }
        } catch (IOException ex) {
            System.out.println("Problem IO: " + ex);
        }
    }

}
