package excelchaos_model.calculations;

import java.util.*;

public class ProjectedSalaryModel {
    /**
     * This method calculates the date of the next paylevel increase of an employee based on the working start date of the employee.
     *
     * @param workingStartDate The date where the employee started working.
     * @param currentLevel     The current paylevel of the employee at a specific time.
     * @return The next date when the paylevel gets increased for an employee that started working on the workingStartDate.
     */
    public static List<Date> calculatePayLevelIncrease(Date workingStartDate, String currentLevel) {
        List<Date> payLevelIncreases = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(workingStartDate);
        switch (currentLevel) {
            case "1A":
                calendar.add(Calendar.MONTH, 6);
                payLevelIncreases.add(calendar.getTime());
                break;
            case "1B":
                calendar.add(Calendar.MONTH, 6);
                calendar.add(Calendar.MONTH, 6);
                payLevelIncreases.add(calendar.getTime());
                break;
            case "1":
                calendar.add(Calendar.YEAR, 1);
                payLevelIncreases.add(calendar.getTime());
                break;
            case "2":
                calendar.add(Calendar.YEAR, 1);
                calendar.add(Calendar.YEAR, 2);
                payLevelIncreases.add(calendar.getTime());
                break;
            case "3":
                calendar.add(Calendar.YEAR, 3);
                calendar.add(Calendar.YEAR, 3);
                payLevelIncreases.add(calendar.getTime());
                break;
            case "4":
                calendar.add(Calendar.YEAR, 6);
                calendar.add(Calendar.YEAR, 4);
                payLevelIncreases.add(calendar.getTime());
                break;
            case "5":
                calendar.add(Calendar.YEAR, 10);
                calendar.add(Calendar.YEAR, 5);
                payLevelIncreases.add(calendar.getTime());
                break;
            case "6":
                calendar.add(Calendar.YEAR, 15);
                payLevelIncreases.add(calendar.getTime());
                break;
        }
        return payLevelIncreases;
    }

    /**
     * This method calculates the paylevel an employee will have on a given Date based on the working start date and the current paylevel.
     *
     * @param workStartDate The date an employee started working.
     * @param currentLevel  The current paylevel of the employee.
     * @param givenDate     The date for which you want the projected paylevel.
     * @return The paylevel on the given Date as String.
     */
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

    /**
     * This method calculates all Dates on which a paylevel increase for an employee will/has happen/ed.
     *
     * @param workingStartDate The date on which the employee started working.
     * @return A list of dates where a paylevel increase will happen.
     */
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

    /**
     * This method calculates the last date on which a paylevel increase for an employee has been performed.
     *
     * @param workingStartDate The date on which the employee started working.
     * @param currentDate      Currentdate represents today's date.
     * @return The date on which the last paylevel increase was performed.
     */
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

    /**
     * This method increases the paylevel based on the given paylevel.
     *
     * @param startLevel The current paylevel that is given to the method.
     * @return The increased paylevel as String.
     */
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
