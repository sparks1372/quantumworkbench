/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.ui.SubPanels;

import info.samratcliff.core.qcevolutionbackend;
import info.samratcliff.ui.SubPanels.SelectionPanels.FitnessFunctionSelectionPanel;
import info.samratcliff.ui.SubPanels.SelectionPanels.SearchEngineSelectionPanel;

import javax.swing.*;

/**
 * @author Sam Ratcliff
 */
public class LPanel extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 7152600839227512484L;
    private final SearchEngineSelectionPanel se_select_panel;
    private final FitnessFunctionSelectionPanel ff_select_panel;

    /**
     *
     */
    public LPanel(qcevolutionbackend be) {
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        se_select_panel = new SearchEngineSelectionPanel(be);
        ff_select_panel = new FitnessFunctionSelectionPanel(be);

        this.add(se_select_panel);
        this.add(ff_select_panel);
    }
}
