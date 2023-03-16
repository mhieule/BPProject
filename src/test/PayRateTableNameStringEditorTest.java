import excelchaos_model.utility.PayRateTableNameStringEditor;
import org.junit.Test;
import static org.junit.Assert.*;

public class PayRateTableNameStringEditorTest {

    @Test
    public void testCreateReadableTableNameForView() {
        String originalName = "pay rates_2023";
        String expected = "pay rates 2023";
        String actual = PayRateTableNameStringEditor.createReadableTableNameForView(originalName);
        assertEquals(expected, actual);
    }

    @Test
    public void testRevertToCorrectTableName() {
        String tableName = "pay rates 2023";
        String expected = "pay rates_2023";
        String actual = PayRateTableNameStringEditor.revertToCorrectTableName(tableName);
        assertEquals(expected, actual);
    }
}
