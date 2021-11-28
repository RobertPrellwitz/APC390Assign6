package a6;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;

public class CityStadiumTable extends AbstractTable {

    public CityStadiumTable() {
        super();
    }

    public void joinTables(AbstractTable stadiumTable, AbstractTable cityTable) {

        cityTable.idSort();
        stadiumTable.idSort();
        int ctSize = cityTable.getCounter();
        int stSize = stadiumTable.getCounter();
        int records = 0;
        for (int ct = 0; ct < ctSize; ct++) {
            CityRow cityRow = (CityRow) cityTable.getRow(ct);
            int cityId = Integer.parseInt(cityRow.getCityID());
            for (int st = 0; st < stSize; st++) {
                StadiumRow stadiumRow = (StadiumRow) stadiumTable.getRow(st);
                int stadiumId = Integer.parseInt(stadiumRow.getStadiumId());
                if (cityId == stadiumId) {
                    CityStadiumRow newRow = new CityStadiumRow(stadiumRow.getStadiumName(), cityRow.getCityID(),
                            stadiumRow.getTeamName(), stadiumRow.getCapacity(), cityRow.getCityName(), cityRow.getCityPop());
                    boolean check = duplicate(newRow);
                    if (!check) {
                        setRow(newRow);
                        incrementCounter();
                        records++;
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(null, "There were " + records + " records" +
                "added to the table through the join function", "Join Operation Results", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public int getSortName(){
        int i;
        try {
            i = Integer.parseInt(JOptionPane.showInputDialog("Which Name element do you want to sort on?\n1:  Stadium Name\n2:  City Name \n3:  Team Name\n\n Default is Stadium"));
        }catch (NumberFormatException nfe){
            i=1;
        }
        if(i < 1 || i > 3){i = 1;}
        return i;
    }

    @Override
    public int getSortSize(){
        int i;
        try {
            i = Integer.parseInt(JOptionPane.showInputDialog("Which Size element do you want to sort on?\n1:  Stadium Capicity\n2:  City Poplulation \n\n Default is Stadium Capacity\n"));
        }catch (NumberFormatException nfe){
            i=1;
        }
        if(i < 1 || i > 2){i = 1;}
        return i;
    }

    @Override
    public void loadTableFromFile(String fileName) throws FileNotFoundException {
    }

    @Override
    public void saveTableToFile(String fileName) throws FileNotFoundException {
        PrintWriter fileOutput = new PrintWriter("src/output/" + fileName);
        String header, stadium, stadiumId, teamName, capacity, city, population;
        CityStadiumRow set;
        if (getHeader() == null) {
            header = "City, Population, City Stadium ID, Stadium, Team Name, Capacity";
        } else {
            header = getHeader();
        }
        fileOutput.println(header);
        for (int i = 0; i < getCounter(); i++) {
            set = (CityStadiumRow) getRow(i);
            stadium = set.getStadiumName();
            stadiumId = set.getCityStadiumId();
            teamName = set.getTeamName();
            capacity = set.getCapacity();
            city = set.getCity();
            population = set.getPopulation();
            fileOutput.println(city + ", " + stadiumId + ", " + population + ", " + stadium + ", " + teamName + ", " + capacity);
        }
        fileOutput.close();
    }

    @Override
    public void addRow() {
        try {
            String stadium = JOptionPane.showInputDialog("Please enter the Stadium you want to add to the table");
            String cityStadiumId = JOptionPane.showInputDialog("Please enter the Id for " + stadium);
            String cityName = JOptionPane.showInputDialog("Please enter the name of the City the stadium resides in.");
            String capacity = JOptionPane.showInputDialog("Please enter the capacity of the Stadium:");
            String teamName = JOptionPane.showInputDialog("Please enter the name of the team that uses " + stadium);
            String population = JOptionPane.showInputDialog("Please enter the population of " + cityName + "in millions.");

            CityStadiumRow newRow = new CityStadiumRow(stadium, cityStadiumId, teamName, capacity, cityName, population);
            if (!isNumeric(cityStadiumId) || !isNumeric(capacity)) {
                JOptionPane.showMessageDialog(null, "Stadium ID and Capacity fields must be integers\n" +
                        "You entered a StadiumId of: " + cityStadiumId + " and a Capacity number of : " + capacity
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

    public boolean duplicate(CityStadiumRow newRow) {
        boolean duplicate = false;
        int count = 0;
        int currentTot = getCounter();
        while (!duplicate && count < currentTot) {
            CityStadiumRow check = (CityStadiumRow) getRow(count);
            duplicate = check.equal(newRow);
            count++;
        }
        return duplicate;
    }

    @Override
    public String displayData() {
        StringBuilder display = new StringBuilder(String.format("\n%-20s%-15s%-15s%-25s%-25s%-25s",
                "City", "Population", "City/Stadium Id", "Stadium Name", "Team Name", "Capacity"));
        CityStadiumRow cityStadiumRow;
        int count = getCounter();
        for (int i = 0; i < count; i++) {
            cityStadiumRow = (CityStadiumRow) getRow(i);
            display.append(String.format("\n%-20s%-15s%-15s%-25s%-25s%-25s",
                    cityStadiumRow.getCity(), cityStadiumRow.getPopulation(), cityStadiumRow.getCityStadiumId(), cityStadiumRow.getStadiumName(),
                    cityStadiumRow.getTeamName(), cityStadiumRow.getCapacity()));
        }
        return display.toString();
    }

    @Override
    public String findRow(String item) {
        StringBuilder display = new StringBuilder(String.format("\n%-20s%-15s%-15s%-25s%-25s%-25s",
                "City", "Population", "City/Stadium Id", "Stadium Name", "Team Name", "Capacity"));
        int count = getCounter(); boolean data = false;
        item = item.toLowerCase();
        String rowInfo = "test";
        CityStadiumRow Row;
        for (int i = 0; i < count; i++) {
            Row = (CityStadiumRow) getRow(i);
            String team = Row.getTeamName().toLowerCase();
            String city = Row.getCity().toLowerCase();
            String stadium = Row.getStadiumName().toLowerCase();
            if (team.contains(item) || city.contains(item) || stadium.contains(item)) {
                display.append(String.format("\n%-20s%-15s%-15s%-25s%-25s%-25s",
                        city, Row.getPopulation(), Row.getCityStadiumId(), stadium,
                        team, Row.getCapacity()));
                rowInfo = display.toString();
                data = true;
            }
        }
        return data ? rowInfo : item + " not found!   Sorry!\nPlease try again!" ;
    }
}

