import Core.CoreModule;
import Core.qcevolutionbackend;
import GUI.GUIModule;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class WorkbenchModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new CoreModule());
		install(new GUIModule());
		bind(qcevolutionbackend.class).asEagerSingleton();
		bind(String.class).annotatedWith(Names.named("SearchEngineFile"))
				.toInstance("SearchEngine.xml");
		bind(String.class).annotatedWith(Names.named("FitnessFunctionFile"))
				.toInstance("FitnessFunction.xml");
		bind(String.class).annotatedWith(Names.named("ProblemFile"))
				.toInstance("Problems.xml");
	}

}
