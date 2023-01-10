package excelchaos_model;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class SearchAndFilterModel {
    private TableRowSorter<TableModel> rowSorter;

    public SearchAndFilterModel(JTable table,JTextField textField){
        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String searching = textField.getText();
                if (searching.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searching));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String searching = textField.getText();

                if (searching.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searching));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }
}
