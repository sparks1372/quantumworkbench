/**
 * @Author = Sam Ratcliff
 */
package Core.CircuitEvolution.QPace4;

import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import Core.Algorithms.QuantumInstructionEnum;
import Core.CircuitBuilder.circuitBuilder;
import Core.CircuitEvaluator.CircuitEvaluator;
import Core.CircuitEvolution.SearchEngineState;
import Core.CircuitEvolution.SearchResult;
import Core.CircuitEvolution.circuitsearchengine;
import Utils.WindowUtils;
import Utils.WrapLayout;

/**
 * @author Sam Ratcliff
 * 
 */
public abstract class QPace4_Imp implements circuitsearchengine, Observer {

	class tickBoxListener implements ItemListener {
		private final int	index;

		/**
		 * 
		 */
		public tickBoxListener(int i) {
			index = i;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent
		 * )
		 */
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.DESELECTED) {
				enabledGate[index] = false;
			} else {
				enabledGate[index] = true;
			}
			System.out.println("enabledGate[" + index + "] = "
					+ enabledGate[index]);
		}

	}

	private static final Logger		logger			= Logger.getLogger(QPace4_Imp.class
															.getClass());

	private JProgressBar			progressBar;
	protected circuitBuilder		cb;
	protected CircuitEvaluator		ce;
	private static final String		NAME			= "QPace 4 Implementation";

	protected QPaceSearchResult[]	searchres;
	protected static final String	filename		= "config/ecparams.params";
	private Params_Writer			pw				= null;
	private SearchEngineState		state			= SearchEngineState.Start;
	private final Vector<Observer>	observers		= new Vector<Observer>();

	private final JPanel			progressPanel;
	private JDialog					evolveDialog;
	private JPanel					allowedGatePanel;
	private JTextField				genTA;
	private JLabel					genL;
	private final static String		genStr			= "Generations";
	private final static int		genDef			= 128;
	private JTextField				popTA;
	private JLabel					popL;
	private final static String		popStr			= "Population Size";
	private final static int		popDef			= 128;
	private JTextField				bthTA;
	private JLabel					bthL;
	private final static String		bthStr			= "# of Breeder Threads";
	private final static int		bthDef			= 8;
	private JTextField				ethTA;
	private JLabel					ethL;
	private final static String		ethStr			= "# of Evaluation Threads";
	private final static int		ethDef			= 8;
	private JTextField				mintreedepthTA;
	private JLabel					mintreedepthL;
	private final static String		mintreedepthStr	= "Minimum Initial Tree Depth";
	private final static int		mintreedepthDef	= 10;
	private JTextField				maxtreedepthTA;
	private JLabel					maxtreedepthL;
	private final static String		maxtreedepthStr	= "Maximum Initial Tree Depth";
	private final static int		maxtreedepthDef	= 20;
	private JCheckBox				timeCB;
	private boolean					time			= true;
	private final static String		timeStr			= "Use Time as Seeds?";
	private JTextField				elTA;
	private JLabel					elL;
	private final static String		elStr			= "# of Elites";
	private final static int		elDef			= 1;
	private JTextField				xoverTA;
	private JLabel					xoverL;
	private final static String		xoverStr		= "CrossOver Rate";
	private final static double		xoverDef		= 0.9;
	private JTextField				mutTA;
	private JLabel					mutL;
	private final static String		mutStr			= "Mutation Rate";
	private final static double		mutDef			= 0.1;
	private JTextField				iterTA;
	private JLabel					iterL;
	private final static String		iterStr			= "Number of Iterations";
	private final static int		iterDef			= 1;
	private JTextField				emailTA;
	private JLabel					emailL;
	private final static String		emailStr		= "Email Address for Completion Email. Leave blank if email is not required";
	private final boolean[]			enabledGate;

	protected int					gen;
	private int						pop;
	private int						bth;
	private int						eth;
	private int						mintreedepth;
	private int						maxtreedepth;
	private int						el;
	private double					xover;
	private double					mut;
	protected int					iterval;

	protected final String			from			= "mengquantum@gmail.com";
	protected String				to;
	protected final String			subject			= "Quantum Algorithm Search Complete";
	protected final String			message			= "Your search has completed for Quantum Problem : ";

	private StatsPanel				statsPanel;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Core.CircuitEvolution.circuitsearchengine#setAvailableinstructions(boolean
	 * [])
	 */
	private static final int		GEN_INDEX		= 0;

	private static final int		POP_INDEX		= 1;

	private static final int		BTH_INDEX		= 2;

	private static final int		ETH_INDEX		= 3;

	private static final int		MINTREE_INDEX	= 4;

	private static final int		MAXTREE_INDEX	= 5;

	private static final int		EL_INDEX		= 6;

	private static final int		XOVER_INDEX		= 7;

	private static final int		MUT_INDEX		= 8;

	private static final int		ITER_INDEX		= 9;
	private static final int		TO_INDEX		= 10;

	/**
	 * 
	 */
	public QPace4_Imp() {

		progressPanel = new JPanel();
		enabledGate = new boolean[QuantumInstructionEnum.values().length];

		QuantumInstructionEnum[] gates = QuantumInstructionEnum.getStandard();
		int i = 0;
		for (; i < gates.length; i++) {
			enabledGate[i] = true;
		}
		int j = i;
		gates = QuantumInstructionEnum.getControl();
		for (; i < j + gates.length; i++) {
			enabledGate[i] = true;
		}
		j = i;
		gates = QuantumInstructionEnum.getFlowControl();
		for (; i < j + gates.length; i++) {
			enabledGate[i] = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Core.CircuitEvolution.circuitsearchengine#addObserver(java.util.Observer)
	 */
	@Override
	public synchronized void addObserver(Observer ob) {
		synchronized (observers) {
			if (!observers.contains(ob)) {
				observers.add(ob);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.CircuitEvolution.circuitsearchengine#getAvailableinstructions()
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		System.out.println("HERE");
		statsPanel = null;
		Component[] carray = allowedGatePanel.getComponents();
		for (Component element : carray) {
			if (element instanceof JCheckBox) {
				JCheckBox c = (JCheckBox) element;
				ItemListener[] ilarray = c.getItemListeners();
				for (ItemListener element2 : ilarray) {
					c.removeItemListener(element2);
				}
			}
		}
		evolveDialog = null;
		super.finalize();
	}

	@Override
	public boolean[] getAvailableinstructions() {
		return enabledGate;
	}

	private JPanel getButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton evButton = new JButton("Evolve Now");
		JButton cancelButton = new JButton("Cancel");

		evButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					gen = Integer.parseInt(genTA.getText());
					try {
						pop = Integer.parseInt(popTA.getText());
						try {
							bth = Integer.parseInt(bthTA.getText());
							try {
								eth = Integer.parseInt(ethTA.getText());
								try {
									mintreedepth = Integer
											.parseInt(mintreedepthTA.getText());
									try {
										maxtreedepth = Integer
												.parseInt(maxtreedepthTA
														.getText());
										try {
											el = Integer.parseInt(elTA
													.getText());
											try {

												xover = Double
														.parseDouble(xoverTA
																.getText());
												try {
													mut = Double
															.parseDouble(mutTA
																	.getText());
													try {
														iterval = Integer
																.parseInt(iterTA
																		.getText());
														to = emailTA.getText();

														System.out
																.println("gen "
																		+ gen
																		+ " pop "
																		+ pop
																		+ " bth "
																		+ bth
																		+ " eth "
																		+ eth
																		+ " minimum tree depth "
																		+ mintreedepth
																		+ " maximum tree depth "
																		+ maxtreedepth
																		+ " xover rate "
																		+ xover
																		+ " mutation rate "
																		+ mut);
														// System.out
														// .println(ce
														// .getQfitnessfunction()
														// .getName()
														// +
														// ce.getQfitnessfunction()
														// .getName()
														// .contains(
														// "Phase"));
														pw.updateParams(
																ce.getQfitnessfunction()
																		.getName()
																		.contains(
																				"Phase"),
																enabledGate,
																gen, pop, bth,
																eth,
																mintreedepth,
																maxtreedepth,
																el, xover, mut,
																time);
														startSearch();
														evolveDialog
																.setVisible(false);

													} catch (NumberFormatException ex) {
														logger.error("NumberFormatException caught when trying to parse the search parameters - # of iterations.");
														JOptionPane
																.showMessageDialog(
																		evolveDialog,
																		"Please check the number of iterations field. This must be numeric",
																		"Message",
																		JOptionPane.ERROR_MESSAGE);
													}
												} catch (NumberFormatException ex) {
													logger.error("NumberFormatException caught when trying to parse the search parameters - mutation rate.");
													JOptionPane
															.showMessageDialog(
																	evolveDialog,
																	"Please check the mutation rate field. This must be numeric",
																	"Message",
																	JOptionPane.ERROR_MESSAGE);
												}
											} catch (NumberFormatException ex) {
												logger.error("NumberFormatException caught when trying to parse the search parameters - crossover rate.");
												JOptionPane
														.showMessageDialog(
																evolveDialog,
																"Please check the crossover rate field. This must be numeric",
																"Message",
																JOptionPane.ERROR_MESSAGE);
											}
										} catch (NumberFormatException ex) {
											logger.error("NumberFormatException caught when trying to parse the search parameters - # of elites.");
											JOptionPane
													.showMessageDialog(
															evolveDialog,
															"Please check the number of elites field. This must be numeric",
															"Message",
															JOptionPane.ERROR_MESSAGE);
										}
									} catch (NumberFormatException ex) {
										logger.error("NumberFormatException caught when trying to parse the search parameters - max tree depth.");
										JOptionPane
												.showMessageDialog(
														evolveDialog,
														"Please check the maximum tree depth field. This must be numeric",
														"Message",
														JOptionPane.ERROR_MESSAGE);
									}
								} catch (NumberFormatException ex) {
									logger.error("NumberFormatException caught when trying to parse the search parameters - min tree depth.");
									JOptionPane
											.showMessageDialog(
													evolveDialog,
													"Please check the minimum tree depth field. This must be numeric",
													"Message",
													JOptionPane.ERROR_MESSAGE);
								}
							} catch (NumberFormatException ex) {
								logger.error("NumberFormatException caught when trying to parse the search parameters - evaluation threads.");
								JOptionPane
										.showMessageDialog(
												evolveDialog,
												"Please check the evaluation threads field. This must be numeric",
												"Message",
												JOptionPane.ERROR_MESSAGE);
							}
						} catch (NumberFormatException ex) {
							logger.error("NumberFormatException caught when trying to parse the search parameters - breading threads.");
							JOptionPane
									.showMessageDialog(
											evolveDialog,
											"Please check the breading threads field. This must be numeric",
											"Message",
											JOptionPane.ERROR_MESSAGE);
						}
					} catch (NumberFormatException ex) {
						logger.error("NumberFormatException caught when trying to parse the search parameters - population.");
						JOptionPane
								.showMessageDialog(
										evolveDialog,
										"Please check the Population field. This must be numeric",
										"Message", JOptionPane.ERROR_MESSAGE);
					}
				} catch (NumberFormatException ex) {
					logger.error("NumberFormatException caught when trying to parse the search parameters - generation.");
					JOptionPane
							.showMessageDialog(
									evolveDialog,
									"Please check the Generation field. This must be numeric",
									"Message", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				evolveDialog.setVisible(false);
			}
		});

		buttonPanel.add(evButton);
		buttonPanel.add(cancelButton);
		return buttonPanel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.CircuitEvolution.circuitsearchengine#getCbuilder()
	 */
	@Override
	public circuitBuilder getCbuilder() {
		return cb;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.CircuitEvolution.circuitsearchengine#getCevaluator()
	 */
	@Override
	public CircuitEvaluator getCevaluator() {
		return ce;
	}

	private JPanel getGatePanel() {

		allowedGatePanel = new JPanel();
		allowedGatePanel.setLayout(new WrapLayout());

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		allowedGatePanel.setSize(new Dimension(screenSize.width / 2,
				screenSize.height));

		QuantumInstructionEnum[] gates = QuantumInstructionEnum.getStandard();
		JLabel l;
		JCheckBox cb;
		JPanel individualGatePanel;
		JPanel collectiveGatePanel = new JPanel();
		collectiveGatePanel.setLayout(new FlowLayout());
		int i;
		for (i = 0; i < gates.length; i++) {
			l = new JLabel(gates[i].name());
			cb = new JCheckBox("", enabledGate[QuantumInstructionEnum.valueOf(
					gates[i].name()).ordinal()]);
			cb.addItemListener(new tickBoxListener(QuantumInstructionEnum
					.valueOf(gates[i].name()).ordinal()));
			individualGatePanel = new JPanel();
			individualGatePanel.setLayout(new FlowLayout());
			individualGatePanel.add(l);
			individualGatePanel.add(cb);
			collectiveGatePanel.add(individualGatePanel);
		}
		allowedGatePanel.add(collectiveGatePanel);
		collectiveGatePanel = new JPanel();
		collectiveGatePanel.setLayout(new FlowLayout());
		gates = QuantumInstructionEnum.getControl();
		for (i = 0; i < gates.length; i++) {
			l = new JLabel(gates[i].name());
			cb = new JCheckBox("", enabledGate[QuantumInstructionEnum.valueOf(
					gates[i].name()).ordinal()]);
			cb.addItemListener(new tickBoxListener(QuantumInstructionEnum
					.valueOf(gates[i].name()).ordinal()));
			individualGatePanel = new JPanel();
			individualGatePanel.setLayout(new FlowLayout());
			individualGatePanel.add(l);
			individualGatePanel.add(cb);
			collectiveGatePanel.add(individualGatePanel);
		}
		allowedGatePanel.add(collectiveGatePanel);
		collectiveGatePanel = new JPanel();
		collectiveGatePanel.setLayout(new FlowLayout());
		gates = QuantumInstructionEnum.getFlowControl();
		for (i = 0; i < gates.length; i++) {
			l = new JLabel(gates[i].name());
			cb = new JCheckBox("", enabledGate[QuantumInstructionEnum.valueOf(
					gates[i].name()).ordinal()]);
			cb.addItemListener(new tickBoxListener(QuantumInstructionEnum
					.valueOf(gates[i].name()).ordinal()));
			individualGatePanel = new JPanel();
			individualGatePanel.setLayout(new FlowLayout());
			individualGatePanel.add(l);
			individualGatePanel.add(cb);
			collectiveGatePanel.add(individualGatePanel);
		}
		allowedGatePanel.add(collectiveGatePanel);
		collectiveGatePanel = new JPanel();
		collectiveGatePanel.setLayout(new FlowLayout());
		gates = QuantumInstructionEnum.getCustom();
		for (i = 0; i < this.ce.getQproblem().getNumOfCustomGates(); i++) {
			l = new JLabel(gates[2 * i].name());
			enabledGate[QuantumInstructionEnum.valueOf(gates[2 * i].name())
					.ordinal()] = true;
			cb = new JCheckBox("", enabledGate[QuantumInstructionEnum.valueOf(
					gates[2 * i].name()).ordinal()]);
			cb.addItemListener(new tickBoxListener(QuantumInstructionEnum
					.valueOf(gates[2 * i].name()).ordinal()));
			individualGatePanel = new JPanel();
			individualGatePanel.setLayout(new FlowLayout());
			individualGatePanel.add(l);
			individualGatePanel.add(cb);
			collectiveGatePanel.add(individualGatePanel);
			l = new JLabel(gates[2 * i + 1].name());
			enabledGate[QuantumInstructionEnum.valueOf(gates[2 * i + 1].name())
					.ordinal()] = true;
			cb = new JCheckBox("", enabledGate[QuantumInstructionEnum.valueOf(
					gates[2 * i + 1].name()).ordinal()]);
			cb.addItemListener(new tickBoxListener(QuantumInstructionEnum
					.valueOf(gates[2 * i + 1].name()).ordinal()));
			individualGatePanel = new JPanel();
			individualGatePanel.setLayout(new FlowLayout());
			individualGatePanel.add(l);
			individualGatePanel.add(cb);
			collectiveGatePanel.add(individualGatePanel);
		}
		for (; i < 3; i++) {
			System.out.println("I = " + i);
			enabledGate[QuantumInstructionEnum.valueOf(gates[2 * i].name())
					.ordinal()] = false;
			enabledGate[QuantumInstructionEnum.valueOf(gates[2 * i + 1].name())
					.ordinal()] = false;
		}
		allowedGatePanel.add(collectiveGatePanel);

		return allowedGatePanel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.CircuitEvolution.circuitsearchengine#getName()
	 */
	@Override
	public String getName() {
		return NAME;
	}

	private JPanel getParamsPanel() {

		JPanel paramsPanel = new JPanel();
		paramsPanel.setLayout(new BoxLayout(paramsPanel, BoxLayout.PAGE_AXIS));
		paramsPanel.setBorder(BorderFactory.createEtchedBorder());

		genTA = new JTextField(Integer.toString(genDef));
		genTA.setPreferredSize(new Dimension(65, 25));
		genL = new JLabel(genStr);

		JPanel genPanel = new JPanel();
		genPanel.setLayout(new FlowLayout());

		genPanel.add(genL);
		genPanel.add(genTA);

		popTA = new JTextField(Integer.toString(popDef));
		popTA.setPreferredSize(new Dimension(65, 25));
		popL = new JLabel(popStr);

		JPanel popPanel = new JPanel();
		popPanel.setLayout(new FlowLayout());

		popPanel.add(popL);
		popPanel.add(popTA);

		bthTA = new JTextField(Integer.toString(bthDef));
		bthTA.setPreferredSize(new Dimension(45, 25));
		bthL = new JLabel(bthStr);

		JPanel bthPanel = new JPanel();
		bthPanel.setLayout(new FlowLayout());

		bthPanel.add(bthL);
		bthPanel.add(bthTA);

		ethTA = new JTextField(Integer.toString(ethDef));
		ethTA.setPreferredSize(new Dimension(45, 25));
		ethL = new JLabel(ethStr);

		JPanel ethPanel = new JPanel();
		ethPanel.setLayout(new FlowLayout());

		ethPanel.add(ethL);
		ethPanel.add(ethTA);

		mintreedepthTA = new JTextField(Integer.toString(mintreedepthDef));
		mintreedepthTA.setPreferredSize(new Dimension(45, 25));
		mintreedepthL = new JLabel(mintreedepthStr);

		JPanel mintreedepthPanel = new JPanel();
		mintreedepthPanel.setLayout(new FlowLayout());

		mintreedepthPanel.add(mintreedepthL);
		mintreedepthPanel.add(mintreedepthTA);

		maxtreedepthTA = new JTextField(Integer.toString(maxtreedepthDef));
		maxtreedepthTA.setPreferredSize(new Dimension(45, 25));
		maxtreedepthL = new JLabel(maxtreedepthStr);

		JPanel maxtreedepthPanel = new JPanel();
		maxtreedepthPanel.setLayout(new FlowLayout());

		maxtreedepthPanel.add(maxtreedepthL);
		maxtreedepthPanel.add(maxtreedepthTA);

		timeCB = new JCheckBox(timeStr, time);
		timeCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				time = !time;

			}
		});

		elTA = new JTextField(Integer.toString(elDef));
		elTA.setPreferredSize(new Dimension(45, 25));
		elL = new JLabel(elStr);

		JPanel elPanel = new JPanel();
		elPanel.setLayout(new FlowLayout());

		elPanel.add(elL);
		elPanel.add(elTA);

		xoverTA = new JTextField(Double.toString(xoverDef));
		xoverTA.setPreferredSize(new Dimension(120, 25));
		xoverL = new JLabel(xoverStr);

		JPanel xoverPanel = new JPanel();
		xoverPanel.setLayout(new FlowLayout());

		xoverPanel.add(xoverL);
		xoverPanel.add(xoverTA);

		mutTA = new JTextField(Double.toString(mutDef));
		mutTA.setPreferredSize(new Dimension(120, 25));
		mutL = new JLabel(mutStr);

		JPanel mutPanel = new JPanel();
		mutPanel.setLayout(new FlowLayout());

		mutPanel.add(mutL);
		mutPanel.add(mutTA);

		iterTA = new JTextField(Integer.toString(iterDef));
		iterTA.setPreferredSize(new Dimension(45, 25));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.CircuitEvolution.circuitsearchengine#getProgressBar()
	 */
	@Override
	public JPanel getProgressBar() {
		return progressPanel;
	}

	@Override
	public SearchResult[] getResults()

	{
		return searchres;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.CircuitEvolution.circuitsearchengine#getSearchStatisticsPanel()
	 */
	@Override
	public JPanel getSearchStatisticsPanel() {
		return statsPanel;
	}

	/**
	 * @return the state
	 */
	@Override
	public synchronized SearchEngineState getState() {
		return state;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.CircuitEvolution.circuitsearchengine#statsDialog()
	 */
	@Override
	public JDialog getStatsDialog() {
		return new QPaceStatsDialog(gen, pop, bth, eth, mintreedepth,
				maxtreedepth, el, xover, mut, iterval, enabledGate, searchres);

	}

	@Override
	public void init(circuitBuilder cb, CircuitEvaluator ce) {
		this.cb = cb;
		this.ce = ce;

		try {
			pw = new Params_Writer(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (ce.getQproblem() != null) {
			setupEvolveDialog();
		}
		setupSearchStatisticsPanel();
		ce.addObserver(this);
	}

	private synchronized void notifyObservers() {
		synchronized (observers) {
			Observer[] iter = new Observer[observers.size()];
			iter = observers.toArray(iter);
			for (Observer ob : iter) {
				ob.update(null, this);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.CircuitEvolution.circuitsearchengine#removeAllObservers()
	 */
	@Override
	public synchronized void removeAllObservers() {
		synchronized (observers) {
			observers.removeAllElements();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * Core.CircuitEvolution.circuitsearchengine#removeObserver(java.util.Observer
	 * )
	 */
	@Override
	public synchronized void removeObserver(Observer ob) {
		synchronized (observers) {
			observers.remove(ob);
		}
	}

	/**
	 * 
	 */
	protected void resetProgressBar() {
		progressBar.setMaximum(gen * iterval);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		statsPanel.resetStatsPanel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ec.Evolve#Evolve()
	 */
	// @Override
	@Override
	public void search() {
		WindowUtils.centre(evolveDialog);
		evolveDialog.setVisible(true);
	}

	@Override
	public boolean search(boolean[] availableinstructions, Object[] p) {
		System.arraycopy(p, 0, enabledGate, 0, enabledGate.length);
		String[] params = (String[]) p;
		try {
			gen = Integer.parseInt(params[GEN_INDEX]);
			try {
				pop = Integer.parseInt(params[POP_INDEX]);
				try {
					bth = Integer.parseInt(params[BTH_INDEX]);
					try {
						eth = Integer.parseInt(params[ETH_INDEX]);
						try {
							mintreedepth = Integer
									.parseInt(params[MINTREE_INDEX]);
							try {
								maxtreedepth = Integer
										.parseInt(params[MAXTREE_INDEX]);
								try {
									el = Integer.parseInt(params[EL_INDEX]);
									try {
										xover = Double
												.parseDouble(params[XOVER_INDEX]);
										try {
											mut = Double
													.parseDouble(params[MUT_INDEX]);
											try {
												iterval = Integer
														.parseInt(params[ITER_INDEX]);
												to = (params[TO_INDEX]);

												// System.out
												// .println(ce
												// .getQfitnessfunction()
												// .getName()
												// + ce.getQfitnessfunction()
												// .getName()
												// .contains(
												// "Phase"));
												pw.updateParams(
														ce.getQfitnessfunction()
																.getName()
																.contains(
																		"Phase"),
														enabledGate, gen, pop,
														bth, eth, mintreedepth,
														maxtreedepth, el,
														xover, mut, time);
												startSearch();
												return true;
											} catch (NumberFormatException ex) {
												logger.error("NumberFormatException caught when trying to parse the search parameters - # of iterations.");
												return false;
											}
										} catch (NumberFormatException ex) {
											logger.error("NumberFormatException caught when trying to parse the search parameters - mutation rate.");
											return false;
										}
									} catch (NumberFormatException ex) {
										logger.error("NumberFormatException caught when trying to parse the search parameters - crossover rate.");
										return false;
									}
								} catch (NumberFormatException ex) {
									logger.error("NumberFormatException caught when trying to parse the search parameters - # of elites.");
									return false;
								}
							} catch (NumberFormatException ex) {
								logger.error("NumberFormatException caught when trying to parse the search parameters - max tree depth.");
								return false;
							}
						} catch (NumberFormatException ex) {
							logger.error("NumberFormatException caught when trying to parse the search parameters - min tree depth.");
							return false;
						}
					} catch (NumberFormatException ex) {
						logger.error("NumberFormatException caught when trying to parse the search parameters - evaluation threads.");
						return false;
					}
				} catch (NumberFormatException ex) {
					logger.error("NumberFormatException caught when trying to parse the search parameters - breading threads.");
					return false;
				}
			} catch (NumberFormatException ex) {
				logger.error("NumberFormatException caught when trying to parse the search parameters - population.");
				return false;
			}
		} catch (NumberFormatException ex) {
			logger.error("NumberFormatException caught when trying to parse the search parameters - generation.");
			return false;
		}
	}

	/**
	 * 
	 */
	private void setupEvolveDialog() {

		JPanel allowedGatePanel;
		JPanel paramsPanel;
		JPanel buttonPanel;

		evolveDialog = new JDialog();

		evolveDialog.getContentPane().setLayout(
				new BoxLayout(evolveDialog.getContentPane(),
						BoxLayout.PAGE_AXIS));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		evolveDialog.setBounds(screenSize.width / 4, screenSize.height / 4,
				screenSize.width / 2, screenSize.height / 2);
		evolveDialog.setMaximumSize(new Dimension(screenSize.width / 2,
				screenSize.height));
		evolveDialog.getContentPane().setBounds(screenSize.width / 4,
				screenSize.height / 4, screenSize.width / 2,
				screenSize.height / 2);
		evolveDialog.getContentPane().setMaximumSize(
				new Dimension(screenSize.width / 2, screenSize.height));
		evolveDialog.setUndecorated(true);
		evolveDialog.setModalityType(ModalityType.APPLICATION_MODAL);

		allowedGatePanel = getGatePanel();

		paramsPanel = getParamsPanel();

		buttonPanel = getButtonPanel();

		emailL = new JLabel(emailStr);
		emailTA = new JTextField();
		emailTA.setPreferredSize(new Dimension(250, 25));
		JPanel emailPanelL = new JPanel();
		emailPanelL.add(emailL);
		JPanel emailPanelA = new JPanel();
		emailPanelA.add(emailTA);

		evolveDialog.getContentPane().add(allowedGatePanel);
		evolveDialog.getContentPane().add(paramsPanel);
		evolveDialog.getContentPane().add(emailPanelL);
		evolveDialog.getContentPane().add(emailPanelA);
		evolveDialog.getContentPane().add(buttonPanel);
		WindowUtils.centre(evolveDialog);
	}

	/**
	 * 
	 */
	private void setupSearchStatisticsPanel() {
		progressBar = new JProgressBar();
		progressBar.setMaximumSize(new Dimension(200, 20));
		statsPanel = new StatsPanel(progressBar);
		statsPanel.setVisible(false);
	}

	protected abstract void startSearch();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (ce.getQproblem() != null) {
			setupEvolveDialog();
		}
	}

	protected synchronized void updateState(SearchEngineState s) {
		this.state = s;

		notifyObservers();
	}
}