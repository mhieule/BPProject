package test.database;

import excelchaos_model.database.SHKSalaryEntry;
import excelchaos_model.database.SHKSalaryTableManager;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class SHKSalaryTableManagerTest {

    @Test
    void testRemoveAll(){
        var manager = new SHKSalaryTableManager();
        manager.removeAllSHKSalaryEntries();
        assertEquals(manager.getAllSHKSalaryEntries().size(), 0);
    }

    @Test
    void testGetValid(){
        var manager = new SHKSalaryTableManager();
        manager.removeAllSHKSalaryEntries();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        BigDecimal num = new BigDecimal("1337.0");
        var shkSalaryEntry = new SHKSalaryEntry(1, calendar.getTime(), num, num, num);
        manager.addSHKTableEntry(shkSalaryEntry);
        var recSHKSalaryEntry = manager.getSHKSalaryEntry(1);
        assertEquals(recSHKSalaryEntry.getBasePayRate(), shkSalaryEntry.getBasePayRate());
        assertEquals(recSHKSalaryEntry.getId(), shkSalaryEntry.getId());
        assertEquals(recSHKSalaryEntry.getValidationDate(), shkSalaryEntry.getValidationDate());
        assertEquals(recSHKSalaryEntry.getExtendedPayRate(), recSHKSalaryEntry.getExtendedPayRate());
        assertEquals(recSHKSalaryEntry.getWHKPayRate(), recSHKSalaryEntry.getExtendedPayRate());
    }

    @Test
    void testGetInvalid(){
        var manager = new SHKSalaryTableManager();
        manager.removeAllSHKSalaryEntries();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        BigDecimal num = new BigDecimal("1337.0");
        var shkSalaryEntry = new SHKSalaryEntry(1, calendar.getTime(), num, num, num);
        manager.addSHKTableEntry(shkSalaryEntry);
        assertNull(manager.getSHKSalaryEntry(2));
    }

    @Test
    void testGetByDateValid(){
        var manager = new SHKSalaryTableManager();
        manager.removeAllSHKSalaryEntries();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        BigDecimal num = new BigDecimal("1337.0");
        var shkSalaryEntry = new SHKSalaryEntry(1, calendar.getTime(), num, num, num);
        manager.addSHKTableEntry(shkSalaryEntry);
        var recSHKSalaryEntry = manager.getSHKSalaryEntryBasedOnDate(calendar.getTime());
        assertEquals(recSHKSalaryEntry.getBasePayRate(), shkSalaryEntry.getBasePayRate());
        assertEquals(recSHKSalaryEntry.getId(), shkSalaryEntry.getId());
        assertEquals(recSHKSalaryEntry.getValidationDate(), shkSalaryEntry.getValidationDate());
        assertEquals(recSHKSalaryEntry.getExtendedPayRate(), recSHKSalaryEntry.getExtendedPayRate());
        assertEquals(recSHKSalaryEntry.getWHKPayRate(), recSHKSalaryEntry.getExtendedPayRate());
    }

    @Test
    void testGetByDateInvalid(){
        var manager = new SHKSalaryTableManager();
        manager.removeAllSHKSalaryEntries();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        BigDecimal num = new BigDecimal("1337.0");
        var shkSalaryEntry = new SHKSalaryEntry(1, calendar.getTime(), num, num, num);
        manager.addSHKTableEntry(shkSalaryEntry);
        assertNull(manager.getSHKSalaryEntryBasedOnDate(calendar.getTime()));
    }

    @Test
    void testRemoveValid(){
        var manager = new SHKSalaryTableManager();
        manager.removeAllSHKSalaryEntries();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        BigDecimal num = new BigDecimal("1337.0");
        var shkSalaryEntry = new SHKSalaryEntry(1, calendar.getTime(), num, num, num);
        manager.addSHKTableEntry(shkSalaryEntry);
        manager.removeSHKSalaryEntry(1);
        assertNull(manager.getSHKSalaryEntry(1));
    }

    @Test
    void testRemoveInvalid(){
        var manager = new SHKSalaryTableManager();
        manager.removeAllSHKSalaryEntries();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        BigDecimal num = new BigDecimal("1337.0");
        var shkSalaryEntry = new SHKSalaryEntry(1, calendar.getTime(), num, num, num);
        manager.addSHKTableEntry(shkSalaryEntry);
        manager.removeSHKSalaryEntry(2);
        assertNotNull((manager.getSHKSalaryEntry(1)));
    }

    @Test
    void testGetAll(){
        var manager = new SHKSalaryTableManager();
        manager.removeAllSHKSalaryEntries();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        BigDecimal num = new BigDecimal("1337.0");
        var entries = new SHKSalaryEntry[10];
        for (int i = 0; i < 10; i++){
            var shkSalaryEntry = new SHKSalaryEntry(i, calendar.getTime(), num, num, num);
            manager.addSHKTableEntry(shkSalaryEntry);
            entries[i] = shkSalaryEntry;
        }
        var recSHKSalaryEntry = manager.getAllSHKSalaryEntries();
        for (int i = 0; i < 10; i++){
            assertEquals(recSHKSalaryEntry.get(i).getBasePayRate(), entries[i].getBasePayRate());
            assertEquals(recSHKSalaryEntry.get(i).getId(), entries[i].getId());
            assertEquals(recSHKSalaryEntry.get(i).getValidationDate(), entries[i].getValidationDate());
            assertEquals(recSHKSalaryEntry.get(i).getExtendedPayRate(), entries[i].getExtendedPayRate());
            assertEquals(recSHKSalaryEntry.get(i).getWHKPayRate(), entries[i].getExtendedPayRate());
        }
    }

    @Test
    void testUpdateValid(){
        var manager = new SHKSalaryTableManager();
        manager.removeAllSHKSalaryEntries();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        BigDecimal num = new BigDecimal("1337.0");
        var shkSalaryEntry = new SHKSalaryEntry(1, calendar.getTime(), num, num, num);
        manager.addSHKTableEntry(shkSalaryEntry);
        num = new BigDecimal("17.3");
        var updateEntry = new SHKSalaryEntry(1, calendar.getTime(), num, num, num);
        manager.updateSHKSalaryEntry(updateEntry);
        var recSHKSalaryEntry = manager.getSHKSalaryEntry(1);
        assertEquals(recSHKSalaryEntry.getBasePayRate(), updateEntry.getBasePayRate());
        assertEquals(recSHKSalaryEntry.getId(), updateEntry.getId());
        assertEquals(recSHKSalaryEntry.getValidationDate(), updateEntry.getValidationDate());
        assertEquals(recSHKSalaryEntry.getExtendedPayRate(), updateEntry.getExtendedPayRate());
        assertEquals(recSHKSalaryEntry.getWHKPayRate(), updateEntry.getExtendedPayRate());
    }

    @Test
    void testUpdateInvalid(){
        var manager = new SHKSalaryTableManager();
        manager.removeAllSHKSalaryEntries();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        BigDecimal num = new BigDecimal("1337.0");
        var shkSalaryEntry = new SHKSalaryEntry(1, calendar.getTime(), num, num, num);
        manager.addSHKTableEntry(shkSalaryEntry);
        num = new BigDecimal("17.3");
        var updateEntry = new SHKSalaryEntry(2, calendar.getTime(), num, num, num);
        manager.updateSHKSalaryEntry(updateEntry);
        var recSHKSalaryEntry = manager.getSHKSalaryEntry(1);
        assertEquals(recSHKSalaryEntry.getBasePayRate(), shkSalaryEntry.getBasePayRate());
        assertEquals(recSHKSalaryEntry.getId(), shkSalaryEntry.getId());
        assertEquals(recSHKSalaryEntry.getValidationDate(), shkSalaryEntry.getValidationDate());
        assertEquals(recSHKSalaryEntry.getExtendedPayRate(), shkSalaryEntry.getExtendedPayRate());
        assertEquals(recSHKSalaryEntry.getWHKPayRate(), shkSalaryEntry.getExtendedPayRate());
    }

    @Test
    void testGetRowCount(){
        var manager = new SHKSalaryTableManager();
        manager.removeAllSHKSalaryEntries();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        BigDecimal num = new BigDecimal("1337.0");
        for (int i = 0; i < 10; i++){
            var shkSalaryEntry = new SHKSalaryEntry(i, calendar.getTime(), num, num, num);
            manager.addSHKTableEntry(shkSalaryEntry);
        }
        assertEquals(manager.getRowCount(), 10);
    }

    @Test
    void testGetNextID(){
        var manager = new SHKSalaryTableManager();
        manager.removeAllSHKSalaryEntries();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        BigDecimal num = new BigDecimal("1337.0");
        var shkSalaryEntry = new SHKSalaryEntry(1, calendar.getTime(), num, num, num);
        manager.addSHKTableEntry(shkSalaryEntry);
        assertEquals(manager.getNextID(), 2);
    }
}
