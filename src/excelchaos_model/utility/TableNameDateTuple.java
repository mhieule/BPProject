package excelchaos_model.utility;

import java.time.LocalDate;
import java.util.Date;

public class TableNameDateTuple {
    public final String tableName;
    public final LocalDate date;

    public TableNameDateTuple(String tableName, LocalDate date) {
        this.tableName = tableName;
        this.date = date;
    }
}
