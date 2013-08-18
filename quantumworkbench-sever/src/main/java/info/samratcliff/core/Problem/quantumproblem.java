package info.samratcliff.core.Problem;

import java.io.Serializable;

public interface quantumproblem extends Serializable {

    /**
     * @return Returns the name.
     * @uml.property name="name" readOnly="true"
     */
    public String getName();

    /**
     * @return Returns the number of qubits.
     * @uml.property name="numOfQubits"
     */

    public int getNumOfCustomGates();

    /**
     * @return Returns the testsuite.
     * @uml.property name="testsuite"
     */

    public testsuite getTestSuite();

    /**
     * @arg the testsuite.
     * @uml.property name="testsuite"
     */
    public void setTestSuite(testsuite tsin);

}
