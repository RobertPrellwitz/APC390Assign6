/***************************
 * @author Robert Prellwitz
 * prellw24@uwm.edu
 * APC 390 Fall '21
 **************************/
package a6;

// custom exception class that provides detailed information to user on input errors
public class InputException extends NumberFormatException{

    public InputException(){super("Field must be numeric");}

    public InputException(String s){ super(s);}
    }



