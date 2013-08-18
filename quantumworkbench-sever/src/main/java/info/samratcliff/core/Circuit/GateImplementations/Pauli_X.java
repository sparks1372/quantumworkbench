package info.samratcliff.core.Circuit.GateImplementations;

import info.samratcliff.core.Circuit.IQuantumGate;
import info.samratcliff.core.Problem.testcase;
import info.samratcliff.jama.Matrix;
import info.samratcliff.predefined_states;
import info.samratcliff.utils.Complex;

public class Pauli_X implements IQuantumGate {
    private static final String labelStr = "X";

    public static void main(String[] args) {

        Pauli_X test = new Pauli_X(1);

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

        System.out.println("3 qubits state 000, Pauli X Qubit 1");
        test_state = predefined_states.get_3q_0();
        test_state.printState();
        result = test.apply(test_state, null);
        result.printState();

        System.out.println("3 qubits state 001, Pauli X Qubit 1");
        test_state = predefined_states.get_3q_1();
        test_state.printState();
        result = test.apply(test_state, null);
        result.printState();

        System.out.println("3 qubits state 010, Pauli X Qubit 1");
        test_state = predefined_states.get_3q_3();
        test_state.printState();
        result = test.apply(test_state, null);
        result.printState();

        System.out.println("3 qubits state 100, Pauli X Qubit 1");
        test_state = predefined_states.get_3q_5();
        test_state.printState();
        result = test.apply(test_state, null);
        result.printState();

        test = new Pauli_X(2);

        System.out.println("2 qubits state 00, Pauli X Qubit 2");
        test_state = predefined_states.get_2q_0();
        test_state.printState();
        result = test.apply(test_state, null);
        result.printState();

        System.out.println("2 qubits state 01, Pauli X Qubit 2");
        test_state = predefined_states.get_2q_1();
        test_state.printState();
        result = test.apply(test_state, null);
        result.printState();

        System.out.println("2 qubits state 10, Pauli X Qubit 2");
        test_state = predefined_states.get_2q_3();
        test_state.printState();
        result = test.apply(test_state, null);
        result.printState();

        System.out.println("3 qubits state 000, Pauli X Qubit 2");
        test_state = predefined_states.get_3q_0();
        test_state.printState();
        result = test.apply(test_state, null);
        result.printState();

        System.out.println("3 qubits state 001, Pauli X Qubit 2");
        test_state = predefined_states.get_3q_1();
        test_state.printState();
        result = test.apply(test_state, null);
        result.printState();

        System.out.println("3 qubits state 010, Pauli X Qubit 2");
        test_state = predefined_states.get_3q_3();
        test_state.printState();
        result = test.apply(test_state, null);
        result.printState();

        System.out.println("3 qubits state 100, Pauli X Qubit 2");
        test_state = predefined_states.get_3q_5();
        test_state.printState();
        result = test.apply(test_state, null);
        result.printState();
    }

    private final int targ;

    private final Matrix unitary;

    /**
     */
    public Pauli_X(int target) {
        // System.out.println(this.getClass().getName());
        this.targ = Math.abs(target);

        unitary = new Matrix(2, 2);
        unitary.set(0, 1, new Complex(1, 0));
        unitary.set(1, 0, new Complex(1, 0));
    }

    @Override
    public Matrix apply(Matrix start_state, testcase tc) {
        Complex temp;

        for (int k = 0; k < start_state.getRowDimension(); k += Math.pow(2,
                targ)) {
            for (int l = 0; l < Math.pow(2, targ - 1); l++) {
                int i0 = k + l;
                int i1 = (int) (k + l + Math.pow(2, targ - 1));

                temp = start_state.get(i0, 0);

                start_state.set(i0, 0, start_state.get(i1, 0));
                start_state.set(i1, 0, temp);
            }
        }
        return start_state;

    }

    /*
     * (non-Javadoc)
     *
     * @see info.samratcliff.core.Circuit.quantumgate#getlabel()
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
        return "\\gate{X}&";
    }
}
