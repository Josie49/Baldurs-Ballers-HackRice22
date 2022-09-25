package database.item;

public class Location {
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private double latit;
    private double longi;

    /**
     * A constructor for a Location.
     *
     * @param address1 the first line of the address
     * @param address2 the second line of the address
     * @param city the name of the city
     * @param state the state
     * @param zip the zip code
     */
    public Location(String address1, String address2, String city, String state, String zip, double latit, double longi) {
        this.longi = longi;
        this.latit = latit;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public double radialDist(Location other){
        int earthRad = 6371;
        double interim = .5 - Math.cos((other.getLatit() - this.getLatit()) * Math.PI/180) + 
                        Math.cos(this.getLatit() * Math.PI/180) * Math.cos(other.getLatit() * Math.PI/180) *
                        (1 - Math.cos((other.longi - this.longi) * Math.PI/180))/2;
        return 2 * earthRad * Math.asin(Math.sqrt(interim));
    }

    public double getLatit() {
        return latit;
    }

    public double getLongi() {
        return longi;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }
}
