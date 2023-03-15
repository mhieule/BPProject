package excelchaos_model.utility;

public class PayRateTableNameStringEditor {


    /**
     * Creates a readable table name for display in a view. Takes the original table name as a parameter
     * and splits it by underscores, concatenating the first two elements with a space in between to form the
     * new table name.
     *
     * @param originalName the original table name to be converted
     * @return a readable table name for display
     */


    public static String createReadableTableNameForView(String originalName) {
        String tableName;
        String[] temporaryStringArray = originalName.split("_");
        tableName = temporaryStringArray[0] + " " + temporaryStringArray[1];


        return tableName;
    }

    /**
     * Reverts a readable table name with spaces to its correct database table name with underscores.
     *
     * @param tableName The readable table name with spaces
     * @return The correct database table name with underscores
     */
    public static String revertToCorrectTableName(String tableName) {
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
