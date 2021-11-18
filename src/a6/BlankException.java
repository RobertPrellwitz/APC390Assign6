/***************************
 * @author Robert Prellwitz
 * prellw24@uwm.edu
 * APC 390 Fall '21
 **************************/
package a6;

import java.io.FileNotFoundException;
 // custom execption class to capture inadvertent blank entries
public class BlankException extends FileNotFoundException {

    public BlankException () {super("File not found in source folder. Please check the name and " +
            "try again!");}

    public BlankException(String s){super(s);}
}
