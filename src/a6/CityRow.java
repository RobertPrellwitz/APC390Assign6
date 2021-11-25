/***************************
 * @author Robert Prellwitz
 * prellw24@uwm.edu
 * APC 390 Fall '21
 **************************/
package a6;

public class CityRow extends AbstractRow {

    // member variables
    private final String cityName;
    private final String cityId;
    private final String population;

    // Constructor
    CityRow(String City, String cityId, String cityPop) {
       this.cityName = City;
        this.cityId = cityId;
        this.population = cityPop;
    }

    protected String getCityName() {
        return cityName;
    }

    protected String getCityID() {
        return cityId;
    }

    protected String getCityPop() {
        return population;
    }

    /* This function checks to see if the city being entered already exists in the data table by
     * comparing the City Id (duplicate City names can exist but not duplicate ID) */

    public boolean equal(CityRow row) {
      return  Integer.parseInt(this.getCityID()) == Integer.parseInt(row.getCityID());
    }

    @Override
    public String getId(){

        return cityId;
    }

}
