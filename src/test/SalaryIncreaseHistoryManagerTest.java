import excelchaos_model.database.SalaryIncreaseHistory;
import excelchaos_model.database.SalaryIncreaseHistoryManager;
import excelchaos_model.database.SalaryTableManager;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalaryIncreaseHistoryManagerTest {
    @Test
    void testRemoveAll(){
        var manager = new SalaryIncreaseHistoryManager();
        manager.removeAllSalaryIncreaseHistories();
        assertEquals(manager.getAllSalaryIncreaseHistories().size(), 0);
    }

    @Test
    void testGetValid(){
        var manager = new SalaryIncreaseHistoryManager();
        manager.removeAllSalaryIncreaseHistories();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        BigDecimal num = new BigDecimal("420.20");
        var salaryIncreaseHistory = new SalaryIncreaseHistory(1, num, calendar.getTime(),num, num,
                "test_comment", false);
        manager.addSalaryIncreaseHistory(salaryIncreaseHistory);
        var recSalaryIncreaseHistory = manager.getSalaryIncreaseHistory(1).get(0);
        assertEquals(salaryIncreaseHistory.getId(), recSalaryIncreaseHistory.getId());
        assertEquals(salaryIncreaseHistory.getNew_salary(), recSalaryIncreaseHistory.getNew_salary());
        assertEquals(salaryIncreaseHistory.getStart_date(), recSalaryIncreaseHistory.getStart_date());
        assertEquals(salaryIncreaseHistory.getComment(), recSalaryIncreaseHistory.getComment());
        assertEquals(salaryIncreaseHistory.getIs_additional_payment(), recSalaryIncreaseHistory.getIs_additional_payment());
        assertEquals(salaryIncreaseHistory.getPercentIncreaseValue(), recSalaryIncreaseHistory.getPercentIncreaseValue());
        assertEquals(salaryIncreaseHistory.getAbsoluteIncreaseValue(), recSalaryIncreaseHistory.getAbsoluteIncreaseValue());
    }

    @Test
    void testGetInvalid(){
        var manager = new SalaryIncreaseHistoryManager();
        manager.removeAllSalaryIncreaseHistories();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        BigDecimal num = new BigDecimal("420.20");
        var salaryIncreaseHistory = new SalaryIncreaseHistory(1, num, calendar.getTime(),num, num,
                "test_comment", false);
        manager.addSalaryIncreaseHistory(salaryIncreaseHistory);
        assertEquals(manager.getSalaryIncreaseHistory(2).size(), 0);
    }

    @Test
    void testGetByDateValid(){
        var manager = new SalaryIncreaseHistoryManager();
        manager.removeAllSalaryIncreaseHistories();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        BigDecimal num = new BigDecimal("420.20");
        var salaryIncreaseHistory = new SalaryIncreaseHistory(1, num, calendar.getTime(),num, num,
                "test_comment", false);
        manager.addSalaryIncreaseHistory(salaryIncreaseHistory);
        var recSalaryIncreaseHistory = manager.getSalaryIncreaseHistoryByDate(1, calendar.getTime()).get(0);
        assertEquals(salaryIncreaseHistory.getId(), recSalaryIncreaseHistory.getId());
        assertEquals(salaryIncreaseHistory.getNew_salary(), recSalaryIncreaseHistory.getNew_salary());
        assertEquals(salaryIncreaseHistory.getStart_date(), recSalaryIncreaseHistory.getStart_date());
        assertEquals(salaryIncreaseHistory.getComment(), recSalaryIncreaseHistory.getComment());
        assertEquals(salaryIncreaseHistory.getIs_additional_payment(), recSalaryIncreaseHistory.getIs_additional_payment());
        assertEquals(salaryIncreaseHistory.getPercentIncreaseValue(), recSalaryIncreaseHistory.getPercentIncreaseValue());
        assertEquals(salaryIncreaseHistory.getAbsoluteIncreaseValue(), recSalaryIncreaseHistory.getAbsoluteIncreaseValue());
    }

    @Test
    void testGetByDateInvalid(){
        var manager = new SalaryIncreaseHistoryManager();
        manager.removeAllSalaryIncreaseHistories();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        BigDecimal num = new BigDecimal("420.20");
        var salaryIncreaseHistory = new SalaryIncreaseHistory(1, num, calendar.getTime(),num, num,
                "test_comment", false);
        manager.addSalaryIncreaseHistory(salaryIncreaseHistory);
        assertEquals(manager.getSalaryIncreaseHistoryByDate(2, calendar.getTime()).size(), 0);
    }

    @Test
    void testRemoveValid(){
        var manager = new SalaryIncreaseHistoryManager();
        manager.removeAllSalaryIncreaseHistories();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        BigDecimal num = new BigDecimal("420.20");
        var salaryIncreaseHistory = new SalaryIncreaseHistory(1, num, calendar.getTime(),num, num,
                "test_comment", false);
        manager.addSalaryIncreaseHistory(salaryIncreaseHistory);
        manager.removeSalaryIncreaseHistory(1, calendar.getTime());
        assertEquals(manager.getSalaryIncreaseHistoryByDate(1, calendar.getTime()).size(), 0);
    }

    @Test
    void testRemoveInvalid(){
        var manager = new SalaryIncreaseHistoryManager();
        manager.removeAllSalaryIncreaseHistories();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        BigDecimal num = new BigDecimal("420.20");
        var salaryIncreaseHistory = new SalaryIncreaseHistory(1, num, calendar.getTime(),num, num,
                "test_comment", false);
        manager.addSalaryIncreaseHistory(salaryIncreaseHistory);
        manager.removeSalaryIncreaseHistory(2, calendar.getTime());
        assertEquals(manager.getSalaryIncreaseHistoryByDate(1, calendar.getTime()).size(), 1);
    }

    @Test
    void testGetAll(){
        var manager = new SalaryIncreaseHistoryManager();
        manager.removeAllSalaryIncreaseHistories();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var salaryIncreaseHistories = new SalaryIncreaseHistory[10];

        for (int i = 0; i < 10; i++){
            BigDecimal num = new BigDecimal("420.20");
            var salaryIncreaseHistory = new SalaryIncreaseHistory(i, num, calendar.getTime(),num, num,
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
            assertEquals(salaryIncreaseHistories[i].getPercentIncreaseValue(), recSalaryIncreaseHistories.get(i).getPercentIncreaseValue());
            assertEquals(salaryIncreaseHistories[i].getAbsoluteIncreaseValue(), recSalaryIncreaseHistories.get(i).getAbsoluteIncreaseValue());
        }
    }

    @Test
    void testGetRowCount(){
        var manager = new SalaryIncreaseHistoryManager();
        manager.removeAllSalaryIncreaseHistories();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        for (int i = 0; i < 10; i++){
            BigDecimal num = new BigDecimal("420.20");
            var salaryIncreaseHistory = new SalaryIncreaseHistory(1, num, calendar.getTime(),num, num,
                    "test_comment", false);
            manager.addSalaryIncreaseHistory(salaryIncreaseHistory);
        }
        assertEquals(manager.getRowCount(1), 10);
    }

    @Test
    void testGetRowCountEmpty(){
        var manager = new SalaryIncreaseHistoryManager();
        manager.removeAllSalaryIncreaseHistories();
        assertEquals(manager.getRowCount(1), 0);
    }
}
