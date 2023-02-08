package excelchaos_model.utility;

public class PayRateTableNameDateSeperator {
    //Datum wird im Format 01.02.2023 (Tag,Monat,Jahr zurückgegeben)
    public String seperateDate(String tableName){
        String resultDate;
        String[] temporary;
        temporary = tableName.split("_");
        resultDate = temporary[1];
        return resultDate;
    }

    public String seperateName(String tableName){
        String resultName;
        String[] temporary;
        temporary = tableName.split("_");
        resultName = temporary[0];
        return resultName;
    }
}
