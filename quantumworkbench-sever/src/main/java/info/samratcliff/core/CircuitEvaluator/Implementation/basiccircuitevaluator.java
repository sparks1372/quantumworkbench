package info.samratcliff.core.CircuitEvaluator.Implementation;

import com.google.inject.Inject;
import info.samratcliff.core.Algorithms.QuantumAlgorithm;
import info.samratcliff.core.Circuit.Circuit;
import info.samratcliff.core.Circuit.IQuantumGate;
import info.samratcliff.core.CircuitBuilder.CircuitBuilder;
import info.samratcliff.core.CircuitEvaluator.CircuitEvaluator;
import info.samratcliff.core.CircuitEvaluator.Suitability;
import info.samratcliff.core.CircuitEvaluator.SuitabilityMeasure;
import info.samratcliff.core.Problem.quantumproblem;
import info.samratcliff.core.Problem.testcase;
import info.samratcliff.core.Problem.testset;
import info.samratcliff.core.Problem.testsuite;
import info.samratcliff.jama.Matrix;

import java.util.*;

public class basiccircuitevaluator extends Observable implements
        CircuitEvaluator {
    /**
     *
     */
    private static final long serialVersionUID = 2422792156137250094L;
    private quantumproblem quantprob;
    private SuitabilityMeasure ff;
    private final CircuitBuilder cb;

    @Inject
    public basiccircuitevaluator(CircuitBuilder cirbui) {
        cb = cirbui;
    }

    @Override
    public Suitability Evaluate(QuantumAlgorithm alg) {
        double fitness_to_return = 0;
        int count = 0;

        if (alg.getSize() == 0) {

            return new Suitability(Float.MAX_VALUE, 0);
        }
        Circuit cir;
        Iterator<IQuantumGate> qgate_iter;
        testsuite ts = quantprob.getTestSuite();
        testcase tc;
        Set<Integer> keys = ts.getKeys();

        Iterator<Integer> kiter = keys.iterator();
        Suitability temp;

        while (kiter.hasNext()) {
            int numofqubits = kiter.next();
            testset tempts = ts.getTestcases(numofqubits);
            cir = cb.Build(alg, numofqubits);
            Iterator<testcase> tciter = tempts.getTestcases();
            IQuantumGate qg;
            while (tciter.hasNext()) {
                tc = tciter.next();
                qgate_iter = cir.getCircuitlayout();
                Matrix state = tc.getStartingStateCopy().copy();
                while (qgate_iter.hasNext()) {
                    qg = qgate_iter.next();
                    state = qg.apply(state, tc);
                }

                temp = ff.evaluate(tc.getStartingStateCopy(), state,
                        tc.getFinalStateCopy(), cir, alg);
                fitness_to_return += temp.getFitness();
                count += temp.getPerfectcount();
            }
        }

        if (fitness_to_return > Float.MAX_VALUE) {
            return new Suitability(Float.MAX_VALUE, 0);
        }

        fitness_to_return = count != 0 ? fitness_to_return / count
                : fitness_to_return;

        return new Suitability(fitness_to_return, count);
    }

    @Override
    public SuitabilityMeasure getQfitnessfunction() {
        return ff;
    }

    @Override
    public quantumproblem getQproblem() {
        return quantprob;
    }

    /*
     * (non-Javadoc)
     *
     * @see info.samratcliff.core.CircuitEvaluator.circuitevaluator#getResults(info.samratcliff.core.Algorithms.
     * QuantumAlgorithm)
     */
    @Override
    public testsuite getResults(QuantumAlgorithm alg) {
        Circuit cir;
        Iterator<IQuantumGate> qgate_iter;
        testsuite ts = quantprob.getTestSuite();
        testsuite to_return = new testsuite(ts.getNumOfCustomGates());
        testcase tc;
        testcase tc_toadd;
        testset ts_toadd;
        Set<Integer> keys = ts.getKeys();

        Iterator<Integer> kiter = keys.iterator();

        while (kiter.hasNext()) {
            int numofqubits = kiter.next();
            testset tempts = ts.getTestcases(numofqubits);

            ts_toadd = new testset(numofqubits);
            cir = cb.Build(alg, numofqubits);
            Iterator<testcase> tciter = tempts.getTestcases();
            IQuantumGate qg;
            while (tciter.hasNext()) {
                tc = tciter.next();
                tc_toadd = new testcase(tc.getId(), tc.getLabel(),
                        tc.getCustomGateDefinitions());
                qgate_iter = cir.getCircuitlayout();
                cir.getSize();
                Matrix state = tc.getStartingStateCopy();
                while (qgate_iter.hasNext()) {
                    qg = qgate_iter.next();
                    state = qg.apply(state, tc);
                }
                tc_toadd.setStartingstate(tc.getStartingStateCopy());
                tc_toadd.setFinalstate(state);
                ts_toadd.addTestcases(tc_toadd);

            }
            to_return.addTestcases(ts_toadd);
        }
        return to_return;
    }

    /*
     * (non-Javadoc)
     *
     * @see info.samratcliff.core.CircuitEvaluator.circuitevaluator#getTrace(info.samratcliff.core.Algorithms.
     * QuantumAlgorithm)
     */
    @Override
    public List<testset> getTrace(QuantumAlgorithm alg, int numofqubits) {
        List<testset> toReturn = new Vector<testset>();
        Circuit cir;
        Iterator<IQuantumGate> qgate_iter;
        int id = 0;

        testsuite ts = quantprob.getTestSuite();
        cir = cb.Build(alg, numofqubits);
        testcase tc;
        Set<Integer> keys = ts.getKeys();

        Iterator<Integer> kiter = keys.iterator();
        while (kiter.hasNext()) {
            int tsnumofqubits = kiter.next();
            testset tempts = ts.getTestcases(tsnumofqubits);
            if (Math.abs(tsnumofqubits) == numofqubits) {
                Iterator<testcase> tciter = tempts.getTestcases();
                IQuantumGate qg;
                while (tciter.hasNext()) {
                    testcase next = tciter.next();
                    testcase temp = new testcase(id++, next.getLabel(),
                            next.getCustomGateDefinitions());
                    temp.setStartingstate(next.getStartingStateCopy());
                    temp.setFinalstate(next.getFinalStateCopy());
                    // System.out.println("basic circuit eval ID : "
                    // + next.getId() + " Label : " + next.getLabel());
                    qgate_iter = cir.getCircuitlayout();
                    cir.getSize();
                    Matrix state = next.getStartingStateCopy();
                    int x = 0;
                    if (toReturn.size() < x + 1) {
                        toReturn.add(x, new testset(numofqubits));
                    }
                    tc = temp.copy();
                    tc.setFinalstate(state.copy());
                    toReturn.get(x).addTestcases(tc);
                    x++;
                    while (qgate_iter.hasNext()) {
                        if (toReturn.size() < x + 1) {
                            toReturn.add(x, new testset(numofqubits));
                        }
                        qg = qgate_iter.next();
                        state = qg.apply(state, tc);
                        tc = temp.copy();
                        tc.setFinalstate(state.copy());
                        toReturn.get(x).addTestcases(tc);
                        x++;
                    }
                }
            }
        }

        return toReturn;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * info.samratcliff.core.CircuitEvaluator.circuitevaluator#removeObserver(java.util.Observer)
     */
    @Override
    public void removeObserver(Observer ob) {
        super.deleteObserver(ob);
    }

    @Override
    public void setQfitnessfunction(SuitabilityMeasure qfitnessfunction) {
        ff = qfitnessfunction;
    }

    @Override
    public void setQproblem(quantumproblem qproblem) {
        quantprob = qproblem;
        setChanged();
        notifyObservers();
    }
}
