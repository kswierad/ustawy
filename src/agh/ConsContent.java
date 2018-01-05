package agh;

/**
 * Created by Kamil on 2018-01-05.
 */
public class ConsContent extends AbstractContent {
    public ConsContent(AbstractContent parent, ContentType type) {
        super(parent, type);
    }

    public void makeID(String madeFrom) {
        switch (this.type) {
            case Section:
                this.id = madeFrom.substring(10);
                break;

            case Article:
                this.id = madeFrom.substring(6);
                break;
            case Paragraph:
            case Point:
                this.id = madeFrom.substring(1, 3);
                if (this.id.substring(1).equals(".") || this.id.substring(1).equals(")"))
                    this.id = this.id.substring(0, 1);
                if (this.type == ContentType.Paragraph) this.id = this.id + ".";
                if (this.type == ContentType.Point) this.id = this.id + ")";
                break;
        }
    }

    @Override
    public String toString() {
        switch (this.type) {
            case Header:
                return "KONSTYTUCJA\n" +
                        "RZECZYPOSPOLITEJ POLSKIEJ\n";
            case Section:
                return "Rozdział " + this.id + "\n";
            case SubSection:
                return "";
            case Article:
                return "Art. " + this.id + "\n";
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
