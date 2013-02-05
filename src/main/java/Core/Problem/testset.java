package Core.Problem;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class testset implements Serializable {

	/**
	 * 
	 */
	private static final long		serialVersionUID	= 7873116058273502900L;
	/**
	 * @uml.property name="testcases"
	 */
	private final List<testcase>	testcases;
	private int						numOfCustomGates;

	/**
	 * @uml.property name="num_of_qubits" readOnly="true"
	 */
	private final int				num_of_qubits;

	public testset(int numofqubits) {
		num_of_qubits = numofqubits;
		testcases = new LinkedList<testcase>();
	}

	/**
	 * Setter of the property <tt>testcases</tt>
	 * 
	 * @param testcases
	 *            The testcases to set.
	 * @uml.property name="testcases"
	 */
	public void addTestcases(testcase tc) {
		testcases.add(tc);
	}

	/**
	 * @return
	 */
	public testset copy() {
		testset to_ret = new testset(num_of_qubits);
		Iterator<testcase> iter = getTestcases();
		testcase tc;
		while (iter.hasNext()) {
			tc = iter.next();
			tc = tc.copy();
			to_ret.addTestcases(tc);
		}
		return to_ret;
	}

	public boolean equal(testset ts) {
		Iterator<testcase> keyset = testcases.iterator();
		while (keyset.hasNext()) {
			testcase key = keyset.next();
			boolean cont = true;
			Iterator<testcase> tskeyset = testcases.iterator();
			while (cont && tskeyset.hasNext()) {
				if (key.equal(tskeyset.next())) {
					cont = false;
				}
			}
			if (cont) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Getter of the property <tt>num_of_qubits</tt>
	 * 
	 * @return Returns the num_of_qubits.
	 * @uml.property name="num_of_qubits"
	 */
	public int getNum_of_qubits() {
		return num_of_qubits;
	}

	/**
	 * Getter of the property <tt>testcases.size()</tt>
	 * 
	 * @return Returns the number of testcases.
	 * @uml.property name="testcases"
	 */
	public int getNumberOfTestcases() {
		return testcases.size();
	}

	/**
	 * @return the numOfCustomGates
	 */
	public int getNumOfCustomGates() {
		return numOfCustomGates;
	}

	/**
	 * Getter of the property <tt>testcases</tt>
	 * 
	 * @return Returns the testcases.
	 * @uml.property name="testcases"
	 */
	public Iterator<testcase> getTestcases() {
		return testcases.listIterator();
	}

	/**
	 * @param numOfCustomGates
	 *            the numOfCustomGates to set
	 */
	public void setNumOfCustomGates(int numOfCustomGates) {
		this.numOfCustomGates = numOfCustomGates;
	}
}
