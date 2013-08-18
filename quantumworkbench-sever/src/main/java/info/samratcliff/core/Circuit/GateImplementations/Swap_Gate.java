package info.samratcliff.core.Circuit.GateImplementations;

import info.samratcliff.core.CircuitEvolution.multiqubitquantumgate;
import info.samratcliff.core.Problem.testcase;
import info.samratcliff.jama.Matrix;
import info.samratcliff.jama.util.MatrixUtils;
import info.samratcliff.utils.Complex;

public class Swap_Gate implements multiqubitquantumgate {
    private static final String labelStr = "Swap";
    private final int targ1;
    private final int targ2;
    private final Matrix unitary;
    /**
     * @uml.property name="a"
     * @uml.associationEnd
     */
    private Matrix A = Matrix.identity(2, 2);
    /**
     * @uml.property name="b"
     * @uml.associationEnd
     */
    private Matrix B = Matrix.identity(2, 2);
    /**
     * @uml.property name="c"
     * @uml.associationEnd
     */
    private Matrix C = Matrix.identity(2, 2);
    /**
     * @uml.property name="d"
     * @uml.associationEnd
     */
    private Matrix D = Matrix.identity(2, 2);

    public Swap_Gate(int target, int target2) {
        // System.out.println(this.getClass().getName());
        this.targ1 = target < target2 ? target : target2;
        this.targ2 = target < target2 ? target2 : target;
        A.set(1, 1, new Complex(0, 0));
        B.set(0, 0, new Complex(0, 0));
        B.set(0, 1, new Complex(0, 0));
        B.set(1, 0, new Complex(1, 0));
        B.set(1, 1, new Complex(0, 0));
        C.set(0, 0, new Complex(0, 0));
        C.set(0, 1, new Complex(1, 0));
        C.set(1, 0, new Complex(0, 0));
        C.set(1, 1, new Complex(0, 0));
        D.set(0, 0, new Complex(0, 0));

        Matrix temp = Matrix.identity(1, 1);
        for (int i = 1; i < Math.abs(targ1 - targ2); i++) {
            temp = MatrixUtils.tensor_prod(temp, Matrix.identity(2, 2));
        }
        A = MatrixUtils.tensor_prod(temp, A);
        B = MatrixUtils.tensor_prod(temp, B);
        C = MatrixUtils.tensor_prod(temp, C);
        D = MatrixUtils.tensor_prod(temp, D);
        int dim = A.getColumnDimension() * 2;
        unitary = new Matrix(dim, dim);
        unitary.setMatrix(0, A.getColumnDimension() - 1, 0,
                A.getColumnDimension() - 1, A);
        unitary.setMatrix(0, A.getColumnDimension() - 1,
                A.getColumnDimension(), dim - 1, B);
        unitary.setMatrix(A.getColumnDimension(), dim - 1, 0,
                A.getColumnDimension() - 1, C);
        unitary.setMatrix(A.getColumnDimension(), dim - 1,
                A.getColumnDimension(), dim - 1, D);

    }

    @Override
    public Matrix apply(Matrix start_state, testcase tc) {
        double qubits = Math.log(start_state.getRowDimension()) / Math.log(2);
        Matrix operation = Matrix.identity(1, 1);
        Matrix iden = Matrix.identity(2, 2);
        for (int index = 1; index <= qubits; index++) {
            if (index == targ1) {
                operation = MatrixUtils.tensor_prod(unitary, operation);
            } else if ((index < targ1) || (index > targ2)) {
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

    /*
     * (non-Javadoc)
     *
     * @see info.samratcliff.core.CircuitEvolution.multiqubitquantumgate#getSecondQubit()
     */
    @Override
    public int getSecondQubit() {
        return targ2;
    }

    @Override
    public int getTarget() {
        return targ1;
    }

    public int getTarget2() {
        return targ2;
    }

    @Override
    public Matrix getUnitary_operation(testcase tc) {
        return unitary;
    }

    @Override
    public String toLatex() {
        return toLatex(false);
    }

    public String toLatex(boolean upward) {
        if (upward) {
            return "\\qswap \\qwx &";
        } else {
            return "\\qswap &";
        }

    }

}
