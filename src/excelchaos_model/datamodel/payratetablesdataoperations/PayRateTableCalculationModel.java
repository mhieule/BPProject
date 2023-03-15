package excelchaos_model.datamodel.payratetablesdataoperations;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PayRateTableCalculationModel {

    public PayRateTableCalculationModel() {

    }


    /**
     * Parses a table containing salary information and extracts the relevant data fields, returning them as a String array.
     *
     * @param tableContents A String containing the entire table of salary information to be parsed.
     * @return A String array containing the extracted data fields in the following order:
     */
    public String[] pasteWholeTable(String tableContents) {
        String[] splitAtLineBreaks = tableContents.split("\\r?\\n");
        String[] removeStringWithoutMoneyValues = new String[15];
        for (int i = 0; i < removeStringWithoutMoneyValues.length; i++) {
            if (i < 14) {
                removeStringWithoutMoneyValues[i] = splitAtLineBreaks[i];
            } else {
                removeStringWithoutMoneyValues[14] = splitAtLineBreaks[15];
            }

        }
        String[] result = new String[removeStringWithoutMoneyValues.length];
        Pattern pattern = Pattern.compile("Grundentgelt ");
        String grundendgelt = pattern.matcher(removeStringWithoutMoneyValues[0]).replaceAll("");
        result[0] = grundendgelt;

        pattern = Pattern.compile("AV-AG-Anteil, lfd\\. Entgelt ");
        String avag = pattern.matcher(removeStringWithoutMoneyValues[1]).replaceAll("");
        result[1] = avag;

        pattern = Pattern.compile("KV-AG-Anteil, lfd\\. Entgelt ");
        String kvag = pattern.matcher(removeStringWithoutMoneyValues[2]).replaceAll("");
        result[2] = kvag;

        pattern = Pattern.compile("ZusBei AG lfd\\. Entgelt ");
        String zusbei = pattern.matcher(removeStringWithoutMoneyValues[3]).replaceAll("");
        result[3] = zusbei;

        pattern = Pattern.compile("PV-AG-Anteil, lfd\\. Entgelt ");
        String pvag = pattern.matcher(removeStringWithoutMoneyValues[4]).replaceAll("");
        result[4] = pvag;

        pattern = Pattern.compile("RV-AG-Anteil, lfd\\. Entgelt ");
        String rvag = pattern.matcher(removeStringWithoutMoneyValues[5]).replaceAll("");
        result[5] = rvag;

        pattern = Pattern.compile("SV-Umlage U2 \\* ");
        String svumlage = pattern.matcher(removeStringWithoutMoneyValues[6]).replaceAll("");
        result[6] = svumlage;

        pattern = Pattern.compile("Steuern AG \\*\\* ");
        String steuern = pattern.matcher(removeStringWithoutMoneyValues[7]).replaceAll("");
        result[7] = steuern;

        pattern = Pattern.compile("ZV-Sanierungsbeitrag ");
        String zvs = pattern.matcher(removeStringWithoutMoneyValues[8]).replaceAll("");
        result[8] = zvs;

        pattern = Pattern.compile("ZV-Umlage, allgemein ");
        String zvu = pattern.matcher(removeStringWithoutMoneyValues[9]).replaceAll("");
        result[9] = zvu;

        pattern = Pattern.compile("VBL Wiss 4% AG Buchung ");
        String vbl = pattern.matcher(removeStringWithoutMoneyValues[10]).replaceAll("");
        result[10] = vbl;

        pattern = Pattern.compile("mtl\\. Kosten ohne JSZ ");
        String monthlywithout = pattern.matcher(removeStringWithoutMoneyValues[11]).replaceAll("");
        result[11] = monthlywithout;

        pattern = Pattern.compile("JSZ als monatliche Zulage\\*\\*\\* ");
        String monthlyjsz = pattern.matcher(removeStringWithoutMoneyValues[12]).replaceAll("");
        result[12] = monthlyjsz;

        pattern = Pattern.compile("mtl\\. Kosten mit JSZ ");
        String monthlywith = pattern.matcher(removeStringWithoutMoneyValues[13]).replaceAll("");
        result[13] = monthlywith;

        pattern = Pattern.compile("inklusive Jahressonderzahlung ");
        String total = pattern.matcher(removeStringWithoutMoneyValues[14]).replaceAll("");
        result[14] = total;


        return result;

    }

    /**
     * Splits a string into an array of strings that can be used for database insertion.
     * If the input string contains the "%" character, the method splits the string at every occurrence of "%" and then at every occurrence of "€" in the resulting strings.
     * Otherwise, the method splits the string only at every occurrence of "€".
     *
     * @param text the input string to be split
     * @return an array of strings that can be used for database insertion
     */


    public String[] prepareInsertionString(String text) {
        ArrayList<String> resultList = new ArrayList<String>();
        String[] resultString;
        String[] temporaryString;
        if (text.contains("%")) {
            temporaryString = text.split("(?<=%)");
            resultList.add(temporaryString[0]);
            resultString = temporaryString[1].split("(?<=€)");
            resultList.addAll(Arrays.asList(resultString));
            Object[] objectArray = resultList.toArray();
            resultString = Arrays.copyOf(objectArray, objectArray.length, String[].class);
        } else {
            resultString = text.split("(?<=€)");
        }
        return resultString;

    }


}
