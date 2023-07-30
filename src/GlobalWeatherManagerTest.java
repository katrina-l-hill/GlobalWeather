import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * GlobalWeatherManagerTest class
 */
class GlobalWeatherManagerTest {


    String filename = "C:\\Users\\Katrina Hill\\Documents\\CSC143 - Java II\\Proj01\\city_temperature.csv";
    File file = new File(filename);

    @Test
    public void constructor() throws FileNotFoundException {
        GlobalWeatherManager testGwm = new GlobalWeatherManager(file);
        assertNotNull(testGwm);
    }

    @Test
    public void getReadingCount() throws FileNotFoundException {
        GlobalWeatherManager testGwm = new GlobalWeatherManager(file);
        int test = testGwm.getReadingCount();
        assertEquals(test, 2885064);
    }

    @Test
    public void getReading() throws FileNotFoundException {
        GlobalWeatherManager testGwm = new GlobalWeatherManager(file);
        WeatherReading reading = testGwm.getReading(1000);
        assertNotNull(reading);
        WeatherReading reading2 = testGwm.getReading(-1);
        assertNull(reading2);
    }

    @Test
    public void getReadings() throws FileNotFoundException {
        GlobalWeatherManager testGwm = new GlobalWeatherManager(file);
        assertEquals(8094, testGwm.getReadings(0,2885064, 1,15).length);
        assertNotNull(testGwm.getReadings(0, 2885064));
        assertEquals(2885064, testGwm.getReadings(0, 2885064).length);
    }

    @Test
    public void getCityListStats() throws FileNotFoundException {
        GlobalWeatherManager testGwm = new GlobalWeatherManager(file);
        CityListStats stats = testGwm.getCityListStats("US", "Texas", "El Paso");
        assertEquals(2452468, stats.startingIndex());
        assertEquals(9265, stats.count());
        assertEquals(26, stats.years().length);
    }

    @Test
    public void binarySearch() throws FileNotFoundException {
        GlobalWeatherManager testGwm = new GlobalWeatherManager(file);
        WeatherReading temp = new WeatherReading("", "US", "Texas", "El Paso", 0,0,0,0);
        WeatherReading garbage = new WeatherReading("", "N/A", "N/A", "N/A", 0,0,0,0);
        int index = testGwm.binarySearch(temp);
        assertNotEquals(-1, index);
        index = testGwm.binarySearch(garbage);
        assertEquals(-1, index);
    }

    @Test
    public void getTemperatureLinearRegressionSlope() throws FileNotFoundException {
        GlobalWeatherManager testGwm = new GlobalWeatherManager(file);
        WeatherReading[] temp = testGwm.getReadings(0, 1000);
        double slope = testGwm.getTemperatureLinearRegressionSlope(temp);
        assertNotEquals(0, slope);
    }

    @Test
    public void calcLinearRegressionSlope() throws FileNotFoundException {
        GlobalWeatherManager testGwm = new GlobalWeatherManager(file);
        Integer[] ints = new Integer[10];
        Double[] dubs = new Double[10];
        for(int i = 0; i < 10; i++){
            ints[i] = i * 100;
            dubs[i] = (double)(i * 1000);
        }
        double slope = testGwm.calcLinearRegressionSlope(ints, dubs);
        assertNotEquals(0, slope);
    }

    @Test
    public void testWeatherReadingEquals() {
        WeatherReading reading1 = new WeatherReading("north america", "us", "florida", "orlando", 4, 15, 1975, 71.6);
        WeatherReading reading2 = new WeatherReading("north america", "us", "florida", "orlando", 4, 15, 1975, 71.6);
        WeatherReading reading3 = new WeatherReading("north america", "us", "georgia", "atlanta", 4, 15, 1975, 71.6);
        WeatherReading reading4 = null;
        assertEquals(true, reading1.equals(reading2));
        assertEquals(false, reading3.equals(reading1));
        assertEquals(false, reading1.equals(reading4));
    }

    @Test
    public void testCompareTo(){
        WeatherReading reading1 = new WeatherReading("north america", "us", "florida", "orlando", 4, 15, 1975, 0);
        WeatherReading reading2 = new WeatherReading("north america", "us", "florida", "orlando", 4, 15, 1975, 50);
        WeatherReading reading3 = new WeatherReading("north america", "us", "florida", "orlando", 4, 15, 1975, 0);
        assertEquals(-1, reading1.compareTo(reading2));
        assertEquals(1, reading2.compareTo(reading1));
        assertEquals(0, reading1.compareTo(reading3));
    }

}