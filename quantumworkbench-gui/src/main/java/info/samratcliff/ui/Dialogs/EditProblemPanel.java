/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.ui.Dialogs;

import info.samratcliff.core.Problem.Util.TestSuiteUtils;
import info.samratcliff.core.Problem.quantumproblem;
import info.samratcliff.core.Problem.testsuite;
import info.samratcliff.core.qcevolutionbackend;
import info.samratcliff.ui.ProblemEditor.InnerEditorPanel;
import info.samratcliff.ui.ProblemEditor.XMLEditor;
import info.samratcliff.ui.ProblemEditor.XMLFilter;
import info.samratcliff.utils.WindowUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author Sam Ratcliff
 */
public class EditProblemPanel extends JDialog implements ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = -9090044543138511945L;
    private final qcevolutionbackend backend;
    private static final String nm = "    Name   ";
    private static final String dsc = "Description";
    private static final String nullStr = "";
    private static final String titleStr = "Edit Current Problem and Test Suite";
    private static final String DELETE_CURRENT_TEST_SET_BUTTONTEXT = "Delete Current TestSet";
    private static final String DELETE_CURRENT_TEST_SET_TOOLTIPTEXT = "<html>Click here to delete the currently selected<br>test set.</html>";
    private static final String ADD_TEST_SET_BUTTONTEXT = "Add Test Set";
    private static final String ADD_TEST_SET_TOOLTIPTEXT = "<html>Click here to add a new test<br>set. You will be prompted for the number<br>of qubits the new test set is for.</html>";
    private static final String CANCEL_BUTTONTEXT = "Cancel";
    private static final String OKAY_BUTTONTEXT = "Okay";
    private static final String SAVE_TO_FILE_AND_CLOSE_BUTTONTEXT = "Save to File and Close";
    private static final String SAVE_TO_FILE_AND_CLOSE_TOOLTIPTEXT = "<html>Click here to save the changes made to<br>the test suite into the XML file selected<br>to define the test suite.</html>";
    private static final String SELECT_DEFINITION_FILENAME_BUTTONTEXT = "Select Definition Filename";
    private static final String SELECT_DEFINITION_FILENAME_TOOLTIPTEXT = "<html>Click here to select the location and name<br>of the XML file used to to store the test suite.</html>";

    private JButton openButton;
    private JButton okayButton;
    private JButton cancelButton;
    private JButton saveButton;
    private JButton addTestSetButton;
    private JButton deleteTestSetButton;
    private JLabel nameLabel, descLabel, title;
    private JTextArea name;
    private JTextArea desc;
    private JFileChooser fc;
    private File selected_file;
    private JLabel filename;
    private XMLEditor tsXmlEditor;

    public EditProblemPanel(qcevolutionbackend be, quantumproblem qp) {
        System.out.println("EditProblemPanel constructor");
        backend = be;
        setUndecorated(true);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.getContentPane().setLayout(
                new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));

        setupButtons();
        setupTextAreas(qp);
        setupLabels(qp);
        setupFileChooser();
        setupXmlEditor(qp.getTestSuite());

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
        descPanel.add(desc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(okayButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

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
                filename.setText(selected_file.getAbsolutePath());
            }

        } else if (e.getSource() == saveButton) {
            int result = JOptionPane
                    .showConfirmDialog(
                            this,
                            "This will save the changes made to "
                                    + selected_file.getAbsolutePath()
                                    + " all the current contents will be overwritten. Continue?");
            if (result == JOptionPane.YES_OPTION) {
                backend.getProbmanager().updateProblem(name.getText(),
                        selected_file.getAbsolutePath(),
                        tsXmlEditor.getTsuite(), desc.getText());
                TestSuiteUtils.TestSuiteToXML(tsXmlEditor.getTsuite(),
                        selected_file);
                this.setVisible(false);
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

                int result = JOptionPane
                        .showConfirmDialog(
                                this,
                                "This does not save changes to file, you must use the save button to ensure changes are saved to file. Continue?");
                if (result == JOptionPane.YES_OPTION) {
                    backend.getProbmanager().updateProblem(name.getText(),
                            selected_file.getAbsolutePath(),
                            tsXmlEditor.getTsuite(), desc.getText());
                    this.setVisible(false);
                }
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

        saveButton = new JButton(SAVE_TO_FILE_AND_CLOSE_BUTTONTEXT);
        saveButton.setToolTipText(SAVE_TO_FILE_AND_CLOSE_TOOLTIPTEXT);
        saveButton.addActionListener(this);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setAlignmentY(Component.CENTER_ALIGNMENT);

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

    private void setupLabels(quantumproblem qp) {
        title = new JLabel(titleStr);
        Font fancyFont = new Font(title.getFont().getFontName(), title
                .getFont().getStyle(), 18);
        title.setFont(fancyFont);
        descLabel = new JLabel(dsc);
        nameLabel = new JLabel(nm);
        if (qp != null) {
            filename = new JLabel(backend.getProbmanager().getProblemTag(
                    qp.getName()).Def_File);
            selected_file = new File(backend.getProbmanager().getProblemTag(
                    qp.getName()).Def_File);
        } else {
            filename = new JLabel(nullStr);
        }
    }

    private void setupSizes() {
        WindowUtils.centre(this);

        name.setSize(new Dimension(this.getWidth() - 50, 20));
        name.setMinimumSize(new Dimension(this.getWidth() - 50, 20));
        name.setPreferredSize(new Dimension(this.getWidth() - 50, 20));
        desc.setSize(new Dimension(this.getWidth() - 50, 100));
        desc.setMinimumSize(new Dimension(this.getWidth() - 50, 100));
        desc.setPreferredSize(new Dimension(this.getWidth() - 50, 100));

        WindowUtils.centre(this);
    }

    private void setupTextAreas(quantumproblem qp) {
        if (qp != null) {
            name = new JTextArea(backend.getProbmanager().getProblemTag(
                    qp.getName()).Name);
        } else {
            name = new JTextArea();
        }
        name.setBorder(BorderFactory.createRaisedBevelBorder());
        name.setEditable(false);

        if (qp != null) {
            desc = new JTextArea(backend.getProbmanager().getProblemTag(
                    qp.getName()).Desc);
        } else {
            desc = new JTextArea();
        }
        desc.setBorder(BorderFactory.createRaisedBevelBorder());
        desc.setEditable(false);
    }

    /**
     *
     */
    private void setupXmlEditor(testsuite ts) {
        if (ts == null) {
            tsXmlEditor = new XMLEditor();
        } else {
            tsXmlEditor = new XMLEditor(ts.copy());
        }
    }

}
