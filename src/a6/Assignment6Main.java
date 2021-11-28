/*
 @author Robert Prellwitz
  * prellw24@uwm.edu
  * APC 390 Fall '21
 */
package a6;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * <p> Title: APC 390 Assignment #3 </p>
 * <p> Description: Interactive Table builder. Assign3Main</p>
 * <p> read a text file containing a table of data </p>
 * <p> Copyright: Copyright (c) 2021 </p>
 * <p> Company: UWEX APC </p>
 **/

public class Assignment6Main {

    static final int LOAD = 1, SAVE = 2, ADD_ROW = 3, REMOVE_ROW = 4, FIND_ROW_NAME = 5, FIND_ROW_ID = 6, DISPLAY_TABLE = 7, SORT = 8, QUIT = 9;
    static final int STADIUM = 1, CITY = 2, CITY_STADIUM = 3, END = 4;
    static String tableType = "Stadium";
    static final AbstractTable myStadium = new StadiumTable();
    static final AbstractTable myCity = new CityTable();
    static final CityStadiumTable CityStadium = new CityStadiumTable();
    //static AbstractTable myCityStadium = new CityStadiumTable();
    //static AbstractRow currentRow = null;

    static final String welcomeMessage = "This program implements an interactive table builder.\n"
            + "You have three table types to choose from:\n City data, Stadium data and combined City Stadium data.\n"
            + "You can add new rows or remove rows from tables.\n"
            + "You can also load a table from a file or save a current\n"
            + "table to a file.\n"
            + "You can also view the current contents of the data table.\n" +
              "The City Stadium Table automatically joins the like items\n" +
              "from the already populated City and Stadium tables. ";

    static final String developerMessage = "Program enhanced and improved by:\n"
            + "Robert Prellwitz\n"
            + "APC 390 Fall 21 Semester\n"
            + "November 28, 2021";

    static final String promptMessage = "What would you like to do?\n"
            + "Please enter the number corresponding to the action you would like:\n"
            + "   " + LOAD + ": Load a table from a file\n"
            + "   " + SAVE + ": Save current table to a file\n"
            + "   " + ADD_ROW + ": Add a row to the current table\n"
            + "   " + REMOVE_ROW + ": Remove a row from the current table\n"
            + "   " + FIND_ROW_NAME + ": Find a row by Name\n"
            + "   " + FIND_ROW_ID + ": Find a Row by Id\n"
            + "   " + DISPLAY_TABLE + ": Display current Table.\n"
            + "   " + SORT + ": Sort the Current Table.\n"
            + "   " + QUIT + ": Return to Main menu (exits table controls)\n";

    static final String tableChoice = "There are the two table types available:\n"
            + "City Data & Stadium data.\n"
            + "Please select what you want to work on:\n"
            + "   " + STADIUM + ":  Stadium Data\n"
            + "   " + CITY + ":  City Data\n"
            + "   " + CITY_STADIUM + ":  City Stadium Data\n"
            + "   " + END + ":  End Program";

    public static void main(String[] args)  {
        final ImageIcon dontPanic = new ImageIcon("src/data/dontpanic_1024.jpg");
        Image logo = dontPanic.getImage().getScaledInstance(240, 180,0 );
        final ImageIcon newAlert = new ImageIcon("src/data/Alert.png");
        Image alert = newAlert.getImage().getScaledInstance(100, 70,0 );
        UIManager.put("OptionPane.messageFont",new Font("Courier", Font.BOLD,13));
        JOptionPane.showMessageDialog(null, developerMessage,"Developer Info",JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showMessageDialog(null, welcomeMessage, "Welcome to Table Builder",JOptionPane.PLAIN_MESSAGE);
        AbstractTable myTable;
        int tableSelection = 0;

        while (tableSelection != END) {
            try{
            tableSelection = Integer.parseInt(JOptionPane.showInputDialog(null,tableChoice,"Select Table Type", JOptionPane.PLAIN_MESSAGE));
            if(tableSelection < 1 || tableSelection > 4){
                throw new InputException("Number not in range! ");}
                myTable = tableSet(tableSelection, logo);
                int userSelection = 0;
                while ((userSelection != QUIT) && (tableSelection != END)) {
                    tableType(tableSelection);
                    try{
                    userSelection = Integer.parseInt(JOptionPane.showInputDialog(null, promptMessage,tableType + " Table Controls",JOptionPane.PLAIN_MESSAGE));
                        if(userSelection < 1 || userSelection > 9){
                            throw new InputException("Number not in range! ");}
                    processSelection(myTable, userSelection);}
                    catch (NumberFormatException nfe){
                        alertMessage(nfe, 1,9, alert);
                    }
                }
            }
            catch (NumberFormatException nfe){
                alertMessage(nfe, 1,3,alert);
            }
        }
    }

    private static void tableType (int i) {

        switch (i) {
            case 1:
                tableType = "Stadium";
                break;
            case 2:
                tableType = "City";
                break;
            case 3:
                tableType = "City & Stadium ";
                break;
        }
    }

    private static void alertMessage(NumberFormatException nfe, int i, int j, Image alert){
        JOptionPane.showMessageDialog(null,nfe + "\nYou need to enter a number between " + i + " & " +  j +"\n" +
                "Please Try again!","Invalid Input", JOptionPane.WARNING_MESSAGE,new ImageIcon(alert));
    }

    private static void tableDisplay(AbstractTable myTable){
        JPanel panel;
        panel = myTable.createPanel();
        JOptionPane.showMessageDialog(null,panel,tableType +" Data ", JOptionPane.PLAIN_MESSAGE);
    }
    // Method to determine which Table type the user wants to work with
    private static AbstractTable tableSet(int tableChoice, Image logo)  {
        AbstractTable userTable = null;
            switch (tableChoice) {
                case STADIUM:
                    userTable = myStadium;
                    break;
                case CITY:
                    userTable = myCity;
                    break;
                case CITY_STADIUM:
                    CityStadium.joinTables(myStadium, myCity);
                    userTable = CityStadium;
                    //userTable = myCityStadium;
                    break;
                case END:
                    JOptionPane.showMessageDialog(null, "Thank you for using our program."
                            + "\nREP Computing Services. \n\n DON'T PANIC Productions LLc\n\n","Don't Forget Your Towel",JOptionPane.INFORMATION_MESSAGE, new ImageIcon(logo));
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid Input");
                    break;
            }
        return userTable;
    }

    // Method to manipulate the data table after the user selects a type
    private static void processSelection(AbstractTable mytable, int userSelection)  {
        switch (userSelection) {
            case LOAD:
                String fileName = JOptionPane.showInputDialog("Please enter the name of the text file to load");
                System.out.println("Opening the file '" + fileName + "'.");
                try {
                if (fileName.matches(""))
                    throw new BlankException("Nothing entered - can't search for file");

                    mytable.loadTableFromFile(fileName);
                } catch (IOException exp) {
                    JOptionPane.showMessageDialog(null, "Error loading file: " + exp);
                }
                break;
            case SAVE:
                fileName = JOptionPane.showInputDialog("Please enter the name of the text file to which to save");
                try {
                    if(fileName.matches(""))
                        throw new BlankException("Nothing Entered - can't save file");
                    mytable.saveTableToFile(fileName);
                }catch(IOException exp){
                    JOptionPane.showMessageDialog(null, exp,"Alert",JOptionPane.WARNING_MESSAGE);
                }
                break;
            case ADD_ROW:
                mytable.addRow();
                break;
            case REMOVE_ROW:
                mytable.removeRow();
                break;
            case FIND_ROW_NAME:
                String name = JOptionPane.showInputDialog("Please enter the name in the row you want to find.");
                JOptionPane.showMessageDialog(null, mytable.findRow(name), tableType, JOptionPane.INFORMATION_MESSAGE);
                break;
            case FIND_ROW_ID:
                String message;
                int id = Integer.parseInt( JOptionPane.showInputDialog("Please enter the CityID of the data you want to find."));
                mytable.idSort();
                int row = mytable.idSearch(id);
                if(row<0){message = "Row not Found";}else {message = mytable.getRow(row).toString();}
                JOptionPane.showMessageDialog(null, message, tableType, JOptionPane.INFORMATION_MESSAGE);
                break;
            case DISPLAY_TABLE:
                tableDisplay(mytable);
                break;
            case SORT:
                String title = "Sorting Hat"; String sortType;
                String sortSelect = "Please select the sorting method you prefer\n1 : ID Sort\n2 : Population / Capacity Sort\n3 : Name Sort";
                int sortMethod = Integer.parseInt(JOptionPane.showInputDialog(null,sortSelect,title, JOptionPane.INFORMATION_MESSAGE));
                switch (sortMethod){
                    case 1:
                        mytable.idSort();
                        sortType = "ID Sort Ascending";
                        break;
                    case 2:
                        mytable.sizeSort();
                        sortType = "Capacity / Population Sort Ascending";
                        break;
                    case 3:
                        mytable.nameSort();
                        sortType = "Name sort - Alphabetical";
                        break;
                    default:
                        sortType = "Invalid Selection - Table not sorted.";
                        break;

                }
                //mytable.sizeSort();
                JOptionPane.showMessageDialog(null,sortType,"sorting hat", JOptionPane.INFORMATION_MESSAGE);
                    tableDisplay(mytable);
                break;
            case QUIT:
                JOptionPane.showMessageDialog(null, "Returning to Main Menu");
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid Input");
        }
    }
}
