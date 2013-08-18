package info.samratcliff.core.Circuit.GateImplementations;

import info.samratcliff.core.Circuit.IQuantumGate;
import info.samratcliff.core.Problem.testcase;
import info.samratcliff.jama.Matrix;
import info.samratcliff.jama.util.MatrixUtils;
import info.samratcliff.predefined_states;
import info.samratcliff.utils.Complex;

public class W_Gate implements IQuantumGate {
    private static final String labelStr = "W";

    public static void main(String[] args) {

        W_Gate test = new W_Gate(1);

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

    private final int targ;

    private final Matrix unitary;

    /**
     */
    public W_Gate(int target) {
        this.targ = Math.abs(target);

        unitary = Matrix.identity(2, 2);
        unitary.set(1, 1, new Complex(0, -1));
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
        return "\\gate{W}&";
    }

}
