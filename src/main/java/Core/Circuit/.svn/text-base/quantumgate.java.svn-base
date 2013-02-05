package Core.Circuit;

import Core.Problem.testcase;
import Jama.Matrix;

public interface quantumgate {

	/**
	 */
	public Matrix apply(Matrix start_state, testcase tc);

	/**
	 * @return Return the gate label to appear in the circuit diagrams
	 * @uml.property name="target" readOnly="true"
	 */
	public String getlabel();

	/**
	 * @return Returns the target.
	 * @uml.property name="target" readOnly="true"
	 */
	public int getTarget();

	/**
	 * @return Returns the unitary_operation.
	 * @uml.property name="unitary_operation" readOnly="true"
	 *               default="new Jama.Matrix()"
	 */
	public Matrix getUnitary_operation(testcase tc);

	/**
	 * @return Returns the QCircuit representation of the gate
	 * @uml.property name="target" readOnly="true"
	 */
	public String toLatex();

}
