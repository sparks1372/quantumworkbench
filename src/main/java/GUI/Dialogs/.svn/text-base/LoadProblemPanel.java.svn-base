/**
 * @Author = Sam Ratcliff
 */
package GUI.Dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Core.qcevolutionbackend;
import GUI.ProblemEditor.XMLFilter;
import Utils.WindowUtils;

/**
 * @author Sam Ratcliff
 * 
 */
public class LoadProblemPanel extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long			serialVersionUID						= 8405271636211472180L;
	private final qcevolutionbackend	backend;
	private static final String			nm										= "    Name   ";
	private static final String			dsc										= "Description";
	private static final String			nullStr									= " ";
	private static final String			titleStr								= "Load Test Suite to Create Problem";
	private static final String			CANCEL_BUTTONTEXT						= "Cancel";
	private static final String			OKAY_BUTTONTEXT							= "Okay";
	private static final String			SELECT_DEFINITION_FILENAME_BUTTONTEXT	= "Select Definition Filename";
	private static final String			SELECT_DEFINITION_FILENAME_TOOLTIPTEXT	= "<html>Click here to select the location and name<br>of the XML file used to to store the test suite.</html>";

	private JButton						openButton;
	private JButton						okayButton;
	private JButton						cancelButton;
	private JLabel						nameLabel, descLabel, title;
	private JTextArea					name;
	private JTextArea					desc;
	private JFileChooser				fc;
	private File						selected_file;
	private JLabel						filename;

	public LoadProblemPanel(qcevolutionbackend be) {
		backend = be;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(screenSize.width / 4, screenSize.height / 4,
				screenSize.width / 2, screenSize.height / 2);
		setUndecorated(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

		setupButtons();
		setupTextAreas();
		setupLabels();
		setupFileChooser();

		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout());
		titlePanel.add(title);

		JPanel namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout());
		namePanel.add(nameLabel);
		namePanel.add(name);

		JPanel filePanel = new JPanel();
		JPanel fileButtonPanel = new JPanel();
		fileButtonPanel.add(openButton);
		filePanel.setLayout(new BorderLayout());
		filePanel.add(fileButtonPanel, BorderLayout.LINE_START);
		filePanel.add(filename, BorderLayout.CENTER);

		JPanel descPanel = new JPanel();
		descPanel.setLayout(new FlowLayout());
		descPanel.add(descLabel);
		descPanel.add(desc);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(okayButton);
		buttonPanel.add(cancelButton);

		this.getContentPane().add(titlePanel);
		this.getContentPane().add(namePanel);
		this.getContentPane().add(filePanel);
		this.getContentPane().add(descPanel);
		this.getContentPane().add(buttonPanel);

		// this.pack();
		WindowUtils.centre(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Handle open button action.
		if (e.getSource() == openButton) {
			int returnVal = fc.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				selected_file = fc.getSelectedFile();
				filename.setText(selected_file.getAbsolutePath());
			}

		} else if (e.getSource() == okayButton) {
			backend.getProbmanager().addProblem(name.getText(),
					selected_file.getAbsolutePath(), desc.getText());
			this.setVisible(false);
		} else if (e.getSource() == cancelButton) {
			this.setVisible(false);
		}
	}

	private void setupButtons() {

		openButton = new JButton(SELECT_DEFINITION_FILENAME_BUTTONTEXT);
		openButton.setToolTipText(SELECT_DEFINITION_FILENAME_TOOLTIPTEXT);
		openButton.addActionListener(this);
		openButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		openButton.setAlignmentY(Component.CENTER_ALIGNMENT);

		okayButton = new JButton(OKAY_BUTTONTEXT);
		okayButton.addActionListener(this);
		okayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		openButton.setAlignmentY(Component.CENTER_ALIGNMENT);

		cancelButton = new JButton(CANCEL_BUTTONTEXT);
		cancelButton.addActionListener(this);
		cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		cancelButton.setAlignmentY(Component.CENTER_ALIGNMENT);

	}

	private void setupFileChooser() {

		fc = new JFileChooser();
		fc.addChoosableFileFilter(new XMLFilter());
		fc.setAcceptAllFileFilterUsed(false);
	}

	private void setupLabels() {
		title = new JLabel(titleStr);
		Font fancyFont = new Font(title.getFont().getFontName(), title
				.getFont().getStyle(), 18);
		title.setFont(fancyFont);
		descLabel = new JLabel(dsc);
		nameLabel = new JLabel(nm);
		filename = new JLabel(nullStr);
	}

	private void setupTextAreas() {
		name = new JTextArea();
		name.setSize(new Dimension(this.getWidth() - 50, 20));
		name.setPreferredSize(new Dimension(this.getWidth() - 50, 20));
		name.setBorder(BorderFactory.createRaisedBevelBorder());

		desc = new JTextArea();
		desc.setSize(new Dimension(this.getWidth() - 50, 100));
		desc.setPreferredSize(new Dimension(this.getWidth() - 50, 100));
		desc.setBorder(BorderFactory.createRaisedBevelBorder());
	}
}
