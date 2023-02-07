package excelchaos_model.utility;

public class PayRateTableNameDateSeperator {
    //Datum wird im Format 01-02-2023 (Tag,Monat,Jahr zurückgegeben)
    public String seperateDate(String tableName){
        String resultDate;
        String[] temporary;
        temporary = tableName.split("_");
        resultDate = temporary[1];
        return resultDate;
    }
}
