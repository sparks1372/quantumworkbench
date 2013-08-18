package info.samratcliff.utils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

/**
 * Complex implements a complex number and defines complex arithmetic and
 * mathematical functions Last Updated February 27, 2001 Copyright 1997-2001
 *
 * @author Andrew G. Bennett
 * @version 1.0
 */
public class Complex extends Object implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4268415886956636625L;

    public static void main(String[] args) {
        System.out.println(parseComplex("1").toString());
        System.out.println(parseComplex("1i").toString());
        System.out.println(parseComplex("-1").toString());
        System.out.println(parseComplex("-1i").toString());
        System.out.println(parseComplex("1+ 1i").toString());
        System.out.println(parseComplex("1- 1i").toString());
        System.out.println(parseComplex("0.07072546156-1i").toString());
    }

    public static Complex parseComplex(String p) {
        Double real = 0.0, imag = 0.0;
        p = p.toLowerCase();
        p = removeSpaces(p);
        int offset;
        String rem;
        if ((p.charAt(0) == '-') || (p.charAt(0) == '+')) {
            rem = p.substring(1);
            offset = 1;
        } else {
            rem = p;
            offset = 0;
        }

        String realStr = null;
        String imagStr = null;
        if (rem.contains("-")) {
            realStr = p.substring(0, rem.indexOf("-") + offset);
            imagStr = p.substring(rem.indexOf("-") + offset, rem.indexOf("i")
                    + offset);

        } else if (rem.contains("+")) {
            realStr = p.substring(0, rem.indexOf("+") + offset);
            imagStr = p.substring(rem.indexOf("+") + offset, rem.indexOf("i")
                    + offset);

        } else if (rem.contains("i")) {
            realStr = "0.0";
            imagStr = p.substring(0, p.length() - 1);
        } else {
            realStr = p;
            imagStr = "0.0";
        }

        try {
            real = Double.parseDouble(realStr);
            imag = Double.parseDouble(imagStr.trim());
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return new Complex(real, imag);
    }

    private static String removeSpaces(String s) {
        StringTokenizer st = new StringTokenizer(s, " ", false);
        String t = "";
        while (st.hasMoreElements()) {
            t += st.nextElement();
        }
        return t;
    }

    protected double x;

    protected double y;

    /**
     * Constructs the complex number z = u + i*v
     *
     * @param u Real part
     * @param v Imaginary part
     */
    public Complex(double u, double v) {
        x = u;
        y = v;
    }

    /**
     * Argument of this Complex number (the angle in radians with the x-axis in
     * polar coordinates).
     *
     * @return arg(z) where z is this Complex number.
     */
    public double arg() {
        return Math.atan2(y, x);
    }

    /**
     * Negative of this complex number (chs stands for change sign). This
     * produces a new Complex number and doesn't change this Complex number.
     * &lt;br&gt;-(x+i*y) = -x-i*y.
     *
     * @return -z where z is this Complex number.
     */
    public Complex chs() {
        return new Complex(-x, -y);
    }

    /**
     * Complex conjugate of this Complex number (the conjugate of x+i*y is
     * x-i*y).
     *
     * @return z-bar where z is this Complex number.
     */
    public Complex conj() {
        return new Complex(x, -y);
    }

    /**
     * Creates a copy of the complex number
     *
     * @return A copy of this
     */
    public Complex copy() {
        return new Complex(x, y);
    }

    /**
     * Cosine of this Complex number (doesn't change this Complex number).
     * &lt;br&gt;cos(z) = (exp(i*z)+exp(-i*z))/ 2.
     *
     * @return cos(z) where z is this Complex number.
     */
    public Complex cos() {
        return new Complex(cosh(y) * Math.cos(x), -sinh(y) * Math.sin(x));
    }

    /**
     * Hyperbolic cosine of this Complex number (doesn't change this Complex
     * number). &lt;br&gt;cosh(z) = (exp(z) + exp(-z)) / 2.
     *
     * @return cosh(z) where z is this Complex number.
     */
    public Complex cosh() {
        return new Complex(cosh(x) * Math.cos(y), sinh(x) * Math.sin(y));
    }

    // Real cosh function (used to compute complex trig functions)
    private double cosh(double theta) {
        return (Math.exp(theta) + Math.exp(-theta)) / 2;
    }

    /**
     * Division of Complex numbers (doesn't change this Complex number).
     * &lt;br&gt;(x+i*y)/(s+i*t) = ((x*s+y*t) + i*(y*s-y*t)) / (s^2+t^2)
     *
     * @param w is the number to divide by
     * @return new Complex number z/w where z is this Complex number
     */
    public Complex div(Complex w) {
        double den = Math.pow(w.mod(), 2);
        return new Complex((x * w.real() + y * w.imag()) / den,
                (y * w.real() - x * w.imag()) / den);
    }

    public boolean equal(Complex w) {
        return ((Math.abs(x - w.real()) < 0.00001) && (Math.abs(y - w.imag()) < 0.00001));
    }

    /**
     * Complex exponential (doesn't change this Complex number).
     *
     * @return exp(z) where z is this Complex number.
     */
    public Complex exp() {
        return new Complex(Math.exp(x) * Math.cos(y), Math.exp(x) * Math.sin(y));
    }

    /**
     * Imaginary part of this Complex number (the y-coordinate in rectangular
     * coordinates).
     *
     * @return Im[z] where z is this Complex number.
     */
    public double imag() {
        return y;
    }

    /**
     * Principal branch of the Complex logarithm of this Complex number.
     * (doesn't change this Complex number). The principal branch is the branch
     * with -pi&nbsp;&lt;&nbsp;arg&nbsp;&lt;=&nbsp;pi.
     *
     * @return log(z) where z is this Complex number.
     */
    public Complex log() {
        return new Complex(Math.log(this.mod()), this.arg());
    }

    /**
     * Subtraction of Complex numbers (doesn't change this Complex number).
     * &lt;br&gt;(x+i*y) - (s+i*t) = (x-s)+i*(y-t).
     *
     * @param w is the number to subtract.
     * @return z-w where z is this Complex number.
     */
    public Complex minus(Complex w) {
        return new Complex(x - w.real(), y - w.imag());
    }

    /**
     * Modulus of this Complex number (the distance from the origin in polar
     * coordinates).
     *
     * @return |z| where z is this Complex number.
     */
    public double mod() {
        if (!((x == 0) && (y == 0))) {
            return Math.sqrt(x * x + y * y);
        } else {
            return 0d;
        }
    }

    /**
     * Addition of Complex numbers (doesn't change this Complex number).
     * &lt;br&gt;(x+i*y) + (s+i*t) = (x+s)+i*(y+t).
     *
     * @param w is the number to add.
     * @return z+w where z is this Complex number.
     */
    public Complex plus(Complex w) {
        return new Complex(x + w.real(), y + w.imag());
    }

    /**
     * Real part of this Complex number (the x-coordinate in rectangular
     * coordinates).
     *
     * @return Re[z] where z is this Complex number.
     */
    public double real() {
        return x;
    }

    /**
     * Sine of this Complex number (doesn't change this Complex number).
     * &lt;br&gt;sin(z) = (exp(i*z)-exp(-i*z))/(2*i).
     *
     * @return sin(z) where z is this Complex number.
     */
    public Complex sin() {
        return new Complex(cosh(y) * Math.sin(x), sinh(y) * Math.cos(x));
    }

    /**
     * Hyperbolic sine of this Complex number (doesn't change this Complex
     * number). &lt;br&gt;sinh(z) = (exp(z)-exp(-z))/2.
     *
     * @return sinh(z) where z is this Complex number.
     */
    public Complex sinh() {
        return new Complex(sinh(x) * Math.cos(y), cosh(x) * Math.sin(y));
    }

    // Real sinh function (used to compute complex trig functions)
    private double sinh(double theta) {
        return (Math.exp(theta) - Math.exp(-theta)) / 2;
    }

    /**
     * Tangent of this Complex number (doesn't change this Complex number).
     * &lt;br&gt;tan(z) = sin(z)/cos(z).
     *
     * @return tan(z) where z is this Complex number.
     */
    public Complex tan() {
        return (this.sin()).div(this.cos());
    }

    /**
     * Complex multiplication (doesn't change this Complex number).
     *
     * @param w is the number to multiply by.
     * @return z*w where z is this Complex number.
     */
    public Complex times(Complex w) {
        return new Complex(x * w.real() - y * w.imag(), x * w.imag() + y
                * w.real());
    }

    /**
     * String representation of this Complex number.
     *
     * @return x+i*y, x-i*y, x, or i*y as appropriate.
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.####");
        if ((x != 0) && (y > 0)) {
            return df.format(x) + " + " + df.format(y) + "i";
        }
        if ((x != 0) && (y < 0)) {
            return df.format(x) + " - " + df.format(-y) + "i";
        }
        if (y == 0) {
            return df.format(x);
        }
        if (x == 0) {
            return df.format(y) + "i";
        }
        // shouldn't get here (unless Inf or NaN)
        return df.format(x) + " + i*" + df.format(y);

    }
}
