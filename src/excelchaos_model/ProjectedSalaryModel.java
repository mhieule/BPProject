package excelchaos_model;

import java.util.*;

public class ProjectedSalaryModel {
    public static List<Date> calculatePayLevelIncrease(Date startDate, String currentLevel){
        List<Date> payIncreases = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        switch (currentLevel){
            case "1a":
                calendar.add(Calendar.MONTH,6);
                payIncreases.add(calendar.getTime());
                calendar.add(Calendar.MONTH,6);
                payIncreases.add(calendar.getTime());
                break;
            case "1b":
                calendar.add(Calendar.MONTH,6);
                payIncreases.add(calendar.getTime());
                calendar.add(Calendar.YEAR, 2);
                payIncreases.add(calendar.getTime());
                break;
            case "2":
                calendar.add(Calendar.YEAR,2);
                payIncreases.add(calendar.getTime());
                calendar.add(Calendar.YEAR, 3);
                payIncreases.add(calendar.getTime());
                break;
            case "3":
                calendar.add(Calendar.YEAR, 3);
                payIncreases.add(calendar.getTime());
                calendar.add(Calendar.YEAR, 4);
                payIncreases.add(calendar.getTime());
                break;
            case "4":
                calendar.add(Calendar.YEAR,4);
                payIncreases.add(calendar.getTime());
                calendar.add(Calendar.YEAR,5);
                payIncreases.add(calendar.getTime());
                break;
            case "5":
                calendar.add(Calendar.YEAR, 5);
                payIncreases.add(calendar.getTime());
                calendar.add(Calendar.YEAR, 6);
                payIncreases.add(calendar.getTime());
                break;
            case "6":
                calendar.add(Calendar.YEAR, 6);
                payIncreases.add(calendar.getTime());
                break;
        }
        return payIncreases;
    }
}
