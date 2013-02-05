/**
 * @Author = Sam Ratcliff
 */
package Core.CircuitEvolution.QPace4;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.IntervalXYDataset;

import Core.Algorithms.QuantumInstructionEnum;
import Utils.WindowUtils;
import Utils.WrapLayout;

/**
 * @author Sam Ratcliff
 * 
 */
public class QPaceStatsDialog extends JDialog {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3964688651473971082L;
	// ///// Stats fields
	private JTextField					avFitTF;
	private JLabel						avFitL;
	private final String				avFitStr		= "Average Best Fitness";
	private final float					avFitVal;
	private JTextField					avTimeTF;
	private JLabel						avTimeL;
	private final String				avTimeStr		= "Average Time Taken (ms)";
	private final float					avTimeVal;

	// ///// Param fields
	private JTextArea					genTA;
	private JLabel						genL;
	private final static String			genStr			= "Generations";
	private final int					genDef;
	private JTextArea					popTA;
	private JLabel						popL;
	private final static String			popStr			= "Population Size";
	private final int					popDef;
	private JTextArea					bthTA;
	private JLabel						bthL;
	private final static String			bthStr			= "# of Breeder Threads";
	private final int					bthDef;
	private JTextArea					ethTA;
	private JLabel						ethL;
	private final static String			ethStr			= "# of Evaluation Threads";
	private final int					ethDef;
	private JTextArea					mintreedepthTA;
	private JLabel						mintreedepthL;
	private final static String			mintreedepthStr	= "Minimum Initial Tree Depth";
	private final int					mintreedepthDef;
	private JTextArea					maxtreedepthTA;
	private JLabel						maxtreedepthL;
	private final static String			maxtreedepthStr	= "Minimum Initial Tree Depth";
	private final int					maxtreedepthDef;
	private JCheckBox					timeCB;
	private final boolean				time			= true;
	private final static String			timeStr			= "Use Time as Seeds?";
	private JTextArea					elTA;
	private JLabel						elL;
	private final static String			elStr			= "# of Elites";
	private final int					elDef;
	private JTextArea					xoverTA;
	private JLabel						xoverL;
	private final static String			xoverStr		= "CrossOver Rate";
	private final double				xoverDef;
	private JTextArea					mutTA;
	private JLabel						mutL;
	private final static String			mutStr			= "Mutation Rate";
	private final double				mutDef;
	private JTextArea					iterTA;
	private JLabel						iterL;
	private final static String			iterStr			= "Number of Iterations";
	private final int					iterDef;
	private final boolean[]				enabledGate;
	private final QPaceSearchResult[]	results;

	public QPaceStatsDialog(int genDef, int popDef, int bthDef, int ethDef,
			int mintreedepthDef, int maxtreedepthDef, int elDef,
			double xoverDef, double mutDef, int iterDef, boolean[] enabledGate,
			QPaceSearchResult[] results) {
		super();
		this.results = results;
		this.genDef = genDef;
		this.popDef = popDef;
		this.bthDef = bthDef;
		this.ethDef = ethDef;
		this.mintreedepthDef = mintreedepthDef;
		this.maxtreedepthDef = maxtreedepthDef;
		this.elDef = elDef;
		this.xoverDef = xoverDef;
		this.mutDef = mutDef;
		this.iterDef = iterDef;
		this.enabledGate = enabledGate;
		float cumFit = 0;
		long cumTime = 0;
		for (QPaceSearchResult result : results) {
			cumFit += result.getSuitability();
			cumTime += result.getTimeinmillis();
		}
		avFitVal = cumFit / results.length;
		avTimeVal = cumTime / (float) results.length;

		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		this.setTitle("Statistics for all iteration");
		this.getContentPane().add(getGatePanel());
		this.getContentPane().add(getParamsPanel());
		this.getContentPane().add(getStatsPanel());
		this.getContentPane().add(getButtonPanel());

		this.setModal(true);
		WindowUtils.centre(this);
	}

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            a dataset.
	 * 
	 * @return The chart.
	 */
	private JFreeChart createChart(IntervalXYDataset dataset) {
		JFreeChart chart = ChartFactory.createHistogram("Histogram Demo", null,
				null, dataset, PlotOrientation.VERTICAL, false, false, false);
		chart.getXYPlot().setForegroundAlpha(0.75f);
		return chart;
	}

	/**
	 * Creates a sample {@link HistogramDataset}.
	 * 
	 * @return The dataset.
	 */
	private IntervalXYDataset createDataset() {
		HistogramDataset dataset = new HistogramDataset();
		dataset.setType(HistogramType.RELATIVE_FREQUENCY);
		double[] genFound = new double[results.length];
		for (int i = 0; i < results.length; i++) {
			genFound[i] = results[i].getFinalGenNum();
		}
		dataset.addSeries("", genFound, 20);
		return dataset;
	}

	private JPanel getButtonPanel() {
		JPanel toReturn = new JPanel();
		JButton okayButton = new JButton("OK");
		okayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				QPaceStatsDialog.this.setVisible(false);
			}
		});

		toReturn.add(okayButton);
		return toReturn;
	}

	private JPanel getGatePanel() {

		JPanel allowedGatePanel = new JPanel();
		allowedGatePanel.setLayout(new WrapLayout());

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		allowedGatePanel.setSize(new Dimension(screenSize.width / 2,
				screenSize.height));

		QuantumInstructionEnum[] gates = QuantumInstructionEnum.values();
		JLabel l;
		JCheckBox cb;
		JPanel p;
		for (int i = 0; i < gates.length; i++) {
			l = new JLabel(gates[i].name());
			cb = new JCheckBox("", enabledGate[i]);
			cb.setEnabled(false);
			p = new JPanel();
			p.setLayout(new FlowLayout());
			p.add(l);
			p.add(cb);
			allowedGatePanel.add(p);
		}

		return allowedGatePanel;
	}

	private JPanel getParamsPanel() {

		JPanel paramsPanel = new JPanel();
		paramsPanel.setLayout(new BoxLayout(paramsPanel, BoxLayout.PAGE_AXIS));
		paramsPanel.setBorder(BorderFactory.createEtchedBorder());

		genTA = new JTextArea(Integer.toString(genDef));
		genTA.setEditable(false);
		genL = new JLabel(genStr);

		JPanel genPanel = new JPanel();
		genPanel.setLayout(new FlowLayout());

		genPanel.add(genL);
		genPanel.add(genTA);

		popTA = new JTextArea(Integer.toString(popDef));
		popTA.setEditable(false);
		popL = new JLabel(popStr);

		JPanel popPanel = new JPanel();
		popPanel.setLayout(new FlowLayout());

		popPanel.add(popL);
		popPanel.add(popTA);

		bthTA = new JTextArea(Integer.toString(bthDef));
		bthTA.setEditable(false);
		bthL = new JLabel(bthStr);

		JPanel bthPanel = new JPanel();
		bthPanel.setLayout(new FlowLayout());

		bthPanel.add(bthL);
		bthPanel.add(bthTA);

		ethTA = new JTextArea(Integer.toString(ethDef));
		ethTA.setEditable(false);
		ethL = new JLabel(ethStr);

		JPanel ethPanel = new JPanel();
		ethPanel.setLayout(new FlowLayout());

		ethPanel.add(ethL);
		ethPanel.add(ethTA);

		mintreedepthTA = new JTextArea(Integer.toString(mintreedepthDef));
		mintreedepthTA.setEditable(false);
		mintreedepthL = new JLabel(mintreedepthStr);

		JPanel mintreedepthPanel = new JPanel();
		mintreedepthPanel.setLayout(new FlowLayout());

		mintreedepthPanel.add(mintreedepthL);
		mintreedepthPanel.add(mintreedepthTA);

		maxtreedepthTA = new JTextArea(Integer.toString(maxtreedepthDef));
		maxtreedepthTA.setEditable(false);
		maxtreedepthL = new JLabel(maxtreedepthStr);

		JPanel maxtreedepthPanel = new JPanel();
		maxtreedepthPanel.setLayout(new FlowLayout());

		maxtreedepthPanel.add(maxtreedepthL);
		maxtreedepthPanel.add(maxtreedepthTA);

		timeCB = new JCheckBox(timeStr, time);
		timeCB.setEnabled(false);

		elTA = new JTextArea(Integer.toString(elDef));
		elTA.setEditable(false);
		elL = new JLabel(elStr);

		JPanel elPanel = new JPanel();
		elPanel.setLayout(new FlowLayout());

		elPanel.add(elL);
		elPanel.add(elTA);

		xoverTA = new JTextArea(Double.toString(xoverDef));
		xoverTA.setEditable(false);
		xoverL = new JLabel(xoverStr);

		JPanel xoverPanel = new JPanel();
		xoverPanel.setLayout(new FlowLayout());

		xoverPanel.add(xoverL);
		xoverPanel.add(xoverTA);

		mutTA = new JTextArea(Double.toString(mutDef));
		mutTA.setEditable(false);
		mutL = new JLabel(mutStr);

		JPanel mutPanel = new JPanel();
		mutPanel.setLayout(new FlowLayout());

		mutPanel.add(mutL);
		mutPanel.add(mutTA);

		iterTA = new JTextArea(Integer.toString(iterDef));
		iterTA.setEditable(false);
		iterL = new JLabel(iterStr);

		JPanel iterPanel = new JPanel();
		iterPanel.setLayout(new FlowLayout());

		iterPanel.add(iterL);
		iterPanel.add(iterTA);

		JPanel upperPanel = new JPanel(new WrapLayout());
		JPanel midPanel = new JPanel(new WrapLayout());
		JPanel lowerPanel = new JPanel(new WrapLayout());

		upperPanel.add(popPanel);
		upperPanel.add(genPanel);
		upperPanel.add(bthPanel);
		upperPanel.add(ethPanel);
		midPanel.add(elPanel);
		midPanel.add(mintreedepthPanel);
		midPanel.add(maxtreedepthPanel);
		midPanel.add(timeCB);
		lowerPanel.add(xoverPanel);
		lowerPanel.add(mutPanel);
		lowerPanel.add(iterPanel);

		paramsPanel.add(upperPanel);
		paramsPanel.add(midPanel);
		paramsPanel.add(lowerPanel);

		return paramsPanel;
	}

	private JPanel getStatsPanel() {
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new BoxLayout(toReturn, BoxLayout.PAGE_AXIS));

		JPanel innerStatsPanel = new JPanel();
		innerStatsPanel.setLayout(new WrapLayout());

		JPanel avFitPanel = new JPanel();
		avFitTF = new JTextField(Float.toString(avFitVal));
		avFitTF.setEditable(false);
		avFitL = new JLabel(avFitStr);
		avFitPanel.add(avFitL);
		avFitPanel.add(avFitTF);

		JPanel avTimePanel = new JPanel();
		avTimeTF = new JTextField(Float.toString(avTimeVal));
		avTimeTF.setEditable(false);
		avTimeL = new JLabel(avTimeStr);
		avTimePanel.add(avTimeL);
		avTimePanel.add(avTimeTF);

		innerStatsPanel.add(avFitPanel);
		innerStatsPanel.add(avTimePanel);

		JTabbedPane graphPanel = new JTabbedPane();

		IntervalXYDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setMouseZoomable(true, false);
		graphPanel.add("Generation Histogram", chartPanel);

		toReturn.add(innerStatsPanel);
		toReturn.add(graphPanel);
		return toReturn;
	}
}
