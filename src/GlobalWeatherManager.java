import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
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
    public static void main(String[] args) throws FileNotFoundException {
        String filename = "C:\\Users\\Katrina Hill\\Documents\\CSC143 - Java II\\Proj01\\city_temperature.csv";
        File file = new File(filename);
        GlobalWeatherManager gwm = new GlobalWeatherManager(file);
    }

    /**
     * Constructor for GlobalWeatherManager
     * @param file the file of weather readings to parse
     */
    public GlobalWeatherManager(File file) throws FileNotFoundException {
        weatherData = new ArrayList<>();
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
                    WeatherReading weatherReading = new WeatherReading(
                            columns[0],
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
        Collections.sort(weatherData);
    }
    /**
     * ArrayList of WeatherReading called weatherData.
     */
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
        WeatherReading[] weatherReadings = new WeatherReading[filtered.size()];
        for (int i = 0; i < filtered.size(); i++) {
            weatherReadings[i] = filtered.get(i);
        }
        return weatherReadings;
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
        int count = 0;
        ArrayList<Integer> years = new ArrayList<Integer>();
        WeatherReading inputReading = new WeatherReading("", country, state, city, 0,0,0,
                0);
        int foundIndex = binarySearch(inputReading);
        if(foundIndex >= 0)
        {
            //find starting index
            int start = foundIndex;
            for(int i = foundIndex; i >= 0; i--){
                WeatherReading current = weatherData.get(i);
                if(!current.city().equals(city)){
                    break;
                }
                start = i;
            }

            for (int i = start; i < weatherData.size(); i++) {
                WeatherReading current = weatherData.get(i);
                if (current.city().equals(city)) {
                    count++;
                    if (!years.contains(current.year())){
                        years.add(current.year());
                    }
                }
                else{
                    break;
                }
            }
            int[] yearsArray = new int[years.size()];
            for(int j = 0; j < years.size(); j++)
            {
                yearsArray[j] = years.get(j);
            }
            return new CityListStats(start, count, yearsArray);
        }
        else {
            return new CityListStats(-1, -1, new int[0]);
        }
    }

    public int binarySearch(WeatherReading reading) {
        int min = 0;
        int max = weatherData.size() - 1;
        while (min <= max) {
            int mid = (max + min) / 2;
            WeatherReading current = weatherData.get(mid);
            int compare = reading.compareTo(current.country(), current.state(), current.city());
            if (compare == 0) {
                System.out.printf("current = Region: %s, Country: %s, State: %s, City: %s\n", current.region(),
                        current.country(), current.state(), current.city());
                return mid;     // found it!
            } else if (compare >  0) {
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
        return weatherData.listIterator();
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
        Integer[] years = new Integer[readings.length];
        Double[] temps = new Double[readings.length];
        for(int i = 0; i < readings.length; i++){
            years[i] = readings[i].year();
            temps[i] = readings[i].avgTemperature();
        }
        return calcLinearRegressionSlope(years, temps);
    }

    /**
     * Calculates the slope of the best-fit line calculated using the Least Squares method.  For more information
     * on that method, see <a href="https://www.youtube.com/watch?v=P8hT5nDai6A">...</a>
     *
     * @param x an array of x values; must not be null and must contain at least two elements.
     * @param y an array of y values; must be the same length as the x array and must not be null.
     * @return the slope of the best-fit line
     * @exception IllegalArgumentException if arrays are not uniform size
     */
    @Override
    public double calcLinearRegressionSlope(Integer[] x, Double[] y) {
        if(x.length != y.length)
            throw new IllegalArgumentException("Arrays must be same size");

        int sumx = 0, sumxx = 0;
        double sumy = 0;
        for(int i = 0; i < x.length; i++){
            sumx += x[i];
            sumxx += x[i] * x[i];
            sumy += y[i];
        }

        double xAvg = (double)sumx / x.length;
        double yAvg = sumy / x.length;
        double xxDif = 0, xyDif = 0;
        for(int i = 0; i < x.length; i++){
            xxDif += (x[i] - xAvg)*(x[i] - xAvg);
            xyDif += (x[i] - xAvg)*(y[i] - yAvg);
        }
        return  xyDif / xxDif;
    }
}
