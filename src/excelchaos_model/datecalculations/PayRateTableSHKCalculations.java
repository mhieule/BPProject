package excelchaos_model.datecalculations;

import excelchaos_model.database.SHKSalaryEntry;
import excelchaos_model.database.SHKSalaryTableManager;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class PayRateTableSHKCalculations {
    private SHKSalaryTableManager shkSalaryTableManager = new SHKSalaryTableManager();


    /**
     * Returns the ID of the currently active SHK salary entry based on the current date.
     * The currently active entry is the one with the latest validation date that is not in the future.
     *
     * @return the ID of the currently active SHK salary entry
     */
    public int getCurrentSHKPayRates() {
        LocalDate currentLocalDate = LocalDate.now();
        Date currentDate = Date.from(currentLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<SHKSalaryEntry> shkSalaryEntries = shkSalaryTableManager.getAllSHKSalaryEntries();
        Date currentlyValidDate = null;
        int iDofCurrentSHKEntry = 0;
        for (SHKSalaryEntry shkSalaryEntry : shkSalaryEntries) {
            if (currentDate.compareTo(shkSalaryEntry.getValidationDate()) >= 0) {
                if (currentlyValidDate == null) {
                    currentlyValidDate = shkSalaryEntry.getValidationDate();
                    iDofCurrentSHKEntry = shkSalaryEntry.getId();
                } else {
                    if (shkSalaryEntry.getValidationDate().compareTo(currentlyValidDate) > 0) {
                        iDofCurrentSHKEntry = shkSalaryEntry.getId();
                        currentlyValidDate = shkSalaryEntry.getValidationDate();
                    }
                }


            } else if (currentDate.compareTo(shkSalaryEntry.getValidationDate()) < 0) {
                continue;
            }
        }
        return iDofCurrentSHKEntry;
    }

    /**
     * Returns the id of the currently valid SHK pay rate entry based on the provided chosen date.
     *
     * @param chosenDate the date for which the SHK pay rate should be determined
     * @return the id of the valid SHK pay rate entry
     */
    public int getSHKPayRatesBasedOnChosenDate(LocalDate chosenDate) {
        Date currentDate = Date.from(chosenDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<SHKSalaryEntry> shkSalaryEntries = shkSalaryTableManager.getAllSHKSalaryEntries();
        Date currentlyValidDate = null;
        int iDofCurrentSHKEntry = 0;
        for (SHKSalaryEntry shkSalaryEntry : shkSalaryEntries) {
            if (currentDate.compareTo(shkSalaryEntry.getValidationDate()) >= 0) {
                if (currentlyValidDate == null) {
                    currentlyValidDate = shkSalaryEntry.getValidationDate();
                    iDofCurrentSHKEntry = shkSalaryEntry.getId();
                } else {
                    if (shkSalaryEntry.getValidationDate().compareTo(currentlyValidDate) > 0) {
                        iDofCurrentSHKEntry = shkSalaryEntry.getId();
                        currentlyValidDate = shkSalaryEntry.getValidationDate();
                    }
                }
            } else if (currentDate.compareTo(shkSalaryEntry.getValidationDate()) < 0) {
                continue;
            }
        }
        return iDofCurrentSHKEntry;
    }

}
