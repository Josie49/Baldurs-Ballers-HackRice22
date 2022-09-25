package database.item;

public class Location {
    private double longitude;
    private double latitude;

    /**
     * A constructor for a Location.
     *
     * @param latitude the latitude
     * @param longitude the longitude
     */
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double radialDist(Location other){
        int earthRad = 6371;
        double interim = .5 - Math.cos((other.getLatitude() - this.getLatitude()) * Math.PI/180) + 
                        Math.cos(this.getLatitude() * Math.PI/180) * Math.cos(other.getLatitude() * Math.PI/180) *
                        (1 - Math.cos((other.getLongitude() - this.getLongitude()) * Math.PI/180))/2;
        return 2 * earthRad * Math.asin(Math.sqrt(interim));
    }
}
