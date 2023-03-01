import excelchaos_model.database.PaygradeIncrease;
import excelchaos_model.database.PaygradeIncreaseManager;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaygradeIncreaseManagerTest {
    @Test
    void testRemoveAll(){
        var manager = new PaygradeIncreaseManager();
        manager.removeAllPaygradeIncreases();
        assertEquals(manager.getAllPaygradeIncreases().size(), 0);
    }

    @Test
    void testGetValid(){
        var manager = new PaygradeIncreaseManager();
        manager.removeAllPaygradeIncreases();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var payGradeIncrease = new PaygradeIncrease(1, 4.20, calendar.getTime());
        manager.addPaygradeIncrease(payGradeIncrease);
        var recPayGradeIncrease = manager.getPaygradeIncrease(1).get(0);
        assertEquals(payGradeIncrease.getId(), recPayGradeIncrease.getId());
        assertEquals(payGradeIncrease.getStart_date(), recPayGradeIncrease.getStart_date());
        assertEquals(payGradeIncrease.getNew_salary(), recPayGradeIncrease.getNew_salary());
    }

    @Test
    void testGetInvalid(){
        var manager = new PaygradeIncreaseManager();
        manager.removeAllPaygradeIncreases();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var payGradeIncrease = new PaygradeIncrease(1, 4.20, calendar.getTime());
        manager.addPaygradeIncrease(payGradeIncrease);
        assertEquals(manager.getPaygradeIncrease(2).size(), 0);
    }

    @Test
    void testGetValidByDate(){
        var manager = new PaygradeIncreaseManager();
        manager.removeAllPaygradeIncreases();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var payGradeIncrease = new PaygradeIncrease(1, 4.20, calendar.getTime());
        manager.addPaygradeIncrease(payGradeIncrease);
        var recPayGradeIncrease = manager.getPaygradeIncreaseByDate(1, calendar.getTime()).get(0);
        assertEquals(payGradeIncrease.getId(), recPayGradeIncrease.getId());
        assertEquals(payGradeIncrease.getStart_date(), recPayGradeIncrease.getStart_date());
        assertEquals(payGradeIncrease.getNew_salary(), recPayGradeIncrease.getNew_salary());
    }

    @Test
    void testGetInvalidByDate(){
        var manager = new PaygradeIncreaseManager();
        manager.removeAllPaygradeIncreases();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var payGradeIncrease = new PaygradeIncrease(1, 4.20, calendar.getTime());
        manager.addPaygradeIncrease(payGradeIncrease);
        assertEquals(manager.getPaygradeIncreaseByDate(2, calendar.getTime()).size(), 0);
    }

    @Test
    void testRemoveValid(){
        var manager = new PaygradeIncreaseManager();
        manager.removeAllPaygradeIncreases();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var payGradeIncrease = new PaygradeIncrease(1, 4.20, calendar.getTime());
        manager.addPaygradeIncrease(payGradeIncrease);
        manager.removePaygradeIncrease(1, calendar.getTime());
        assertEquals(manager.getPaygradeIncreaseByDate(1, calendar.getTime()).size(), 0);
    }

    @Test
    void testRemoveInvalid(){
        var manager = new PaygradeIncreaseManager();
        manager.removeAllPaygradeIncreases();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var payGradeIncrease = new PaygradeIncrease(1, 4.20, calendar.getTime());
        manager.addPaygradeIncrease(payGradeIncrease);
        manager.removePaygradeIncrease(2, calendar.getTime());
        assertEquals(manager.getPaygradeIncreaseByDate(1, calendar.getTime()).size(), 1);
    }

    @Test
    void testGetAll(){
        var manager = new PaygradeIncreaseManager();
        manager.removeAllPaygradeIncreases();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        var payGradeIncreases = new PaygradeIncrease[10];
        for (int i = 0; i < 10; i++){
            var payGradeIncrease = new PaygradeIncrease(i, 4.20, calendar.getTime());
            manager.addPaygradeIncrease(payGradeIncrease);
            payGradeIncreases[i] = payGradeIncrease;
        }

        var recPayGradeIncreases = manager.getAllPaygradeIncreases();

        for (int i = 0; i < 10; i++){
            assertEquals(payGradeIncreases[i].getId(), recPayGradeIncreases.get(i).getId());
            assertEquals(payGradeIncreases[i].getStart_date(), recPayGradeIncreases.get(i).getStart_date());
            assertEquals(payGradeIncreases[i].getNew_salary(), recPayGradeIncreases.get(i).getNew_salary());
        }
    }

    @Test
    void testGetRowCount(){
        var manager = new PaygradeIncreaseManager();
        manager.removeAllPaygradeIncreases();
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970,12,30);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        for (int i = 0; i < 10; i++){
            var payGradeIncrease = new PaygradeIncrease(5, 4.20, calendar.getTime());
            manager.addPaygradeIncrease(payGradeIncrease);
        }
        assertEquals(manager.getRowCount(5), 10);
    }

    @Test
    void testGetRowCountEmpty(){
        var manager = new PaygradeIncreaseManager();
        manager.removeAllPaygradeIncreases();
        assertEquals(manager.getRowCount(5), 0);
    }
}
