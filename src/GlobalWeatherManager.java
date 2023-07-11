import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * GlobalWeatherManager ingests data and provides core functionality
 */
public class GlobalWeatherManager implements GlobalWeatherManagerInterface, Iterable<WeatherReading> {
    /**
     * Executes on launch to start the program
     * @param args passed in arguments
     */
    public static void main(String[] args) {
        String filename = "C:\\Users\\Katrina Hill\\Documents\\CSC143 - Java II\\Proj01\\city_temperature.csv";
        GlobalWeatherManager gwm = new GlobalWeatherManager(filename);
    }

    /**
     * Constructor for GlobalWeatherManager
     * @param filename the file of weather readings to parse
     */
    public GlobalWeatherManager(String filename) {
        weatherData = new ArrayList<WeatherReading>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // step 1: split into columns
                String[] columns = line.split(",");
                if(columns.length > 0 && columns[0] != null && !columns[0].equals("Region"))
                {
                    // step 2: parse int from string
                    try{
                        int month = Integer.parseInt(columns[4]);
                        int day = Integer.parseInt(columns[5]);
                        int year = Integer.parseInt(columns[6]);
                        double avgTemperature = Double.parseDouble(columns[7]);

                        // step 3: create WeatherReading object and add it to ArrayList
                        WeatherReading weatherReading = new WeatherReading(columns[0],
                                columns[1],
                                columns[2],
                                columns[3],
                                month,
                                day,
                                year,
                                avgTemperature);
                        weatherData.add(weatherReading);
                    }
                    catch (Exception ex){
                        System.out.println("Error parsing weather data file.");
                    }
                }
            }
            scanner.close();
        } catch (Exception ex) {
            System.out.println("Error opening weather data file.");
            ex.printStackTrace();
        }
    }

    private ArrayList<WeatherReading> weatherData;

    /**
     * Retrieves a count of readings
     *
     * @return count of readings
     */
    @Override
    public int getReadingCount() {
        return weatherData.size();
    }

    /**
     * Retrieves the weather reading at the specified index.
     *
     * @param index the index for the desired reading; must be a valid element index.
     * @return the reading at the specified index.
     */
    @Override
    public WeatherReading getReading(int index) {
        if (!(getReadingCount() - 1 < index || index < 0)) {
            return weatherData.get(index);
        }
        return null;
    }

    /**
     * Retrieves a set of weather readings.
     *
     * @param index the index of the first reading; must be a valid index.
     * @param count the count of readings to potentially include.  Must be at least 1.  Must imply a valid range;
     *              index + count must be less than the total reading count.
     * @return an array of readings.
     */
    @Override
    public WeatherReading[] getReadings(int index, int count) {
        int offset = index - getReadingCount() - 1;
        count = count + offset;
        WeatherReading[] readings = new WeatherReading[count];
        for (int i = 0; i < count; i++) {
            readings[i] = getReading(index + i);
        }
        return readings;
    }

    /**
     * Retrieves a set of weather readings.
     *
     * @param index the index of the first reading.
     * @param count the count of readings to check for potential inclusion.  Must be at least 1.
     *              Must imply a valid range; index + count must be less than the total reading count.
     * @param month the month to filter; must be a valid month (1 to 12).
     * @param day   the day to filter; must be a valid day (1 to 31).
     * @return an array of readings matching the specified criteria.  Length will usually be smaller than
     * the count specified as a parameter, as each year will only have one matching day.
     */
    @Override
    public WeatherReading[] getReadings(int index, int count, int month, int day) {
        WeatherReading[] unfiltered = getReadings(index, count);
        ArrayList<WeatherReading> filtered = new ArrayList<WeatherReading>();
        for (int i = 0; i < unfiltered.length; i++) {
            WeatherReading current = unfiltered[i];
            if (current.month() == month &&
                current.day() == day &&
                month > 0 &&
                month < 13 &&
                day > 0 &&
                day < 32) {
                filtered.add(current);
            }
        }
        return (WeatherReading[]) filtered.toArray();
    }

    /**
     * Retrieves key list statistics for the specified country/state/city.
     * Student note:  okay to use an additional ArrayList in this method.
     *
     * @param country the country of interest; must not be null or blank.
     * @param state   the state of interest; must not be null.
     * @param city    the city of interest; must not be null or blank.
     * @return the list stats for the specified city, or null if not found.
     */
    @Override
    public CityListStats getCityListStats(String country, String state, String city) {
        return null;
    }

    public static int binarySearch(String[] strings, String target) { // tweak these parameters
        int min = 0;
        int max = strings.length - 1;

        while (min <= max) {
            int mid = (max + min) / 2;
            int compare = strings[mid].compareTo(target); // this line enacts a multi-level compare; write another compareTo like method here
            if (compare == 0) {
                return mid;     // found it! Add code that figures out the first occurrence. E.g., for loop that backs up by one each pass
            } else if (compare < 0) {
                min = mid + 1;  // too small
            } else {   // compare > 0
                max = mid - 1;  // too large
            }
        }
        return -1;   // not found
    }

    /**
     * Retrieves an iterator over all weather readings.
     *
     * @return strongly typed iterator for.
     */
    @Override
    public Iterator<WeatherReading> iterator() {
        return null;
    }

    /**
     * Does a linear regression analysis on the data, using x = year and y = temperature.
     * Calculates the slope of a best-fit line using the Least Squares method.   For more information
     * on that method, see <a href="https://www.youtube.com/watch?v=P8hT5nDai6A">...</a>
     * Student note:  okay to use two additional ArrayLists in this method.
     *
     * @param readings array of readings to analyze.  Should typically be readings for a single day over
     *                 a number of years; larger data sets will likely yield better results.  Ignores
     *                 temperature data of -99.0, a default value indicating no temperature data was present.
     *                 Must not be null and must contain at least two readings.
     * @return slope of best-fit line; positive slope indicates increasing temperatures.
     */
    @Override
    public double getTemperatureLinearRegressionSlope(WeatherReading[] readings) {
        return 0;
    }

    /**
     * Calculates the slope of the best-fit line calculated using the Least Squares method.  For more information
     * on that method, see <a href="https://www.youtube.com/watch?v=P8hT5nDai6A">...</a>
     *
     * @param x an array of x values; must not be null and must contain at least two elements.
     * @param y an array of y values; must be the same length as the x array and must not be null.
     * @return the slope of the best-fit line
     */
    @Override
    public double calcLinearRegressionSlope(Integer[] x, Double[] y) {
        return 0;
    }
}
