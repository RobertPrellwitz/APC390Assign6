/*
 * @author Robert Prellwitz
 * prellw24@uwm.edu
 * APC 390 Fall '21
 */
package a6;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
//import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CityTable extends AbstractTable {

    public CityTable() {
    }

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
                        String[] array = input.split("\\s*,\\s*", 3);
                        CityRow newRow = new CityRow(array[0], array[1], array[2]);
                        if (!isNumeric(array[1]) || !(isNumeric(array[2]) || array[2].matches("^\\d+\\.\\d+"))) {
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
                } catch (Exception ignored) {
                }
                if (getCounter() > 1) {
                    insertSort();
                }
            }
            JOptionPane.showMessageDialog(null, "There were  " + duplicates + " duplicate records in  the data file.\n" +
                    " Duplicate items not added to the table.\n" +
                    "There were " + errors + " errors in the data - these were ignored.\n" +
                    records + " items were added to the data table.");

            loadFile.close();
        } catch (FileNotFoundException fnfe) {
            JOptionPane.showMessageDialog(null, fnfe + "\nThe file name / path you entered appears to be invalid.");
        } catch (NoSuchElementException nsee) {
            JOptionPane.showMessageDialog(null, nsee);
        } catch (Exception exp) {
            JOptionPane.showMessageDialog(null, exp + "\nThe file does not conform to the required data stucture:");
        }
    }

    //saves the current data table to a text file
    public void saveTableToFile(String fileName) throws FileNotFoundException {
        PrintWriter fileOutput = new PrintWriter("src/output/" + fileName);

        String city, cityId, population, header;
        CityRow cityRow;
        if (getHeader() == null) {
            header = "City, City ID, Population in Millions";
        } else {
            header = getHeader();
        }
        fileOutput.println(header);

        for (int i = 0; i < getCounter(); i++) {
            cityRow = (CityRow) getRow(i);
            city = cityRow.getCityName();
            cityId = cityRow.getCityID();
            population = cityRow.getCityPop();
            fileOutput.println(city + ", " + cityId + ", " + population);
        }
        fileOutput.close();
    }

    // allows user to manually add a row of data to the table
    @Override
    public void addRow() {
        String city = JOptionPane.showInputDialog("Please enter the City you want to add to the table");
        String cityId = JOptionPane.showInputDialog("Please enter the Id for " + city);
        String population = JOptionPane.showInputDialog("Please enter the population in millions for " + city);
        CityRow newRow = new CityRow(city, cityId, population);

        if (!isNumeric(cityId) || !(isNumeric(population) || population.matches("^\\d+\\.\\d+"))) {
            JOptionPane.showMessageDialog(null, "City ID must be an integer and Population field must be a number\n"
                    + "Data not added to the table.  Please try again.");
            throw new InputException("City ID should be an integer \n Population should be in the form of x.xx");
        }
        boolean duplicate = duplicate(newRow);
        if (duplicate) {
            JOptionPane.showMessageDialog(null, newRow.getCityName() + " is a duplicate - data can't be added");
        } else {
            setRow(newRow);
            incrementCounter();
        }
    }

    // checks for duplicate items when adding a new data row to the table
    public boolean duplicate(CityRow newRow) {
        boolean duplicate = false;
        int count = 0;
        int currentTot = getCounter();
        while (!duplicate && count < currentTot) {
            CityRow check = (CityRow) getRow(count);
            duplicate = check.equal(newRow);
            count++;
        }
        return duplicate;
    }


    // looks for a data row that has a matching city Name
    public String findRow(String name) {
        name = name.toLowerCase();
        String row = "";
        CityRow array;
        int count = getCounter();
        for (int i = 0; i < count; i++) {
            array = (CityRow) getRow(i);
            String check = array.getCityName().toLowerCase();
            if (check.toLowerCase().contains(name)) {
                row = "City: " + array.getCityName() + " City Id: " + array.getCityID() + " Population: " + array.getCityPop();
                break;
            } else {
                row = name + " not found!   Sorry!\nPlease try again!";
            }
        }
        return row;
    }

    // builds to string of data for view in delete and view functions
    public String displayData() {
        StringBuilder display = new StringBuilder(String.format("\n%-30s%-50s%-50s", "City Id", "City Name", "Population"));
        CityRow cityRow;
        int count = getCounter();
        for (int i = 0; i < count; i++) {
            cityRow = (CityRow) getRow(i);
            display.append(String.format("\n%-30s%-50s%-50s", cityRow.getCityID(), cityRow.getCityName(), cityRow.getCityPop()));
        }
        return display.toString();
    }

    public int getSortName() {
        return 1;
    }
    public int getSortSize() {return 1;}

}
