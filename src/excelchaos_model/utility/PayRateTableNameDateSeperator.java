package excelchaos_model.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PayRateTableNameDateSeperator {
    /**
     * Returns a String representation of a date extracted from the given table name.
     *
     * @param tableName a String representing the table name.
     * @return a String representing the extracted date, in the format "date"
     */
    public String seperateDateAsString(String tableName) {
        String resultDate;
        String[] temporary;
        temporary = tableName.split("_");
        resultDate = temporary[1];
        return resultDate;
    }

    /**
     * Returns the name extracted from the given table name. The table name is assumed to be in the format "name_date".
     *
     * @param tableName the table name to extract the name from.
     * @return the name extracted from the table name.
     */
    public String seperateName(String tableName) {
        String resultName;
        String[] temporary;
        temporary = tableName.split("_");
        resultName = temporary[0];
        return resultName;
    }

    /**
     * Parses the date from a table name in the format "name_dd.MM.yyyy" to a LocalDate object.
     *
     * @param tableName the name of the table in the format "name_dd.MM.yyyy"
     * @return the date extracted from the table name as a LocalDate object
     */
    public LocalDate seperateDateAsDate(String tableName) {
        LocalDate resultDate;
        String[] temporary;
        temporary = tableName.split("_");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        resultDate = LocalDate.parse(temporary[1], dateTimeFormatter);

        return resultDate;
    }
}
