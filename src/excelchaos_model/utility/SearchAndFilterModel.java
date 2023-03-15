package excelchaos_model.utility;

import excelchaos_view.components.SearchPanelToolbar;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class SearchAndFilterModel {
    private TableRowSorter<TableModel> rowSorter;

    /**
     * This class provides a search and filter functionality for a JTable component based on the input provided by a JTextField.
     * It sets up a TableRowSorter on the JTable and adds a DocumentListener to the JTextField.
     * When the user inputs text into the text field, the table is filtered based on the search query. If the query is empty, the filter is removed.
     * The filter is case insensitive and uses a regular expression to match substrings in the rows of the table.
     *
     * @param table     The JTable to apply the search and filter functionality to.
     * @param textField The JTextField that provides the input for the search query.
     */


    public SearchAndFilterModel(JTable table, JTextField textField) {
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

    /**
     * This method sets up the SearchAndFilterModel to add search and filter functionality to the given JTable object
     * using the given SearchPanelToolbar object. The SearchAndFilterModel listens for changes to the text in the
     * search field of the SearchPanelToolbar and filters the table rows based on the entered search text.
     *
     * @param table   The JTable object to which search and filter functionality is to be added
     * @param toolBar The SearchPanelToolbar object containing the search field to be used for filtering the table rows
     */
    public static void setUpSearchAndFilterModel(JTable table, SearchPanelToolbar toolBar) {
        new SearchAndFilterModel(table, toolBar.getSearchPanel().getSearchField());
    }
}
