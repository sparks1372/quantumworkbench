package Core.Circuit.GateImplementations;

import Core.Circuit.IQuantumGate;
import Core.Problem.testcase;
import Jama.Matrix;
import Testing.predefined_states;
import Utils.Complex;

public class Hadamard_Gate implements IQuantumGate {
	private static final String	labelStr	= "H";

	public static void main(String[] args) {

		Hadamard_Gate test = new Hadamard_Gate(1);

		System.out.println("2 qubits state 00, Hadamard Gate Qubit 1");
		Matrix test_state = predefined_states.get_2q_0();
		test_state.printState();
		Matrix result = test.apply(test_state, null);
		result.printState();

		System.out.println("2 qubits state 01, Hadamard Gate Qubit 1");
		test_state = predefined_states.get_2q_1();
		test_state.printState();
		result = test.apply(test_state, null);
		result.printState();

		System.out.println("2 qubits state 10, Hadamard Gate Qubit 1");
		test_state = predefined_states.get_2q_3();
		test_state.printState();
		result = test.apply(test_state, null);
		result.printState();

		System.out.println("3 qubits state 000, Hadamard Gate Qubit 1");
		test_state = predefined_states.get_3q_0();
		test_state.printState();
		result = test.apply(test_state, null);
		result.printState();

		System.out.println("3 qubits state 001, Hadamard Gate Qubit 1");
		test_state = predefined_states.get_3q_1();
		test_state.printState();
		result = test.apply(test_state, null);
		result.printState();

		System.out.println("3 qubits state 010, Hadamard Gate Qubit 1");
		test_state = predefined_states.get_3q_3();
		test_state.printState();
		result = test.apply(test_state, null);
		result.printState();

		System.out.println("3 qubits state 100, Hadamard Gate Qubit 1");
		test_state = predefined_states.get_3q_5();
		test_state.printState();
		result = test.apply(test_state, null);
		result.printState();

		test = new Hadamard_Gate(2);

		System.out.println("2 qubits state 00, Hadamard Gate Qubit 2");
		test_state = predefined_states.get_2q_0();
		test_state.printState();
		result = test.apply(test_state, null);
		result.printState();

		System.out.println("2 qubits state 01, Hadamard Gate Qubit 2");
		test_state = predefined_states.get_2q_1();
		test_state.printState();
		result = test.apply(test_state, null);
		result.printState();

		System.out.println("2 qubits state 10, Hadamard Gate Qubit 2");
		test_state = predefined_states.get_2q_3();
		test_state.printState();
		result = test.apply(test_state, null);
		result.printState();

		test = new Hadamard_Gate(3);

		System.out.println("3 qubits state 000, Hadamard Gate Qubit 3");
		test_state = predefined_states.get_3q_0();
		test_state.printState();
		result = test.apply(test_state, null);
		result.printState();

		System.out.println("3 qubits state 001, Hadamard Gate Qubit 3");
		test_state = predefined_states.get_3q_1();
		test_state.printState();
		result = test.apply(test_state, null);
		result.printState();

		System.out.println("3 qubits state 010, Hadamard Gate Qubit 3");
		test_state = predefined_states.get_3q_3();
		test_state.printState();
		result = test.apply(test_state, null);
		result.printState();

		System.out.println("3 qubits state 100, Hadamard Gate Qubit 3");
		test_state = predefined_states.get_3q_5();
		test_state.printState();
		result = test.apply(test_state, null);
		result.printState();
	}

	private final int		targ;

	private final Matrix	unitary;

	/**
		 */
	public Hadamard_Gate(int target) {
		if (target == 0) {
			System.out.println("Something is really really wrong");
		}
		this.targ = target;

		unitary = new Matrix(2, 2);
		unitary.set(0, 0, new Complex(1 / Math.sqrt(2), 0));
		unitary.set(0, 1, new Complex(1 / Math.sqrt(2), 0));
		unitary.set(1, 0, new Complex(1 / Math.sqrt(2), 0));
		unitary.set(1, 1, new Complex(-1 / Math.sqrt(2), 0));
	}

	@Override
	public Matrix apply(Matrix start_state, testcase tc) {
		Matrix to_return = start_state.copy();
		Complex temp0;
		Complex temp1;

		for (int k = 0; k < to_return.getRowDimension(); k += Math.pow(2, targ)) {
			for (int l = 0; l < Math.pow(2, targ - 1); l++) {
				int i0 = k + l;
				int i1 = (int) (k + l + Math.pow(2, targ - 1));

				temp0 = to_return.get(i0, 0);
				temp1 = to_return.get(i1, 0);

				to_return.set(
						i0,
						0,
						new Complex((temp0.real() + temp1.real())
								/ Math.sqrt(2), (temp0.imag() + temp1.imag())
								/ Math.sqrt(2)));
				to_return.set(
						i1,
						0,
						new Complex((temp0.real() - temp1.real())
								/ Math.sqrt(2), (temp0.imag() - temp1.imag())
								/ Math.sqrt(2)));
			}
		}
		return to_return;

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
		return "\\gate{H}&";
	}
}
