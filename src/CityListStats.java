/**
 * Record containing weather statistics
 * @param startingIndex starting index
 * @param count count of weather readings
 * @param years array of year values in weather readings
 */
public record CityListStats(int startingIndex, int count, int[] years) { }
