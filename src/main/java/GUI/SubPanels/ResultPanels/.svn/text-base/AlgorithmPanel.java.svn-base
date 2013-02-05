/**
 * @Author = Sam Ratcliff
 */
package GUI.SubPanels.ResultPanels;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Core.Algorithms.QuantumAlgorithm;

/**
 * @author Sam Ratcliff
 * 
 */
public class AlgorithmPanel extends JPanel {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 9219235475209840392L;
	private static final String	nameStr				= "Algorithm";
	private final JTextArea		algoListing;
	private final JScrollPane	scrollPane;

	/**
	 * 
	 */
	public AlgorithmPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		algoListing = new JTextArea();
		algoListing.setEditable(false);

		scrollPane = new JScrollPane(algoListing);
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

	public void update(QuantumAlgorithm qa) {
		algoListing.setText(qa.print());
	}

}
