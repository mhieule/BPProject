package excelchaos_model;

public class PayRateTableStringOperationModel {

    public String[] prepareString(String text){
        String[] resultString;
        text.replaceAll("\\s+","");
        resultString = text.split("(?<=€)");
        return resultString;
    }


}
