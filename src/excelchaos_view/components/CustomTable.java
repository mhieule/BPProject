package excelchaos_view.components;

import excelchaos_model.customcomponentmodels.CustomTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Class CustomTable represents the view of one CustomTable. CustomTable extends the functionality of JTable in which it supports multiple rows selection (currently disable,
 * see the setAutoCheckboxSelection method below and hide/unhide multiple columns in the view.
 */

public class CustomTable extends JTable {
    //This attribute indicates weather the select dialog is already opened
    boolean isSelectDialogOpened = false;

    //This attribute indicates if a column is visible or it is currently hidden. It represents the mapping (index of column, visibility)
    Map<Integer, Boolean> columnVisibility = new HashMap<>();

    /**
     * Constructor of the CustomTable class
     * @param data the row values
     * @param header the header values of the columns
     */
    public CustomTable(String[][] data, String[] header) {
        super(new CustomTableModel(data, header));
        for (int i = 0; i < getColumnCount(); i++) {
            columnVisibility.put(i, true);
        }
        setColumnSelectionAllowed(false);
        setRowSelectionAllowed(true);
        setAutoCreateRowSorter(true);
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        init();

    }

    /**
     *  Purpose: This method is used to support multiple rows selection in the custom table view/model
     *  Side-effect: current implementation is not used because when a row is selected, it can only be deselected with 2 clicks on the checkbox
     */
    private void setAutoCheckboxSelection() {
        ListSelectionModel rowSM = getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //Ignore extra messages.
                if (e.getValueIsAdjusting()) return;

                //Handle the case when no rows are selected or when one or multiple rows are selected
                if (rowSM.isSelectionEmpty()) {
                } else {
                    int selectedRowStart = rowSM.getMinSelectionIndex();
                    int selectedRowEnd = rowSM.getMaxSelectionIndex();
                    for (int i = selectedRowStart; i < selectedRowEnd + 1; i++) {
                        setValueAt(true, i, 0);
                    }
                }
            }
        });
    }

    /**
     * Second constructor of the CustomTable class. It allows user to create a CustomTable from an existing DefaultTableModel
     * @param model the DefaultTableModel which the to-be-created CustomTable bases on
     */
    public CustomTable(DefaultTableModel model) {
        super(model);
    }

    /**
     * This method adds mouse listener to the table in order to open up the choose dialog when user right-clicks on any cell. Popupmenu can't be used
     * directly because it would not expose the location where the popupmenu is triggered. Therefore there would be no information about the column which
     * was clicked. Hence I opted for the workaround adding MouseListener to the component which triggers Popupmenu, in this case the table header.
     * Further details are explained in the comments in code.
     */

    private void init() {
        this.getTableHeader().addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                //super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    doPop(e);
                }
            }

            private void doPop(MouseEvent e) {
                //create a popup menu on click
                JPopupMenu popupMenu = new JPopupMenu();

                //create a final instance of MouseEvent e
                final MouseEvent CLICKED_EVENT = e;

                //create all items on the popupmenu
                JMenuItem copyItem = new JMenuItem("Kopieren");
                popupMenu.add(copyItem);
                JMenuItem pasteItem = new JMenuItem("Einfügen");
                popupMenu.add(pasteItem);
                JMenuItem hideThisColumnItem = new JMenuItem("Diese Spalte ausblenden");
                popupMenu.add(hideThisColumnItem);
                JMenuItem hideMultipleColumnsItem = new JMenuItem("Mehrere Spalten ein/ausblenden");
                popupMenu.add(hideMultipleColumnsItem);

                //show the popup menu on-click
                popupMenu.show(e.getComponent(), e.getX(), e.getY());

                //select the column which was clicked on

                int selectedColumn = columnAtPoint(e.getPoint());
                setColumnSelectionInterval(selectedColumn, selectedColumn);

                //add functionality to hideThisColumnItem
                hideThisColumnItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        columnVisibility.put(selectedColumn, false);
                        getColumnModel().getColumn(selectedColumn).setMinWidth(0);
                        getColumnModel().getColumn(selectedColumn).setMaxWidth(0);
                        getColumnModel().getColumn(selectedColumn).setPreferredWidth(0);
                        doLayout();
                    }
                });

                //add functionality to hideMultipleColumnsItem
                hideMultipleColumnsItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!isSelectDialogOpened) {
                            //change isSelectDialogOpened to true
                            isSelectDialogOpened = true;
                            JDialog dialog = new JDialog();
                            dialog.setLocationRelativeTo(dialog.getParent());
                            dialog.addWindowListener(new WindowAdapter() {
                                /**
                                 * Invoked when a window is in the process of being closed.
                                 * The close operation can be overridden at this point.
                                 *
                                 * @param e
                                 */
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    super.windowClosing(e);
                                    isSelectDialogOpened = false;
                                }
                            });
                            // create the panel with checkboxes
                            JPanel panel = new JPanel();
                            panel.setLayout(new BorderLayout());

                            //create a panel to display the names of the columns
                            JPanel fields = new JPanel();
                            fields.setLayout(new BoxLayout(fields, BoxLayout.Y_AXIS));
                            fields.add(new JLabel("Spaltenauswahl"));
                            panel.add(fields, BorderLayout.NORTH);


                            //add the name of the columns in form of a checkbox
                            for (int i = 0; i < getColumnCount(); i++) {
                                int finalI = i;
                                String columnName = getColumnName(i);
                                JCheckBox checkboxItem = new JCheckBox(columnName, columnVisibility.get(i));
                                checkboxItem.addItemListener(new ItemListener() {
                                    @Override
                                    public void itemStateChanged(ItemEvent e) {
                                        if (e.getStateChange() == ItemEvent.SELECTED) {
                                            columnVisibility.put(finalI, true);
                                            getColumnModel().getColumn(finalI).setMinWidth(20);
                                            getColumnModel().getColumn(finalI).setMaxWidth(10000);
                                            getColumnModel().getColumn(finalI).setPreferredWidth(100);
                                        } else {
                                            columnVisibility.put(finalI, false);
                                            getColumnModel().getColumn(finalI).setMinWidth(0);
                                            getColumnModel().getColumn(finalI).setMaxWidth(0);
                                            getColumnModel().getColumn(finalI).setPreferredWidth(0);
                                        }
                                        doLayout();
                                    }
                                });
                                fields.add(checkboxItem);
                            }
                            // add the panel to the JDialog
                            dialog.add(panel);
                            dialog.pack();
                            // show the JDialog
                            dialog.setVisible(true);
                            dialog.setPreferredSize(dialog.getPreferredSize());
                        }
                    }
                });
            }

        });

    }

    /**
     * The method is used to return the current selected row as a complete table with header to assist tasks involving selecting table rows
     *
     * @return CustomTable with header and without the first column of the normal CustomTable (checkbox column)
     */
    public CustomTable getCurrentSelectedRowAsTable() {
        DefaultTableModel result = new DefaultTableModel(null, getTableHeaderAsStringArray());
        for (int i = 0; i < this.getRowCount(); i++) {
            if ((Boolean) getValueAt(i, 0)) {
                Object[] rowResult = new Object[getColumnCount()];
                for (int j = 0; j < this.getColumnCount(); j++) {
                    rowResult[j] = getValueAt(i, j);
                }
                result.addRow(rowResult);
            }
        }
        CustomTable resultTable = new CustomTable(result);
        resultTable.getColumnModel().removeColumn(resultTable.getColumnModel().getColumn(0));
        return resultTable;
    }

    /**
     * Purpose: Loop through the CustomTable to retrieve the number of currently selected rows.
     * @return the number of the currently selected rows.
     */

    public int getNumberOfSelectedRows() {
        int selectedRows = 0;
        for (int row = 0; row < this.getRowCount(); row++) {
            if ((Boolean) getValueAt(row, 0)) {
                selectedRows++;
            }

        }
        return selectedRows;
    }

    /**
     * Purpose: Loop through the CustomTable to retrieve the ID of the employees in currently selected rows.
     * @return the ID of the employees in currently selected rows as String array.
     */
    public String[] getIdsOfCurrentSelectedRows() {
        int selectedRows = getNumberOfSelectedRows();

        String[] result = new String[selectedRows];
        int index = 0;
        for (int row = 0; row < this.getRowCount(); row++) {
            if ((Boolean) getValueAt(row, 0)) {
                result[index] = (String) getValueAt(row, 1);
                index++;

            }
        }
        return result;
    }

    /**
     * Purpose: deselect all the selected rows in current view
     */
    public void deselectSelectedRows() {
        for (int row = 0; row < this.getRowCount(); row++) {
            if ((Boolean) getValueAt(row, 0)) {
                setValueAt(false, row, 0);
            }
        }
    }

    /**
     * Purpose: retrieve all the currently selected rows as a 2D String array.
     * @return all the currently selected rows as a 2D String array.
     */

    public String[][] getCurrentSelectedRowsAsArray() {
        int selectedRows = 0;
        for (int row = 0; row < this.getRowCount(); row++) {
            if ((Boolean) getValueAt(row, 0)) {
                selectedRows++;
            }
        }
        String[][] result = new String[selectedRows][this.getColumnCount() - 1];
        int index = 0;
        for (int row = 0; row < this.getRowCount(); row++) {
            for (int column = 1; column < this.getColumnCount(); column++) {
                if ((Boolean) getValueAt(row, 0)) {
                    result[index][column - 1] = (String) getValueAt(row, column);

                }

            }
            if ((Boolean) getValueAt(row, 0)) {
                index++;
            }


        }
        return result;
    }

    /**
     * Purpose: check if there's any row in the view currently selected
     * @return true if there's any selected row, false otherwise
     */

    public boolean isRowCurrentlySelected() {
        int selectedRows = 0;
        for (int row = 0; row < this.getRowCount(); row++) {
            if ((Boolean) getValueAt(row, 0)) {
                selectedRows++;
            }
        }
        return selectedRows > 0;
    }

    /**
     * Same as the method above, this method is used to retrieve one row as a complete table
     *
     * @param rowIndex the row index indicating the row to be chosen to turn into a table
     * @return a JTable with header containing only the selected row
     * @see CustomTable
     */
    public JTable getRowAsTable(int rowIndex) {
        DefaultTableModel result = new DefaultTableModel(null, getTableHeaderAsStringArray());
        Object[] rowResult = new Object[getColumnCount()];
        for (int j = 0; j < this.getColumnCount(); j++) {
            rowResult[j] = getValueAt(rowIndex, j);

        }
        result.addRow(rowResult);
        return new JTable(result);
    }


    /**
     * This method is used to retrieve the headers of the table colummns.
     *
     * @return headers of the table colummns as string array
     */


    public String[] getTableHeaderAsStringArray() {
        String[] result = new String[getColumnCount()];
        for (int i = 0; i < getColumnCount(); i++) {
            result[i] = getColumnName(i);
        }
        return result;
    }

}


