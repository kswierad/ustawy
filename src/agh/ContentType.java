package agh;

/**
 * Created by Kamil on 2017-12-08.
 */
public enum ContentType {
    Header,
    Section,
    SubSection,
    Article,
    Paragraph,
    Point,
    Letter;

    @Override
    public String toString(){
        switch(this){
            case Header: return "H";
            case Section: return "S";
            case SubSection: return "B";
            case Article: return "A";
            case Paragraph: return "R";
            case Point: return "P";
            case Letter: return "L";
            default: return null;
        }
    }

    public boolean lowerType(ContentType type){
        switch(this){
            case Header: return type==Header;
            case Section: return (type==Header||type==Section);
            case SubSection:
                switch (type){
                    case Header:
                    case Section:
                    case SubSection:
                        return true;
                    case Article:
                    case Paragraph:
                    case Point:
                    case Letter:
                        return false;
                    default: return false;
                }
            case Article:
                switch (type){
                    case Header:
                    case Section:
                    case SubSection:
                    case Article:
                        return true;
                    case Paragraph:
                    case Point:
                    case Letter:
                        return false;
                    default: return false;
                }
            case Paragraph:
                switch (type){
                    case Header:
                    case Section:
                    case SubSection:
                    case Article:
                    case Paragraph:
                        return true;
                    case Point:
                    case Letter:
                        return false;
                    default: return false;
                }
            case Point:
                switch (type){
                    case Header:
                    case Section:
                    case SubSection:
                    case Article:
                    case Paragraph:
                    case Point:
                        return true;
                    case Letter:
                        return false;
                    default: return false;
                }
            case Letter: return true;
            default: return false;
        }
    }
    public Contents findParent(Contents node){
        while(!(!node.getType().lowerType(this)||node.getParent()==null)){
            node=node.getParent();
        }
        return node;
    }
}
