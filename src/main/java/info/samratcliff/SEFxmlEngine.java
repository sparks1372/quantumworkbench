package info.samratcliff;

import info.samratcliff.ui.controller.CreateProblemController;
import info.samratcliff.ui.controller.CreateProblemControllerImpl;
import info.samratcliff.ui.controller.CustomGateQuantityController;
import info.samratcliff.ui.controller.CustomGateQuantityControllerImpl;
import info.samratcliff.ui.controller.FitnessFunctionController;
import info.samratcliff.ui.controller.FitnessFunctionControllerImpl;
import info.samratcliff.ui.controller.LoginController;
import info.samratcliff.ui.controller.LoginControllerImpl;
import info.samratcliff.ui.controller.ProblemController;
import info.samratcliff.ui.controller.ProblemControllerImpl;
import info.samratcliff.ui.controller.SearchEngineController;
import info.samratcliff.ui.controller.SearchEngineControllerImpl;

import java.util.Collection;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Core.CoreModule;

import com.cathive.fx.guice.GuiceApplication;
import com.cathive.fx.guice.GuiceFXMLLoader;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.name.Names;

public class SEFxmlEngine extends GuiceApplication {

	private Stage main;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public Collection<Module> initModules() {
		// return Lists.<Module> newArrayList(new WorkbenchModule());
		return Lists.<Module> newArrayList(new AbstractModule() {
			@Override
			protected void configure() {
				install(new CoreModule());
				bind(String.class).annotatedWith(
						Names.named("SearchEngineFile")).toInstance(
						"SearchEngine.xml");
				bind(String.class).annotatedWith(
						Names.named("FitnessFunctionFile")).toInstance(
						"FitnessFunction.xml");
				bind(String.class).annotatedWith(Names.named("ProblemFile"))
						.toInstance("Problems.xml");
				bind(SearchEngineController.class).to(
						SearchEngineControllerImpl.class).asEagerSingleton();
				bind(FitnessFunctionController.class).to(
						FitnessFunctionControllerImpl.class).asEagerSingleton();
				bind(ProblemController.class).to(ProblemControllerImpl.class)
						.asEagerSingleton();
				bind(LoginController.class).to(LoginControllerImpl.class)
						.asEagerSingleton();
				bind(CustomGateQuantityController.class).to(
						CustomGateQuantityControllerImpl.class)
						.asEagerSingleton();
				bind(CreateProblemController.class).to(
						CreateProblemControllerImpl.class).asEagerSingleton();

			}
		});
	}

	@Override
	public void start(Stage stage) throws Exception {
		main = stage;
		GuiceFXMLLoader loader = new GuiceFXMLLoader(getInjector());
		Parent root = loader.load(getClass().getClassLoader().getResource(
				"fxml/main.fxml"));

		stage.setScene(new Scene(root/* , 300, 275 */));
		stage.setFullScreen(true);
		stage.show();

	}

	public Stage getMain() {
		return main;
	}
}
