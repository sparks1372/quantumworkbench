/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.ui.SubPanels.StatePanels;

import info.samratcliff.core.Problem.quantumproblem;
import info.samratcliff.core.qcevolutionbackend;
import info.samratcliff.ui.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

/**
 * @author Sam Ratcliff
 */
public class stateVisualiserTabPanel extends JPanel implements Observer {
    /**
     *
     */
    private static final long serialVersionUID = -6672031608491044781L;
    private final qcevolutionbackend backend;
    private static String qubits_string = " Qubit(s)";
    private static String psLabelStr = "Test Suite State Visualisation";
    private JLabel psLabel;
    private JPanel labelPanel;
    private final JTabbedPane tabPane;

    /**
     *
     */
    public stateVisualiserTabPanel(qcevolutionbackend be) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        backend = be;
        update(null, null);
        backend.addObserver(this);
        backend.getProbmanager().addObserver(this);

        tabPane = new JTabbedPane();
        setupLabels();

        this.add(labelPanel);
        this.add(tabPane);
    }

    private void setupLabels() {

        psLabel = new JLabel(psLabelStr);
        Font fancyFont = new Font(psLabel.getFont().getFontName(), psLabel
                .getFont().getStyle(), MainPanel.titleFontSize);
        psLabel.setFont(fancyFont);

        labelPanel = new JPanel();
        labelPanel.add(psLabel);

    }

    /*
     * (non-Javadoc)
     *
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
        quantumproblem qp = backend.getQproblem();
        if ((qp != null) && (qp.getTestSuite() != null)) {
            tabPane.removeAll();

            Set<Integer> keys = qp.getTestSuite().getKeys();

            Iterator<Integer> iter = keys.iterator();

            Component tab;
            while (iter.hasNext()) {
                int numofqubits = iter.next();
                tab = new stateVisualiserTab(qp.getTestSuite().getTestcases(
                        numofqubits), "");
                tabPane.add(
                        Integer.toString(numofqubits).concat(qubits_string),
                        tab);
            }
        }
    }
}
