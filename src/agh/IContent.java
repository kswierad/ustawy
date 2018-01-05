package agh;

import java.io.IOException;

/**
 * Created by Kamil on 2018-01-05.
 */
public interface IContent {
    void addText(String text);

    void addChild(AbstractContent child);

    ContentType getType();

    boolean sameType(AbstractContent anotherContent);

    IContent getParent();

    void makeID(String madeFrom);

    void print(boolean withText);

    void printRange(String firstOne, String lastOne, boolean withText) throws IOException;

    boolean printChild(String id);

    void printSpecificArticle(String whichOne, String toPrint, boolean withText) throws IOException;

    void printSection(String whichOne, boolean withText) throws IOException;
}
