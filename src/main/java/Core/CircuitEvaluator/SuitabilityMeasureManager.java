package Core.CircuitEvaluator;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Set;

import Core.CircuitEvaluator.Util.FF_XML_Parser;
import Core.CircuitEvaluator.Util.FitnessFunctionTag;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class SuitabilityMeasureManager {

	/**
	 * @uml.property name="availablefitnessfuntions" multiplicity="(0 -1)"
	 *               dimension="1"
	 */
	// Search Engine Name - Search Engine Class Name
	private final HashMap<String, FitnessFunctionTag> availablefitnessfuntions;

	/**
			 */
	@Inject
	public SuitabilityMeasureManager(
			@Named("FitnessFunctionFile") String se_xml_file_name) {
		FF_XML_Parser xmlp = new FF_XML_Parser(se_xml_file_name);
		availablefitnessfuntions = xmlp.parseDocument();
	}

	public Set<String> getAvailableFitnessFunctions() {
		return availablefitnessfuntions.keySet();
	}

	public SuitabilityMeasure getFitnessFuntion(String key) {
		Object retobj = null;
		try {
			FitnessFunctionTag ff = availablefitnessfuntions.get(key);
			Class<?> cls = Class.forName(ff.Class);
			Class<?> partypes[] = new Class[1];
			partypes[0] = String.class;
			Constructor<?> ct = cls.getConstructor(partypes);
			Object arglist[] = new Object[1];
			arglist[0] = ff.Name;
			retobj = ct.newInstance(arglist);
		} catch (Throwable e) {
			System.err.println(e);
		}
		return (SuitabilityMeasure) retobj;
	}

	public String getFitnessFunctionDesc(String key) {

		FitnessFunctionTag selected = availablefitnessfuntions.get(key);

		return selected.Desc;
	}
}
