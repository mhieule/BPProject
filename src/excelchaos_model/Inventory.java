package excelchaos_model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Inventories")
public class Inventory {
    @DatabaseField(id = true)
    private int id;

    public Inventory(){}

    public Inventory(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

}
