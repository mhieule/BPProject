package excelchaos_model.datamodel;

import excelchaos_model.database.*;

public class EmployeeDataDeleter {
    private EmployeeDataManager employeeDataManager = EmployeeDataManager.getInstance();
    private ContractDataManager contractDataManager = ContractDataManager.getInstance();
    private ProjectParticipationManager projectParticipationManager = ProjectParticipationManager.getInstance();
    private ManualSalaryEntryManager manualSalaryEntryManager = ManualSalaryEntryManager.getInstance();
    private SalaryIncreaseHistoryManager salaryIncreaseHistoryManager = SalaryIncreaseHistoryManager.getInstance();


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
