package excelchaos_model.datamodel.employeedataoperations;

import excelchaos_model.database.*;

public class EmployeeDataDeleter {
    private EmployeeDataManager employeeDataManager = new EmployeeDataManager();
    private ContractDataManager contractDataManager = new ContractDataManager();
    private ProjectParticipationManager projectParticipationManager = new ProjectParticipationManager();
    private ManualSalaryEntryManager manualSalaryEntryManager = new ManualSalaryEntryManager();
    private SalaryIncreaseHistoryManager salaryIncreaseHistoryManager = new SalaryIncreaseHistoryManager();


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
