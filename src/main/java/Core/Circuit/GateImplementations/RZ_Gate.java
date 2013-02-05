package Core.Circuit.GateImplementations;

import Core.Circuit.IQuantumGate;
import Core.Problem.testcase;
import Jama.Matrix;
import Testing.predefined_states;
import Utils.Complex;
import Utils.MatrixUtils;

public class RZ_Gate implements IQuantumGate {
	private static final String	labelStr	= "RZ";

	public static void main(String[] args) {

		RZ_Gate test = new RZ_Gate(1, Math.PI / 2);

		System.out.println("2 qubits state 00, Pauli X Qubit 1");
		Matrix test_state = predefined_states.get_2q_0();
		test_state.printState();
		Matrix result = test.apply(test_state, null);
		result.printState();

		System.out.println("2 qubits state 01, Pauli X Qubit 1");
		test_state = predefined_states.get_2q_1();
		test_state.printState();
		result = test.apply(test_state, null);
		result.printState();

		System.out.println("2 qubits state 10, Pauli X Qubit 1");
		test_state = predefined_states.get_2q_3();
		test_state.printState();
		result = test.apply(test_state, null);
		result.printState();
	}

	private final int		targ;

	private final double	theta;
	private final Matrix	unitary;

	/**
		 */
	public RZ_Gate(int target, double th) {
		this.targ = Math.abs(target);
		this.theta = th;

		Matrix iden = Matrix.identity(2, 2);

		Matrix X = new Pauli_Z(1).getUnitary_operation(null);

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
		return "\\gate{RZ(" + theta + ")}&";
	}

}
