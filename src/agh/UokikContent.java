package agh;

/**
 * Created by Kamil on 2018-01-05.
 */
public class UokikContent extends AbstractContent {
    public UokikContent(AbstractContent parent, ContentType type) {
        super(parent, type);
    }

    public void makeID(String madeFrom) {

        switch (this.type) {
            case Section:
                this.id = madeFrom.substring(7);
                break;
            case SubSection:
                this.id = madeFrom.substring(10);
                break;
            case Article:
                this.id = madeFrom.substring(6);
                this.id = this.id + ".";
                break;
            case Paragraph:
            case Point:
            case Letter:
                this.id = madeFrom.substring(1, 4);
                if (this.id.substring(2).equals(".") || this.id.substring(2).equals(" ") ||
                        this.id.substring(2).equals(")"))
                    this.id = this.id.substring(0, 2);
                if (this.id.substring(1).equals(".") || this.id.substring(1).equals(")"))
                    this.id = this.id.substring(0, 1);
                if (this.type == ContentType.Paragraph)
                    this.id = this.id + ".";
                if (this.type == ContentType.Point || this.type == ContentType.Letter)
                    this.id = this.id + ")";
                break;
        }
    }

    @Override
    public String toString() {
        switch (this.type) {
            case Header:
                return "Ustawa\n" +
                        "o ochronie konkurencji i konsumentów\n";
            case Section:
                return "DZIAŁ " + this.id + "\n";
            case SubSection:
                return "";//"Rozdział " + this.id + "\n";
            case Article:
                return "Art. " + this.id + " ";
            case Paragraph:
                return this.id + " ";
            case Point:
                return this.id + " ";
            case Letter:
                return this.id + " ";
            default:
                return "";
        }

    }
}
