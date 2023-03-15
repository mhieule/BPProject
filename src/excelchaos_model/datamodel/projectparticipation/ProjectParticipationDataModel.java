package excelchaos_model.datamodel.projectparticipation;

import excelchaos_model.calculations.SalaryCalculation;
import excelchaos_model.database.*;
import excelchaos_model.sorter.ParticipationSortByDate;
import excelchaos_model.utility.StringAndBigDecimalFormatter;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProjectParticipationDataModel {
    private ProjectManager projectManager = new ProjectManager();
    private ProjectParticipationManager projectParticipationManager = new ProjectParticipationManager();

    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();
    private int[] projectIds;

    private SalaryCalculation salaryCalculation = new SalaryCalculation();

    private BigDecimal[] monthlyProjectPersonalCost;

    private HashSet<String> allShownMonths;

    private HashSet<String> allShownEmployeeNames;


    public ProjectParticipationDataModel(String[] projectIds) {
        this.projectIds = new int[projectIds.length];
        for (int i = 0; i < this.projectIds.length; i++) {
            this.projectIds[i] = Integer.parseInt(projectIds[i]);
        }
        allShownMonths = new HashSet<>();
        allShownEmployeeNames = new HashSet<>();
    }


    public int[] getProjectIds() {
        return projectIds;
    }


    public String getProjectName(int projectId) {
        return projectManager.getProject(projectId).getProject_name();
    }

    /**
     * Returns an array of strings representing the names of the months between the start and end dates of a project.
     * The first element of the array is always "Namen". The names of the months are formatted in "MMM-yyyy" format.
     *
     * @param projectId the ID of the project to get the run time for
     * @return an array of strings representing the names of the months between the start and end dates of the project
     */
    public String[] getProjectRunTimeInMonths(int projectId) {
        String[] months;
        int arrayLength = 0;
        Project project = projectManager.getProject(projectId);
        Date start_date = project.getStart_date();
        Date end_date = project.getDuration();
        DateFormat formatter = new SimpleDateFormat("MMM-yyyy");

        Calendar beginCalendarArrayLength = Calendar.getInstance();
        Calendar finishCalendarArrayLength = Calendar.getInstance();
        Calendar beginCalendar = Calendar.getInstance();
        Calendar finishCalendar = Calendar.getInstance();

        beginCalendarArrayLength.setTime(start_date);
        finishCalendarArrayLength.setTime(end_date);
        beginCalendar.setTime(start_date);
        finishCalendar.setTime(end_date);

        while (beginCalendarArrayLength.before(finishCalendarArrayLength)) {
            arrayLength++;
            beginCalendarArrayLength.add(Calendar.MONTH, 1);
        }
        arrayLength += 1;
        months = new String[arrayLength];
        months[0] = "Namen";
        int index = 1;
        while (beginCalendar.before(finishCalendar)) {
            if (index < arrayLength) {
                months[index] = formatter.format(beginCalendar.getTime());
                allShownMonths.add(months[index]);
                index++;
            }
            beginCalendar.add(Calendar.MONTH, 1);


        }
        return months;
    }


    /**
     * Returns an array of person names for a given project id.
     *
     * @param projectId The id of the project.
     * @return An array of person names for the given project.
     */
    public String[] getPersonNamesForProject(int projectId) {
        String[] names;
        List<ProjectParticipation> projectParticipationsList = new ArrayList<>();
        int[] employeeIds = getPersonIdsForProject(projectId);
        names = new String[employeeIds.length];
        for (int i = 0; i < employeeIds.length; i++) {
            names[i] = employeeDataManager.getEmployee(employeeIds[i]).getSurname() + " " + employeeDataManager.getEmployee(employeeIds[i]).getName();
            allShownEmployeeNames.add(names[i]);
        }
        return names;


    }

    /**
     * Returns an array of person ids for a given project id.
     *
     * @param projectId The id of the project.
     * @return An array of person ids for the given project.
     */

    private int[] getPersonIdsForProject(int projectId) {
        int[] personIds;
        List<ProjectParticipation> projectParticipationsList = new ArrayList<>();
        projectParticipationsList = projectParticipationManager.getProjectParticipationByProjectID(projectId);
        HashSet<Integer> personIdSet = new HashSet<Integer>();
        for (ProjectParticipation participation : projectParticipationsList) {
            personIdSet.add(participation.getPerson_id());
        }
        personIds = new int[personIdSet.size()];
        int index = 0;
        for (Integer integer : personIdSet) {
            System.out.println(integer);
            personIds[index] = integer;
            index++;
        }

        return personIds;
    }

    /**
     * Returns a two-dimensional array of table data for a given project id, number of rows, months, and employee names.
     * The table data consists of employee names, percentage scope of project participation, and corresponding salaries for each month.
     *
     * @param projectId     The id of the project.
     * @param numOfRows     The number of rows in the table.
     * @param months        The array of month strings to be included in the table.
     * @param employeeNames The array of employee names to be included in the table.
     * @return A two-dimensional array of table data for the given project.
     */
    public String[][] getTableData(int projectId, int numOfRows, String[] months, String[] employeeNames) throws ParseException {
        String[][] tableData = new String[numOfRows * 2][months.length];
        DateFormat format = new SimpleDateFormat("MMM-yyyy");
        int[] personIdsForProject = getPersonIdsForProject(projectId);
        for (int row = 0; row < tableData.length; row++) {
            int lastCorrectGivenValue = 0;
            List<ProjectParticipation> projectParticipationsList = new ArrayList<>();
            projectParticipationsList = projectParticipationManager.getProjectParticipationByProjectIDAndPersonID(projectId, personIdsForProject[row / 2]);
            projectParticipationsList.sort(new ParticipationSortByDate());
            for (int column = 0; column < months.length; column++) {
                if (column == 0) {
                    tableData[row][column] = employeeNames[row / 2];
                    continue;
                }
                if ((row % 2) == 0) {
                    if (column < projectParticipationsList.size()) {
                        tableData[row][column] = StringAndBigDecimalFormatter.formatPercentageToStringForScope(projectParticipationsList.get(column - 1).getScope());
                        lastCorrectGivenValue = column;
                    } else {
                        tableData[row][column] = StringAndBigDecimalFormatter.formatPercentageToStringForScope(projectParticipationsList.get(lastCorrectGivenValue).getScope());
                    }

                } else {
                    if (column < projectParticipationsList.size()) {
                        tableData[row][column] = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(salaryCalculation.projectSalaryToGivenMonth(personIdsForProject[row / 2], format.parse(months[column])).multiply(projectParticipationsList.get(column - 1).getScope()));
                    } else {
                        tableData[row][column] = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(salaryCalculation.projectSalaryToGivenMonth(personIdsForProject[row / 2], format.parse(months[column])).multiply(projectParticipationsList.get(lastCorrectGivenValue).getScope()));

                    }
                }
            }
        }


        return tableData;
    }

    /**
     * This method returns a two-dimensional String array that contains the summed data of project participation and personal costs of a project for each month in the given array.
     * The number of rows in the array is twice the given numOfRows.
     * The first row of the returned array represents the total project participation in person months for each month in the given array,
     * and the second row represents the total personal costs of the project for each month in the given array.
     *
     * @param projectId the ID of the project for which the summed data is required.
     * @param numOfRows the number of rows in the returned array. It should be half the number of persons participating in the project.
     * @param months    an array of String containing the names of the months in the format MMM-yyyy.
     * @return a two-dimensional String array containing the summed data of project participation and personal costs of a project for each month in the given array.
     * @throws ParseException if there is an error in parsing the date from the given months array.
     */
    public String[][] getSummedTableData(int projectId, int numOfRows, String[] months) throws ParseException {
        String[][] summedData;
        BigDecimal[] sumPersonenMonate = new BigDecimal[months.length];
        monthlyProjectPersonalCost = new BigDecimal[months.length];
        for (int i = 0; i < sumPersonenMonate.length; i++) {
            sumPersonenMonate[i] = new BigDecimal(0);
            monthlyProjectPersonalCost[i] = new BigDecimal(0);
        }
        int lastCorrectGivenValue = 0;
        DateFormat format = new SimpleDateFormat("MMM-yyyy");
        int[] personIdsForProject = getPersonIdsForProject(projectId);
        for (int row = 0; row < numOfRows * 2; row++) {
            List<ProjectParticipation> projectParticipationsList = new ArrayList<>();
            projectParticipationsList = projectParticipationManager.getProjectParticipationByProjectIDAndPersonID(projectId, personIdsForProject[row / 2]);
            projectParticipationsList.sort(new ParticipationSortByDate());
            for (int column = 0; column < months.length; column++) {
                if (column == 0) {
                    continue;
                }
                if ((row % 2) == 0) {
                    if (column < projectParticipationsList.size()) {
                        sumPersonenMonate[column] = sumPersonenMonate[column].add(projectParticipationsList.get(column - 1).getScope());
                        lastCorrectGivenValue = column;
                    } else {
                        sumPersonenMonate[column] = sumPersonenMonate[column].add(projectParticipationsList.get(lastCorrectGivenValue).getScope());
                    }

                } else {
                    if (column < projectParticipationsList.size()) {
                        monthlyProjectPersonalCost[column] = monthlyProjectPersonalCost[column].add(salaryCalculation.projectSalaryToGivenMonth(personIdsForProject[row / 2], format.parse(months[column])).multiply(projectParticipationsList.get(column - 1).getScope()));
                    } else {
                        monthlyProjectPersonalCost[column] = monthlyProjectPersonalCost[column].add(salaryCalculation.projectSalaryToGivenMonth(personIdsForProject[row / 2], format.parse(months[column])).multiply(projectParticipationsList.get(lastCorrectGivenValue).getScope()));

                    }
                }
            }
        }
        summedData = new String[2][months.length];
        for (int column = 0; column < sumPersonenMonate.length; column++) {
            summedData[0][column] = StringAndBigDecimalFormatter.formatBigDecimalToPersonenMonate(sumPersonenMonate[column]);
        }
        for (int column = 0; column < monthlyProjectPersonalCost.length; column++) {
            summedData[1][column] = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(monthlyProjectPersonalCost[column]);
        }


        return summedData;
    }

    /**
     * Calculates the total personal cost of the project by summing up the monthly personal costs.
     *
     * @return a String representing the total project personal cost formatted as currency.
     */
    public String getTotalProjectPersonalCost() {
        String totalCost;
        BigDecimal totalCostNumber = new BigDecimal(0);
        for (int i = 0; i < monthlyProjectPersonalCost.length; i++) {
            totalCostNumber = totalCostNumber.add(monthlyProjectPersonalCost[i]);
        }
        totalCost = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(totalCostNumber);
        return totalCost;
    }

    /**
     * Transforms all months in the "allShownMonths" list to a sorted list of Strings in the format of "MMM-yyyy".
     *
     * @return ArrayList of Strings with all the months in "allShownMonths" sorted in ascending order.
     */
    private ArrayList<String> transformAllMonthsToSortedList() {
        ArrayList<String> allMonthsList = new ArrayList<String>(allShownMonths);
        allMonthsList.sort(new Comparator<String>() {
            DateFormat format = new SimpleDateFormat("MMM-yyyy");

            @Override
            public int compare(String firstDate, String secondDate) {
                try {
                    return format.parse(firstDate).compareTo(format.parse(secondDate));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
        return allMonthsList;
    }

    private ArrayList<String> transformAllEmployeeNamesToList() {
        return new ArrayList<String>(allShownEmployeeNames);
    }


    /**
     * Returns an array of all months in which project data is available, sorted in chronological order.
     * This method obtains a sorted list of all months from the "allShownMonths" field, and then
     * converts it to an array of strings to be returned.
     *
     * @return an array of strings containing the names of all months in chronological order
     * @see #transformAllMonthsToSortedList()
     */
    public String[] getRuntimeInMonthsForAllProjects() {
        ArrayList<String> allMonthsList = transformAllMonthsToSortedList();
        String[] allMonthsArray = new String[allMonthsList.size()];
        for (int index = 0; index < allMonthsArray.length; index++) {
            allMonthsArray[index] = allMonthsList.get(index);
        }
        return allMonthsArray;
    }

    /**
     * Returns an array of Strings containing all employee names for the currently selected projects.
     * The method retrieves the employee names from a Set of employee names previously collected from the selected projects.
     * The returned array has the same order as the original Set.
     * If no employee names were collected previously, an empty array is returned.
     *
     * @return String[] containing all employee names for the selected projects, in the same order as previously collected
     */
    public String[] getAllEmployeesNamesForSelectedProjects() {
        String[] allEmployeeNamesArray = new String[allShownEmployeeNames.size()];
        for (int index = 0; index < allEmployeeNamesArray.length; index++) {
            allEmployeeNamesArray[index] = (String) allShownEmployeeNames.toArray()[index];
        }

        return allEmployeeNamesArray;
    }

    /**
     * Returns a two-dimensional string array representing the total participation of each shown employee in each shown month.
     * The first dimension of the array corresponds to each shown employee, and the second dimension corresponds to each shown month.
     * The values are calculated by iterating through all projects and project participations associated with each shown employee,
     * and accumulating the scope of each project participation that occurs in each shown month.
     * The resulting BigDecimal values are formatted as percentage strings using the StringAndBigDecimalFormatter.formatPercentageToStringForScope() method.
     *
     * @return a two-dimensional string array representing the total participation of each shown employee in each shown month
     */
    public String[][] getTotalParticipationsOfShownEmployees() {
        ArrayList<String> allNamesList = transformAllEmployeeNamesToList();
        ArrayList<String> allMonthsList = transformAllMonthsToSortedList();
        BigDecimal[][] totalParticipations = new BigDecimal[allNamesList.size()][allMonthsList.size()];
        String[][] totalParticipationsString = new String[allNamesList.size()][allMonthsList.size()];
        for (int i = 0; i < totalParticipations.length; i++) {
            for (int j = 0; j < totalParticipations[0].length; j++) {
                totalParticipations[i][j] = new BigDecimal(0);
            }
        }

        DateFormat format = new SimpleDateFormat("MMM-yyyy");
        List<Project> allProjectsList = projectManager.getAllProjects();
        for (Project project : allProjectsList) {
            for (int i = 0; i < totalParticipations.length; i++) {
                List<ProjectParticipation> projectParticipationListForEmployee = projectParticipationManager.getProjectParticipationByProjectIDAndPersonID(project.getProject_id(), employeeDataManager.getEmployeeByName(allNamesList.get(i)).getId());
                for (ProjectParticipation participation : projectParticipationListForEmployee) {
                    for (int j = 0; j < totalParticipations[0].length; j++) {
                        if (format.format(participation.getParticipation_period()).equals(allMonthsList.get(j))) {
                            totalParticipations[i][j] = totalParticipations[i][j].add(participation.getScope());
                        }
                    }
                }

            }

        }
        for (int i = 0; i < totalParticipations.length; i++) {
            for (int j = 0; j < totalParticipations[0].length; j++) {
                totalParticipationsString[i][j] = StringAndBigDecimalFormatter.formatPercentageToStringForScope(totalParticipations[i][j]);
            }
        }


        return totalParticipationsString;
    }


}
