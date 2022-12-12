package excelchaos_view;

import excelchaos_controller.TabsController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class NewTabView extends MainFrame {
    private JMenuBar menubar;
    private JMenu actionMenu, helpMenu, docuMenu;
    private JMenuItem insertItem, updateItem, deleteItem, seeItem, aboutUsItem, docuItem;
    private TabsController tabs;



    Font f1 = new Font(Font.SANS_SERIF, Font.PLAIN, 13);
    Font f2 = new Font(Font.SANS_SERIF, Font.PLAIN, 15);

    public void init() {
        menubar = new JMenuBar();
        menubar.setBackground(Color.WHITE);
        actionMenu = new JMenu("Aktionen");
        actionMenu.setFont(f1);
        actionMenu.setMnemonic(KeyEvent.VK_A);
        helpMenu = new JMenu("Hilfe");
        helpMenu.setFont(f1);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        docuMenu = new JMenu("Dokumentation");
        docuMenu.setFont(f1);
        docuMenu.setMnemonic(KeyEvent.VK_D);
        //tabs = new DnDCloseButtonTabbedPane(this);

        menubar.add(actionMenu);
        menubar.add(helpMenu);
        menubar.add(docuMenu);
        insertItem = new JMenuItem("Neue Person anlegen");
        insertItem.setFont(f2);
        insertItem.setMnemonic(KeyEvent.VK_I);
        updateItem = new JMenuItem("Bestehende Einträge aktualisieren");
        updateItem.setFont(f2);
        updateItem.setMnemonic(KeyEvent.VK_U);
        deleteItem = new JMenuItem("Bestehende Einträge löschen");
        deleteItem.setFont(f2);
        deleteItem.setMnemonic(KeyEvent.VK_E);
        seeItem = new JMenuItem("Daten anzeigen");
        seeItem.setFont(f2);
        seeItem.setMnemonic(KeyEvent.VK_S);
        aboutUsItem = new JMenuItem("Über");
        aboutUsItem.setFont(f2);
        aboutUsItem.setMnemonic(KeyEvent.VK_U);
        docuItem = new JMenuItem("Dokumentation v1.0");
        docuItem.setFont(f2);
        docuItem.setMnemonic(KeyEvent.VK_T);
        actionMenu.add(seeItem);
        actionMenu.add(insertItem);
        actionMenu.add(updateItem);
        actionMenu.add(deleteItem);
        docuMenu.add(aboutUsItem);
        docuMenu.add(docuItem);
        tabs = new TabsController(this);


        setTitle("Excelchaos");
        setResizable(true);
        setSize(750, 750);
        setJMenuBar(menubar);
        add(tabs.getTabs());
    }

    public DnDCloseButtonTabbedPane getTabs() {
        return tabs.getTabs();
    }

    public JMenuItem getInsertItem() {
        return insertItem;
    }

    public JMenuItem getSeeItem() {
        return seeItem;
    }

    public void setActionListener(ActionListener l) {
        insertItem.addActionListener(l);
        seeItem.addActionListener(l);
    }
}
