package info.samratcliff.core.Implementation;

import info.samratcliff.core.Problem.quantumproblem;
import info.samratcliff.core.Problem.testsuite;

public class simpleqcproblem implements quantumproblem {

    /**
     *
     */
    private static final long serialVersionUID = 3785647079669667694L;
    private final String name;
    private testsuite ts;

    public simpleqcproblem(String n, int numcustgates) {
        name = n;
        ts = new testsuite(numcustgates);
    }

    @Override
    public String getName() {
        return name;
    }

    /*
     * (non-Javadoc)
     *
     * @see info.samratcliff.core.Problem.quantumproblem#getNumOfQubits()
     */
    @Override
    public int getNumOfCustomGates() {
        return ts.getNumOfCustomGates();
    }

    @Override
    public testsuite getTestSuite() {
        return ts;
    }

    @Override
    public void setTestSuite(testsuite ints) {
        ts = ints;
    }

}
