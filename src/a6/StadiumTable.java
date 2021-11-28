/***************************
 * @author Robert Prellwitz
 * prellw24@uwm.edu
 * APC 390 Fall '21
 **************************/
package a6;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class StadiumTable extends AbstractTable {

    public StadiumTable() {
        super();
    }

    @Override
    public void loadTableFromFile(String fileName) {
        try {
            Scanner loadFile = new Scanner(new FileReader("src/" + fileName));
            String input;

            int duplicates = 0;
            int errors = 0;
            int records = 0;
            setHeader(loadFile.nextLine());
            while (loadFile.hasNext()) {
                input = loadFile.nextLine();
                boolean isEmpty = (input == null || input.trim().isEmpty());
                try {
                    if (!isEmpty) {

                        // OPTIMIZE - Changed from the original Array to use a List - I think this is less efficient
                        // Professor could you please comment on what is the best Approach

                        List<String> stadium = new ArrayList<>();
                        stadium.add(input.split("\\s*,\\s*", 4)[0]);
                        stadium.add(input.split("\\s*,\\s*", 4)[1]);
                        stadium.add(input.split("\\s*,\\s*", 4)[2]);
                        stadium.add(input.split("\\s*,\\s*", 4)[3]);
                        StadiumRow newRow = new StadiumRow(stadium.get(0), stadium.get(1), stadium.get(2), stadium.get(3));

                        // Refactor - perhaps this is better as originally coded - comment code below is initial implementation

//                              String[] array = input.split("\\s*,\\s*", 4);
//                              StadiumRow newRow = new StadiumRow(array[0], array[1], array[2], array[3]);

                        if (!isNumeric(stadium.get(1)) || !isNumeric(stadium.get(3))) {
                            errors++;
                            throw new InputException();
                        }
                        boolean check = duplicate(newRow);
                        if (!check) {
                            setRow(newRow);
                            incrementCounter();
                            records++;
                        } else {
                            duplicates++;
                        }
                    }
                    if (getCounter() > 1) {
                        insertSort();
                    }
                } catch (Exception ignored) {
                }
            }
            JOptionPane.showMessageDialog(null, "There were  " + duplicates + " duplicate records in  the data file.\n" +
                    " Duplicate items not added to the table.\n" +
                    "There were " + errors + " errors in the data - these were ignored.\n" +
                    records + " items were added to the data table.");
            loadFile.close();
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(null, fnfe + "\nThe file name / path you entered appers to be invalid.");
        } catch (NoSuchElementException nsee) {
            JOptionPane.showMessageDialog(null, nsee);
        } catch (Exception exp) {
            JOptionPane.showMessageDialog(null, exp + "\nThe file does not conform to the required data stucture:");
        }

    }

    @Override
    public void saveTableToFile(String fileName) throws FileNotFoundException {
        PrintWriter fileOutput = new PrintWriter("src/output/" + fileName);
        String header, stadium, stadiumId, teamName, capacity;
        StadiumRow set;
        if (getHeader() == null) {
            header = "Stadium, Stadium ID, Team Name, Capacity";
        } else {
            header = getHeader();
        }
        fileOutput.println(header);
        for (int i = 0; i < getCounter(); i++) {
            set = (StadiumRow) getRow(i);
            stadium = set.getStadiumName();
            stadiumId = set.getStadiumId();
            teamName = set.getTeamName();
            capacity = set.getCapacity();
            fileOutput.println(stadium + ", " + stadiumId + ", " + teamName + ", " + capacity);
        }
        fileOutput.close();
    }

    // allows user to manually input a data row
    @Override
    public void addRow() {
        try {
            String stadium = JOptionPane.showInputDialog("Please enter the Stadium you want to add to the table");
            String stadiumId = JOptionPane.showInputDialog("Please enter the Id for " + stadium);
            String cityName = JOptionPane.showInputDialog("Please enter the name of the Team that uses the stadium.");
            String capacity = JOptionPane.showInputDialog("Please enter the capacity of the Stadium:");
            StadiumRow newRow = new StadiumRow(stadium, stadiumId, cityName, capacity);
            if (!isNumeric(stadiumId) || !isNumeric(capacity)) {
                JOptionPane.showMessageDialog(null, "Stadium ID and Capacity fields must be integers\n" +
                        "You entered a StadiumId of: " + stadiumId + " and a Capacity number of : " + capacity
                        + "\n Data not added to the table.  Please try again.");
                throw new InputException();
            }
            boolean duplicate = duplicate(newRow);
            if (duplicate) {
                JOptionPane.showMessageDialog(null, "This a duplicate item - data not added");
            } else {
                setRow(newRow);
                incrementCounter();
            }
        } catch (Exception exp) {
            System.out.println(exp);
        }
    }

    //checks to determine if the row item is a duplicate
    public boolean duplicate(StadiumRow newRow) {
        boolean duplicate = false;
        int count = 0;
        int currentTot = getCounter();
        while (!duplicate && count < currentTot) {
            StadiumRow check = (StadiumRow) getRow(count);
            duplicate = check.equal(newRow);
            count++;
        }
        return duplicate;
    }
    @Override
    public int getSortName(){
        int i;
        try {
            i = Integer.parseInt(JOptionPane.showInputDialog("Which Name element do you want to sort on?\n1:  Stadium Name \n2:  Team Name\n\n Default is Stadium"));
        }catch (NumberFormatException nfe){
            i=1;
        }
        if(i < 1 || i > 2){i = 1;}
        return i;
    }

    @Override
    public int getSortSize(){return 1;}
    // searches for a row with matching stadium name
    @Override
    public String findRow(String stadium) {
        String stadiumName = stadium.toLowerCase();
        String row = "";
        StadiumRow set;
        int count = getCounter();
        for (int i = 0; i < count; i++) {
            set = (StadiumRow) getRow(i);
            String check = set.getStadiumName().toLowerCase();
            if (check.contains(stadiumName)) {
                row = "Stadium: " + set.getStadiumName() + "\nTeam: " + set.getTeamName() + "\nStadium Capacity: " + set.getCapacity() + "\nStadium Id: " + set.getStadiumId();
                break;
            } else {
                row = "Sorry " + stadium + " not found!   Sorry!\nPlease try again!";
            }
        }
        return row;
    }

    public StadiumRow search(int id) {
        StadiumRow row = null;
        int count = getCounter();
        for (int i = 0; i < count; i++) {
            row = (StadiumRow) getRow(i);
            if (id == Integer.parseInt(row.getStadiumId())) {
                return row;
            }
        }
        return row;
    }

    // Method to display the current contents of the table
    @Override
    public String displayData() {
        StringBuilder display = new StringBuilder(String.format("\n%-30s%-30s%-30s%-30s", "Stadium Id", "Stadium Name", "Team Name", "Capacity"));
        StadiumRow stadiumRow;
        int count = getCounter();
        for (int i = 0; i < count; i++) {
            stadiumRow = (StadiumRow) getRow(i);
            display.append(String.format("\n%-30s%-30s%-30s%-30s", stadiumRow.getStadiumId(), stadiumRow.getStadiumName(), stadiumRow.getTeamName(),stadiumRow.getCapacity()));
        }
        return display.toString();
    }

    // creates list panel with data to help user determine which item to delete.
    public int selection() {
        JPanel panel;
        panel = createPanel();
        String input = JOptionPane.showInputDialog(null, panel, "Please Enter the Stadium ID to Remove", JOptionPane.PLAIN_MESSAGE);
        if (!isNumeric(input))
            throw new InputException("\nYou must enter the Stadium ID as a number to delete a data element.");
        return Integer.parseInt(input);
    }

}
