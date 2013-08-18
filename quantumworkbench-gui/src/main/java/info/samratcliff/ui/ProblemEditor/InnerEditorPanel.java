/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.ui.ProblemEditor;

import info.samratcliff.core.Problem.testcase;
import info.samratcliff.core.Problem.testset;
import info.samratcliff.jama.Matrix;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

/**
 * @author Sam Ratcliff
 */
public class InnerEditorPanel extends JPanel implements ListSelectionListener,
        ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = -2379137691435088526L;
    private final JList test_case_list;
    private final JButton addTestCase;
    private final testset tset;
    private final DefaultListModel model;
    private final JLabel tc_label = new JLabel(
            "TestCase ID");
    private final JLabel start_label = new JLabel(
            "Starting State");
    private final JLabel final_label = new JLabel(
            "Final State");
    private final TestCaseEditor startingStateEditor;
    private final TestCaseEditor finalStateEditor;
    private static final String addtcStr = "Add Test Case";
    private final JFileChooser[] filechooser_array;
    private final JLabel[] filename_array;
    private final JButton[] browseButton_array;
    private static final String browButtonStr = "Browse";
    private static final String CustGateStr = "Custom Gate ";

    public InnerEditorPanel(testset tset) {
        super();
        this.tset = tset;
        this.startingStateEditor = new TestCaseEditor();
        this.finalStateEditor = new TestCaseEditor();
        this.setLayout(new BorderLayout());

        model = new DefaultListModel();

        Iterator<testcase> iter = tset.getTestcases();
        testcase tc;
        while (iter.hasNext()) {
            tc = iter.next();
            model.add(tc.getId(), tc.getLabel());
        }
        test_case_list = new JList(model);

        test_case_list.setVisibleRowCount(-1);
        test_case_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        test_case_list.setSelectedIndex(0);

        JPanel lowerCentrePanel = new JPanel();
        lowerCentrePanel.setLayout(new BoxLayout(lowerCentrePanel,
                BoxLayout.PAGE_AXIS));

        filename_array = new JLabel[tset.getNumOfCustomGates()];
        filechooser_array = new JFileChooser[tset.getNumOfCustomGates()];
        browseButton_array = new JButton[tset.getNumOfCustomGates()];
        JPanel toAdd;
        for (int i = 0; i < filechooser_array.length; i++) {
            toAdd = new JPanel();
            toAdd.add(new JLabel(CustGateStr.concat(Integer.toString(i))));
            filename_array[i] = new JLabel();
            toAdd.add(filename_array[i]);
            filechooser_array[i] = new JFileChooser();
            filechooser_array[i].addChoosableFileFilter(new XMLFilter());
            filechooser_array[i].setAcceptAllFileFilterUsed(false);
            browseButton_array[i] = new JButton(browButtonStr);
            browseButton_array[i].addActionListener(this);
            toAdd.add(browseButton_array[i]);
            lowerCentrePanel.add(toAdd);
        }

        valueChanged(null);
        test_case_list.addListSelectionListener(this);

        addTestCase = new JButton(addtcStr);
        addTestCase.addActionListener(this);
        JScrollPane listScroller = new JScrollPane(test_case_list);
        listScroller.setPreferredSize(new Dimension(250, 80));

        JPanel left_panel = new JPanel();
        left_panel.setLayout(new BoxLayout(left_panel, BoxLayout.PAGE_AXIS));
        left_panel.add(tc_label);
        left_panel.add(listScroller);
        left_panel.add(addTestCase);

        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.PAGE_AXIS));
        startPanel.add(start_label);
        startPanel.add(startingStateEditor);

        JPanel finalPanel = new JPanel();
        finalPanel.setLayout(new BoxLayout(finalPanel, BoxLayout.PAGE_AXIS));
        finalPanel.add(final_label);
        finalPanel.add(finalStateEditor);

        JPanel upperCentrePanel = new JPanel();
        upperCentrePanel.setLayout(new FlowLayout());
        upperCentrePanel.add(startPanel);
        upperCentrePanel.add(finalPanel);

        JPanel centrePanel = new JPanel();
        centrePanel.setLayout(new BoxLayout(centrePanel, BoxLayout.PAGE_AXIS));
        centrePanel.add(upperCentrePanel);
        centrePanel.add(lowerCentrePanel);

        this.add(left_panel, BorderLayout.LINE_START);

        this.add(centrePanel, BorderLayout.CENTER);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == addTestCase) {
            int num_of_qubits = tset.getNum_of_qubits();
            int num_of_currect_test_case = tset.getNumberOfTestcases();
            int vectorSize;
            switch (num_of_qubits) {
                case 1:
                    vectorSize = 2;
                    break;
                case 2:
                    vectorSize = 4;
                    break;
                case 3:
                    vectorSize = 8;
                    break;
                case 4:
                    vectorSize = 16;
                    break;
                case 5:
                    vectorSize = 32;
                    break;
                case 6:
                    vectorSize = 64;
                    break;
                case 7:
                    vectorSize = 128;
                    break;
                case 8:
                    vectorSize = 256;
                    break;
                case 9:
                    vectorSize = 512;
                    break;
                case 10:
                    vectorSize = 1024;
                    break;
                default:
                    vectorSize = 2;
                    break;
            }

            Matrix m;
            testcase tc;
            m = new Matrix(vectorSize, 1);
            String b_str = "Test Case ".concat(Integer
                    .toString(num_of_currect_test_case + 1));

            tc = new testcase(num_of_currect_test_case, b_str,
                    tset.getNumOfCustomGates());
            tc.setStartingstate(m);

            tc.setFinalstate(m.copy());

            tset.addTestcases(tc);

            model.add(tc.getId(), tc.getLabel());
        } else {
            for (int i = 0; i < browseButton_array.length; i++) {
                if (browseButton_array[i] == arg0.getSource()) {
                    testcase tc = null;
                    int returnVal = filechooser_array[i].showOpenDialog(this);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        filename_array[i].setText(filechooser_array[i]
                                .getSelectedFile().getAbsolutePath());
                        Iterator<testcase> iter = tset.getTestcases();
                        while ((tc == null) && iter.hasNext()) {
                            testcase temp = iter.next();
                            if (temp.getId() == test_case_list
                                    .getSelectedIndex()) {
                                tc = temp;
                                tc.setCustomGates(i,
                                        filename_array[i].getText());
                            }

                        }
                        // upperPanel.remove(filename);
                        // filename.setText(selected_file.getAbsolutePath());
                        // upperPanel.add(filename);
                        // loadFile(selected_file);
                    }
                }
            }
        }
    }

    public int getQubit() {
        return tset.getNum_of_qubits();
    }

    protected testset getTestSet() {
        return tset;
    }

    @Override
    public void valueChanged(ListSelectionEvent arg0) {
        int index;
        index = test_case_list.getSelectedIndex();
        Iterator<testcase> iter = tset.getTestcases();
        testcase tc = null;
        while ((tc == null) && iter.hasNext()) {
            testcase temp = iter.next();
            if (temp.getId() == index) {
                tc = temp;
            }

        }
        startingStateEditor.setMatrix(tc.getStartingState());
        finalStateEditor.setMatrix(tc.getFinalstate());

        for (int i = 0; i < filechooser_array.length; i++) {
            filename_array[i].setText(tc.getCustomGateDefinitions()[i]);
        }
    }
}
