/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.ui.SubPanels.ResultPanels;

import javax.swing.*;
import java.awt.*;

/**
 * @author Sam Ratcliff
 */
public class QCircuitPanel extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 6809659226104762074L;
    private static final String nameStr = "QCircuit Representation";
    private final JTextArea qcircuitLising;
    private final JScrollPane scrollPane;

    /**
     *
     */
    public QCircuitPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        qcircuitLising = new JTextArea();
        qcircuitLising.setEditable(false);

        scrollPane = new JScrollPane(qcircuitLising);
        scrollPane
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(250, 155));
        scrollPane.setMinimumSize(new Dimension(10, 10));

        add(scrollPane);

    }

    @Override
    public String getName() {
        return nameStr;
    }

    public void update(String latex) {
        qcircuitLising.setText(latex);
    }

}
