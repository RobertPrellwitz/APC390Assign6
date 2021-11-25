package a6;

public class StadiumRow extends AbstractRow implements Comparable<StadiumRow>   {
    // class data members
    private final String stadiumName;
    private final String stadiumId;
    private final String teamName;
    private final String capacity;

    // class constructor
    public StadiumRow(String stadiumName, String stadiumId, String teamName, String capacity){
        super();
        this.stadiumName = stadiumName;
        this.stadiumId = stadiumId;
        this.capacity = capacity;
        this.teamName = teamName;
    }
    //TODO alternate constructors?

    //Helper Methods
    protected String getStadiumName(){
        return stadiumName;
    }
    protected String getStadiumId(){
        return stadiumId;
    }
    protected String getTeamName(){
        return teamName;
    }
    protected String getCapacity(){
        return capacity;
    }

    /*This checks to see if the Stadium being added is the same as another based on Team & StadiumId
    This allows for a stadium to exist with multiple Teams and have a multiple listings based on a unique ID and Team */

    public boolean equal(StadiumRow row) {
        return this.getTeamName().equals(row.getTeamName()) || Integer.parseInt(this.getStadiumId()) == Integer.parseInt(row.getStadiumId());

    }
    public int compareTo(StadiumRow row){
        return 0;
    }
    @Override
    public String getId(){

        return stadiumId;
    }
//    @Override
//    public int compareTo(AbstractRow row) {
//        return 0;
//    }
}
