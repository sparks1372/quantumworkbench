package info.samratcliff.ui.ProblemEditor;

import info.samratcliff.core.Problem.Util.TestSuiteUtils;
import info.samratcliff.core.Problem.testsuite;
import info.samratcliff.utils.WindowUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class VisualProblemEditor extends JPanel implements ActionListener {
    /**
     *
     */
    private static final long serialVersionUID = -2201715634231784308L;
    private static final Logger logger = Logger.getLogger(VisualProblemEditor.class
            .getClass());

    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame("VisualProblemEditor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add content to the window.
        frame.getContentPane().add(new VisualProblemEditor(frame));
        // frame.setSize(1000, 550);

        // Display the window.
        // frame.pack();
        WindowUtils.centre(frame);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        PropertyConfigurator.configure("log4j.properties");

        // Schedule a job for the event dispatch thread:
        // creating and showing this application's info.samratcliff.ui.GUI.
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Turn off metal's use of bold fonts
                // UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }

    private final JButton newButton, openButton, saveButton,
            saveAsButton;
    private final JButton addTestSetButton;
    private final JFileChooser fc;
    private final Window parent;
    private File selected_file;
    private final JLabel filename;
    private final JPanel upperPanel;

    private final JPanel midPanel;

    private final XMLEditor xmled;

    public VisualProblemEditor(Window p) {
        parent = p;
        this.setLayout(new BorderLayout());

        // Create a file chooser
        fc = new JFileChooser();
        fc.addChoosableFileFilter(new XMLFilter());
        fc.setAcceptAllFileFilterUsed(false);

        newButton = new JButton("New Test Suite");
        newButton.addActionListener(this);
        openButton = new JButton("Open File");
        openButton.addActionListener(this);
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveAsButton = new JButton("Save As");
        saveAsButton.addActionListener(this);

        upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.PAGE_AXIS));

        // For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); // use FlowLayout
        buttonPanel.add(newButton);
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(saveAsButton);

        filename = new JLabel();
        filename.setSize(parent.getWidth(), filename.getHeight());

        upperPanel.add(buttonPanel);
        upperPanel.add(filename);

        addTestSetButton = new JButton("Add TestSet");
        addTestSetButton.addActionListener(this);

        midPanel = new JPanel();
        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.PAGE_AXIS));

        xmled = new XMLEditor();

        JPanel buttonPanel1 = new JPanel();
        buttonPanel1.add(addTestSetButton);

        midPanel.add(buttonPanel1);
        midPanel.add(xmled);

        // Add the buttons and the log to this panel.
        add(upperPanel, BorderLayout.PAGE_START);
        add(midPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle open button action.
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(parent);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                selected_file = fc.getSelectedFile();
                // upperPanel.remove(filename);
                filename.setText(selected_file.getAbsolutePath());
                // upperPanel.add(filename);
                loadFile(selected_file);
            }

            // Handle save button action.
        } else if (e.getSource() == newButton) {
            Object[] selectionValues = {"0", "1", "2", "3"};
            String initialSelection = "0";
            Object selection = JOptionPane.showInputDialog(null,
                    "Please select the number of custom gates", "",
                    JOptionPane.QUESTION_MESSAGE, null, selectionValues,
                    initialSelection);
            try {
                int index = Integer.parseInt((String) selection);
                xmled.setTestSuite(new testsuite(index));
            } catch (NumberFormatException ex) {
                logger.error("Error when parsing number of custom gates", ex);
            }
        } else if (e.getSource() == saveButton) {
            saveFile(selected_file);
        } else if (e.getSource() == saveAsButton) {
            int returnVal = fc.showSaveDialog(parent);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                saveFile(file);
                if (selected_file == null) {
                    selected_file = file;
                }
            }
        } else if (e.getSource() == addTestSetButton) {
            try {
                int s = Integer.parseInt((String) JOptionPane.showInputDialog(
                        parent, "Add Test Set for how many Qubits?",
                        "Qubit Number", JOptionPane.PLAIN_MESSAGE, null, null,
                        "1"));

                if (s > 10) {
                    JOptionPane
                            .showMessageDialog(
                                    parent,
                                    "Maximum number of Qubits is 10. Adding test set for 10 Qubits",
                                    "Message", JOptionPane.INFORMATION_MESSAGE);
                    s = 10;
                }
                xmled.addTestSet(s);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parent,
                        "The number entered cannot be parsed. Try again.",
                        "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        this.validate();
        WindowUtils.centre(parent);
    }

    private void loadFile(File file) {
        xmled.load(file);
    }

    private void saveFile(File file) {
        TestSuiteUtils.TestSuiteToXML(xmled.getTsuite(), file);
    }
}
