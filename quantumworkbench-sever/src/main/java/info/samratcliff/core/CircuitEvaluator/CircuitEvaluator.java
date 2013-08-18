package info.samratcliff.core.CircuitEvaluator;

import info.samratcliff.core.Algorithms.QuantumAlgorithm;
import info.samratcliff.core.Problem.quantumproblem;
import info.samratcliff.core.Problem.testset;
import info.samratcliff.core.Problem.testsuite;

import java.io.Serializable;
import java.util.List;
import java.util.Observer;

public interface CircuitEvaluator extends Serializable {

    public void addObserver(Observer ob);

    /**
     */
    public abstract Suitability Evaluate(QuantumAlgorithm alg);

    /**
     * @return Returns the qfitnessfunction.
     * @uml.property name="qfitnessfunction"
     */
    public SuitabilityMeasure getQfitnessfunction();

    /**
     * @return Returns the qproblem.
     * @uml.property name="qproblem"
     */
    public quantumproblem getQproblem();

    /**
     */
    public abstract testsuite getResults(QuantumAlgorithm alg);

    /**
     */
    public abstract List<testset> getTrace(QuantumAlgorithm alg, int numofqubits);

    public void removeObserver(Observer ob);

    /**
     * Setter of the property <tt>qfitnessfunction</tt>
     *
     * @param qfitnessfunction The qfitnessfunction to set.
     * @uml.property name="qfitnessfunction"
     */
    public void setQfitnessfunction(SuitabilityMeasure qfitnessfunction);

    /**
     * Setter of the property <tt>qproblem</tt>
     *
     * @param qproblem The qproblem to set.
     * @uml.property name="qproblem"
     */
    public void setQproblem(quantumproblem qproblem);

}
