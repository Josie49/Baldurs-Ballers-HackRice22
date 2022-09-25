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

    /**
     * @param other Another location to get distance from
     * @return The distance in kilometers along the surface of a 
     * theoretically spherical earth between the given points
     */
    public double radialDist(Location other){
        double deg2rad = Math.PI/180;
        int earthRad = 6371;
        double interim = .5 - Math.cos((other.getLatitude() - this.getLatitude()) * deg2rad) / 2 +
                        Math.cos(this.getLatitude() * Math.PI/180) * Math.cos(other.getLatitude() * deg2rad) *
                        (1 - Math.cos((other.getLongitude() - this.getLongitude()) * deg2rad))/2;
        return 2 * earthRad * Math.asin(Math.sqrt(interim));
    }
}
