package excelchaos_view;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InsertBaseMoneyDialogView extends JDialog {
    private JButton okayButton;
    private JButton closeButton;

    private JTextField insertField;

    private JPopupMenu popupMenu;

    private JLabel label, errorLabel;

    private JPanel textfieldPanel,buttonPanel;

    public void init(){
        setLayout(new BorderLayout());
        setTitle("Grundentgelt einfügen");

        okayButton = new JButton("Ok");
        closeButton = new JButton("Abbrechen");
        label = new JLabel("Bitte Grundentgelt hier einfügen");
        errorLabel = new JLabel("<html>Fehlerhafte Eingabe<br>Unterscheiden Sie Stufe 1A und 1B und Stufe 1</html>");
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        insertField = new JTextField();
        insertField.setPreferredSize(new Dimension(200,30));

        textfieldPanel = new JPanel();
        buttonPanel = new JPanel();


        buttonPanel.setLayout(new FlowLayout());
        textfieldPanel.setLayout(new GridBagLayout());
        textfieldPanel.setPreferredSize(new Dimension(280,50));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 1.0;
        textfieldPanel.add(label,constraints);
        textfieldPanel.add(errorLabel,constraints);
        constraints.gridy = 1;
        constraints.weighty = 0.0;
        textfieldPanel.add(insertField,constraints);
        createPopUpMenu();
        buttonPanel.add(okayButton);
        buttonPanel.add(closeButton);
        add(buttonPanel,BorderLayout.SOUTH);
        add(textfieldPanel,BorderLayout.CENTER);


        setSize(new Dimension(300, 200));
        setLocationRelativeTo(getParent());
        setAlwaysOnTop(true);
        setResizable(false);
        setVisible(true);
    }

    public void createPopUpMenu(){
        popupMenu = new JPopupMenu();
        JMenuItem pasteItem = new JMenuItem(new DefaultEditorKit.PasteAction());
        pasteItem.setText("Einfügen");
        JMenuItem copyItem = new JMenuItem(new DefaultEditorKit.CopyAction());
        copyItem.setText("Kopieren");
        JMenuItem cutItem = new JMenuItem(new DefaultEditorKit.CutAction());
        cutItem.setText("Ausschneiden");
        JMenuItem selectAll = new JMenuItem();
        selectAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertField.selectAll();
            }
        });
        selectAll.setText("Alles auswählen");

        popupMenu.add(selectAll);
        popupMenu.add(cutItem);
        popupMenu.add(copyItem);
        popupMenu.add(pasteItem);
    }

    public void showContextMenu(MouseEvent e){
        if(e.isPopupTrigger()){
            popupMenu.show(e.getComponent(),e.getX(),e.getY());
        }

    }

    public void setMouseListener(MouseListener m){
        insertField.addMouseListener(m);
    }

    public void setActionListener(ActionListener l){
        okayButton.addActionListener(l);
        closeButton.addActionListener(l);
    }

    public JButton getOkayButton() {
        return okayButton;
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public JLabel getErrorLabel() {
        return errorLabel;
    }

    public JLabel getLabel() {
        return label;
    }

    public JTextField getInsertField() {
        return insertField;
    }
}