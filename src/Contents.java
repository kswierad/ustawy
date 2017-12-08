import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Kamil on 2017-12-08.
 */
public class Contents {

    private Contents parent;
    private ContentType type;
    private List<Contents> children= new ArrayList<Contents>();
    private String number;
    private String text;


    public void Contents(Contents parent, ContentType type, String number, String text){
        this.parent=parent;
        this.type=type;
        this.number=number;
        this.text=text;
    }

    public void addChild(Contents child){
        this.children.add(child);
    }
}
