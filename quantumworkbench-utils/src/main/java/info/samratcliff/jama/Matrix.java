package info.samratcliff.jama;

import info.samratcliff.utils.Complex;

import java.io.*;
import java.security.SecureRandom;

/**
 * This class is an adaptation of the original JAMA class. This adaptation
 * produces a matrix of complex numbers rather than doubles. info.samratcliff.jama.Jama = Java Matrix
 * class.
 * <p/>
 * The Java Matrix Class provides the fundamental operations of numerical linear
 * algebra. Various constructors create Matrices from two dimensional arrays of
 * double precision floating point numbers. Various "gets" and "sets" provide
 * access to submatrices and matrix elements. Several methods implement basic
 * matrix arithmetic, including matrix addition and multiplication, matrix
 * norms, and element-by-element array operations. Methods for reading and
 * printing matrices are also included. All the operations in this version of
 * the Matrix Class involve real matrices. Complex matrices may be handled in a
 * future version.
 * <p/>
 * Five fundamental matrix decompositions, which consist of pairs or triples of
 * matrices, permutation vectors, and the like, produce results in five
 * decomposition classes. These decompositions are accessed by the Matrix class
 * to compute solutions of simultaneous linear equations, determinants, inverses
 * and other matrix functions. The five decompositions are:
 * <p/>
 * <UL>
 * <LI>Cholesky Decomposition of symmetric, positive definite matrices.
 * <LI>LU Decomposition of rectangular matrices.
 * <LI>QR Decomposition of rectangular matrices.
 * <LI>Singular Value Decomposition of rectangular matrices.
 * <LI>Eigenvalue Decomposition of both symmetric and nonsymmetric square
 * matrices.
 * </UL>
 * <DL>
 * <DT><B>Example of use:</B></DT>
 * <p/>
 * <DD>Solve a linear system A x = b and compute the residual norm, ||b - A x||.
 * <p/>
 * <p/>
 * <PRE>
 * double[][]	vals	= { { 1., 2., 3 }, { 4., 5., 6. }, { 7., 8., 10. } };
 * Matrix	A	= new Matrix(
 * vals);
 * Matrix	b	= Matrix.random(
 * 3,
 * 1);
 * Matrix	x	= A.solve(b);
 * Matrix	r	= A.times(
 * x)
 * .minus(b);
 * double	rnorm	= r.normInf();
 * </PRE>
 * <p/>
 * </DD>
 * </DL>
 *
 * @author The MathWorks, Inc. and the National Institute of Standards and
 *         Technology.
 * @version 5 August 1998
 */

public class Matrix implements Cloneable, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6160230780405251708L;

	/*
     * ------------------------ Class variables ------------------------
	 */

    // /**
    // *
    // */
    //
    // public static Matrix constructWithCopy(Complex[][] A) {
    // int m = A.length;
    // int n = A[0].length;
    // Matrix X = new Matrix(m, n);
    // Complex[][] C = X.getArray();
    // for (int i = 0; i < m; i++) {
    // if (A[i].length != n) {
    // throw new IllegalArgumentException(
    // "All rows must have the same length.");
    // }
    // for (int j = 0; j < n; j++) {
    // C[i][j] = A[i][j];
    // }
    // }
    // return X;
    // }

    public static boolean equal(Matrix A, Matrix B) {
        try {
            A.checkMatrixDimensions(B);
        } catch (IllegalArgumentException e) {
            return false;
        }
        for (int i = 0; i < A.getRowDimension(); i++) {
            for (int j = 0; j < A.getColumnDimension(); j++) {
                if (!A.get(i, j).equal(B.get(i, j))) {
                    return false;
                }
            }
        }
        return true;

    }

    /**
     * Generate identity matrix
     *
     * @param m Number of rows.
     * @param n Number of colums.
     * @return An m-by-n matrix with ones on the diagonal and zeros elsewhere.
     */

    public static Matrix identity(int m, int n) {
        Matrix A = null;
        try {
            A = new Matrix(m, n);
            Complex[][] X = A.getArray();
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    X[i][j] = (i == j ? new Complex(1.0, 0.0) : new Complex(
                            0.0, 0.0));
                }
            }
        } catch (java.lang.NegativeArraySizeException e) {
            e.printStackTrace();
        }
        return A;
    }

    /**
     * Construct a matrix from a copy of a 2-D array.
     *
     * @param A Two-dimensional array of doubles.
     * @throws IllegalArgumentException All rows must have the same length
     */

    public static void main(String[] args) {
        Matrix a = Matrix.identity(3, 3);
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream("test.xml");
            out = new ObjectOutputStream(fos);
            out.writeObject(a);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Matrix b;
        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream("test.xml");
            in = new ObjectInputStream(fis);
            b = (Matrix) in.readObject();
            in.close();
            b.printMatrix();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

    }

	/*
     * ------------------------ Constructors ------------------------
	 */

    /**
     * Generate matrix with random elements
     *
     * @param m Number of rows.
     * @param n Number of colums.
     * @return An m-by-n matrix with uniformly distributed random elements.
     */

    public static Matrix random(int m, int n) {
        SecureRandom random = new SecureRandom();
        Matrix A = new Matrix(m, n);
        Complex[][] X = A.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                X[i][j] = new Complex(random.nextDouble(), random.nextDouble());
            }
        }
        return A;
    }

    /**
     * Array for internal storage of elements.
     *
     * @serial internal array storage.
     * @uml.property name="a"
     * @uml.associationEnd multiplicity="(0 -1)"
     */
    private final Complex[][] A;

    /**
     * Row and column dimensions.
     *
     * @serial row dimension.
     * @serial column dimension.
     */
    private final int m;

    /**
     * Row and column dimensions.
     *
     * @serial row dimension.
     * @serial column dimension.
     */
    private final int n;

	/*
	 * ------------------------ Public Methods ------------------------
	 */

    // /**
    // * Construct a matrix from a one-dimensional packed array
    // *
    // * @param vals
    // * One-dimensional array of doubles, packed by columns (ala
    // * Fortran).
    // * @param m
    // * Number of rows.
    // * @exception IllegalArgumentException
    // * Array length must be a multiple of m.
    // */
    //
    // public Matrix(Complex vals[], int m) {
    // this.m = m;
    // n = (m != 0 ? vals.length / m : 0);
    // if (m * n != vals.length) {
    // throw new IllegalArgumentException(
    // "Array length must be a multiple of m.");
    // }
    // A = new Complex[m][n];
    // for (int i = 0; i < m; i++) {
    // for (int j = 0; j < n; j++) {
    // A[i][j] = vals[i + j * m];
    // }
    // }
    // }
    //
    // /**
    // * Construct a matrix from a 2-D array.
    // *
    // * @param A
    // * Two-dimensional array of doubles.
    // * @exception IllegalArgumentException
    // * All rows must have the same length
    // * @see #constructWithCopy
    // */
    //
    // public Matrix(Complex[][] A) {
    // m = A.length;
    // n = A[0].length;
    // for (int i = 0; i < m; i++) {
    // if (A[i].length != n) {
    // throw new IllegalArgumentException(
    // "All rows must have the same length.");
    // }
    // }
    // this.A = A;
    // }
    //
    // /**
    // * Construct a matrix quickly without checking arguments.
    // *
    // * @param A
    // * Two-dimensional array of doubles.
    // * @param m
    // * Number of rows.
    // * @param n
    // * Number of colums.
    // */
    //
    // public Matrix(Complex[][] A, int m, int n) {
    // this.A = A;
    // this.m = m;
    // this.n = n;
    // }

    /**
     * Construct an m-by-n matrix of zeros.
     *
     * @param m Number of rows.
     * @param n Number of colums.
     */

    public Matrix(int m, int n) {
        this.m = m;
        this.n = n;
        A = new Complex[m][n];
        Complex def = new Complex(0, 0);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                set(i, j, def);
            }
        }
    }

    /**
     * Construct an m-by-n constant matrix.
     *
     * @param m Number of rows.
     * @param n Number of colums.
     * @param s Fill the matrix with this scalar value.
     */

    public Matrix(int m, int n, double s) {
        this.m = m;
        this.n = n;
        A = new Complex[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = new Complex(s, 0);
            }
        }
    }

    /**
     * Element-by-element left division, C = A.\B
     *
     * @param B another matrix
     * @return A.\B
     */

    public Matrix arrayLeftDivide(Matrix B) {
        checkMatrixDimensions(B);
        Matrix X = new Matrix(m, n);
        Complex[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = B.A[i][j].div(A[i][j]);
            }
        }
        return X;
    }

    /**
     * Element-by-element left division in place, A = A.\B
     *
     * @param B another matrix
     * @return A.\B
     */

    public Matrix arrayLeftDivideEquals(Matrix B) {
        checkMatrixDimensions(B);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = B.A[i][j].div(A[i][j]);
            }
        }
        return this;
    }

    /**
     * Element-by-element right division, C = A./B
     *
     * @param B another matrix
     * @return A./B
     */

    public Matrix arrayRightDivide(Matrix B) {
        checkMatrixDimensions(B);
        Matrix X = new Matrix(m, n);
        Complex[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j].div(B.A[i][j]);
            }
        }
        return X;
    }

    /**
     * Element-by-element right division in place, A = A./B
     *
     * @param B another matrix
     * @return A./B
     */

    public Matrix arrayRightDivideEquals(Matrix B) {
        checkMatrixDimensions(B);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = A[i][j].div(B.A[i][j]);
            }
        }
        return this;
    }

    /**
     * Element-by-element multiplication, C = A.*B
     *
     * @param B another matrix
     * @return A.*B
     */

    public Matrix arrayTimes(Matrix B) {
        checkMatrixDimensions(B);
        Matrix X = new Matrix(m, n);
        Complex[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j].times(B.A[i][j]);
            }
        }
        return X;
    }

    /**
     * Element-by-element multiplication in place, A = A.*B
     *
     * @param B another matrix
     * @return A.*B
     */

    public Matrix arrayTimesEquals(Matrix B) {
        checkMatrixDimensions(B);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = A[i][j].times(B.A[i][j]);
            }
        }
        return this;
    }

    /**
     * Check if size(A) == size(B) *
     */

    private void checkMatrixDimensions(Matrix B) {
        if ((B.m != m) || (B.n != n)) {
            throw new IllegalArgumentException("Matrix dimensions must agree.");
        }
    }

    /**
     * Clone the Matrix object.
     */

    @Override
    public Object clone() {
        return this.copy();
    }

    /**
     * Make a deep copy of a matrix
     */

    public Matrix copy() {
        Matrix X = new Matrix(m, n);
        Complex[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j].copy();
            }
        }
        return X;
    }

    /**
     * Get a single element.
     *
     * @param i Row index.
     * @param j Column index.
     * @return A(i, j)
     * @throws ArrayIndexOutOfBoundsException
     */

    public Complex get(int i, int j) {
        return A[i][j];
    }

    /**
     * Access the internal two-dimensional array.
     *
     * @return Pointer to the two-dimensional array of matrix elements.
     */

    public Complex[][] getArray() {
        return A;
    }

    /**
     * Copy the internal two-dimensional array.
     *
     * @return Two-dimensional array copy of matrix elements.
     */

    public Complex[][] getArrayCopy() {
        Complex[][] C = new Complex[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j].copy();
            }
        }
        return C;
    }

    /**
     * Get column dimension.
     *
     * @return n, the number of columns.
     */

    public int getColumnDimension() {
        return n;
    }

    /**
     * Make a one-dimensional column packed copy of the internal array.
     *
     * @return Matrix elements packed in a one-dimensional array by columns.
     */

    public Complex[] getColumnPackedCopy() {
        Complex[] vals = new Complex[m * n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                vals[i + j * m] = A[i][j].copy();
            }
        }
        return vals;
    }

    /**
     * Get a submatrix.
     *
     * @param i0 Initial row index
     * @param i1 Final row index
     * @param j0 Initial column index
     * @param j1 Final column index
     * @return A(i0:i1, j0:j1)
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */

    public Matrix getMatrix(int i0, int i1, int j0, int j1) {
        Matrix X = new Matrix(i1 - i0 + 1, j1 - j0 + 1);
        Complex[][] B = X.getArray();
        try {
            for (int i = i0; i <= i1; i++) {
                // for (int j = j0; j <= j1; j++) {
                // B[i - i0][j - j0] = A[i][j];
                // }
                System.arraycopy(A[i], j0, B[i - i0], 0, j1 - j0);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
        return X;
    }

    /**
     * Get a submatrix.
     *
     * @param i0 Initial row index
     * @param i1 Final row index
     * @param c  Array of column indices.
     * @return A(i0:i1, c(:))
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */

    public Matrix getMatrix(int i0, int i1, int[] c) {
        Matrix X = new Matrix(i1 - i0 + 1, c.length);
        Complex[][] B = X.getArray();
        try {
            for (int i = i0; i <= i1; i++) {
                for (int j = 0; j < c.length; j++) {
                    B[i - i0][j] = A[i][c[j]];
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
        return X;
    }

    /**
     * Two norm
     *
     * @return maximum singular value.
     */

    // public Complex norm2() {
    // return (new SingularValueDecomposition(this).norm2());
    // }

    /**
     * Get a submatrix.
     *
     * @param r  Array of row indices.
     * @param i0 Initial column index
     * @param i1 Final column index
     * @return A(r(:), j0:j1)
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */

    public Matrix getMatrix(int[] r, int j0, int j1) {
        Matrix X = new Matrix(r.length, j1 - j0 + 1);
        Complex[][] B = X.getArray();
        try {
            for (int i = 0; i < r.length; i++) {
                // for (int j = j0; j <= j1; j++) {
                // B[i][j - j0] = A[r[i]][j];
                // }
                System.arraycopy(A[r[i]], j0, B[i], 0, j1 - j0);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
        return X;
    }

    /**
     * Get a submatrix.
     *
     * @param r Array of row indices.
     * @param c Array of column indices.
     * @return A(r(:), c(:))
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */

    public Matrix getMatrix(int[] r, int[] c) {
        Matrix X = new Matrix(r.length, c.length);
        Complex[][] B = X.getArray();
        try {
            for (int i = 0; i < r.length; i++) {
                for (int j = 0; j < c.length; j++) {
                    B[i][j] = A[r[i]][c[j]];
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
        return X;
    }

    /**
     * Get row dimension.
     *
     * @return m, the number of rows.
     */

    public int getRowDimension() {
        return m;
    }

    /**
     * Make a one-dimensional row packed copy of the internal array.
     *
     * @return Matrix elements packed in a one-dimensional array by rows.
     */

    public Complex[] getRowPackedCopy() {
        Complex[] vals = new Complex[m * n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) { // $codepro.audit.disable
                // useArraycopyRatherThanALoop
                vals[i * n + j] = A[i][j];
            }
        }
        return vals;
    }

    /**
     * C = A - B
     *
     * @param B another matrix
     * @return A - B
     */

    public Matrix minus(Matrix B) {
        checkMatrixDimensions(B);
        Matrix X = new Matrix(m, n);
        Complex[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j].minus(B.A[i][j]);
            }
        }
        return X;
    }

    /**
     * A = A - B
     *
     * @param B another matrix
     * @return A - B
     */

    public Matrix minusEquals(Matrix B) {
        checkMatrixDimensions(B);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = A[i][j].minus(B.A[i][j]);
            }
        }
        return this;
    }

    /**
     * One norm
     *
     * @return maximum column sum.
     */

    public Complex norm1() {
        Complex f = new Complex(0, 0);
        for (int j = 0; j < n; j++) {
            Complex s = new Complex(0, 0);
            for (int i = 0; i < m; i++) {
                s = s.plus(new Complex(Math.abs((A[i][j]).real()), Math
                        .abs((A[i][j]).imag())));
            }
            f = new Complex(Math.max(f.mod(), s.mod()), 0);
        }
        return f;
    }

    /**
     * Infinity norm
     *
     * @return maximum row sum.
     */

    public Complex normInf() {
        Complex f = new Complex(0, 0);
        for (int i = 0; i < m; i++) {
            Complex s = new Complex(0, 0);
            for (int j = 0; j < n; j++) {
                s = s.plus(new Complex(Math.abs((A[i][j]).real()), Math
                        .abs((A[i][j]).imag())));
            }
            f = new Complex(Math.max(f.mod(), s.mod()), 0);
        }
        return f;
    }

    /**
     * C = A + B
     *
     * @param B another matrix
     * @return A + B
     */

    public Matrix plus(Matrix B) {
        checkMatrixDimensions(B);
        Matrix X = new Matrix(m, n);
        Complex[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j].plus(B.A[i][j]);
            }
        }
        return X;
    }

    /**
     * A = A + B
     *
     * @param B another matrix
     * @return A + B
     */

    public Matrix plusEquals(Matrix B) {
        checkMatrixDimensions(B);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = A[i][j].plus(B.A[i][j]);
            }
        }
        return this;
    }

    /**
     * Print the matrix to stdout. Line the elements up in columns. Use the
     * format object, and right justify within columns of width characters. Note
     * that is the matrix is to be read back in, you probably will want to use a
     * NumberFormat that is set to US Locale.
     *
     * @param format A Formatting object for individual elements.
     * @param width  Field width for each column.
     * @see java.text.DecimalFormat#setDecimalFormatSymbols
     */

    public void printMatrix() {
        printMatrix(new PrintWriter(System.out, true));
    }

    /**
     * Print the matrix to the output stream. Line the elements up in columns.
     * Use the format object, and right justify within columns of width
     * characters. Note that is the matrix is to be read back in, you probably
     * will want to use a NumberFormat that is set to US Locale.
     *
     * @param output the output stream.
     * @param format A formatting object to format the matrix elements
     * @param width  Column width.
     * @see java.text.DecimalFormat#setDecimalFormatSymbols
     */

    public void printMatrix(PrintWriter output) {
        output.println(); // start on new line.
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                String s;
                if (A[i][j].imag() >= 0) {
                    if (A[i][j].imag() == 0) {
                        s = "" + Double.toString(A[i][j].real());
                    } else if (A[i][j].real() == 0) {
                        s = "" + Double.toString(A[i][j].imag()) + "i";
                    } else {
                        s = "" + Double.toString(A[i][j].real()) + "+"
                                + Double.toString(A[i][j].imag()) + "i";
                    }
                } else {
                    if (A[i][j].real() == 0) {
                        s = "" + Double.toString(A[i][j].imag()) + "i";
                    } else {
                        s = "" + Double.toString(A[i][j].real())
                                + Double.toString(A[i][j].imag()) + "i";
                    }
                }
                int padding = Math.max(1, 13 - s.length()); // At _least_ 1
                // space
                for (int k = 0; k < padding; k++) {
                    output.print(' ');
                }
                output.print(s);
            }
            output.println();
        }
        output.println(); // end with blank line.
    }

    public void printState() {
        printState(new PrintWriter(System.out, true));
    }

    public void printState(PrintWriter output) {
        output.println(); // start on new line.
        for (int i = 0; i < m; i++) {
            String s = Integer.toBinaryString(i);
            while (Math.pow(2, s.length()) != m) {
                s = ("0").concat(s);
            }
            s = ("|").concat(s.concat("> "));

            if (A[i][0].imag() >= 0) {
                if (A[i][0].imag() == 0) {
                    s = s.concat("" + Double.toString(A[i][0].real()));
                } else if (A[i][0].real() == 0) {
                    s = s.concat("" + Double.toString(A[i][0].imag()) + "i");
                } else {
                    s = s.concat("" + Double.toString(A[i][0].real()) + "+"
                            + Double.toString(A[i][0].imag()) + "i");
                }
            } else {
                if (A[i][0].real() == 0) {
                    s = s.concat("" + Double.toString(A[i][0].imag()) + "i");
                } else {
                    s = s.concat("" + Double.toString(A[i][0].real())
                            + Double.toString(A[i][0].imag()) + "i");
                }
            }
            int padding = Math.max(1, 13 - s.length()); // At _least_ 1
            // space
            for (int k = 0; k < padding; k++) {
                output.print(' ');
            }
            output.println(s);
        }
        output.println(); // end with blank line.
    }

    /**
     * Set a single element.
     *
     * @param i Row index.
     * @param j Column index.
     * @param s A(i,j).
     * @throws ArrayIndexOutOfBoundsException
     */

    public void set(int i, int j, Complex s) {
        A[i][j] = s;
    }

    /**
     * Set a submatrix.
     *
     * @param i0 Initial row index
     * @param i1 Final row index
     * @param j0 Initial column index
     * @param j1 Final column index
     * @param X  A(i0:i1,j0:j1)
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */

    public void setMatrix(int i0, int i1, int j0, int j1, Matrix X) {
        try {
            for (int i = i0; i <= i1; i++) {
                for (int j = j0; j <= j1; j++) {
                    A[i][j] = X.get(i - i0, j - j0);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
    }

    /**
     * Set a submatrix.
     *
     * @param i0 Initial row index
     * @param i1 Final row index
     * @param c  Array of column indices.
     * @param X  A(i0:i1,c(:))
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */

    public void setMatrix(int i0, int i1, int[] c, Matrix X) {
        try {
            for (int i = i0; i <= i1; i++) {
                for (int j = 0; j < c.length; j++) {
                    A[i][c[j]] = X.get(i - i0, j);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
    }

    /**
     * Set a submatrix.
     *
     * @param r  Array of row indices.
     * @param j0 Initial column index
     * @param j1 Final column index
     * @param X  A(r(:),j0:j1)
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */

    public void setMatrix(int[] r, int j0, int j1, Matrix X) {
        try {
            for (int i = 0; i < r.length; i++) {
                for (int j = j0; j <= j1; j++) {
                    A[r[i]][j] = X.get(i, j - j0);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
    }

    /**
     * Set a submatrix.
     *
     * @param r Array of row indices.
     * @param c Array of column indices.
     * @param X A(r(:),c(:))
     * @throws ArrayIndexOutOfBoundsException Submatrix indices
     */

    public void setMatrix(int[] r, int[] c, Matrix X) {
        try {
            for (int i = 0; i < r.length; i++) {
                for (int j = 0; j < c.length; j++) {
                    A[r[i]][c[j]] = X.get(i, j);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Submatrix indices");
        }
    }

    /**
     * Multiply a matrix by a scalar, C = s*A
     *
     * @param s scalar
     * @return s*A
     */

    public Matrix times(Complex s) {
        Matrix X = new Matrix(m, n);
        Complex[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j].times(s);
            }
        }
        return X;
    }

    // DecimalFormat is a little disappointing coming from Fortran or C's
    // printf.
    // Since it doesn't pad on the left, the elements will come out different
    // widths. Consequently, we'll pass the desired column width in as an
    // argument and do the extra padding ourselves.

    /**
     * Linear algebraic matrix multiplication, A * B
     *
     * @param B another matrix
     * @return Matrix product, A * B
     * @throws IllegalArgumentException Matrix inner dimensions must agree.
     */

    public Matrix times(Matrix B) {
        if (B.m != n) {
            throw new IllegalArgumentException(
                    "Matrix inner dimensions must agree. B.m = " + B.m
                            + " n = " + n);
        }
        Matrix X = new Matrix(m, B.n);
        Complex[][] C = X.getArray();
        Complex[] Bcolj = new Complex[n];
        for (int j = 0; j < B.n; j++) {
            for (int k = 0; k < n; k++) {
                Bcolj[k] = B.A[k][j];
            }
            for (int i = 0; i < m; i++) {
                Complex[] Arowi = A[i];
                Complex s = new Complex(0, 0);
                for (int k = 0; k < n; k++) {
                    s = s.plus(Arowi[k].times(Bcolj[k]));
                }
                C[i][j] = s;
            }
        }
        return X;
    }

    /**
     * Multiply a matrix by a scalar in place, A = s*A
     *
     * @param s scalar
     * @return replace A by s*A
     */

    public Matrix timesEquals(Complex s) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = A[i][j].times(s);
            }
        }
        return this;
    }

    /**
     * Matrix trace.
     *
     * @return sum of the diagonal elements.
     */

    public Complex trace() {
        Complex t = new Complex(0, 0);
        for (int i = 0; i < Math.min(m, n); i++) {
            t = t.plus(A[i][i]);
        }
        return t;
    }

    /**
     * Matrix transpose.
     *
     * @return A'
     */

    public Matrix transpose() {
        Matrix X = new Matrix(n, m);
        Complex[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[j][i] = A[i][j];
            }
        }
        return X;
    }

	/*
	 * ------------------------ Private Methods ------------------------
	 */

    /**
     * Unary minus
     *
     * @return -A
     */

    public Matrix uminus() {
        Matrix X = new Matrix(m, n);
        Complex[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j].chs();
            }
        }
        return X;
    }

}
