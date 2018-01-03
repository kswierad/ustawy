package agh;

import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Created by Kamil on 2018-01-03.
 */
public abstract class AbstractParser {
    protected ArrayList<String> toAdd = new ArrayList<>();
    public abstract String[] parse(BufferedReader reader);

}
