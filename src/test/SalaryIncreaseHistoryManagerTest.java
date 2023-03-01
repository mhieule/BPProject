import excelchaos_model.database.SalaryIncreaseHistory;
import excelchaos_model.database.SalaryIncreaseHistoryManager;
import excelchaos_model.database.SalaryTableManager;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalaryIncreaseHistoryManagerTest {
    @Test
    void testRemoveAll(){
        var manager = SalaryIncreaseHistoryManager.getInstance();
        manager.removeAllSalaryIncreaseHistories();
        assertEquals(manager.getAllSalaryIncreaseHistories().size(), 0);
    }

    @Test
    void testGetValid(){
        var manager = SalaryIncreaseHistoryManager.getInstance();
        manager.removeAllSalaryIncreaseHistories();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        var salaryIncreaseHistory = new SalaryIncreaseHistory(1, 0.5, calendar.getTime(),
                "test_comment", false);
        manager.addSalaryIncreaseHistory(salaryIncreaseHistory);
        var recSalaryIncreaseHistory = manager.getSalaryIncreaseHistory(1).get(0);
        assertEquals(salaryIncreaseHistory.getId(), recSalaryIncreaseHistory.getId());
        assertEquals(salaryIncreaseHistory.getNew_salary(), recSalaryIncreaseHistory.getNew_salary());
        assertEquals(salaryIncreaseHistory.getStart_date(), recSalaryIncreaseHistory.getStart_date());
        assertEquals(salaryIncreaseHistory.getComment(), recSalaryIncreaseHistory.getComment());
        assertEquals(salaryIncreaseHistory.getIs_additional_payment(), recSalaryIncreaseHistory.getIs_additional_payment());
    }

    @Test
    void testGetInvalid(){
        var manager = SalaryIncreaseHistoryManager.getInstance();
        manager.removeAllSalaryIncreaseHistories();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        var salaryIncreaseHistory = new SalaryIncreaseHistory(1, 0.5, calendar.getTime(),
                "test_comment", false);
        manager.addSalaryIncreaseHistory(salaryIncreaseHistory);
        assertEquals(manager.getSalaryIncreaseHistory(2).size(), 0);
    }

    @Test
    void testGetByDateValid(){
        var manager = SalaryIncreaseHistoryManager.getInstance();
        manager.removeAllSalaryIncreaseHistories();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        var salaryIncreaseHistory = new SalaryIncreaseHistory(1, 0.5, calendar.getTime(),
                "test_comment", false);
        manager.addSalaryIncreaseHistory(salaryIncreaseHistory);
        var recSalaryIncreaseHistory = manager.getSalaryIncreaseHistoryByDate(1, calendar.getTime()).get(0);
        assertEquals(salaryIncreaseHistory.getId(), recSalaryIncreaseHistory.getId());
        assertEquals(salaryIncreaseHistory.getNew_salary(), recSalaryIncreaseHistory.getNew_salary());
        assertEquals(salaryIncreaseHistory.getStart_date(), recSalaryIncreaseHistory.getStart_date());
        assertEquals(salaryIncreaseHistory.getComment(), recSalaryIncreaseHistory.getComment());
        assertEquals(salaryIncreaseHistory.getIs_additional_payment(), recSalaryIncreaseHistory.getIs_additional_payment());
    }

    @Test
    void testGetByDateInvalid(){
        var manager = SalaryIncreaseHistoryManager.getInstance();
        manager.removeAllSalaryIncreaseHistories();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        var salaryIncreaseHistory = new SalaryIncreaseHistory(1, 0.5, calendar.getTime(),
                "test_comment", false);
        manager.addSalaryIncreaseHistory(salaryIncreaseHistory);
        assertEquals(manager.getSalaryIncreaseHistoryByDate(2, calendar.getTime()).size(), 0);
    }

    @Test
    void testRemoveValid(){
        var manager = SalaryIncreaseHistoryManager.getInstance();
        manager.removeAllSalaryIncreaseHistories();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        var salaryIncreaseHistory = new SalaryIncreaseHistory(1, 0.5, calendar.getTime(),
                "test_comment", false);
        manager.addSalaryIncreaseHistory(salaryIncreaseHistory);
        manager.removeSalaryIncreaseHistory(1, calendar.getTime());
        assertEquals(manager.getSalaryIncreaseHistoryByDate(1, calendar.getTime()).size(), 0);
    }

    @Test
    void testRemoveInvalid(){
        var manager = SalaryIncreaseHistoryManager.getInstance();
        manager.removeAllSalaryIncreaseHistories();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        var salaryIncreaseHistory = new SalaryIncreaseHistory(1, 0.5, calendar.getTime(),
                "test_comment", false);
        manager.addSalaryIncreaseHistory(salaryIncreaseHistory);
        manager.removeSalaryIncreaseHistory(2, calendar.getTime());
        assertEquals(manager.getSalaryIncreaseHistoryByDate(1, calendar.getTime()).size(), 1);
    }

    @Test
    void testGetAll(){
        var manager = SalaryIncreaseHistoryManager.getInstance();
        manager.removeAllSalaryIncreaseHistories();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var salaryIncreaseHistories = new SalaryIncreaseHistory[10];

        for (int i = 0; i < 10; i++){
            var salaryIncreaseHistory = new SalaryIncreaseHistory(i, 0.5, calendar.getTime(),
                    "test_comment", false);
            manager.addSalaryIncreaseHistory(salaryIncreaseHistory);
            salaryIncreaseHistories[i] = salaryIncreaseHistory;
        }

        var recSalaryIncreaseHistories = manager.getAllSalaryIncreaseHistories();

        for (int i = 0; i < 10; i++){
            assertEquals(salaryIncreaseHistories[i].getId(), recSalaryIncreaseHistories.get(i).getId());
            assertEquals(salaryIncreaseHistories[i].getNew_salary(), recSalaryIncreaseHistories.get(i).getNew_salary());
            assertEquals(salaryIncreaseHistories[i].getStart_date(), recSalaryIncreaseHistories.get(i).getStart_date());
            assertEquals(salaryIncreaseHistories[i].getComment(), recSalaryIncreaseHistories.get(i).getComment());
            assertEquals(salaryIncreaseHistories[i].getIs_additional_payment(), recSalaryIncreaseHistories.get(i).getIs_additional_payment());
        }
    }

    @Test
    void testGetRowCount(){
        var manager = SalaryIncreaseHistoryManager.getInstance();
        manager.removeAllSalaryIncreaseHistories();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        for (int i = 0; i < 10; i++){
            var salaryIncreaseHistory = new SalaryIncreaseHistory(1, 0.5, calendar.getTime(),
                    "test_comment", false);
            manager.addSalaryIncreaseHistory(salaryIncreaseHistory);
        }
        assertEquals(manager.getRowCount(1), 10);
    }

    @Test
    void testGetRowCountEmpty(){
        var manager = SalaryIncreaseHistoryManager.getInstance();
        manager.removeAllSalaryIncreaseHistories();
        assertEquals(manager.getRowCount(1), 0);
    }
}
