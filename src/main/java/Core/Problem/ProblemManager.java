/**
 * @Author = Sam Ratcliff
 */
package Core.Problem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Set;

import Core.Implementation.simpleqcproblem;
import Core.Problem.Util.Prob_XML_Parser;
import Core.Problem.Util.ProblemTag;
import Core.Problem.Util.TestSuiteUtils;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * @author Sam Ratcliff
 * 
 */
public class ProblemManager extends Observable {

	/**
	 * @uml.property name="availablesearchengines" readOnly="true"
	 */
	// Search Engine Name - Search Engine Class Name
	private final HashMap<String, ProblemTag> availableproblems;
	private final HashMap<String, quantumproblem> instatiatedproblems;
	private final String prob_file;
	private boolean saved;

	/**
			 */
	@Inject
	public ProblemManager(@Named("ProblemFile") String prob_xml_file_name) {
		prob_file = prob_xml_file_name;
		Prob_XML_Parser xmlp = new Prob_XML_Parser(prob_file);
		availableproblems = xmlp.parseDocument();
		saved = true;
		instatiatedproblems = new HashMap<String, quantumproblem>();
	}

	public boolean addProblem(String Name, String FileName, String Description) {
		boolean to_return = true;
		if (availableproblems.containsKey(Name)) {
			to_return = false;
		} else {
			ProblemTag pt = new ProblemTag(Name, FileName, Description);
			availableproblems.put(Name, pt);
			saved = false;
			setChanged();
			notifyObservers(this);
		}
		return to_return;
	}

	public Set<String> getAvailableProblems() {
		return availableproblems.keySet();
	}

	public quantumproblem getProblem(String key) {
		quantumproblem to_ret;

		if (!instatiatedproblems.containsKey(key)) {
			ProblemTag selected = availableproblems.get(key);
			testsuite ts = TestSuiteUtils.XMLToTestSuite(selected.Def_File);
			to_ret = new simpleqcproblem(selected.Name,
					ts.getNumOfCustomGates());
			to_ret.setTestSuite(ts);
			instatiatedproblems.put(key, to_ret);
		} else {
			to_ret = instatiatedproblems.get(key);
		}

		return to_ret;

	}

	/**
	 * @param name
	 * @return
	 */
	public ProblemTag getProblemTag(String name) {
		return availableproblems.get(name);
	}

	public String getSearchEngineDesc(String key) {

		ProblemTag selected = availableproblems.get(key);

		return selected.Desc;
	}

	/**
	 * @return the saved
	 */
	public boolean isSaved() {
		return saved;
	}

	public void saveToFile() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(prob_file));
			out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			out.write("<Problems xmlns=\"http://www.w3schools.com\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.w3schools.com problem_spec.xsd\">\n");
			Iterator<String> problem_iterator = availableproblems.keySet()
					.iterator();
			ProblemTag pr;
			while (problem_iterator.hasNext()) {
				pr = availableproblems.get(problem_iterator.next());
				out.write("<prob>");
				out.write("<Name>" + pr.Name + "</Name>");
				out.write("<DefFile>" + pr.Def_File + "</DefFile>");
				out.write("<Desc>" + pr.Desc + "</Desc>");
				out.write("</prob>");
				out.newLine();
			}
			out.write("</Problems>");
			out.close();
			saved = true;
			setChanged();
			notifyObservers();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean updateProblem(String Name, String FileName, testsuite ts,
			String Description) {
		boolean to_return = true;
		if (!availableproblems.containsKey(Name)) {
			to_return = false;
		} else {
			quantumproblem qp = getProblem(Name);
			qp.setTestSuite(ts);
			setChanged();
		}
		notifyObservers(this);
		return to_return;
	}
}
