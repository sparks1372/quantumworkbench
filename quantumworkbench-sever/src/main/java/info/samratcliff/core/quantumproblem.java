package info.samratcliff.core;

import info.samratcliff.core.Problem.testsuite;

public interface quantumproblem {

    /**
     * @return Returns the name.
     * @uml.property name="name" readOnly="true"
     */
    public String getName();

    /**
     * @return Returns the testset.
     * @uml.property name="testset"
     */
    public testsuite getTestSuite();

    /**
     * @arg the testset.
     * @uml.property name="testset"
     */
    public void setTestSuite(testsuite tsin);

}
