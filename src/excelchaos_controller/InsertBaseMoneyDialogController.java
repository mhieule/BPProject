package excelchaos_controller;

import excelchaos_view.InsertBaseMoneyDialogView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InsertBaseMoneyDialogController implements ActionListener, MouseListener {
    private InsertBaseMoneyDialogView insertBaseMoneyDialogView;

    private InsertPayRateTableController insertPayRateTableController;
    private MainFrameController frameController;

    public InsertBaseMoneyDialogController(MainFrameController mainFrameController){
        frameController = mainFrameController;
        insertBaseMoneyDialogView = new InsertBaseMoneyDialogView();
        insertBaseMoneyDialogView.init();
        insertBaseMoneyDialogView.setActionListener(this);
        insertBaseMoneyDialogView.setMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insertBaseMoneyDialogView.getCloseButton()){
            insertBaseMoneyDialogView.dispose();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        insertBaseMoneyDialogView.showContextMenu(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
