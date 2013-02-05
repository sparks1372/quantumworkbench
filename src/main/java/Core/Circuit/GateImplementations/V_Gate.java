package Core.Circuit.GateImplementations;

import Core.Circuit.IQuantumGate;
import Core.Problem.testcase;
import Jama.Matrix;
import Utils.Complex;
import Utils.MatrixUtils;

public class V_Gate implements IQuantumGate {
	private static final String	labelStr	= "V";
	private final int			targ;
	private final Matrix		unitary;

	/**
		 */
	public V_Gate(int target) {
		this.targ = Math.abs(target);

		unitary = Matrix.identity(2, 2);
		unitary.set(1, 1, new Complex(0, 1));
	}

	@Override
	public Matrix apply(Matrix start_state, testcase tc) {
		double qubits = Math.log(start_state.getRowDimension()) / Math.log(2);
		Matrix operation = Matrix.identity(1, 1);
		Matrix iden = Matrix.identity(2, 2);
		for (int index = 1; index <= qubits; index++) {
			if (index == targ) {
				operation = MatrixUtils.tensor_prod(unitary, operation);
			} else {
				operation = MatrixUtils.tensor_prod(iden, operation);
			}
		}
		return operation.times(start_state);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see Core.Circuit.quantumgate#getlabel()
	 */
	@Override
	public String getlabel() {
		return labelStr;
	}

	@Override
	public int getTarget() {
		return targ;
	}

	@Override
	public Matrix getUnitary_operation(testcase tc) {
		return unitary;
	}

	@Override
	public String toLatex() {
		return "\\gate{V}&";
	}

}
