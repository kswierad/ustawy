package agh;

/**
 * Created by Kamil on 2018-01-02.
 */
public class ContentBuilder {
    private final String[] rawText;
    boolean cons;

    public ContentBuilder(String[] rawText,boolean cons) {
        this.rawText = rawText;
        this.cons=cons;
    }

    public Contents buildContent(){
        Contents document = new Contents(null
                , ContentType.Header,this.cons);
        Contents lastContent = document;
        for(String line : this.rawText){
            Contents tmp;
            String[] lines;
            switch (line.charAt(0)) {
                case 'X': break;
                case 'S':

                    lastContent = new Contents(document, ContentType.Section, this.cons);
                    lastContent.makeId(line,this.cons);
                    document.addChild(lastContent);
                    break;
                case 'A':
                    lastContent = ContentType.Article.findParent(lastContent);
                    tmp = new Contents(lastContent, ContentType.Article, this.cons
                    );
                    tmp.makeId(line,this.cons);
                    lastContent.addChild(tmp);
                    lastContent = tmp;
                    break;
                case 'P':
                case 'R':
                case 'L':
                    if(line.charAt(0)=='P'){
                        lines = line.split("\\) ");
                        lastContent = ContentType.Point.findParent(lastContent);
                        tmp = new Contents(lastContent, ContentType.Point, this.cons);
                        tmp.makeId(line,this.cons);
                    } else if(line.charAt(0)=='L') {
                        lines = line.split("\\) ");
                        lastContent = ContentType.Letter.findParent(lastContent);
                        tmp = new Contents(lastContent, ContentType.Letter, this.cons);
                        tmp.makeId(line,this.cons);
                    } else {
                        lines = line.split("\\. ");
                        lastContent = ContentType.Paragraph.findParent(lastContent);
                        tmp = new Contents(lastContent, ContentType.Paragraph, this.cons);
                        tmp.makeId(line,this.cons);
                    }


                    lastContent.addChild(tmp);
                    for (int i = 1; i < lines.length ; i++ ) tmp.addText(lines[i]);
                    tmp.addText("\n");
                    lastContent = tmp;
                    break;
                case 'B':
                    if(lastContent.sameType(document)) break;
                    lastContent = ContentType.SubSection.findParent(lastContent);
                    tmp = new Contents(lastContent, ContentType.SubSection, this.cons);
                    tmp.makeId(line,this.cons);
                    tmp.addText(line.substring(1));
                    tmp.addText("\n");
                    lastContent.addChild(tmp);
                    lastContent = tmp;
                    break;
                case 'T':
                    lastContent.addText(line.substring(1));
                    lastContent.addText("\n");
                    break;
                default:
                    //throw new Exception();
            }

        }
        return document;
    }
}
