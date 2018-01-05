package agh;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Kamil on 2018-01-05.
 */
public abstract class AbstractContent implements IContent {

    protected AbstractContent parent;
    protected ContentType type;
    protected LinkedList<AbstractContent> children = new LinkedList<>();
    protected String id;
    protected String text;
    protected LinkedList<AbstractContent> articles = new LinkedList<>();


    public AbstractContent(AbstractContent parent, ContentType type) {//, String id){
        this.parent = parent;
        this.type = type;
        this.text = "";
    }

    public abstract void makeID(String madeFrom);

    public void addText(String text) {
        this.text = this.text + text;
    }

    public void addChild(AbstractContent child) {
        this.children.add(child);
        if (child.type == ContentType.Article) {
            AbstractContent iterator = this;
            while (iterator.parent != null) iterator = iterator.parent;
            iterator.articles.add(child);
        }

    }

    public ContentType getType() {
        return this.type;
    }

    public boolean sameType(AbstractContent anotherContent) {
        return this.type == anotherContent.type;
    }

    public AbstractContent getParent() {
        return parent;
    }


    public void print(boolean withText) {
        if (withText) {
            System.out.print(this + this.text);
            for (AbstractContent child : this.children) {
                child.print(withText);
            }
        } else {
            if (ContentType.SubSection.lowerType(this.type)) {
                if (ContentType.Header == this.type) System.out.print(this);
                else System.out.print(this + this.text);
                for (AbstractContent child : this.children) {
                    child.print(withText);
                }
            }
        }
    }

    public void printRange(String firstOne, String lastOne, boolean withText) throws IOException {
        int i = 0;
        AbstractContent iterator = this.articles.get(0);
        if (this.cmpNumerals(firstOne, articles.getLast().id) > 0 ||
                this.cmpNumerals(lastOne, articles.getLast().id) > 0 ||
                this.cmpNumerals(firstOne, lastOne) < 0) throw new IOException("Incorrect Range!");
        while (i < this.articles.size() && this.cmpNumerals(iterator.id, firstOne) < 0) {
            iterator = this.articles.get(i);
            i++;
        }
        while (i < this.articles.size() && this.cmpNumerals(iterator.id, lastOne) < 0) {
            iterator.print(withText);
            iterator = this.articles.get(i);
            i++;
        }
        if (this.cmpNumerals(iterator.id, lastOne) == 0) iterator.print(withText);

    }

    public boolean printChild(String id) {
        if (this.children.isEmpty()) return false;
        int i = 0;
        AbstractContent iterator = this.children.get(i);
        while (i < this.children.size() && this.cmpNumerals(iterator.id, id) != 0) {
            iterator = this.children.get(i);
            i++;
        }
        if (i < this.children.size()) {
            System.out.print(iterator + iterator.text);
            return true;
        }
        for (AbstractContent child : children) {
            if (child.printChild(id)) return true;
        }
        return false;
    }

    public void printSpecificArticle(String whichOne, String toPrint, boolean withContents) throws IOException {
        int i = 0;

        AbstractContent iterator = this.articles.get(0);
        while (i < this.articles.size() && this.cmpNumerals(iterator.id, whichOne) != 0) {
            iterator = this.articles.get(i);
            i++;
        }
        if (this.cmpNumerals(iterator.id,whichOne)==0) {
            iterator.print(withContents);
            return;
        }
        if (iterator.printChild(toPrint)) return;

        boolean flag = true;
        for (AbstractContent child : iterator.children) {
            flag = !child.printChild(toPrint);
        }
        if (flag) throw new IOException("Couldn't find given Article/Paragraph/Point/Letter");
    }

    public void printSection(String whichOne, boolean withContents) throws IOException {
        if (whichOne.matches("Rozdział.*")) {
            for (AbstractContent child : children)
                if (child.id.equals(whichOne.substring(9))) {
                    child.print(withContents);
                    return;
                }
            throw new IOException("Couldn't find given Section/Subsection");
        }
        if (whichOne.matches("DZIAŁ .{1,5}")) {
            for (AbstractContent child : children) {
                if (child.id.equals(whichOne.substring(6))) {
                    child.print(withContents);
                    return;
                }
            }
            throw new IOException("Couldn't find given Section/Subsection");
        }
        String[] tmp = whichOne.split(", ");
        for (AbstractContent child : children) {
            if (child.toString().startsWith("DZIAŁ")) {
                for (AbstractContent childsChild : child.children) {
                    if (childsChild.type == ContentType.SubSection &&
                            childsChild.id.equals(tmp[1].substring(9))) {
                        childsChild.print(withContents);
                        return;
                    }
                }
            }
        }
        throw new IOException("Couldn't find given Section/Subsection");
    }

    private int cmpNumerals(String s1, String s2) {
        String[] str1 = this.trim(s1);
        String[] str2 = this.trim(s2);
        if (Integer.parseInt(str1[0]) == Integer.parseInt(str2[0])) return str1[1].compareTo(str2[1]);
        return Integer.parseInt(str1[0]) - Integer.parseInt(str2[0]);
    }

    private String[] trim(String string) {
        if (string.startsWith("Art")) {
            string = string.substring(3);
        }
        StringBuilder letterBuilder = new StringBuilder();
        StringBuilder digitBuilder = new StringBuilder();
        int j = 0;
        while (j < string.length()) {
            if (Character.isLetter(string.charAt(j)))
                letterBuilder.append(string.charAt(j));
            if (Character.isDigit(string.charAt(j)))
                digitBuilder.append(string.charAt(j));
            j++;
        }
        return new String[]{digitBuilder.toString(), letterBuilder.toString()};
    }
}
