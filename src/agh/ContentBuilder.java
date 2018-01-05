package agh;

/**
 * Created by Kamil on 2018-01-02.
 */
public class ContentBuilder {
    private final String[] rawText;
    private boolean cons;

    public ContentBuilder(String[] rawText,boolean cons) {
        this.rawText = rawText;
        this.cons=cons;
    }

    public AbstractContent buildContent(){
        AbstractContent document;
        if(cons)
            document = new ConsContent(null, ContentType.Header);
        else
            document = new UokikContent(null, ContentType.Header);
        AbstractContent lastContent = document;
        for(String line : this.rawText){
            AbstractContent tmp;
            String[] lines;
            switch (line.charAt(0)) {
                case 'X': break;
                case 'S':
                    if(cons)
                        lastContent = new ConsContent(document, ContentType.Section);
                    else
                        lastContent = new UokikContent(document, ContentType.Section);
                    lastContent.makeID(line);
                    document.addChild(lastContent);
                    break;
                case 'A':
                    lastContent = ContentType.Article.findParentType(lastContent);
                    if(cons)
                        tmp = new ConsContent(lastContent, ContentType.Article);
                    else
                        tmp = new UokikContent(lastContent, ContentType.Article);
                    tmp.makeID(line);
                    lastContent.addChild(tmp);
                    lastContent = tmp;
                    break;
                case 'P':
                case 'R':
                case 'L':
                    if(line.charAt(0)=='P'){
                        lines = line.split("\\) ");
                        lastContent = ContentType.Point.findParentType(lastContent);
                        if(cons)
                            tmp = new ConsContent(lastContent, ContentType.Point);
                        else
                            tmp = new UokikContent(lastContent, ContentType.Point);
                        tmp.makeID(line);
                    } else if(line.charAt(0)=='L') {
                        lines = line.split("\\) ");
                        if(cons)
                            tmp = new ConsContent(lastContent, ContentType.Letter);
                        else
                            tmp = new UokikContent(lastContent, ContentType.Letter);
                        tmp.makeID(line);
                    } else {
                        lines = line.split("\\. ");
                        lastContent = ContentType.Paragraph.findParentType(lastContent);
                        if(cons)
                            tmp = new ConsContent(lastContent, ContentType.Paragraph);
                        else
                            tmp = new UokikContent(lastContent, ContentType.Paragraph);
                        tmp.makeID(line);
                    }
                    lastContent.addChild(tmp);
                    for (int i = 1; i < lines.length ; i++ ) tmp.addText(lines[i]);
                    tmp.addText("\n");
                    lastContent = tmp;
                    break;
                case 'B':
                    if(lastContent.sameType(document)) break;
                    lastContent = ContentType.SubSection.findParentType(lastContent);
                    if(cons)
                        tmp = new ConsContent(lastContent, ContentType.SubSection);
                    else
                        tmp = new UokikContent(lastContent, ContentType.SubSection);
                    tmp.makeID(line);
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

            }

        }
        return document;
    }
}
