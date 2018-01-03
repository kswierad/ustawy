package agh;

import org.apache.commons.cli.CommandLine;

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
                System.out.println("Unsupported mode !\n");
                return;
        }
        if(cmd.hasOption("w")) {
            parsedDocument.print(showContent);
            return;
        }

        String specificArticle = cmd.getOptionValue("a");
        if (specificArticle!=null) {
            if (!showContent) {
                System.out.println(" unsupported mode!");
                return;
            }
            if (specificArticle.indexOf('-')==-1) {
                System.out.println("\n Printing: " + specificArticle);
                String args[] = specificArticle.split(", ");
                //System.out.print(args[0]+" "+args[args.length-1]+" | ");
                parsedDocument.printSpecificArticle(args[0]+" ",args[args.length-1],showContent);
            } else {
                System.out.println("\nPrinting range: " + specificArticle);
                String [] range = specificArticle.split("-");
                //System.out.print(range[0]+" "+range[1]+" | ");
                parsedDocument.printRange(range[0]+" ",range[1]+" ",showContent);
            }
            return;
        }

        String specificSection = cmd.getOptionValue("s");
        if (specificSection!=null) {
            if (showContent) {
                System.out.println("\n printing content of : " + specificSection);

            } else {
                System.out.println("\n printing table of : " + specificSection);
            }

            return;
        }

    }

}
