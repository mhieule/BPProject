package excelchaos_model.datamodel.payratetablesdataoperations;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PayRateTableCalculationModel {

    private String[] stringMoneyValues;
    private String[] stringPercentageValues;

    private String[] stringBonusMoneyValues;
    private BigDecimal[] moneyValues;

    private BigDecimal[] bonusMoneyValues;
    private BigDecimal[] percentageValues;

    private BigDecimal[] monthlyCostWithYearBonus;
    private BigDecimal[] monthlyCostWithYearBonusRounded;

    private BigDecimal[] monthlyCostWithoutYearBonus;
    private BigDecimal[] monthlyCostWithoutYearBonusRounded;

    private BigDecimal[] lastRow;
    private BigDecimal[][] numberResults;

    private BigDecimal[][] roundedResults;


    public PayRateTableCalculationModel() {

    }

    public PayRateTableCalculationModel(String[] baseMoney, String[] percentages, String[] bonusMoney) {
        stringMoneyValues = baseMoney;
        stringBonusMoneyValues = bonusMoney;
        stringPercentageValues = percentages;
    }

    public void doStringOperations() {
        stringPercentageValues = modifyPercentageValuesForCalculation(stringPercentageValues);
        stringMoneyValues = modifyMoneyValuesForCalculation(stringMoneyValues);
        stringBonusMoneyValues = modifyMoneyValuesForCalculation(stringBonusMoneyValues);
        percentageValues = convertPercentageStringsToBigDecimal(stringPercentageValues);
        moneyValues = convertMoneyStringToBigDecimal(stringMoneyValues);
        bonusMoneyValues = convertMoneyStringToBigDecimal(stringBonusMoneyValues);

    }

    public String[][] calculateResults() {
        numberResults = new BigDecimal[percentageValues.length][moneyValues.length];
        roundedResults = new BigDecimal[percentageValues.length][moneyValues.length];
        monthlyCostWithoutYearBonus = new BigDecimal[moneyValues.length];
        monthlyCostWithYearBonus = new BigDecimal[moneyValues.length];
        String[][] stringResults = new String[percentageValues.length][moneyValues.length];
        for (int i = 0; i < percentageValues.length; i++) {
            for (int j = 0; j < moneyValues.length; j++) {
                numberResults[i][j] = moneyValues[j].multiply(percentageValues[i]);
                roundedResults[i][j] = numberResults[i][j].setScale(2, RoundingMode.HALF_EVEN);
                stringResults[i][j] = roundedResults[i][j].toString();

            }
        }
        return stringResults;
    }

    private void initMonthlyCost() {
        for (int i = 0; i < monthlyCostWithoutYearBonus.length; i++) {
            monthlyCostWithoutYearBonus[i] = new BigDecimal(0);

        }
    }

    private void removeMissingValuesFromArray() {
        BigDecimal zero = new BigDecimal(0);
        System.out.println(percentageValues.length);
        System.out.println(moneyValues.length);
        for (int rows = 0; rows < percentageValues.length; rows++) {
            for (int columns = 0; columns < moneyValues.length; columns++) {
                if ((columns % 2 == 0) && ((rows == 7) || (rows == 8))) {
                    numberResults[rows][columns] = zero;

                } else if ((columns % 2 == 1) && (rows == 9)) {
                    numberResults[rows][columns] = zero;
                } else {

                }

            }


        }
    }

    public void calculateMonthlyCostWithoutYearBonus() {
        initMonthlyCost();
        removeMissingValuesFromArray();
        for (int columns = 0; columns < roundedResults[0].length; columns++) {
            for (int rows = 0; rows < roundedResults.length; rows++) {
                monthlyCostWithoutYearBonus[columns] = monthlyCostWithoutYearBonus[columns].add(numberResults[rows][columns]);
            }
            monthlyCostWithoutYearBonus[columns] = monthlyCostWithoutYearBonus[columns].add(moneyValues[columns]);

        }
    }

    public void calculateMonthlyCostWithYearBonus() {
        for (int i = 0; i < monthlyCostWithYearBonus.length; i++) {
            monthlyCostWithYearBonus[i] = monthlyCostWithoutYearBonus[i].add(bonusMoneyValues[i]);
        }
    }

    public void calculateLastRow() {
        lastRow = new BigDecimal[monthlyCostWithYearBonus.length];
        BigDecimal multiplicand = new BigDecimal(12);
        for (int i = 0; i < lastRow.length; i++) {
            lastRow[i] = monthlyCostWithYearBonus[i].multiply(multiplicand);
            lastRow[i] = lastRow[i].setScale(2, RoundingMode.HALF_EVEN);
        }
    }

    public String[] convertLastRowResultsToString() {
        String[] stringResult = new String[lastRow.length];
        for (int i = 0; i < monthlyCostWithoutYearBonus.length; i++) {
            stringResult[i] = lastRow[i].toString();
            stringResult[i] = stringResult[i].replaceAll("\\.", ",");
            stringResult[i] = stringResult[i].concat(" €");
        }
        return stringResult;
    }

    public String[] convertMonthlyCostWithoutYearBonusToString() {
        monthlyCostWithoutYearBonusRounded = new BigDecimal[monthlyCostWithoutYearBonus.length];
        String[] stringResult = new String[monthlyCostWithoutYearBonus.length];
        for (int i = 0; i < monthlyCostWithoutYearBonus.length; i++) {
            monthlyCostWithoutYearBonusRounded[i] = monthlyCostWithoutYearBonus[i].setScale(2, RoundingMode.HALF_EVEN);
            stringResult[i] = monthlyCostWithoutYearBonusRounded[i].toString();
            stringResult[i] = stringResult[i].replaceAll("\\.", ",");
            stringResult[i] = stringResult[i].concat(" €");
        }
        return stringResult;
    }

    public String[] convertMonthlyCostWithYearBonusToString() {
        monthlyCostWithYearBonusRounded = new BigDecimal[monthlyCostWithYearBonus.length];
        String[] stringResult = new String[monthlyCostWithYearBonus.length];
        for (int i = 0; i < monthlyCostWithYearBonus.length; i++) {
            monthlyCostWithYearBonusRounded[i] = monthlyCostWithYearBonus[i].setScale(2, RoundingMode.HALF_EVEN);
            stringResult[i] = monthlyCostWithYearBonusRounded[i].toString();
            stringResult[i] = stringResult[i].replaceAll("\\.", ",");
            stringResult[i] = stringResult[i].concat(" €");
        }
        return stringResult;
    }

    public String[][] rearrangeStringFormat(String[][] stringResults) {
        for (int i = 0; i < stringResults.length; i++) {
            for (int j = 0; j < stringResults[i].length; j++) {
                stringResults[i][j] = stringResults[i][j].replaceAll("\\.", ",");
                stringResults[i][j] = stringResults[i][j].concat(" €");
            }
        }

        return stringResults;
    }

    public String[] pasteWholeTable(String tableContents){
        String[] splitAtLineBreaks = tableContents.split("\\r?\\n");
        String[] removeStringWithoutMoneyValues = new String[15];
        for (int i = 0; i < removeStringWithoutMoneyValues.length; i++) {
            if(i < 14){
                removeStringWithoutMoneyValues[i] = splitAtLineBreaks[i];
            } else {
                removeStringWithoutMoneyValues[14] = splitAtLineBreaks[15];
            }

        }
       /* String[] grundentgelt = removeStringWithoutMoneyValues[0].split("(?<=€)");

        String[] percentSplitter = removeStringWithoutMoneyValues[1].split("(?<=%)");
        String avagPercentPart = percentSplitter[0];
        String[] avagValue = percentSplitter[1].split("(?<=€)");

        percentSplitter = removeStringWithoutMoneyValues[2].split("(?<=%)");
        String kvagPercentPart = percentSplitter[0];
        String[] kvagValue = percentSplitter[1].split("(?<=€)");

        percentSplitter = removeStringWithoutMoneyValues[3].split("(?<=%)");
        String zusbeiPercentPart = percentSplitter[0];
        String[] zusbeiValue = percentSplitter[1].split("(?<=€)");

        percentSplitter = removeStringWithoutMoneyValues[4].split("(?<=%)");
        String pvagPercentPart = percentSplitter[0];
        String[] pvagValue = percentSplitter[1].split("(?<=€)");

        percentSplitter = removeStringWithoutMoneyValues[5].split("(?<=%)");
        String rvagPercentPart = percentSplitter[0];
        String[] rvagValue = percentSplitter[1].split("(?<=€)");

        percentSplitter = removeStringWithoutMoneyValues[6].split("(?<=%)");
        String svumlagePercentPart = percentSplitter[0];
        String[] svumlageValue = percentSplitter[1].split("(?<=€)");

        percentSplitter = removeStringWithoutMoneyValues[7].split("(?<=%)");
        String steuernPercentPart = percentSplitter[0];
        String[] steuernValue = percentSplitter[1].split("(?<=€)");

        percentSplitter = removeStringWithoutMoneyValues[8].split("(?<=%)");
        String ZVSPercentPart = percentSplitter[0];
        String[] ZVSValue = percentSplitter[1].split("(?<=€)");

        percentSplitter = removeStringWithoutMoneyValues[9].split("(?<=%)");
        String ZVUPercentPart = percentSplitter[0];
        String[] ZVUValue = percentSplitter[1].split("(?<=€)");

        percentSplitter = removeStringWithoutMoneyValues[10].split("(?<=%)");
        String VBLPercentPart = percentSplitter[0];
        String[] VBLValue = percentSplitter[1].split("(?<=€)");

        String[] monthlywithout = removeStringWithoutMoneyValues[11].split("(?<=€)");
        String[] monthlyJSZ = removeStringWithoutMoneyValues[12].split("(?<=€)");
        String[] monthlyWith = removeStringWithoutMoneyValues[13].split("(?<=€)");
        String[] total = removeStringWithoutMoneyValues[14].split("(?<=€)");*/

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

    public String[] prepareInsertionString(String text) {
        ArrayList<String> resultList = new ArrayList<String>();
        String[] resultString;
        String[] temporaryString;
        //text = text.replaceAll("\\s+", "");
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

    public String[] modifyMoneyValuesForCalculation(String[] values) {
        String[] result = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i].replaceAll("€", "");
            values[i] = values[i].replaceAll("\\.", "");
            values[i] = values[i].replaceAll(",", ".");
            values[i] = values[i].replaceAll(" ", "");
            result[i] = values[i];
        }
        return result;
    }

    public BigDecimal[] convertMoneyStringToBigDecimal(String[] values) {
        BigDecimal[] result = new BigDecimal[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = new BigDecimal(values[i]);
        }
        return result;
    }

    public String[] modifyPercentageValuesForCalculation(String[] values) {
        String[] result = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            values[i] = values[i].replaceAll("%", "");
            values[i] = values[i].replaceAll(",", ".");
            result[i] = values[i];
        }
        return result;
    }

    public BigDecimal[] convertPercentageStringsToBigDecimal(String[] values) {
        BigDecimal[] result = new BigDecimal[values.length];
        BigDecimal divisor = new BigDecimal(100);
        for (int i = 0; i < values.length; i++) {
            result[i] = new BigDecimal(values[i]);
            result[i] = result[i].divide(divisor);
        }
        return result;
    }


}
