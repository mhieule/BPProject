package excelchaos_model.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PayRateTableNameDateSeperator {
    //Datum wird im Format 01.02.2023 (Tag,Monat,Jahr zurückgegeben)
    public String seperateDateAsString(String tableName) {
        String resultDate;
        String[] temporary;
        temporary = tableName.split("_");
        resultDate = temporary[1];
        return resultDate;
    }

    public String seperateName(String tableName) {
        String resultName;
        String[] temporary;
        temporary = tableName.split("_");
        resultName = temporary[0];
        return resultName;
    }

    public LocalDate seperateDateAsDate(String tableName) {
        LocalDate resultDate;
        String[] temporary;
        temporary = tableName.split("_");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        resultDate = LocalDate.parse(temporary[1], dateTimeFormatter);

        return resultDate;
    }
}
