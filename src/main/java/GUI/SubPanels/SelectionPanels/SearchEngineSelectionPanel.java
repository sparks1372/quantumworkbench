/**
 * @Author = Sam Ratcliff
 */
package GUI.SubPanels.SelectionPanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import Core.qcevolutionbackend;
import Core.CircuitEvolution.SearchEngineState;
import GUI.MainPanel;

import com.google.inject.Inject;

/**
 * @author Sam Ratcliff
 * 
 */
public class SearchEngineSelectionPanel extends JPanel implements
		ActionListener, Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4691811963577024360L;
	private final JComboBox selection;
	private final ComboBoxModel selection_model;
	private final JTextPane description;
	private final JScrollPane description_scroller;
	private final qcevolutionbackend backend;
	private static String psLabelStr = "Search Engine Selection";
	private JLabel psLabel;
	private JPanel labelPanel;

	/**
	 * 
	 */
	@Inject
	public SearchEngineSelectionPanel(qcevolutionbackend be) {
		this.setLayout(new BorderLayout());
		backend = be;

		Set<String> probs = backend.getSemanager().getAvailableSearchEngines();
		String[] options = new String[probs.size() + 1];
		options[0] = "Please Select Search Engine";
		int index = 1;
		Iterator<String> iter = probs.iterator();
		while (iter.hasNext()) {
			options[index++] = iter.next();
		}
		selection_model = new DefaultComboBoxModel(options);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		selection = new JComboBox(selection_model);
		selection.addActionListener(this);
		selection.setPreferredSize(new Dimension(
				(int) (screenSize.width * MainPanel.left_perc), 30));
		selection.setMaximumSize(new Dimension(
				(int) (screenSize.width * MainPanel.left_perc), 30));

		description = new JTextPane();
		description.setEditable(false);
		description_scroller = new JScrollPane(description);
		description_scroller
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// description_scroller.setPreferredSize(new Dimension(250, 155));
		description_scroller.setMinimumSize(new Dimension(10, 10));

		if (backend.getCurrentse() != null) {
			String key = backend.getCurrentse().getName();
			selection_model.setSelectedItem(key);
			description
					.setText(backend.getSemanager().getSearchEngineDesc(key));
		}

		backend.addObserver(this);
		if (null != backend.getCurrentse()) {
			backend.getCurrentse().addObserver(this);
		}

		setupLabels();

		JPanel npanel = new JPanel();
		npanel.setLayout(new BoxLayout(npanel, BoxLayout.PAGE_AXIS));
		npanel.add(labelPanel);
		npanel.add(selection);

		this.add(npanel, BorderLayout.NORTH);
		this.add(description_scroller, BorderLayout.CENTER);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox) e.getSource();
		if (cb.getSelectedIndex() != 0) {
			String se_key = (String) cb.getSelectedItem();
			System.out.println("se key " + se_key);
			description.setText(backend.getSemanager().getSearchEngineDesc(
					se_key));
			backend.setCurrentse(backend.getSemanager().getSearchEngine(se_key));
		}

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
		if (o instanceof qcevolutionbackend) {
			if (null != backend.getCurrentse()) {
				backend.getCurrentse().addObserver(this);
			}
		} else {
			if (backend.getCurrentse() != null) {
				if (backend.getCurrentse().getState() == SearchEngineState.Searching) {
					selection.setEnabled(false);
				} else {
					selection.setEnabled(true);
				}
				validate();
			}
		}
	}
}
