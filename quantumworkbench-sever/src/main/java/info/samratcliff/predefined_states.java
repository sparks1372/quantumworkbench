package info.samratcliff;

import info.samratcliff.jama.Matrix;
import info.samratcliff.utils.Complex;

public class predefined_states {

    public static Matrix get_1q_0() {
        Matrix state = new Matrix(2, 1);
        state.set(0, 0, new Complex(1, 0));
        return state;
    }

    public static Matrix get_1q_1() {
        Matrix state = new Matrix(2, 1);
        state.set(1, 0, new Complex(1, 0));
        return state;
    }

    public static Matrix get_2q_0() {
        Matrix state = new Matrix(4, 1);
        state.set(0, 0, new Complex(1, 0));
        return state;
    }

    public static Matrix get_2q_1() {
        Matrix state = new Matrix(4, 1);
        state.set(1, 0, new Complex(1, 0));
        return state;
    }

    public static Matrix get_2q_2() {
        Matrix state = new Matrix(4, 1);
        state.set(2, 0, new Complex(1, 0));
        return state;
    }

    public static Matrix get_2q_3() {
        Matrix state = new Matrix(4, 1);
        state.set(3, 0, new Complex(1, 0));
        return state;
    }

    public static Matrix get_3q_0() {
        Matrix state = new Matrix(8, 1);
        state.set(0, 0, new Complex(1, 0));
        return state;
    }

    public static Matrix get_3q_1() {
        Matrix state = new Matrix(8, 1);
        state.set(1, 0, new Complex(1, 0));
        return state;
    }

    public static Matrix get_3q_2() {
        Matrix state = new Matrix(8, 1);
        state.set(2, 0, new Complex(1, 0));
        return state;
    }

    public static Matrix get_3q_3() {
        Matrix state = new Matrix(8, 1);
        state.set(3, 0, new Complex(1, 0));
        return state;
    }

    public static Matrix get_3q_4() {
        Matrix state = new Matrix(8, 1);
        state.set(4, 0, new Complex(1, 0));
        return state;
    }

    public static Matrix get_3q_5() {
        Matrix state = new Matrix(8, 1);
        state.set(5, 0, new Complex(1, 0));
        return state;
    }

    public static Matrix get_3q_6() {
        Matrix state = new Matrix(8, 1);
        state.set(6, 0, new Complex(1, 0));
        return state;
    }

    public static Matrix get_3q_7() {
        Matrix state = new Matrix(8, 1);
        state.set(7, 0, new Complex(1, 0));
        return state;
    }
}
