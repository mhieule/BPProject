package excelchaos_model;

import java.util.*;

public class ProjectedSalaryModel {
    public static List<Date> calculatePayLevelIncrease(Date startDate, String currentLevel){
        List<Date> payIncreases = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        switch (currentLevel){
            case "1A":
                calendar.add(Calendar.MONTH,6);
                payIncreases.add(calendar.getTime());
                break;
            case "1B":
                calendar.add(Calendar.MONTH,6);
                calendar.add(Calendar.MONTH,6);
                payIncreases.add(calendar.getTime());
                break;
            case "1":
                calendar.add(Calendar.YEAR,1);
                payIncreases.add(calendar.getTime());
                break;
            case "2":
                calendar.add(Calendar.YEAR,1);
                calendar.add(Calendar.YEAR,2);
                payIncreases.add(calendar.getTime());
                break;
            case "3":
                calendar.add(Calendar.YEAR,3);
                calendar.add(Calendar.YEAR, 3);
                payIncreases.add(calendar.getTime());
                break;
            case "4":
                calendar.add(Calendar.YEAR,6);
                calendar.add(Calendar.YEAR,4);
                payIncreases.add(calendar.getTime());
                break;
            case "5":
                calendar.add(Calendar.YEAR,10);
                calendar.add(Calendar.YEAR, 5);
                payIncreases.add(calendar.getTime());
                break;
            case "6":
                calendar.add(Calendar.YEAR,15);
                calendar.add(Calendar.YEAR, 6);
                payIncreases.add(calendar.getTime());
                break;
        }
        return payIncreases;
    }

    public static List<Date> getAllPayLevelChangesForEmployee(Date workingStartDate){
        List<Date> payLevelChanges = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(workingStartDate);
        calendar.add(Calendar.MONTH,6);
        payLevelChanges.add(calendar.getTime());
        calendar.add(Calendar.MONTH,6);
        payLevelChanges.add(calendar.getTime());
        calendar.add(Calendar.YEAR,2);
        payLevelChanges.add(calendar.getTime());
        calendar.add(Calendar.YEAR,3);
        payLevelChanges.add(calendar.getTime());
        calendar.add(Calendar.YEAR,4);
        payLevelChanges.add(calendar.getTime());
        calendar.add(Calendar.YEAR,5);
        payLevelChanges.add(calendar.getTime());

        return payLevelChanges;

    }

    public static Date getLastPayLevelIncreaseDate(Date workingStartDate,Date currentDate){
        Date lastIncreaseDate = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(workingStartDate);
        List<Date> payLevelChanges = getAllPayLevelChangesForEmployee(workingStartDate);
        for (int i = 0; i < payLevelChanges.size(); i++) {
            if(payLevelChanges.get(i).compareTo(currentDate) <= 0){
                calendar.setTime(payLevelChanges.get(i));
                lastIncreaseDate = calendar.getTime();
            }
        }
        return lastIncreaseDate;
    }
}
