package excelchaos_model.datamodel.employeedataoperations;

import excelchaos_model.database.*;

public class EmployeeDataDeleter {
    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();
    private ContractDataManager contractDataManager = new ContractDataManager();
    private ProjectParticipationManager projectParticipationManager = new ProjectParticipationManager();
    private ManualSalaryEntryManager manualSalaryEntryManager = new ManualSalaryEntryManager();
    private SalaryIncreaseHistoryManager salaryIncreaseHistoryManager = new SalaryIncreaseHistoryManager();

    /**
     * Removes all data related to the employees with the given IDs from the system. This includes removing the employee, their contract, any manually entered salary entries, their salary increase history, and their project participation.
     *
     * @param employeeIds an array of integer IDs of the employees to be deleted
     */
    public void deleteData(int[] employeeIds) {
        for (int i = 0; i < employeeIds.length; i++) {
            employeeDataManager.removeEmployee(employeeIds[i]);
            contractDataManager.removeContract(employeeIds[i]);
            manualSalaryEntryManager.removeAllManualSalaryEntryForEmployee(employeeIds[i]);
            salaryIncreaseHistoryManager.removeAllSalaryIncreaseHistoryForEmployee(employeeIds[i]);
            projectParticipationManager.removeProjectParticipationBasedOnEmployeeId(employeeIds[i]);
        }

    }
}
