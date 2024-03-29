package excelchaos_view.components;

import excelchaos_controller.NewFrameController;
import excelchaos_view.NewFrame;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * This class is used to implement a drag-and-drop tabbed pane with close button
 */
public class DnDCloseButtonTabbedPane extends JTabbedPane {

    public static final long serialVersionUID = 1L;
    private static final int LINEWIDTH = 3;
    private static final String NAME = "TabTransferData";
    private final DataFlavor FLAVOR = new DataFlavor(
            DataFlavor.javaJVMLocalObjectMimeType, NAME);
    private static GhostGlassPane s_glassPane = new GhostGlassPane();

    private boolean m_isDrawRect = false;
    private final Rectangle2D m_lineRect = new Rectangle2D.Double();

    private final Color m_lineColor = new Color(0, 100, 255);
    private TabAcceptor m_acceptor = null;

    private final DropTarget dropTarget;

    private TabCloseButton button;
    private final ImageIcon icon;
    private final Dimension buttonSize;

    private JLabel label;


    /**
     * Constructor of the DnDCloseButtonTabbedPane
     *
     * @param parent component of the tabs
     */
    public DnDCloseButtonTabbedPane(final Component parent) {
        super();

        final DragSourceListener dsl = new DragSourceListener() {
            @Override
            public void dragEnter(DragSourceDragEvent e) {
                e.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
            }

            @Override
            public void dragExit(DragSourceEvent e) {
                e.getDragSourceContext()
                        .setCursor(DragSource.DefaultMoveNoDrop);
                m_lineRect.setRect(0, 0, 0, 0);
                m_isDrawRect = false;
                s_glassPane.setPoint(new Point(-1000, -1000));
                s_glassPane.repaint();
            }

            @Override
            public void dragOver(DragSourceDragEvent e) {
                //This method returns a Point indicating the cursor location in screen coordinates at the moment

                TabTransferData data = getTabTransferData(e);

                if (data == null) {
                    e.getDragSourceContext().setCursor(
                            DragSource.DefaultMoveNoDrop);
                    return;
                }

                e.getDragSourceContext().setCursor(
                        DragSource.DefaultMoveDrop);
            }

            public void dragDropEnd(DragSourceDropEvent e) {
                m_isDrawRect = false;
                m_lineRect.setRect(0, 0, 0, 0);

                if (hasGhost()) {
                    s_glassPane.setVisible(false);
                    s_glassPane.setImage(null);
                }

                // if drop failed, create new JFrame with JTabbedPane included with public access
                if (!e.getDropSuccess()) {
                    // MenuLight class Extends JFrame and Included 1 component JTabbedPane called superPane
                    NewFrameController m = new NewFrameController();
                    m.getWindow().setLocation(e.getLocation());
                    m.getWindow().setVisible(true);

                    // after create Frame, transfer the tab to other jtabbedpane
                    ((DnDCloseButtonTabbedPane) m.getTabs()).convertTab(getTabTransferData(e), getTargetTabIndex(e.getLocation()));
                }


                // if current JTabbedPane Tab is empty dispose it.
                if (getTabCount() < 1) {
                    // unfortunly i didnt want to close my Original menu, so check the class of parent of DnD is create from MenuLight and dispose it
                    if (parent.getClass().equals(NewFrame.class)) {
                        ((javax.swing.JFrame) parent).dispose();
                        return;
                    }
                }

            }

            @Override
            public void dropActionChanged(DragSourceDragEvent e) {

            }
        };

        final DragGestureListener dgl = new DragGestureListener() {
            @Override
            public void dragGestureRecognized(DragGestureEvent e) {

                Point tabPt = e.getDragOrigin();
                int dragTabIndex = indexAtLocation(tabPt.x, tabPt.y);
                if (dragTabIndex < 0) {
                    return;
                } // if

                initGlassPane(e.getComponent(), e.getDragOrigin(), dragTabIndex);
                try {
                    e.startDrag(DragSource.DefaultMoveDrop,
                            new TabTransferable(DnDCloseButtonTabbedPane.this, dragTabIndex), dsl);
                } catch (InvalidDnDOperationException idoe) {
                    idoe.printStackTrace();
                }
            }
        };

        //dropTarget =
        dropTarget = new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE,
                new CDropTargetListener(), true);
        new DragSource().createDefaultDragGestureRecognizer(this,
                DnDConstants.ACTION_COPY_OR_MOVE, dgl);
        m_acceptor = new TabAcceptor() {
            @Override
            public boolean isDropAcceptable(DnDCloseButtonTabbedPane a_component, int a_index) {
                return true;
            }
        };

        icon = new ImageIcon(getClass().getClassLoader().getResource("images/xsymbol.png"));
        buttonSize = new Dimension(icon.getIconWidth(), icon.getIconHeight());

    }

    /**
     * This method is used to add new Tab to the tabbedPane
     * @param title the title to be displayed in this tab
     * @param component the component to be displayed when this tab is clicked
     *
     */
    @Override
    public void addTab(String title, final Component component) {
        JPanel tab = new JPanel(new BorderLayout());
        tab.setOpaque(false);
        label = new JLabel(title);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 4));
        button = new TabCloseButton(icon);
        button.setPreferredSize(buttonSize);
        button.setOpaque(false);
        button.setTabName(title);

        button.setUI(new BasicButtonUI());
        button.setContentAreaFilled(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (component.getParent().getParent().getParent().getParent().getParent().getClass() == NewFrame.class) {
                    if (((DnDCloseButtonTabbedPane) component.getParent()).getTabCount() <= 1) {
                        ((javax.swing.JFrame) component.getParent().getParent().getParent().getParent().getParent()).dispose();
                    } else if (((DnDCloseButtonTabbedPane) component.getParent()).getTabCount() > 1) {
                        ((DnDCloseButtonTabbedPane) component.getParent()).remove(component);
                    }
                } else {
                    ((DnDCloseButtonTabbedPane) component.getParent()).remove(component);
                }


            }
        });
        tab.add(label, BorderLayout.WEST);
        tab.add(button, BorderLayout.EAST);
        tab.setBorder(BorderFactory.createEmptyBorder(2, 1, 1, 1));
        super.addTab(title, component);
        setTabComponentAt(indexOfComponent(component), tab);
    }

    /**
     * Sets the name of a tab
     *
     * @param title the new title of the tab
     */
    public void setLabel(String title) {
        label.setText(title);
    }

    public void setActionListener(ActionListener l) {
        button.addActionListener(l);
    }

    /**
     * Removes a component from the view
     *
     * @param component the component to be removed
     */
    public void removeTabNewWindow(Component component) {
        if (component.getParent().getParent().getParent().getParent().getParent().getClass() == NewFrame.class) {
            if (((DnDCloseButtonTabbedPane) component.getParent()).getTabCount() <= 1) {
                ((javax.swing.JFrame) component.getParent().getParent().getParent().getParent().getParent()).dispose();
            } else if (((DnDCloseButtonTabbedPane) component.getParent()).getTabCount() > 1) {
                ((DnDCloseButtonTabbedPane) component.getParent()).remove(component);
            }
        } else {
            ((DnDCloseButtonTabbedPane) component.getParent()).remove(component);
        }
    }

    public TabCloseButton getButton() {
        return button;
    }


    public TabAcceptor getAcceptor() {
        return m_acceptor;
    }

    public void setAcceptor(TabAcceptor a_value) {
        m_acceptor = a_value;
    }

    /**
     * Retrieves the TabTransferData from the given DropTargetDropEvent, if present.
     *
     * @param a_event The DropTargetDropEvent to retrieve the data from.
     * @return The TabTransferData contained within the given DropTargetDropEvent, or null if no such data is present.
     */
    private TabTransferData getTabTransferData(DropTargetDropEvent a_event) {
        try {
            TabTransferData data = (TabTransferData) a_event.getTransferable().getTransferData(FLAVOR);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieves the TabTransferData from the given DropTargetDropEvent, if present.
     *
     * @param a_event The DropTargetDropEvent to retrieve the data from.
     * @return The TabTransferData contained within the given DropTargetDropEvent, or null if no such data is present.
     */
    private TabTransferData getTabTransferData(DropTargetDragEvent a_event) {
        try {
            TabTransferData data = (TabTransferData) a_event.getTransferable().getTransferData(FLAVOR);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Retrieves the TabTransferData from the given DropTargetDropEvent, if present.
     *
     * @param a_event The DropTargetDropEvent to retrieve the data from.
     * @return The TabTransferData contained within the given DropTargetDropEvent, or null if no such data is present.
     */
    private TabTransferData getTabTransferData(DragSourceDragEvent a_event) {
        try {
            TabTransferData data = (TabTransferData) a_event.getDragSourceContext()
                    .getTransferable().getTransferData(FLAVOR);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Retrieves the TabTransferData from the given DropTargetDropEvent, if present.
     *
     * @param a_event The DropTargetDropEvent to retrieve the data from.
     * @return The TabTransferData contained within the given DropTargetDropEvent, or null if no such data is present.
     */
    private TabTransferData getTabTransferData(DragSourceDropEvent a_event) {
        try {
            TabTransferData data = (TabTransferData) a_event.getDragSourceContext()
                    .getTransferable().getTransferData(FLAVOR);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    class TabTransferable implements Transferable {

        private TabTransferData m_data = null;

        public TabTransferable(DnDCloseButtonTabbedPane a_tabbedPane, int a_tabIndex) {
            m_data = new TabTransferData(DnDCloseButtonTabbedPane.this, a_tabIndex);
        }

        public Object getTransferData(DataFlavor flavor) {
            return m_data;
            // return DnDTabbedPane.this;
        }

        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] f = new DataFlavor[1];
            f[0] = FLAVOR;
            return f;
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.getHumanPresentableName().equals(NAME);
        }
    }

    class TabTransferData {

        private DnDCloseButtonTabbedPane m_tabbedPane = null;
        private int m_tabIndex = -1;

        public TabTransferData() {
        }

        public TabTransferData(DnDCloseButtonTabbedPane a_tabbedPane, int a_tabIndex) {
            m_tabbedPane = a_tabbedPane;
            m_tabIndex = a_tabIndex;
        }

        public DnDCloseButtonTabbedPane getTabbedPane() {
            return m_tabbedPane;
        }

        public void setTabbedPane(DnDCloseButtonTabbedPane pane) {
            m_tabbedPane = pane;
        }

        public int getTabIndex() {
            return m_tabIndex;
        }

        public void setTabIndex(int index) {
            m_tabIndex = index;
        }
    }

    private Point buildGhostLocation(Point a_location) {
        Point retval = new Point(a_location);

        retval = SwingUtilities.convertPoint(DnDCloseButtonTabbedPane.this,
                retval, s_glassPane);
        return retval;
    }

    class CDropTargetListener implements DropTargetListener {

        public void dragEnter(DropTargetDragEvent e) {

            if (isDragAcceptable(e)) {
                e.acceptDrag(e.getDropAction());
            } else {
                e.rejectDrag();
            } // if
        }

        public void dragExit(DropTargetEvent e) {
            m_isDrawRect = false;
        }

        public void dropActionChanged(DropTargetDragEvent e) {
        }

        public void dragOver(final DropTargetDragEvent e) {
            TabTransferData data = getTabTransferData(e);

            if (getTabPlacement() == JTabbedPane.TOP
                    || getTabPlacement() == JTabbedPane.BOTTOM) {
                initTargetLeftRightLine(getTargetTabIndex(e.getLocation()), data);
            } else {
                initTargetTopBottomLine(getTargetTabIndex(e.getLocation()), data);
            } // if-else

            repaint();
            if (hasGhost()) {
                s_glassPane.setPoint(buildGhostLocation(e.getLocation()));
                s_glassPane.repaint();
            }
        }

        @Override
        public void drop(DropTargetDropEvent a_event) {

            if (isDropAcceptable(a_event)) {
                convertTab(getTabTransferData(a_event),
                        getTargetTabIndex(a_event.getLocation()));
                a_event.dropComplete(true);

            } else {
                a_event.dropComplete(false);
            } // if-else

            m_isDrawRect = false;
            repaint();
        }

        public boolean isDragAcceptable(DropTargetDragEvent e) {
            Transferable t = e.getTransferable();
            if (t == null) {
                return false;
            } // if

            DataFlavor[] flavor = e.getCurrentDataFlavors();
            if (!t.isDataFlavorSupported(flavor[0])) {
                return false;
            } // if

            TabTransferData data = getTabTransferData(e);

            if (DnDCloseButtonTabbedPane.this == data.getTabbedPane()
                    && data.getTabIndex() >= 0) {
                return true;
            } // if

            if (DnDCloseButtonTabbedPane.this != data.getTabbedPane()) {
                if (m_acceptor != null) {
                    return m_acceptor.isDropAcceptable(data.getTabbedPane(), data.getTabIndex());
                } // if
            } // if

            boolean transferDataFlavorFound = false;
            for (DataFlavor transferDataFlavor : t.getTransferDataFlavors()) {
                if (FLAVOR.equals(transferDataFlavor)) {
                    transferDataFlavorFound = true;
                    break;
                }
            }
            if (transferDataFlavorFound == false) {
                return false;
            }
            return false;
        }

        public boolean isDropAcceptable(DropTargetDropEvent e) {

            Transferable t = e.getTransferable();
            if (t == null) {
                return false;
            } // if

            DataFlavor[] flavor = e.getCurrentDataFlavors();
            if (!t.isDataFlavorSupported(flavor[0])) {
                return false;
            } // if

            TabTransferData data = getTabTransferData(e);

            if (DnDCloseButtonTabbedPane.this == data.getTabbedPane()
                    && data.getTabIndex() >= 0) {
                return true;
            } // if

            if (DnDCloseButtonTabbedPane.this != data.getTabbedPane()) {
                if (m_acceptor != null) {
                    return m_acceptor.isDropAcceptable(data.getTabbedPane(), data.getTabIndex());
                } // if
            } // if

            return false;
        }
    }

    private boolean m_hasGhost = true;

    public void setPaintGhost(boolean flag) {
        m_hasGhost = flag;
    }

    public boolean hasGhost() {
        return m_hasGhost;
    }

    /**
     * returns potential index for drop.
     *
     * @param a_point point given in the drop site component's coordinate
     * @return returns potential index for drop.
     */
    private int getTargetTabIndex(Point a_point) {
        boolean isTopOrBottom = getTabPlacement() == JTabbedPane.TOP
                || getTabPlacement() == JTabbedPane.BOTTOM;

        // if the pane is empty, the target index is always zero.
        if (getTabCount() == 0) {
            return 0;
        } // if

        for (int i = 0; i < getTabCount(); i++) {
            Rectangle r = getBoundsAt(i);
            if (isTopOrBottom) {
                r.setRect(r.x - r.width / 2, r.y, r.width, r.height);
            } else {
                r.setRect(r.x, r.y - r.height / 2, r.width, r.height);
            } // if-else

            if (r.contains(a_point)) {
                return i;
            } // if
        } // for

        Rectangle r = getBoundsAt(getTabCount() - 1);
        if (isTopOrBottom) {
            int x = r.x + r.width / 2;
            r.setRect(x, r.y, getWidth() - x, r.height);
        } else {
            int y = r.y + r.height / 2;
            r.setRect(r.x, y, r.width, getHeight() - y);
        } // if-else

        return r.contains(a_point) ? getTabCount() : -1;
    }

    private void convertTab(TabTransferData a_data, int a_targetIndex) {
        DnDCloseButtonTabbedPane source = a_data.getTabbedPane();
        int sourceIndex = a_data.getTabIndex();
        if (sourceIndex < 0) {
            return;
        } // if
        //Save the tab's component, title, and TabComponent.
        Component cmp = source.getComponentAt(sourceIndex);
        String str = source.getTitleAt(sourceIndex);
        Component tcmp = source.getTabComponentAt(sourceIndex);

        if (this != source) {
            source.remove(sourceIndex);

            if (a_targetIndex == getTabCount()) {
                addTab(str, cmp);
                setTabComponentAt(getTabCount() - 1, tcmp);
            } else {
                if (a_targetIndex < 0) {
                    a_targetIndex = 0;
                } // if
                insertTab(str, null, cmp, null, a_targetIndex);
                setTabComponentAt(a_targetIndex, tcmp);
            } // if

            setSelectedComponent(cmp);
            return;
        } // if
        if (a_targetIndex < 0 || sourceIndex == a_targetIndex) {
            return;
        } // if
        if (a_targetIndex == getTabCount()) {
            source.remove(sourceIndex);
            addTab(str, cmp);
            setTabComponentAt(getTabCount() - 1, tcmp);
            setSelectedIndex(getTabCount() - 1);

        } else if (sourceIndex > a_targetIndex) {
            source.remove(sourceIndex);
            insertTab(str, null, cmp, null, a_targetIndex);
            setTabComponentAt(a_targetIndex, tcmp);
            setSelectedIndex(a_targetIndex);

        } else {
            source.remove(sourceIndex);
            insertTab(str, null, cmp, null, a_targetIndex - 1);
            setTabComponentAt(a_targetIndex - 1, tcmp);
            setSelectedIndex(a_targetIndex - 1);
        }
    }

    private void initTargetLeftRightLine(int next, TabTransferData a_data) {
        if (next < 0) {
            m_lineRect.setRect(0, 0, 0, 0);
            m_isDrawRect = false;
            return;
        } // if

        if ((a_data.getTabbedPane() == this)
                && (a_data.getTabIndex() == next
                || next - a_data.getTabIndex() == 1)) {
            m_lineRect.setRect(0, 0, 0, 0);
            m_isDrawRect = false;
        } else if (getTabCount() == 0) {
            m_lineRect.setRect(0, 0, 0, 0);
            m_isDrawRect = false;
            return;
        } else if (next == 0) {
            Rectangle rect = getBoundsAt(0);
            m_lineRect.setRect(-LINEWIDTH / 2, rect.y, LINEWIDTH, rect.height);
            m_isDrawRect = true;
        } else if (next == getTabCount()) {
            Rectangle rect = getBoundsAt(getTabCount() - 1);
            m_lineRect.setRect(rect.x + rect.width - LINEWIDTH / 2, rect.y,
                    LINEWIDTH, rect.height);
            m_isDrawRect = true;
        } else {
            Rectangle rect = getBoundsAt(next - 1);
            m_lineRect.setRect(rect.x + rect.width - LINEWIDTH / 2, rect.y,
                    LINEWIDTH, rect.height);
            m_isDrawRect = true;
        }
    }

    private void initTargetTopBottomLine(int next, TabTransferData a_data) {
        if (next < 0) {
            m_lineRect.setRect(0, 0, 0, 0);
            m_isDrawRect = false;
            return;
        } // if

        if ((a_data.getTabbedPane() == this)
                && (a_data.getTabIndex() == next
                || next - a_data.getTabIndex() == 1)) {
            m_lineRect.setRect(0, 0, 0, 0);
            m_isDrawRect = false;
        } else if (getTabCount() == 0) {
            m_lineRect.setRect(0, 0, 0, 0);
            m_isDrawRect = false;
            return;
        } else if (next == getTabCount()) {
            Rectangle rect = getBoundsAt(getTabCount() - 1);
            m_lineRect.setRect(rect.x, rect.y + rect.height - LINEWIDTH / 2,
                    rect.width, LINEWIDTH);
            m_isDrawRect = true;
        } else if (next == 0) {
            Rectangle rect = getBoundsAt(0);
            m_lineRect.setRect(rect.x, -LINEWIDTH / 2, rect.width, LINEWIDTH);
            m_isDrawRect = true;
        } else {
            Rectangle rect = getBoundsAt(next - 1);
            m_lineRect.setRect(rect.x, rect.y + rect.height - LINEWIDTH / 2,
                    rect.width, LINEWIDTH);
            m_isDrawRect = true;
        }
    }

    private void initGlassPane(Component c, Point tabPt, int a_tabIndex) {
        //Point p = (Point) pt.clone();
        getRootPane().setGlassPane(s_glassPane);
        if (hasGhost()) {
            Rectangle rect = getBoundsAt(a_tabIndex);
            BufferedImage image = new BufferedImage(c.getWidth(),
                    c.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = image.getGraphics();
            c.paint(g);
            image = image.getSubimage(rect.x, rect.y, rect.width, rect.height);
            s_glassPane.setImage(image);
        } // if

        s_glassPane.setPoint(buildGhostLocation(tabPt));
        s_glassPane.setVisible(true);
    }

    private Rectangle getTabAreaBound() {
        Rectangle lastTab = getUI().getTabBounds(this, getTabCount() - 1);
        return new Rectangle(0, 0, getWidth(), lastTab.y + lastTab.height);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (m_isDrawRect) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setPaint(m_lineColor);
            g2.fill(m_lineRect);
        } // if
    }

    public interface TabAcceptor {

        boolean isDropAcceptable(DnDCloseButtonTabbedPane a_component, int a_index);
    }
}

class GhostGlassPane extends JPanel {

    public static final long serialVersionUID = 1L;
    private final AlphaComposite m_composite;

    private Point m_location = new Point(0, 0);

    private BufferedImage m_draggingGhost = null;

    public GhostGlassPane() {
        setOpaque(false);
        m_composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
    }

    public void setImage(BufferedImage draggingGhost) {
        m_draggingGhost = draggingGhost;
    }

    public void setPoint(Point a_location) {
        m_location.x = a_location.x;
        m_location.y = a_location.y;
    }

    public int getGhostWidth() {
        if (m_draggingGhost == null) {
            return 0;
        } // if

        return m_draggingGhost.getWidth(this);
    }

    public int getGhostHeight() {
        if (m_draggingGhost == null) {
            return 0;
        } // if

        return m_draggingGhost.getHeight(this);
    }

    public void paintComponent(Graphics g) {
        if (m_draggingGhost == null) {
            return;
        } // if

        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(m_composite);

        g2.drawImage(m_draggingGhost, (int) m_location.getX(), (int) m_location.getY(), null);
    }
}
