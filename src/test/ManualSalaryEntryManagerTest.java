import excelchaos_model.database.ManualSalaryEntry;
import excelchaos_model.database.ManualSalaryEntryManager;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManualSalaryEntryManagerTest {
    @Test
    void testRemoveAll(){
        var manager = new ManualSalaryEntryManager();
        manager.removeAllManualSalaryEntries();
        assertEquals(manager.getAllManualSalaryEntries().size(), 0);
    }

    @Test
    void testGetValid(){
        var manager = new ManualSalaryEntryManager();
        manager.removeAllManualSalaryEntries();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        BigDecimal num = new BigDecimal("213.21");
        var manualSalaryEntry = new ManualSalaryEntry(1, num, calendar.getTime(),
                "test_comment");
        manager.addManualSalaryEntry(manualSalaryEntry);
        var recManualSalaryEntry = manager.getManualSalaryEntry(1).get(0);
        assertEquals(manualSalaryEntry.getNew_salary(), recManualSalaryEntry.getNew_salary());
        assertEquals(manualSalaryEntry.getId(), recManualSalaryEntry.getId());
        assertEquals(manualSalaryEntry.getStart_date(), recManualSalaryEntry.getStart_date());
        assertEquals(manualSalaryEntry.getComment(), recManualSalaryEntry.getComment());
    }

    @Test
    void testGetInvalid(){
        var manager = new ManualSalaryEntryManager();
        manager.removeAllManualSalaryEntries();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        BigDecimal num = new BigDecimal("213.21");
        var manualSalaryEntry = new ManualSalaryEntry(1, num, calendar.getTime(),
                "test_comment");
        manager.addManualSalaryEntry(manualSalaryEntry);
        assertEquals(manager.getManualSalaryEntry(2).size(), 0);
    }

    @Test
    void testGetByDateValid(){
        var manager = new ManualSalaryEntryManager();
        manager.removeAllManualSalaryEntries();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        BigDecimal num = new BigDecimal("213.21");
        var manualSalaryEntry = new ManualSalaryEntry(1, num, calendar.getTime(),
                "test_comment");
        manager.addManualSalaryEntry(manualSalaryEntry);
        var recManualSalaryEntry = manager.getManualSalaryEntryByDate(1, calendar.getTime()).get(0);
        assertEquals(manualSalaryEntry.getNew_salary(), recManualSalaryEntry.getNew_salary());
        assertEquals(manualSalaryEntry.getId(), recManualSalaryEntry.getId());
        assertEquals(manualSalaryEntry.getStart_date(), recManualSalaryEntry.getStart_date());
        assertEquals(manualSalaryEntry.getComment(), recManualSalaryEntry.getComment());
    }

    @Test
    void testGetByDateInvalid(){
        var manager = new ManualSalaryEntryManager();
        manager.removeAllManualSalaryEntries();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        BigDecimal num = new BigDecimal("213.21");
        var manualSalaryEntry = new ManualSalaryEntry(1, num, calendar.getTime(),
                "test_comment");
        manager.addManualSalaryEntry(manualSalaryEntry);
        assertEquals(manager.getManualSalaryEntryByDate(2, calendar.getTime()).size(), 0);
    }

    @Test
    void testRemoveValid(){
        var manager = new ManualSalaryEntryManager();
        manager.removeAllManualSalaryEntries();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        BigDecimal num = new BigDecimal("213.21");
        var manualSalaryEntry = new ManualSalaryEntry(1, num, calendar.getTime(),
                "test_comment");
        manager.addManualSalaryEntry(manualSalaryEntry);
        manager.removeManualSalaryEntry(1, calendar.getTime());
        assertEquals(manager.getManualSalaryEntryByDate(1, calendar.getTime()).size(), 0);
    }

    @Test
    void testRemoveInvalid(){
        var manager = new ManualSalaryEntryManager();
        manager.removeAllManualSalaryEntries();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        BigDecimal num = new BigDecimal("213.21");
        var manualSalaryEntry = new ManualSalaryEntry(1, num, calendar.getTime(),
                "test_comment");
        manager.addManualSalaryEntry(manualSalaryEntry);
        manager.removeManualSalaryEntry(2, calendar.getTime());
        assertEquals(manager.getManualSalaryEntryByDate(1, calendar.getTime()).size(), 1);
    }

    @Test
    void testGetAll(){
        var manager = new ManualSalaryEntryManager();
        manager.removeAllManualSalaryEntries();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var manualSalaryEntries = new ManualSalaryEntry[10];

        for (int i = 0; i < 10; i++){
            BigDecimal num = new BigDecimal("213.21");
            var manualSalaryEntry = new ManualSalaryEntry(i, num, calendar.getTime(),
                    "test_comment");
            manager.addManualSalaryEntry(manualSalaryEntry);
            manualSalaryEntries[i] = manualSalaryEntry;
        }

        var recManualSalaryEntries = manager.getAllManualSalaryEntries();

        for (int i = 0; i < 10; i++){
            assertEquals(manualSalaryEntries[i].getNew_salary(), recManualSalaryEntries.get(i).getNew_salary());
            assertEquals(manualSalaryEntries[i].getId(), recManualSalaryEntries.get(i).getId());
            assertEquals(manualSalaryEntries[i].getStart_date(), recManualSalaryEntries.get(i).getStart_date());
            assertEquals(manualSalaryEntries[i].getComment(), recManualSalaryEntries.get(i).getComment());
        }
    }

    @Test
    void testGetRowCount(){
        var manager = new ManualSalaryEntryManager();
        manager.removeAllManualSalaryEntries();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        for (int i = 0; i < 10; i++){
            BigDecimal num = new BigDecimal("213.21");
            var manualSalaryEntry = new ManualSalaryEntry(5, num, calendar.getTime(),
                    "test_comment");
            manager.addManualSalaryEntry(manualSalaryEntry);
        }
        assertEquals(manager.getRowCount(5), 10);
    }

    @Test
    void testGetRowCountEmpty(){
        var manager = new ManualSalaryEntryManager();
        manager.removeAllManualSalaryEntries();
        assertEquals(manager.getRowCount(5), 0);
    }
}
