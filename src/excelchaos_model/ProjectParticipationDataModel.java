package excelchaos_model;

import excelchaos_model.calculations.NewAndImprovedSalaryCalculation;
import excelchaos_model.database.*;
import excelchaos_model.sorter.ParticipationSortByDate;
import excelchaos_model.utility.StringAndBigDecimalFormatter;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProjectParticipationDataModel {
    private ProjectManager projectManager = ProjectManager.getInstance();
    private ProjectParticipationManager projectParticipationManager = ProjectParticipationManager.getInstance();

    private EmployeeDataManager employeeDataManager = EmployeeDataManager.getInstance();
    private int[] projectIds;

    private NewAndImprovedSalaryCalculation salaryCalculation = new NewAndImprovedSalaryCalculation();

    private BigDecimal[] monthlyProjectPersonalCost;

    private HashSet<String> allShownMonths;

    private HashSet<String> allShownEmployeeNames;

    private HashMap<String, BigDecimal> fullParticipations;

    public ProjectParticipationDataModel(String[] projectIds) {
        this.projectIds = new int[projectIds.length];
        for (int i = 0; i < this.projectIds.length; i++) {
            this.projectIds[i] = Integer.parseInt(projectIds[i]);
        }
        allShownMonths = new HashSet<>();
        allShownEmployeeNames = new HashSet<>();
        fullParticipations = new HashMap<String, BigDecimal>();
    }

    public ProjectParticipationDataModel() {
    }

    public int[] getProjectIds() {
        return projectIds;
    }

    public String[] getProjectNames() {
        String[] projectNames = new String[projectIds.length];
        for (int i = 0; i < projectIds.length; i++) {
            projectNames[i] = projectManager.getProject(projectIds[i]).getProject_name();
        }
        return projectNames;
    }

    public String getProjectName(int projectId) {
        return projectManager.getProject(projectId).getProject_name();
    }

    public String[] getProjectRunTimeInMonths(int projectId) {
        String[] months;
        int arrayLength = 0;
        Project project = projectManager.getProject(projectId);
        Date start_date = project.getStart_date();
        Date end_date = project.getDuration();
        DateFormat formatter = new SimpleDateFormat("MMMM-yyyy");

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

    public String[] getPersonNamesForProject(int projectId) {
        String[] names;
        List<ProjectParticipation> projectParticipationsList = new ArrayList<>();
        int[] employeeIds = getPersonIdsForProject(projectId);
        names = new String[employeeIds.length];
        for (int i = 0; i < employeeIds.length; i++) {
            names[i] = employeeDataManager.getEmployee(employeeIds[i]).getSurname() + " " + employeeDataManager.getEmployee(employeeIds[i]).getName();
            allShownEmployeeNames.add(names[i]);
        }
        /*int arrayLength = 0;
        int oldemployeeId = -1;
        for (int iterator = 0; iterator < projectParticipationsList.size(); iterator++) {
            if (oldemployeeId < 0) {
                if (projectParticipationsList.get(iterator) != null) {
                    arrayLength++;
                    oldemployeeId = projectParticipationsList.get(iterator).getPerson_id();
                    continue;
                }
            } else if (projectParticipationsList.get(iterator) != null) {
                if (oldemployeeId != projectParticipationsList.get(iterator).getPerson_id()) {
                    arrayLength++;
                    oldemployeeId = projectParticipationsList.get(iterator).getPerson_id();
                }
            }
        }
        names = new String[arrayLength];

        int i = 0;
        String oldname = null;
        for (ProjectParticipation participation : projectParticipationsList) {
            if(oldname == null){
                names[i] = employeeDataManager.getEmployee(participation.getPerson_id()).getSurname() + " " + employeeDataManager.getEmployee(participation.getPerson_id()).getName();
                oldname = names[i];
                i++;
            } else if(!oldname.equals(employeeDataManager.getEmployee(participation.getPerson_id()).getSurname() + " " + employeeDataManager.getEmployee(participation.getPerson_id()).getName())){
                names[i] = employeeDataManager.getEmployee(participation.getPerson_id()).getSurname() + " " + employeeDataManager.getEmployee(participation.getPerson_id()).getName();
                i++;
            }

        }*/
        return names;


    }

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

    public String[][] getTableData(int projectId, int numOfRows, String[] months, String[] employeeNames) throws ParseException {
        String[][] tableData = new String[numOfRows * 2][months.length];
        DateFormat format = new SimpleDateFormat("MMMM-yyyy");
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
                        List<ProjectParticipation> finalProjectParticipationsList = projectParticipationsList;
                        int finalColumn = column;
                        fullParticipations.compute(employeeNames[row/2] + " " + months[column],(key, val) -> val.add(finalProjectParticipationsList.get(finalColumn -1).getScope()));
                        lastCorrectGivenValue = column;
                    } else {
                        tableData[row][column] = StringAndBigDecimalFormatter.formatPercentageToStringForScope(projectParticipationsList.get(lastCorrectGivenValue).getScope());
                        List<ProjectParticipation> finalProjectParticipationsList1 = projectParticipationsList;
                        int finalLastCorrectGivenValue = lastCorrectGivenValue;
                        fullParticipations.compute(employeeNames[row/2] + " " + months[column],(key, val) -> val.add(finalProjectParticipationsList1.get(finalLastCorrectGivenValue).getScope()));
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

    public String[][] getSummedTableData(int projectId, int numOfRows, String[] months) throws ParseException {
        String[][] summedData;
        BigDecimal[] sumPersonenMonate = new BigDecimal[months.length];
        monthlyProjectPersonalCost = new BigDecimal[months.length];
        for (int i = 0; i < sumPersonenMonate.length; i++) {
            sumPersonenMonate[i] = new BigDecimal(0);
            monthlyProjectPersonalCost[i] = new BigDecimal(0);
        }
        int lastCorrectGivenValue = 0;
        DateFormat format = new SimpleDateFormat("MMMM-yyyy");
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

    public String getTotalProjectPersonalCost() {
        String totalCost;
        BigDecimal totalCostNumber = new BigDecimal(0);
        for (int i = 0; i < monthlyProjectPersonalCost.length; i++) {
            totalCostNumber = totalCostNumber.add(monthlyProjectPersonalCost[i]);
        }
        totalCost = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(totalCostNumber);
        return totalCost;
    }

    private ArrayList<String> transformAllMonthsToSortedList() {
        ArrayList<String> allMonthsList = new ArrayList<String>(allShownMonths);
        allMonthsList.sort(new Comparator<String>() {
            DateFormat format = new SimpleDateFormat("MMMM-yyyy");

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
        ArrayList<String> allNamesList = new ArrayList<String>(allShownEmployeeNames);
        return allNamesList;
    }

    public void setUpParticipationHashMap(){
        ArrayList<String> allNamesList = transformAllEmployeeNamesToList();
        ArrayList<String> allMonthsList = transformAllMonthsToSortedList();

        for(String name : allNamesList){
            for (String month : allMonthsList){
                fullParticipations.put(name + " " + month,new BigDecimal(0));
            }
        }
    }

    public String[][] getTotalParticipationOfSelectedProjectsForEmployees(){
        ArrayList<String> allNamesList = transformAllEmployeeNamesToList();
        ArrayList<String> allMonthsList = transformAllMonthsToSortedList();
        String[][] resultArray = new String[allNamesList.size()][allMonthsList.size()];
        for (int name = 0; name < resultArray.length; name++) {
            for (int month = 0; month < resultArray[0].length; month++) {
                resultArray[name][month] = StringAndBigDecimalFormatter.formatPercentageToStringForScope(fullParticipations.get(allNamesList.get(name) + " " + allMonthsList.get(month)));
            }
        }
        return resultArray;
    }

    public String[] getRuntimeInMonthsForAllProjects() {
        ArrayList<String> allMonthsList = transformAllMonthsToSortedList();
        String[] allMonthsArray = new String[allMonthsList.size()];
        for (int index = 0; index < allMonthsArray.length; index++) {
            allMonthsArray[index] = allMonthsList.get(index);
        }
        return allMonthsArray;
    }

    public String[] getAllEmployeesNamesForSelectedProjects() {
        String[] allEmployeeNamesArray = new String[allShownEmployeeNames.size()];
        for (int index = 0; index < allEmployeeNamesArray.length; index++) {
            allEmployeeNamesArray[index] = (String) allShownEmployeeNames.toArray()[index];
        }

        return allEmployeeNamesArray;
    }


}
