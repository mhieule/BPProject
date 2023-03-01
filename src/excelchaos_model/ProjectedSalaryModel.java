package excelchaos_model;

import java.util.*;

public class ProjectedSalaryModel {
    public static List<Date> calculatePayLevelIncrease(Date startDate, String currentLevel) {
        List<Date> payIncreases = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        switch (currentLevel) {
            case "1A":
                calendar.add(Calendar.MONTH, 6);
                payIncreases.add(calendar.getTime());
                break;
            case "1B":
                calendar.add(Calendar.MONTH, 6);
                calendar.add(Calendar.MONTH, 6);
                payIncreases.add(calendar.getTime());
                break;
            case "1":
                calendar.add(Calendar.YEAR, 1);
                payIncreases.add(calendar.getTime());
                break;
            case "2":
                calendar.add(Calendar.YEAR, 1);
                calendar.add(Calendar.YEAR, 2);
                payIncreases.add(calendar.getTime());
                break;
            case "3":
                calendar.add(Calendar.YEAR, 3);
                calendar.add(Calendar.YEAR, 3);
                payIncreases.add(calendar.getTime());
                break;
            case "4":
                calendar.add(Calendar.YEAR, 6);
                calendar.add(Calendar.YEAR, 4);
                payIncreases.add(calendar.getTime());
                break;
            case "5":
                calendar.add(Calendar.YEAR, 10);
                calendar.add(Calendar.YEAR, 5);
                payIncreases.add(calendar.getTime());
                break;
            case "6":
                calendar.add(Calendar.YEAR, 15);
                payIncreases.add(calendar.getTime());
                break;
        }
        System.out.println("Test");
        return payIncreases;
    }

    public static String calculatePayLevelBasedOnDate(Date workStartDate, String currentLevel, Date givenDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(workStartDate);
        calendar.add(Calendar.MONTH, 6);
        Date level1B = calendar.getTime();
        calendar.add(Calendar.MONTH, 6);
        Date level2 = calendar.getTime();
        calendar.add(Calendar.YEAR, 2);
        Date level3 = calendar.getTime();
        calendar.add(Calendar.YEAR, 3);
        Date level4 = calendar.getTime();
        calendar.add(Calendar.YEAR, 4);
        Date level5 = calendar.getTime();
        calendar.add(Calendar.YEAR, 5);
        Date level6 = calendar.getTime();
        calendar.setTime(workStartDate);


        if (givenDate.compareTo(level1B) < 0) {
            if (!currentLevel.equals("1")) {
                return "1A";
            } else return "1";
        }
        if (givenDate.compareTo(level2) < 0) {
            if (!currentLevel.equals("1")) {
                return "1B";
            } else return "1";
        }
        if (givenDate.compareTo(level3) < 0) {
            return "2";
        }
        if (givenDate.compareTo(level4) < 0) {
            return "3";
        }
        if (givenDate.compareTo(level5) < 0) {
            return "4";
        }
        if (givenDate.compareTo(level6) < 0) {
            return "5";
        }
        return "6";
    }

    //TODO Debugging angesagt
    public static List<Date> calculateNextPayLevelDate(Date startDate, String currentLevel) {
        List<Date> payIncreases = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        switch (currentLevel) {
            case "1A":
            case "1B":
                calendar.add(Calendar.MONTH, 6);
                payIncreases.add(calendar.getTime());
                break;
            case "1":
                calendar.add(Calendar.YEAR, 1);
                payIncreases.add(calendar.getTime());
                break;
            case "2":
                calendar.add(Calendar.YEAR, 2);
                payIncreases.add(calendar.getTime());
                break;
            case "3":
                calendar.add(Calendar.YEAR, 3);
                payIncreases.add(calendar.getTime());
                break;
            case "4":
                calendar.add(Calendar.YEAR, 4);
                payIncreases.add(calendar.getTime());
                break;
            case "5":
                calendar.add(Calendar.YEAR, 5);
                payIncreases.add(calendar.getTime());
                break;
            case "6":
                payIncreases.add(calendar.getTime());
        }
        return payIncreases;
    }

    public static List<Date> getAllPayLevelChangesForEmployee(Date workingStartDate) {
        List<Date> payLevelChanges = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(workingStartDate);
        calendar.add(Calendar.MONTH, 6);
        payLevelChanges.add(calendar.getTime());
        calendar.add(Calendar.MONTH, 6);
        payLevelChanges.add(calendar.getTime());
        calendar.add(Calendar.YEAR, 2);
        payLevelChanges.add(calendar.getTime());
        calendar.add(Calendar.YEAR, 3);
        payLevelChanges.add(calendar.getTime());
        calendar.add(Calendar.YEAR, 4);
        payLevelChanges.add(calendar.getTime());
        calendar.add(Calendar.YEAR, 5);
        payLevelChanges.add(calendar.getTime());

        return payLevelChanges;

    }

    //TODO Debugging, wirft ganz merkwürkdigen Nullpointer
    public static Date getLastPayLevelIncreaseDate(Date workingStartDate, Date currentDate) {
        Date lastIncreaseDate = workingStartDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(workingStartDate);
        List<Date> payLevelChanges = getAllPayLevelChangesForEmployee(workingStartDate);
        for (int i = 0; i < payLevelChanges.size(); i++) {
            if (payLevelChanges.get(i).compareTo(currentDate) <= 0) {
                calendar.setTime(payLevelChanges.get(i));
                lastIncreaseDate = calendar.getTime();
            }
        }
        return lastIncreaseDate;
    }

    public static String getNextPayLevel(String startLevel) {
        switch (startLevel) {
            case "1A":
                return "1B";
            case "1B":
            case "1":
                return "2";
            case "2":
                return "3";
            case "3":
                return "4";
            case "4":
                return "5";
            case "5":
            case "6":
                return "6";
        }
        return null;
    }
}
