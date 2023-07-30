import java.sql.SQLSyntaxErrorException;

/**
 * Record for holding a single weather reading
 * @param region region of weather reading
 * @param country country of weather reading
 * @param state state of weather reading
 * @param city city of weather reading
 * @param month month of weather reading
 * @param day day of weather reading
 * @param year year of weather reading
 * @param avgTemperature average temperature
 */
public record WeatherReading(String region,
                             String country,
                             String state,
                             String city,
                             int month,
                             int day,
                             int year,
                             double avgTemperature) implements Comparable<WeatherReading>
{
    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure {@link Integer#signum
     * signum}{@code (x.compareTo(y)) == -signum(y.compareTo(x))} for
     * all {@code x} and {@code y}.  (This implies that {@code
     * x.compareTo(y)} must throw an exception if and only if {@code
     * y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code
     * x.compareTo(y)==0} implies that {@code signum(x.compareTo(z))
     * == signum(y.compareTo(z))}, for all {@code z}.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @apiNote It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     */
    @Override
    public int compareTo(WeatherReading o) {
        if(this.region().compareTo(o.region()) != 0)
            return  region().compareTo(o.region());
        
        if(this.country().compareTo(o.country()) != 0)
            return  this.country().compareTo(o.country());

        if(this.state().compareTo(o.state()) != 0)
            return  this.state().compareTo(o.state());

        if(this.city().compareTo(o.city()) != 0)
            return  this.city().compareTo(o.city());

        if(Integer.compare(this.month(), o.month()) != 0)
            return  Integer.compare(this.month(), o.month());

        if(Integer.compare(this.day(), o.day()) != 0)
            return  Integer.compare(this.day(), o.day());

        if(Integer.compare(this.year(), o.year()) != 0)
            return  Integer.compare(this.year(), o.year());

        if(Double.compare(this.avgTemperature(), o.avgTemperature()) != 0)
            return  Double.compare(this.avgTemperature(), o.avgTemperature());
        else
            return 0;
    }

    /**
     * Compares this object's country, state, and city fields with the provided fields.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * @param country country string
     * @param state state string
     * @param city city string
     */
    public int compareTo(String country, String state, String city) {
        if (this.country.compareTo(country) > 0)
            return 1;
        else if (this.country.compareTo(country) < 0)
            return -1;

        if (this.state.compareTo(state) > 0)
            return 1;
        else if (this.state.compareTo(state) < 0)
            return -1;

        if (this.city.compareTo(city) > 0)
            return 1;
        else if (this.city.compareTo(city) < 0)
            return -1;

        return 0;
    }

    @Override
    public boolean equals(Object o) {
        try {
            WeatherReading other = (WeatherReading)o;
            if (this.compareTo(other) == 0)
                return true;
            else
                return false;
        }
        catch (Exception ex){
            return false;
        }
    }
}
