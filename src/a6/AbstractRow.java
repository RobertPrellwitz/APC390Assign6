/***************************
 * @author Robert Prellwitz
 * prellw24@uwm.edu
 * APC 390 Fall '21
 **************************/
package a6;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRow {

    private final List<String> Row = new ArrayList<>();

    public void setElement(String item){
        Row.add(item);
    }
    public String getElement(int i){
       String info = Row.get(i);
       return info;
    }
    abstract String getId();
//    public abstract int compareTo(AbstractRow row);
  //  abstract boolean equal(AbstractRow row);

}
