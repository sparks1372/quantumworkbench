package info.samratcliff.core.utils;

import info.samratcliff.core.CircuitEvaluator.Suitability;
import info.samratcliff.jama.Matrix;

/**
 * Created with IntelliJ IDEA.
 * User: Sam Ratcliff
 * Date: 18/08/13
 * Time: 18:38
 */
public class MatrixUtils {

    private static void checkMatrixDimensions(Matrix A, Matrix B) {
        if ((B.getRowDimension() != A.getRowDimension()) || (B.getColumnDimension() != A.getColumnDimension())) {
            throw new IllegalArgumentException("Matrix dimensions must agree.");
        }
    }


    public static Suitability euclid(Matrix A, Matrix B) {
        if (A.getColumnDimension() != 1) {
            throw new IllegalArgumentException("Matrix dimensions must agree.");
        }
        checkMatrixDimensions(A, B);
        double sum = 0;
        int count = 0;
        for (int i = 0; i < A.getRowDimension(); i++) {
            sum += Math.sqrt(Math.pow(A.get(i, 0).minus(B.get(i, 0)).mod(), 2));

            if (A.get(i, 0).equal(B.get(i, 0))) {
                count++;
            } else {
                // System.out.println("Complex A " + A.get(i, 0).toString()
                // + " Complex B " + B.get(i, 0).toString());
            }
        }
        // A.print(0, 0);
        // B.print(0, 0);
        // System.out.println(sum);
        return new Suitability(sum, count);

    }
}
