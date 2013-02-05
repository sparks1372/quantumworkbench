package Core.Circuit.GateImplementations;

import Core.Circuit.quantumgate;
import Core.Problem.testcase;
import Jama.Matrix;
import Utils.Complex;
import Utils.MatrixUtils;

public class RX_Gate implements quantumgate {
	private static final String	labelStr	= "RX";
	private final int			targ;
	private final double		theta;
	private final Matrix		unitary;

	/**
		 */
	public RX_Gate(int target, double th) {
		this.targ = Math.abs(target);
		this.theta = th;
		Matrix iden = Matrix.identity(2, 2);

		Matrix X = new Pauli_X(1).getUnitary_operation(null);

		iden = iden.times(new Complex(Math.cos(th / 2), 0));
		X = X.times(new Complex(0, 1).times(new Complex(Math.sin(th / 2), 0)));

		unitary = iden.minus(X);
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
		return "\\gate{RX(" + theta + ")}&";
	}

}
