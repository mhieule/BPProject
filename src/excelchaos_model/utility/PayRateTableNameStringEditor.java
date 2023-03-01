package excelchaos_model.utility;

public class PayRateTableNameStringEditor {


    public String createReadableTableNameForView(String originalName) {
        String tableName;
        String[] temporaryStringArray = originalName.split("_");
        tableName = temporaryStringArray[0] + " " + temporaryStringArray[1];


        return tableName;
    }

    public String revertToCorrectTableName(String tableName) {
        String databaseTableName;
        String toReplace = " ";
        String replacement = "_";
        int start = tableName.lastIndexOf(toReplace);

        StringBuilder builder = new StringBuilder();
        builder.append(tableName.substring(0, start));
        builder.append(replacement);
        builder.append(tableName.substring(start + toReplace.length()));

        databaseTableName = builder.toString();

        return databaseTableName;
    }
}
