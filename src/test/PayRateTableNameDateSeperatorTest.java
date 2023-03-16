import excelchaos_model.utility.PayRateTableNameDateSeperator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class PayRateTableNameDateSeperatorTest {

    @Test
    public void testSeperateDateAsString() {
        PayRateTableNameDateSeperator separator = new PayRateTableNameDateSeperator();
        String tableName = "payrate_15.03.2023";
        String expectedDate = "15.03.2023";
        Assertions.assertEquals(expectedDate, separator.seperateDateAsString(tableName));
    }

    @Test
    public void testSeperateName() {
        PayRateTableNameDateSeperator separator = new PayRateTableNameDateSeperator();
        String tableName = "payrate_15.03.2023";
        String expectedName = "payrate";
        Assertions.assertEquals(expectedName, separator.seperateName(tableName));
    }

    @Test
    public void testSeperateDateAsDate() {
        PayRateTableNameDateSeperator separator = new PayRateTableNameDateSeperator();
        String tableName = "payrate_15.03.2023";
        LocalDate expectedDate = LocalDate.of(2023, 3, 15);
        Assertions.assertEquals(expectedDate, separator.seperateDateAsDate(tableName));
    }
}
