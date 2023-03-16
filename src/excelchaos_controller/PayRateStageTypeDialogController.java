package excelchaos_controller;

import excelchaos_view.PayRateStageTypeDialogView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PayRateStageTypeDialogController implements ActionListener {
    private PayRateStageTypeDialogView payRateStageTypeDialogView;
    private InsertPayRateTableController insertPayRateTableController;

    private PayRateTablesController payRateController;
    private MainFrameController frameController;

    private final String[] columns13WithAAndB = {
            "%-Satz", "E13 St. 1A VBL-befreit", "E13 St. 1A VBL-pflichtig", "E13 St. 1B VBL-befreit", "E13 St. 1B VBL-pflichtig", "E13 St. 2 VBL-befreit", "E13 St. 2 VBL-pflichtig", "E13 St. 3 VBL-befreit", "E13 St. 3 VBL-pflichtig",
            "E13 St. 4 VBL-befreit", "E13 St. 4 VBL-pflichtig", "E13 St. 5 VBL-befreit", "E13 St. 5 VBL-pflichtig", "E13 St. 6 VBL-befreit", "E13 St. 6 VBL-pflichtig"
    };
    private final String[] columns13WithoutAAndB = {
            "%-Satz", "E13 St. 1 VBL-befreit", "E13 St. 1 VBL-pflichtig", "E13 St. 2 VBL-befreit", "E13 St. 2 VBL-pflichtig", "E13 St. 3 VBL-befreit", "E13 St. 3 VBL-pflichtig",
            "E13 St. 4 VBL-befreit", "E13 St. 4 VBL-pflichtig", "E13 St. 5 VBL-befreit", "E13 St. 5 VBL-pflichtig", "E13 St. 6 VBL-befreit", "E13 St. 6 VBL-pflichtig"
    };
    private final String[] columns14WithAAndB = {
            "%-Satz", "E14 St. 1A VBL-befreit", "E14 St. 1A VBL-pflichtig", "E14 St. 1B VBL-befreit", "E14 St. 1B VBL-pflichtig", "E14 St. 2 VBL-befreit", "E14 St. 2 VBL-pflichtig", "E14 St. 3 VBL-befreit", "E14 St. 3 VBL-pflichtig",
            "E14 St. 4 VBL-befreit", "E14 St. 4 VBL-pflichtig", "E14 St. 5 VBL-befreit", "E14 St. 5 VBL-pflichtig", "E14 St. 6 VBL-befreit", "E14 St. 6 VBL-pflichtig"
    };
    private final String[] columns14WithoutAAndB = {
            "%-Satz", "E14 St. 1 VBL-befreit", "E14 St. 1 VBL-pflichtig", "E14 St. 2 VBL-befreit", "E14 St. 2 VBL-pflichtig", "E14 St. 3 VBL-befreit", "E14 St. 3 VBL-pflichtig",
            "E14 St. 4 VBL-befreit", "E14 St. 4 VBL-pflichtig", "E14 St. 5 VBL-befreit", "E14 St. 5 VBL-pflichtig", "E14 St. 6 VBL-befreit", "E14 St. 6 VBL-pflichtig"
    };

    /**
     * Constructor for pay rate stage type dialog controller
     *
     * @param mainFrameController     main frame controller
     * @param payRateTablesController pay rate tables controller
     */

    public PayRateStageTypeDialogController(MainFrameController mainFrameController, PayRateTablesController payRateTablesController) {
        frameController = mainFrameController;
        payRateController = payRateTablesController;
        payRateStageTypeDialogView = new PayRateStageTypeDialogView();
        payRateStageTypeDialogView.init();
        payRateStageTypeDialogView.setActionListener(this);
    }

    /**
     * Checks if action has been performed
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == payRateStageTypeDialogView.getCloseButton()) {
            payRateStageTypeDialogView.dispose();
        }
        if (e.getSource() == payRateStageTypeDialogView.getOkayButton()) {
            if (payRateController.getTitle() == "E13 Entgelttabellen" && tablePayRateStageType()) {
                createInsertPayRateTable("E13 Entgelttabelle mit Stufe 1A und 1B hinzufügen", columns13WithAAndB, "E13 Entgelttabellen");
            } else if (payRateController.getTitle() == "E13 Entgelttabellen" && !tablePayRateStageType()) {
                createInsertPayRateTable("E13 Entgelttabelle mit Stufe 1 hinzufügen", columns13WithoutAAndB, "E13 Entgelttabellen");
            } else if (payRateController.getTitle() == "E14 Entgelttabellen" && tablePayRateStageType()) {
                createInsertPayRateTable("E14 Entgelttabelle mit Stufe 1A und 1B hinzufügen", columns14WithAAndB, "E14 Entgelttabellen");
            } else if (payRateController.getTitle() == "E14 Entgelttabellen" && !tablePayRateStageType()) {
                createInsertPayRateTable("E14 Entgelttabelle mit Stufe 1 hinzufügen", columns14WithoutAAndB, "E14 Entgelttabellen");
            }
            payRateStageTypeDialogView.dispose();
        }

    }

    private String getComboBoxSelection() {
        return payRateStageTypeDialogView.getStageTypeSelecter().getSelectedItem().toString();
    }

    /**
     * Checks if Entgeldtabelle is with 1A and 1B
     *
     * @return
     */
    private boolean tablePayRateStageType() {
        if (getComboBoxSelection() == "Entgeltabelle mit Stufe 1A und 1B") {
            return true;
        } else return false;
    }

    /**
     * Creates InsertPayRateTable
     *
     * @param title         title of the table
     * @param columnNames   names of the table's columns
     * @param originalTitle original title
     */
    private void createInsertPayRateTable(String title, String[] columnNames, String originalTitle) {
        payRateController.setTitle(title);
        insertPayRateTableController = new InsertPayRateTableController(frameController, payRateController, payRateController.getTitle(), columnNames, tablePayRateStageType());
        insertPayRateTableController.showInsertPayRateTableView(frameController);
        payRateController.setTitle(originalTitle);

    }
}
