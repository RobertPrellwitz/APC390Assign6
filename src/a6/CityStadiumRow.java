package a6;

public class CityStadiumRow extends AbstractRow{

    private final String stadiumName;
    private final String Id;
    private final String teamName;
    private final String capacity;
    private final String city;
    private final String population;

    public CityStadiumRow(String stadiumName, String Id, String teamName, String capacity, String city, String population){
        super();
        this.stadiumName = stadiumName;
        this.Id = Id;
        this.teamName = teamName;
        this.capacity = capacity;
        this.city = city;
        this.population = population;
    }

    protected String getStadiumName(){
        return stadiumName;
    }
    protected String getTeamName(){
        return teamName;
    }
    protected String getCityStadiumId() {return Id;}
    protected String getCapacity(){
        return capacity;
    }
    protected String getPopulation() {return population;}
    protected String getCity() {return city;}

    /*This checks to see if the City / Stadium combination being added is the same as another based on City & Stadium Id
    This does not allow for a stadium to exist with multiple Teams  */

    public boolean equal(CityStadiumRow row){
        return this.getCityStadiumId().equals(row.getCityStadiumId());
    }

    @Override
    public String getId(){return Id;}
    @Override
    public String getSize(){return capacity;}

}
