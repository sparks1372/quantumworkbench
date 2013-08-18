package info.samratcliff.core.CircuitEvolution;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import info.samratcliff.core.CircuitEvolution.Util.SE_XML_Parser;
import info.samratcliff.core.CircuitEvolution.Util.SearchEngineTag;

import java.util.HashMap;
import java.util.Set;

public class searchenginemanager {

    /**
     * @uml.property name="availablesearchengines" readOnly="true"
     */
    // Search Engine Name - Search Engine Class Name
    private final HashMap<String, SearchEngineTag> availablesearchengines;

    /**
     */
    @Inject
    public searchenginemanager(
            @Named("SearchEngineFile") String se_xml_file_name) {
        SE_XML_Parser xmlp = new SE_XML_Parser(se_xml_file_name);
        availablesearchengines = xmlp.parseDocument();
    }

    public Set<String> getAvailableSearchEngines() {
        return availablesearchengines.keySet();
    }

    public circuitsearchengine getSearchEngine(String key) {
        Object retobj = null;
        try {
            SearchEngineTag selected = availablesearchengines.get(key);
            Class<?> cls = Class.forName(selected.Class);
            retobj = cls.newInstance();
        } catch (Throwable e) {
            System.err.println(e);
            e.printStackTrace();
        }
        return (circuitsearchengine) retobj;
    }

    public String getSearchEngineDesc(String key) {

        SearchEngineTag selected = availablesearchengines.get(key);

        return selected.Desc;
    }
}
