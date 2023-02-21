package excelchaos_model;

import excelchaos_model.calculations.SalaryCalculation;
import excelchaos_model.utility.StringAndDoubleTransformationForDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProjectParticipationDataModel {
    private int[] projectIds;
    private ProjectManager projectManager;
    private ProjectParticipationManager participationManager;

    private EmployeeDataManager employeeDataManager;
    private StringAndDoubleTransformationForDatabase transformer = new StringAndDoubleTransformationForDatabase();
    private SalaryCalculation salaryCalculationModel = new SalaryCalculation();

    double[] monthlyProjectPersonalCost;

    public ProjectParticipationDataModel(String[] projectIds) {
        projectManager = new ProjectManager();
        participationManager = new ProjectParticipationManager();
        employeeDataManager = new EmployeeDataManager();
        this.projectIds = new int[projectIds.length];
        for (int i = 0; i < this.projectIds.length; i++) {
            this.projectIds[i] = Integer.parseInt(projectIds[i]);
        }
    }

    public ProjectParticipationDataModel(){
        projectManager = new ProjectManager();
        participationManager = new ProjectParticipationManager();
        employeeDataManager = new EmployeeDataManager();
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
        arrayLength +=1;
        months = new String[arrayLength];
        months[0] = "Namen";
        int index = 1;
        while (beginCalendar.before(finishCalendar)) {
            if (index < arrayLength) {
                months[index] = formatter.format(beginCalendar.getTime());
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
        projectParticipationsList = participationManager.getProjectParticipationByProjectID(projectId);
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
    public String[][] getTableData(int projectId, int numOfRows, String[] months,String[] employeeNames) throws ParseException {
        String[][] tableData = new String[numOfRows * 2][months.length];
        DateFormat format = new SimpleDateFormat("MMMM-yyyy");
        int lastCorrectGivenValue = 0;
        int[] personIdsForProject = getPersonIdsForProject(projectId);
        for (int row = 0; row < tableData.length; row++) {
            List<ProjectParticipation> projectParticipationsList = new ArrayList<>();
            projectParticipationsList = participationManager.getProjectParticipationByProjectIDandPersonID(projectId, personIdsForProject[row / 2]);
            for (int column = 0; column < months.length; column++) {
                if(column == 0){
                    tableData[row][column] = employeeNames[row/2];
                    continue;
                }
                if ((row % 2) == 0) {
                    if(column < projectParticipationsList.size()){
                        tableData[row][column] = transformer.formatPercentageToStringForScope(projectParticipationsList.get(column).getScope());
                        lastCorrectGivenValue = column;
                    } else {
                        tableData[row][column] = transformer.formatPercentageToStringForScope(projectParticipationsList.get(lastCorrectGivenValue).getScope());
                    }

                } else {
                    if(column < projectParticipationsList.size()){
                        tableData[row][column] = transformer.formatDoubleToString(salaryCalculationModel.determineSalaryOfGivenMonth(personIdsForProject[row / 2], format.parse(months[column]))*projectParticipationsList.get(column).getScope(), 1);
                    } else {
                        tableData[row][column] = transformer.formatDoubleToString(salaryCalculationModel.determineSalaryOfGivenMonth(personIdsForProject[row / 2], format.parse(months[column]))*projectParticipationsList.get(lastCorrectGivenValue).getScope(), 1);

                    }
                }
            }
        }


        return tableData;
    }

    public String[][] getSummedTableData(int projectId, int numOfRows, String[] months) throws ParseException {
        String[][] summedData;
        double[] sumPersonenMonate = new double[months.length];
        monthlyProjectPersonalCost = new double[months.length];
        int lastCorrectGivenValue = 0;
        DateFormat format = new SimpleDateFormat("MMMM-yyyy");
        int[] personIdsForProject = getPersonIdsForProject(projectId);
        for (int row = 0; row < numOfRows * 2; row++) {
            List<ProjectParticipation> projectParticipationsList = new ArrayList<>();
            projectParticipationsList = participationManager.getProjectParticipationByProjectIDandPersonID(projectId, personIdsForProject[row / 2]);
            for (int column = 0; column < months.length; column++) {
                if(column == 0){
                    continue;
                }
                if ((row % 2) == 0) {
                    if(column < projectParticipationsList.size()){
                        sumPersonenMonate[column] += projectParticipationsList.get(column).getScope();
                        lastCorrectGivenValue = column;
                    } else {
                        sumPersonenMonate[column] += projectParticipationsList.get(lastCorrectGivenValue).getScope();
                    }

                } else {
                    if(column < projectParticipationsList.size()){
                        monthlyProjectPersonalCost[column] += salaryCalculationModel.determineSalaryOfGivenMonth(personIdsForProject[row / 2], format.parse(months[column]))*projectParticipationsList.get(column).getScope();
                    } else {
                        monthlyProjectPersonalCost[column] += salaryCalculationModel.determineSalaryOfGivenMonth(personIdsForProject[row / 2], format.parse(months[column]))*projectParticipationsList.get(lastCorrectGivenValue).getScope();

                    }
                }
            }
        }
        summedData = new String[2][months.length];
        for (int column = 0; column < sumPersonenMonate.length; column++) {
            summedData[0][column] = transformer.formatPercentageToStringForScope(sumPersonenMonate[column]);
        }
        for (int column = 0; column < monthlyProjectPersonalCost.length; column++) {
            summedData[1][column] = transformer.formatDoubleToString(monthlyProjectPersonalCost[column],1);
        }


        return summedData;
    }

    public String getTotalProjectPersonalCost(){
        String totalCost;
        double totalCostNumber = 0;
        for (int i = 0; i < monthlyProjectPersonalCost.length; i++) {
            totalCostNumber += monthlyProjectPersonalCost[i];
        }
        totalCost = transformer.formatDoubleToString(totalCostNumber,1);
        return totalCost;
    }


    }
