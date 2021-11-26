/***************************
 * @author Robert Prellwitz
 * prellw24@uwm.edu
 * APC 390 Fall '21
 **************************/
package a6;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

//TODO REMOVE OLD Code that is commented out
public abstract class AbstractTable {
    //Abstract class data members
    private String header;
    private final List<AbstractRow> tableRow = new ArrayList<>();
    private int counter = 0;

    // helper methods for private data members
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setCounter(int count) {
        this.counter = count;
    }

    public void incrementCounter() {
        this.counter = counter + 1;
    }

    public void decrementCounter() {
        this.counter = counter - 1;
    }

    public int getCounter() {
        return counter;
    }

    public void setRow(AbstractRow row) {
        tableRow.add(row);
    }

    public void setRow(AbstractRow row, int spot) {
        tableRow.add(spot, row);
    }

    public AbstractRow getRow(int row) {
        return tableRow.get(row);
    }

    public void deleteRow(int row) {
        tableRow.remove(row);
    }

    // checks to see if the input is a number
    public static boolean isNumeric(String input) {
        boolean number = false;
        if (input == null || input.length() == 0) {
            return number;
        }
        number = input.chars().allMatch(Character::isDigit);
        return number;
    }

    public void removeRow() {
        try {
            int numberToRemoveFromTable = selection();
            int row = idSearch(numberToRemoveFromTable);
            boolean check = true;
            if (row >= 0) {
                deleteRow(row);
                decrementCounter();
                check = false;
            }

            if (check) {
                JOptionPane.showMessageDialog(null, "The Number you entered: " + numberToRemoveFromTable
                                + " did not match a record in the table. \n  Nothing deleted.  Please try again.", "Record Not Found",
                        JOptionPane.WARNING_MESSAGE);
            }

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, nfe, "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int selection() {
        JPanel panel;
        panel = createPanel();
        String input = JOptionPane.showInputDialog(null, panel, "Please Enter the ID to Remove", JOptionPane.PLAIN_MESSAGE);
        if (!isNumeric(input))
            throw new InputException("\nYou must enter a valid ID as a number to delete a data element.");
        return Integer.parseInt(input);
    }

    // sorts that table by ID field
    public void IdSort() {
        AbstractRow one, two;
        int size = counter;
        for (int i = 1; i < counter; i++) {
            one = tableRow.get(i - 1);
            two = tableRow.get(i);

            int first = Integer.parseInt(one.getId());
            int second = Integer.parseInt(two.getId());

            if (second < first) {
                AbstractRow temp = two;
                int location = i;
                do {
                    //tableRow.set(location, two);
                    tableRow.set(location, tableRow.get(location - 1));
                    location--;
                }
                while (location > 0 && Integer.parseInt(tableRow.get(location - 1).getId()) > Integer.parseInt(temp.getId()));
                {
                    tableRow.set(location, temp);
                }
            }
        }
    }

    // sorts the current table by size field (capacity for stadium - population for city etc)
    public void sizeSort() {
        AbstractRow one, two;
        int size = counter;
        for (int i = 1; i < counter; i++) {
            one = tableRow.get(i - 1);
            two = tableRow.get(i);

            double first = Double.parseDouble(one.getSize());
            double second = Double.parseDouble(two.getSize());

            if (second < first) {
                AbstractRow temp = two;
                int location = i;
                do {
                    //tableRow.set(location, two);
                    tableRow.set(location, tableRow.get(location - 1));
                    location--;
                }
                while (location > 0 && Double.parseDouble(tableRow.get(location - 1).getId()) > Double.parseDouble(temp.getId()));
                {
                    tableRow.set(location, temp);
                }
            }
        }
    }

    // sorting
    public void bubbleSort() {
        AbstractRow one, two;
        final int length = counter; // assume locations 0-size-1 are not null!
        for (int size = 0; size < length - 1; size++) {
            for (int index = 0; index < length - 1 - size; index++){
                one = tableRow.get(index);
                two = tableRow.get(index + 1);
            if (Integer.parseInt(one.getId()) > Integer.parseInt(two.getId())) {
                // Swap
               // temp = one;
                tableRow.set(index, one);
                tableRow.set(index + 1, two);
            }
        }
        }
    }



    //sorts the tqble by Name Value
    public void nameSort() {
    }

    public AbstractRow repSearch(int searchId) {
        int size = tableRow.size();
        int first = 0, last = size - 1, middle = first;
        boolean found = false;
        while ((first <= last) && !found) {
            middle = (first + last) / 2;
            int id = Integer.parseInt(tableRow.get(middle).getId());
            if (id == searchId)
                found = true;
            else if (id > searchId)
                last = middle - 1;
            else first = middle + 1;
        }
        return found ? tableRow.get(middle) : null;
    }

    public int idSearch(int searchId) {
        int size = tableRow.size();
        int first = 0, last = size - 1, middle = first;
        boolean found = false;
        while ((first <= last) && !found) {
            middle = (first + last) / 2;
            int id = Integer.parseInt(tableRow.get(middle).getId());
            if (id == searchId)
                found = true;
            else if (id > searchId)
                last = middle - 1;
            else first = middle + 1;
        }
        return found ? middle : -1;
    }

    // creates a panel for displaying data in delete and display options
    public JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setSize(1050, 500);
        JTextArea displayTable = new JTextArea(105, 150);
        displayTable.append(displayData());
        Font font = new Font("Courier", Font.PLAIN, 13);
        displayTable.setFont(font);
        JScrollPane scrollPane = new JScrollPane(displayTable);
        scrollPane.setPreferredSize(new Dimension(1020, 320));
        panel.add(scrollPane);
        return panel;
    }

    //Abstract class methods to be overridden in Concrete Classes
    abstract void addRow();

    //abstract void removeRow();

    abstract String displayData();

    abstract String findRow(String input);

    abstract void loadTableFromFile(String fileName) throws IOException;

    abstract void saveTableToFile(String fileName) throws FileNotFoundException;
}
