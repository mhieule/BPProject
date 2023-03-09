package excelchaos_controller;

import excelchaos_model.CustomTableColumnAdjuster;
import excelchaos_model.CustomTableModel;
import excelchaos_model.SearchAndFilterModel;
import excelchaos_model.database.SHKSalaryEntry;
import excelchaos_model.database.SHKSalaryTableManager;
import excelchaos_model.utility.StringAndBigDecimalFormatter;
import excelchaos_view.ShowSHKTableView;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ShowSHKTableController implements TableModelListener {

    private SHKSalaryTableManager shkSalaryTableManager = SHKSalaryTableManager.getInstance();

    private ShowSHKTableView showSHKTableView;

    private ToolbarShowSHKTableController toolbarShowSHKTableController;

    private MainFrameController frameController;

    private String title = "SHK Entgelttabelle";

    private String[] columns = {
            "ID", "Stundensätze gültig ab", "SHK Basisvergütung", "SHK erhöhter Stundensatz", "WHK"
    };


    public ShowSHKTableController(MainFrameController mainFrameController){
        frameController = mainFrameController;
        showSHKTableView = new ShowSHKTableView();
        showSHKTableView.init();
        createTableWithData(getSHKDataFromDatabase());
        toolbarShowSHKTableController = new ToolbarShowSHKTableController(frameController,this);
        showSHKTableView.add(toolbarShowSHKTableController.getToolbar(), BorderLayout.NORTH);
        SearchAndFilterModel.setUpSearchAndFilterModel(showSHKTableView.getTable(),toolbarShowSHKTableController.getToolbar());
    }

    public void showSHKTableView(MainFrameController mainFrameController){
        if (mainFrameController.getTabs().indexOfTab(title) == -1) {
            mainFrameController.addTab(title, showSHKTableView);
        } else {
            mainFrameController.getTabs().setSelectedIndex(mainFrameController.getTabs().indexOfTab(title));
        }
    }

    public String[][] getSHKDataFromDatabase(){
        int lines = shkSalaryTableManager.getRowCount();
        String[][] resultData = new String[lines][];
        int currentIndex = 0;
        List<SHKSalaryEntry> shkSalaryEntries = shkSalaryTableManager.getAllSHKSalaryEntries();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        for (SHKSalaryEntry entry : shkSalaryEntries){
            String id = String.valueOf(entry.getId());
            String validationDate = dateFormat.format(entry.getValidationDate());
            String basePayRate = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(entry.getBasePayRate());
            String extendedPayRate = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(entry.getExtendedPayRate());
            String whkPayRate = StringAndBigDecimalFormatter.formatBigDecimalCurrencyToString(entry.getWHKPayRate());

            String[] values = {id,validationDate,basePayRate,extendedPayRate,whkPayRate};
            resultData[currentIndex] = values;
            currentIndex++;
        }

        return resultData;

    }


    private void createTableWithData(String [][] tableData){
        showSHKTableView.createSHKTable(tableData,columns);
        showSHKTableView.getTable().getModel().addTableModelListener(this);
    }

    public void updateData(String[][] tableData){
        CustomTableModel customTableModel = new CustomTableModel(tableData, columns);
        showSHKTableView.getTable().setModel(customTableModel);
        showSHKTableView.getTable().getColumnModel().getColumn(1).setMinWidth(0);
        showSHKTableView.getTable().getColumnModel().getColumn(1).setMaxWidth(0);
        showSHKTableView.getTable().getColumnModel().getColumn(1).setWidth(0);
        CustomTableColumnAdjuster tca = new CustomTableColumnAdjuster(showSHKTableView.getTable());
        tca.adjustColumns();
        customTableModel.addTableModelListener(this);

        SearchAndFilterModel.setUpSearchAndFilterModel(showSHKTableView.getTable(), toolbarShowSHKTableController.getToolbar());
        toolbarShowSHKTableController.getToolbar().getEditEntry().setEnabled(false);
    }

    public void deleteSHKEntries(String[] entryIDs){
        int id = 0;
        for (int i = 0; i < entryIDs.length; i++) {
            id = Integer.parseInt(entryIDs[i]);
            shkSalaryTableManager.removeSHKSalaryEntry(id);
        }
        updateData(getSHKDataFromDatabase());
        //TODO Wahrscheinlich SalaryUpdate aufrufen
    }

    public ShowSHKTableView getShowSHKTableView() {
        return showSHKTableView;
    }

    public ToolbarShowSHKTableController getToolbarShowSHKTableController() {
        return toolbarShowSHKTableController;
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int numberOfSelectedRows = showSHKTableView.getTable().getNumberOfSelectedRows();
        if (e.getColumn() == 0) {
            if (numberOfSelectedRows == 0) {
                toolbarShowSHKTableController.getToolbar().getEditEntry().setEnabled(false);
                toolbarShowSHKTableController.getToolbar().getDeleteEntry().setEnabled(false);
            } else if (numberOfSelectedRows == 1) {
                toolbarShowSHKTableController.getToolbar().getEditEntry().setEnabled(true);
                toolbarShowSHKTableController.getToolbar().getDeleteEntry().setEnabled(true);
            } else {
                toolbarShowSHKTableController.getToolbar().getEditEntry().setEnabled(false);
                toolbarShowSHKTableController.getToolbar().getDeleteEntry().setEnabled(true);
            }
        }
    }
}
