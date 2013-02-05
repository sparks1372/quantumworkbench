/**
 * @Author = Sam Ratcliff
 */
package GUI.GateEditor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import GUI.ProblemEditor.XMLFilter;
import Jama.Matrix;
import Utils.Complex;
import Utils.MatrixUtils;
import Utils.WindowUtils;

/**
 * @author Sam Ratcliff
 * 
 */
public class MatrixEditor extends Observable implements TableModelListener,
		ActionListener {
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Object rowData[][] = {
				{ "Row1-Column1", "Row1-Column2", "Row1-Column3" },
				{ "Row2-Column1", "Row2-Column2", "Row2-Column3" } };
		Object columnNames[] = { "Column One", "Column Two", "Column Three" };
		JTable table = new JTable(rowData, columnNames);

		JScrollPane scrollPane = new JScrollPane(table);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setSize(300, 150);
		frame.setVisible(true);

	}

	private final MyMatixTableModel	tm;

	private final Window			parent;
	private int						qubitCount;
	private Matrix					mat;

	private JPanel					upperButtonPanel;
	private JButton					newMatrixButton;
	private JButton					loadMatrixButton;
	private JButton					saveMatrixButton;
	private JButton					saveAsMatrixButton;

	private static final String		newMatrixButtonStr		= "New";
	private static final String		newMatrixToolTipStr		= "<html>Click here to create a new matrix.<br>You will be promtped to enter the number<br>of qubits the matrix is to apply to.</html>";
	private static final String		loadMatrixButtonStr		= "Load";
	private static final String		loadMatrixToolTipStr	= "<html>Click here to load a matrix from<br>a pre-existing XML matrix definitionfile.</html>";
	private static final String		saveMatrixButtonStr		= "Save";
	private static final String		saveMatrixToolTipStr	= "<html>Click here to save the current matrix<br>to the currently selected XML definition<br>file.</html>";
	private static final String		saveAsMatrixButtonStr	= "Save As";
	private static final String		saveAsMatrixToolTipStr	= "<html>Click here to save the current matrix<br>to a different XML definition file.</html>";

	private File					selected_file;

	private JFileChooser			fc;

	private final JScrollPane		editorScrollPane;

	private final JPanel			editorPane;
	private final JLabel			fname;

	/**
	 * @param q
	 */
	public MatrixEditor(Window p) {
		parent = p;
		tm = new MyMatixTableModel();
		editorPane = new JPanel();
		editorPane.setLayout(new BoxLayout(editorPane, BoxLayout.PAGE_AXIS));
		editorScrollPane = new JScrollPane();
		fname = new JLabel();
		editorPane.add(fname);
		editorPane.add(editorScrollPane);
		setupButtons();
		setupFileChooser();
		tm.addTableModelListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		editorPane.setVisible(false);
		if (arg0.getSource() == newMatrixButton) {
			String qubit_number_string = (String) JOptionPane.showInputDialog(
					parent, "Add Custom Gate acting on how many Qubits?",
					"Qubit Number", JOptionPane.PLAIN_MESSAGE, null, null, "1");
			if (qubit_number_string != null) {
				int q = Integer.parseInt(qubit_number_string);

				if (q > 4) {
					JOptionPane.showMessageDialog(parent,
							"Maximum number of Qubits is 5.", "Message",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					newMatrix(q);

					newMatrixButton.setEnabled(true);
					loadMatrixButton.setEnabled(true);
					saveMatrixButton.setEnabled(false);
					saveAsMatrixButton.setEnabled(true);
				}
				selected_file = null;
			}
		} else if (arg0.getSource() == loadMatrixButton) {
			int returnVal = fc.showOpenDialog(null);
			String fn;
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				selected_file = fc.getSelectedFile();
				fn = selected_file.getAbsolutePath();
				if (!fn.endsWith(".xml")) {
					fn = fn.concat(".xml");
					selected_file = new File(fn);
				}

				setMatrix(MatrixUtils.fromFile(fn));

				newMatrixButton.setEnabled(true);
				loadMatrixButton.setEnabled(true);
				saveMatrixButton.setEnabled(false);
				saveAsMatrixButton.setEnabled(true);
			}

		} else if (arg0.getSource() == saveMatrixButton) {

			MatrixUtils.toFile(mat, selected_file.getAbsolutePath());
			newMatrixButton.setEnabled(true);
			loadMatrixButton.setEnabled(true);
			saveMatrixButton.setEnabled(false);
			saveAsMatrixButton.setEnabled(true);

		} else if (arg0.getSource() == saveAsMatrixButton) {
			int returnVal = fc.showOpenDialog(null);
			String fn;
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				selected_file = fc.getSelectedFile();
				fn = selected_file.getAbsolutePath();
				if (!fn.endsWith(".xml")) {
					fn = fn.concat(".xml");
					selected_file = new File(fn);
				}

				MatrixUtils.toFile(mat, fn);

				newMatrixButton.setEnabled(true);
				loadMatrixButton.setEnabled(true);
				saveMatrixButton.setEnabled(false);
				saveAsMatrixButton.setEnabled(true);
			}

		}
		if (selected_file != null) {
			fname.setText(selected_file.getAbsolutePath());
		}
		editorPane.setVisible(true);
		if (parent != null) {
			WindowUtils.centre(parent);
		}
	}

	/**
	 * @return
	 */
	public Component getControlButtonPanel() {
		return upperButtonPanel;
	}

	/**
	 * @return the editor
	 */
	public JPanel getEditor() {
		return editorPane;
	}

	/**
	 * @param q
	 */
	public void newMatrix(int q) {
		qubitCount = q;
		int dim = (int) Math.pow(2, qubitCount);
		dim = dim != 1 ? dim : 0;
		mat = Matrix.identity(dim, dim);
		setupEditor();
	}

	/**
	 * @param fromFile
	 */
	public void setMatrix(Matrix m) {
		mat = m;
		qubitCount = (int) (Math.log(mat.getRowDimension()) / Math.log(2));
		setupEditor();
	}

	/**
	 * @param cancelListener
	 * @param okayListener
	 * 
	 */
	private void setupButtons() {

		upperButtonPanel = new JPanel();
		upperButtonPanel.setLayout(new FlowLayout());

		newMatrixButton = new JButton(newMatrixButtonStr);
		newMatrixButton.setToolTipText(newMatrixToolTipStr);
		newMatrixButton.addActionListener(this);

		loadMatrixButton = new JButton(loadMatrixButtonStr);
		loadMatrixButton.setToolTipText(loadMatrixToolTipStr);
		loadMatrixButton.addActionListener(this);

		saveMatrixButton = new JButton(saveMatrixButtonStr);
		saveMatrixButton.setToolTipText(saveMatrixToolTipStr);
		saveMatrixButton.addActionListener(this);

		saveAsMatrixButton = new JButton(saveAsMatrixButtonStr);
		saveAsMatrixButton.setToolTipText(saveAsMatrixToolTipStr);
		saveAsMatrixButton.addActionListener(this);

		newMatrixButton.setEnabled(true);
		loadMatrixButton.setEnabled(true);
		saveMatrixButton.setEnabled(false);
		saveAsMatrixButton.setEnabled(false);

		upperButtonPanel.add(newMatrixButton);
		upperButtonPanel.add(loadMatrixButton);
		upperButtonPanel.add(saveMatrixButton);
		upperButtonPanel.add(saveAsMatrixButton);

	}

	/**
	 * 
	 */
	private void setupEditor() {
		editorScrollPane.getViewport().removeAll();
		tm.reset(mat.getRowDimension());
		JTable editor = new JTable(tm);
		for (int r = 0; r < mat.getRowDimension(); r++) {
			for (int c = 0; c < mat.getColumnDimension(); c++) {
				tm.internalSetValueAt(mat.get(r, c).toString(), r, c);
			}
		}
		editor.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		editorScrollPane.setViewportView(editor);
		editorScrollPane.validate();
		editorScrollPane.setPreferredSize(new Dimension(
				editor.getWidth() < 500 ? editor.getWidth() + 3 : 500, editor
						.getHeight() < 500 ? editor.getHeight()
						+ editor.getRowHeight() * 2 + 3 : 500));
	}

	private void setupFileChooser() {

		fc = new JFileChooser();
		fc.addChoosableFileFilter(new XMLFilter());
		fc.setAcceptAllFileFilterUsed(false);
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		int i = e.getFirstRow();
		int j = e.getColumn();
		Complex s;
		s = Complex.parseComplex((String) tm.getValueAt(i, j));
		mat.set(i, j, s);
		tm.internalSetValueAt(s.toString(), i, j);

		if (selected_file != null) {
			newMatrixButton.setEnabled(true);
			loadMatrixButton.setEnabled(true);
			saveMatrixButton.setEnabled(true);
			saveAsMatrixButton.setEnabled(true);
		}
	}
}

class MyMatixTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3046689534961948855L;
	private final boolean		DEBUG				= false;
	private String[]			columnNames			= {};
	private Object[][]			data;

	public MyMatixTableModel() {
		data = new Object[0][0];
	}

	/*
	 * JTable uses this method to determine the default renderer/ editor for
	 * each cell. If we didn't implement this method, then the last column would
	 * contain text ("true"/"false"), rather than a check box.
	 */
	@Override
	public Class<?> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	/*
	 * Don't need to implement this method unless your table's data can change.
	 */
	public void internalSetValueAt(Object value, int row, int col) {
		if (DEBUG) {
			System.out.println("Setting value at " + row + "," + col + " to "
					+ value + " (an instance of " + value.getClass() + ")");
		}

		data[row][col] = value;

		if (DEBUG) {
			System.out.println("New value of data:");
			printDebugData();
		}
	}

	/*
	 * Don't need to implement this method unless your table's editable.
	 */
	@Override
	public boolean isCellEditable(int row, int col) {
		System.out.println("here");
		return true;
	}

	private void printDebugData() {
		int numRows = getRowCount();
		int numCols = getColumnCount();

		for (int i = 0; i < numRows; i++) {
			System.out.print("    row " + i + ":");
			for (int j = 0; j < numCols; j++) {
				System.out.print("  " + data[i][j]);
			}
			System.out.println();
		}
		System.out.println("--------------------------");
	}

	public void reset(int dim) {
		data = new Object[dim][dim];
		columnNames = new String[dim];
		for (int i = 0; i < dim; i++) {
			columnNames[i] = "";
		}
	}

	/*
	 * Don't need to implement this method unless your table's data can change.
	 */
	@Override
	public void setValueAt(Object value, int row, int col) {
		if (DEBUG) {
			System.out.println("Setting value at " + row + "," + col + " to "
					+ value + " (an instance of " + value.getClass() + ")");
		}

		data[row][col] = value;
		fireTableCellUpdated(row, col);

		if (DEBUG) {
			System.out.println("New value of data:");
			printDebugData();
		}
	}
}
