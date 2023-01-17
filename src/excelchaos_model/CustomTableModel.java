package excelchaos_model;

import javax.swing.table.DefaultTableModel;
import java.util.Arrays;

public class CustomTableModel extends DefaultTableModel /*implements TableModelListener*/ {

    private String[][] data;
    private String[] header;
    private boolean[] checkboxValues;
    public CustomTableModel(String[][] data, String[] header) {
        super(data, Arrays.copyOf(header, header.length));
        this.header=header;
        this.data=data;


        checkboxValues = new boolean[data.length];
        for (int i = 0; i < data.length; i++) {
            checkboxValues[i]=false;
        }

    }

    @Override
    public String getColumnName(int column) {
        if(column==0) {
            return "";
        } else return header[column-1];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        if(column==0){
            return true;
        } else return super.isCellEditable(row, column);
    }

    /**
     * Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     */
    @Override
    public int getRowCount() {
        return super.getRowCount();
    }

    /**
     * Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     * @see #getRowCount
     */
    @Override
    public int getColumnCount() {
        return super.getColumnCount()+1;
    }


    /**
     * Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param rowIndex    the row whose value is to be queried
     * @param columnIndex the column whose value is to be queried
     * @return the value Object at the specified cell
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex==0){
            return checkboxValues[rowIndex];
        }
        return super.getValueAt(rowIndex, columnIndex-1);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(columnIndex==0){
            checkboxValues[rowIndex]=(boolean) aValue;
        } else super.setValueAt(aValue, rowIndex, columnIndex-1);

    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex == 0){
            return Boolean.class;
        }
        return super.getColumnClass(columnIndex);
    }


    /*@Override
    public void tableChanged(TableModelEvent e) {
        if(e.getType()==TableModelEvent.UPDATE){
            int row=e.getFirstRow();
            int column=e.getColumn();
            System.out.println("Row and column has been changed at "+row+".."+column);
            System.out.println("New value "+getValueAt(row, column));
        }
    }*/


}
