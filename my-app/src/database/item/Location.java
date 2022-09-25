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
}
