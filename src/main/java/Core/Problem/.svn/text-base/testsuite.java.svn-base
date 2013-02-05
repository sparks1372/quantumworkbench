package Core.Problem;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class testsuite implements Serializable {

	/**
	 * 
	 */
	private static final long				serialVersionUID	= 1034384689497780033L;
	/**
	 * @uml.property name="testsets" multiplicity="(0 -1)" dimension="1"
	 */
	private final HashMap<Integer, testset>	testsets;
	private int								numOfCustomGates;

	/**
	 * 
	 */
	public testsuite(int cg) {
		testsets = new HashMap<Integer, testset>();
		numOfCustomGates = cg;
	}

	/**
	 * Setter of the property <tt>testsets</tt>
	 * 
	 * @param testcases
	 *            The testsets to set.
	 * @uml.property name="testsets"
	 */
	public void addTestcases(testset ts) {
		if (testsets.containsKey(ts.getNum_of_qubits())) {
			testset current = testsets.get(ts.getNum_of_qubits());
			Iterator<testcase> iter = ts.getTestcases();
			int numtcs = current.getNumberOfTestcases();
			while (iter.hasNext()) {
				testcase next = iter.next().copy();
				next.setId(numtcs++);
				next.setLabel("Test Case " + next.getId());
				current.addTestcases(next);
			}
		} else {
			testsets.put(ts.getNum_of_qubits(), ts);
		}
		this.setNumOfCustomGates(getNumOfCustomGates());
	}

	/**
	 * @return
	 */
	public testsuite copy() {
		testsuite to_ret = new testsuite(numOfCustomGates);
		Iterator<Integer> iter = testsets.keySet().iterator();
		testset ts;
		while (iter.hasNext()) {
			int key = iter.next();
			ts = testsets.get(key).copy();
			to_ret.addTestcases(ts);
		}
		return to_ret;
	}

	public boolean equal(testsuite ts) {
		Iterator<Integer> keyset = testsets.keySet().iterator();
		while (keyset.hasNext()) {
			int key = keyset.next();
			if (!ts.testsets.containsKey(key)) {
				return false;
			} else if (!testsets.get(key).equal(ts.testsets.get(key))) {
				return false;
			}
		}

		return true;
	}

	public Set<Integer> getKeys() {
		return testsets.keySet();
	}

	/**
	 * @return the numOfCustomGates
	 */
	public int getNumOfCustomGates() {
		return numOfCustomGates;
	}

	/**
	 * Getter of the property <tt>testsets</tt>
	 * 
	 * @return Returns the testsets.
	 * @uml.property name="testsets"
	 */
	public testset getTestcases(int qubit) {
		return testsets.get(qubit);
	}

	public void removeTestSet(testset ts) {
		testsets.remove(ts.getNum_of_qubits());
	}

	/**
	 * @param numOfCustomGates
	 *            the numOfCustomGates to set
	 */
	public void setNumOfCustomGates(int numOfCustomGates) {
		this.numOfCustomGates = numOfCustomGates;
		Iterator<Integer> iter = testsets.keySet().iterator();
		while (iter.hasNext()) {
			int key = iter.next();
			testsets.get(key).setNumOfCustomGates(numOfCustomGates);
		}
	}

}
