/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.ui.Dialogs;

import info.samratcliff.core.Problem.Util.TestSuiteUtils;
import info.samratcliff.core.Problem.testsuite;
import info.samratcliff.core.qcevolutionbackend;
import info.samratcliff.ui.ProblemEditor.InnerEditorPanel;
import info.samratcliff.ui.ProblemEditor.XMLEditor;
import info.samratcliff.ui.ProblemEditor.XMLFilter;
import info.samratcliff.utils.WindowUtils;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author Sam Ratcliff
 */
public class CreateProblemPanel extends JDialog implements ActionListener {
    private static final String DELETE_CURRENT_TEST_SET_BUTTONTEXT = "Delete Current TestSet";
    private static final String DELETE_CURRENT_TEST_SET_TOOLTIPTEXT = "<html>Click here to delete the currently selected<br>test set.</html>";
    private static final String ADD_TEST_SET_BUTTONTEXT = "Add Test Set";
    private static final String ADD_TEST_SET_TOOLTIPTEXT = "<html>Click here to add a new test<br>set. You will be prompted for the number<br>of qubits the new test set is for.</html>";
    private static final String CANCEL_BUTTONTEXT = "Cancel";
    private static final String OKAY_BUTTONTEXT = "Okay";
    private static final String SELECT_DEFINITION_FILENAME_BUTTONTEXT = "Select Definition Filename";
    private static final String SELECT_DEFINITION_FILENAME_TOOLTIPTEXT = "<html>Click here to select the location and name<br>of the XML file used to to store the test suite.</html>";
    /**
     *
     */
    private static final long serialVersionUID = -1591002125774750046L;
    private static final Logger logger = Logger.getLogger(CreateProblemPanel.class
            .getClass());
    private final qcevolutionbackend backend;
    private static final String nm = "    Name   ";
    private static final String dsc = "Description";
    private static final String nullStr = "";
    private static final String titleStr = "Create Problem and Test Suite";

    private JButton openButton;
    private JButton okayButton;
    private JButton cancelButton;
    private JButton addTestSetButton;
    private JButton deleteTestSetButton;
    private JLabel nameLabel, descLabel, title;
    private JTextArea name;
    private JTextArea desc;
    private JFileChooser fc;
    private File selected_file;
    private JLabel filename;
    private XMLEditor tsXmlEditor;

    public CreateProblemPanel(qcevolutionbackend be) {

        backend = be;
        setUndecorated(true);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.getContentPane().setLayout(
                new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        setupButtons();
        setupTextAreas();
        setupLabels();
        setupFileChooser();
        setupXmlEditor();

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

        JPanel editorButtonPanel = new JPanel();
        editorButtonPanel.setLayout(new BoxLayout(editorButtonPanel,
                BoxLayout.PAGE_AXIS));
        editorButtonPanel.add(addTestSetButton);
        editorButtonPanel.add(deleteTestSetButton);

        JPanel editorTablePanel = new JPanel();
        editorTablePanel.setLayout(new FlowLayout());
        editorTablePanel.add(tsXmlEditor);

        JPanel editorPanel = new JPanel();
        editorPanel.setLayout(new FlowLayout());
        editorPanel.add(editorButtonPanel);
        editorPanel.add(editorTablePanel);

        JPanel descPanel = new JPanel();
        descPanel.setLayout(new FlowLayout());
        descPanel.add(descLabel);
        JScrollPane scrollPane = new JScrollPane(desc);
        descPanel.add(scrollPane);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(okayButton);
        buttonPanel.add(cancelButton);

        this.getContentPane().add(titlePanel);
        this.getContentPane().add(namePanel);
        this.getContentPane().add(filePanel);
        this.getContentPane().add(editorPanel);
        this.getContentPane().add(descPanel);
        this.getContentPane().add(buttonPanel);

        setupSizes();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                selected_file = fc.getSelectedFile();
                String fn = selected_file.getAbsolutePath();
                if (!fn.endsWith(".xml")) {
                    fn = fn.concat(".xml");
                    selected_file = new File(fn);
                }
                filename.setText(fn);
            }

        } else if (e.getSource() == okayButton) {
            if (selected_file == null) {
                JOptionPane
                        .showMessageDialog(
                                this,
                                "No file selected for the test suite to be saved to. Please select a file and try again.",
                                "Message", JOptionPane.ERROR_MESSAGE);
            } else if (name.getText().length() == 0) {
                JOptionPane
                        .showMessageDialog(
                                this,
                                "No Problem name specified. Please enter a Problem name and retry",
                                "Message", JOptionPane.ERROR_MESSAGE);
            } else if (desc.getText().length() == 0) {
                JOptionPane
                        .showMessageDialog(
                                this,
                                "No Problem description specified. Please enter a Problem description and retry",
                                "Message", JOptionPane.ERROR_MESSAGE);
            } else {
                TestSuiteUtils.TestSuiteToXML(tsXmlEditor.getTsuite(),
                        selected_file);
                backend.getProbmanager().addProblem(name.getText(),
                        selected_file.getAbsolutePath(), desc.getText());
                this.setVisible(false);
            }
        } else if (e.getSource() == cancelButton) {
            this.setVisible(false);
        } else if (e.getSource() == addTestSetButton) {
            String qubit_number_string = (String) JOptionPane.showInputDialog(
                    this, "Add Test Set for how many Qubits?", "Qubit Number",
                    JOptionPane.PLAIN_MESSAGE, null, null, "1");
            if (qubit_number_string != null) {
                int q = Integer.parseInt(qubit_number_string);

                if (q > 10) {
                    JOptionPane
                            .showMessageDialog(
                                    this,
                                    "Maximum number of Qubits is 10. Adding test set for 10 Qubits",
                                    "Message", JOptionPane.INFORMATION_MESSAGE);
                    q = 10;
                }
                tsXmlEditor.addTestSet(q);
                setupSizes();
            }
        } else if (e.getSource() == deleteTestSetButton) {
            int q = ((InnerEditorPanel) (tsXmlEditor.getSelectedComponent()))
                    .getQubit();
            int result = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete the Test Set for " + q
                            + " Qubits");
            if (result == JOptionPane.YES_OPTION) {
                tsXmlEditor.removeCurrentTestSet();
                setupSizes();
            }
        }
        WindowUtils.centre(this);
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
        okayButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        cancelButton = new JButton(CANCEL_BUTTONTEXT);
        cancelButton.addActionListener(this);
        cancelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        addTestSetButton = new JButton(ADD_TEST_SET_BUTTONTEXT);
        addTestSetButton.setToolTipText(ADD_TEST_SET_TOOLTIPTEXT);
        addTestSetButton.addActionListener(this);
        addTestSetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addTestSetButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        deleteTestSetButton = new JButton(DELETE_CURRENT_TEST_SET_BUTTONTEXT);
        deleteTestSetButton.setToolTipText(DELETE_CURRENT_TEST_SET_TOOLTIPTEXT);
        deleteTestSetButton.addActionListener(this);
        deleteTestSetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteTestSetButton.setAlignmentY(Component.CENTER_ALIGNMENT);

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

    private void setupSizes() {
        WindowUtils.centre(this);

        name.setSize(new Dimension(this.getWidth() - 50, 20));
        name.setMinimumSize(new Dimension(this.getWidth() - 50, 20));
        name.setPreferredSize(new Dimension(this.getWidth() - 50, 20));
        desc.setSize(new Dimension(this.getWidth() - 50, 100));
        desc.setMinimumSize(new Dimension(this.getWidth() - 50, 100));
        desc.setPreferredSize(new Dimension(this.getWidth() - 50, 100));
        validate();

        WindowUtils.centre(this);
    }

    private void setupTextAreas() {
        name = new JTextArea();
        name.setBorder(BorderFactory.createRaisedBevelBorder());

        desc = new JTextArea();
        desc.setBorder(BorderFactory.createRaisedBevelBorder());
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
    }

    /**
     *
     */
    private void setupXmlEditor() {

        Object[] selectionValues = {"0", "1", "2", "3"};
        String initialSelection = "0";
        Object selection = JOptionPane.showInputDialog(null,
                "Please select the number of custom gates", "",
                JOptionPane.QUESTION_MESSAGE, null, selectionValues,
                initialSelection);
        try {
            int index = Integer.parseInt((String) selection);
            tsXmlEditor = new XMLEditor(new testsuite(index));
        } catch (NumberFormatException ex) {
            logger.error("Error when parsing number of custom gates", ex);
        }
    }

}
