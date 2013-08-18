package info.samratcliff.core.Circuit.GateImplementations;

import info.samratcliff.core.Circuit.IQuantumGate;
import info.samratcliff.core.Problem.testcase;
import info.samratcliff.jama.Matrix;
import info.samratcliff.jama.util.MatrixUtils;

public class Custom_Gate implements IQuantumGate {
    private static final String labelStr = "C";
    private final int targ;
    private Matrix unitary;
    private final int cgate_index;
    private final boolean idengate = false;

    /**
     */
    public Custom_Gate(int target, int cindex) {
        // System.out.println(this.getClass().getName());
        this.targ = Math.abs(target);
        this.cgate_index = cindex;
    }

    @Override
    public Matrix apply(Matrix start_state, testcase tc) {
        double qubits = Math.log(start_state.getRowDimension()) / Math.log(2);
        Matrix operation = Matrix.identity(1, 1);
        Matrix iden = Matrix.identity(2, 2);
        try {
            unitary = tc.getCustomGate(cgate_index);
        } catch (IndexOutOfBoundsException e) {
            return start_state;
        }
        double unitaryqubits = Math.log(unitary.getRowDimension())
                / Math.log(2);
        for (int index = 1; index <= qubits; ) {
            if ((index == targ) && ((index + (unitaryqubits - 1)) <= qubits)) {
                operation = MatrixUtils.tensor_prod(unitary, operation);
                index += unitaryqubits;
            } else if ((index == targ)) {
                operation = MatrixUtils.tensor_prod(iden, operation);
                index++;
            } else {
                operation = MatrixUtils.tensor_prod(iden, operation);
                index++;
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
        return labelStr.concat(Integer.toString(cgate_index));
    }

    @Override
    public int getTarget() {
        return targ;
    }

    @Override
    public Matrix getUnitary_operation(testcase customGateDefs) {
        try {
            return customGateDefs.getCustomGate(cgate_index);
        } catch (IndexOutOfBoundsException e) {
            return Matrix.identity(2, 2);
        }
    }

    @Override
    public String toLatex() {
        if (idengate) {
            return "\\gate{I}&";
        } else {
            return "\\gate{C" + cgate_index + "}&";
        }
    }

}
