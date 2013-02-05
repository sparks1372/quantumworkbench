package Core.Problem;

import java.io.Serializable;

import Jama.Matrix;
import Utils.MatrixUtils;

public class testcase implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6276508731897152835L;

	/**
	 * @uml.property name="startingstate"
	 */
	private Matrix				startingstate;

	/**
	 * @uml.property name="label"
	 */
	private String				label;

	/**
	 * @uml.property name="id"
	 */
	private int					id;

	/**
	 * @uml.property name="finalstate"
	 */
	private Matrix				finalstate;

	private final String[]		customGateFiles;
	private final Matrix[]		customGates;

	public testcase(int id, String label, int numCustomGates) {
		this.id = id;
		this.label = label;
		customGates = new Matrix[numCustomGates];
		customGateFiles = new String[numCustomGates];
	}

	public testcase(int id, String label, String[] customGatesF) {
		this.id = id;
		this.label = label;
		customGateFiles = customGatesF;
		customGates = new Matrix[customGateFiles.length];
		int index = 0;
		for (String file : customGateFiles) {
			this.customGates[index++] = MatrixUtils.fromFile(file);
		}
	}

	/**
	 * @return
	 */
	public testcase copy() {
		testcase to_ret = new testcase(getId(), getLabel(),
				getCustomGateDefinitions());
		to_ret.setStartingstate(getStartingStateCopy());
		to_ret.setFinalstate(getFinalStateCopy());
		for (int i = 0; i < customGateFiles.length; i++) {
			to_ret.setCustomGates(i, customGateFiles[i]);
		}
		return to_ret;
	}

	public boolean equal(testcase tc) {
		if (!Matrix.equal(startingstate, tc.startingstate)) {
			return false;
		}
		if (!Matrix.equal(finalstate, tc.finalstate)) {
			return false;
		}
		if (!(id == tc.id)) {
			return false;
		}
		if (!(label.equalsIgnoreCase(tc.label))) {
			return false;
		}
		if (!(customGates.length == tc.customGates.length)) {
			return false;
		}
		for (int i = 0; i < customGates.length; i++) {
			if (!Matrix.equal(customGates[i], tc.customGates[i])) {
				return false;
			}
			if (!(customGateFiles[i].equalsIgnoreCase(tc.customGateFiles[i]))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return the customGates
	 */
	public Matrix getCustomGate(int i) throws IndexOutOfBoundsException {
		return customGates[i].copy();
	}

	/**
	 * @return the customGates
	 */
	public String[] getCustomGateDefinitions() {
		return customGateFiles;
	}

	/**
	 * Getter of the property <tt>finalstate</tt>
	 * 
	 * @return Returns the finalstate.
	 * @uml.property name="finalstate"
	 */
	public Matrix getFinalstate() {
		return finalstate;
	}

	/**
	 * Getter of the property <tt>finalstate</tt> BE CAREFULL WHEN USING, ONLY
	 * TO BE USED WHEN EDITTING THE TEST CASE. FOR READ-ONLY USE getFinalstate
	 * INSTEAD.
	 * 
	 * @return Returns the finalstate.
	 * @uml.property name="finalstate"
	 */
	public Matrix getFinalStateCopy() {
		return finalstate.copy();
	}

	/**
	 * Getter of the property <tt>id</tt>
	 * 
	 * @return Returns the id.
	 * @uml.property name="id"
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter of the property <tt>label</tt>
	 * 
	 * @return Returns the label.
	 * @uml.property name="label"
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Getter of the property <tt>startingstate</tt> BE CAREFULL WHEN USING,
	 * ONLY TO BE USED WHEN EDITTING THE TEST CASE. FOR READ-ONLY USE
	 * getStartingStateCopy INSTEAD.
	 * 
	 * @return Returns the startingstate.
	 * @uml.property name="startingstate"
	 */
	public Matrix getStartingState() {
		return startingstate;
	}

	/**
	 * Getter of the property <tt>startingstate</tt>
	 * 
	 * @return Returns the startingstate.
	 * @uml.property name="startingstate"
	 */
	public Matrix getStartingStateCopy() {
		return startingstate.copy();
	}

	/**
	 * @param customGates
	 *            the customGates to set
	 */
	public void setCustomGates(int index, String customGates) {
		this.customGateFiles[index] = customGates;
		this.customGates[index] = MatrixUtils.fromFile(customGates);
	}

	/**
	 * Setter of the property <tt>finalstate</tt>
	 * 
	 * @param finalstate
	 *            The finalstate to set.
	 * @uml.property name="finalstate"
	 */
	public void setFinalstate(Matrix finalstate) {
		this.finalstate = finalstate.copy();
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param label
	 *            the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Setter of the property <tt>startingstate</tt>
	 * 
	 * @param startingstate
	 *            The startingstate to set.
	 * @uml.property name="startingstate"
	 */
	public void setStartingstate(Matrix startingstate) {
		this.startingstate = startingstate;
	}

}
