package info.samratcliff;

import com.cathive.fx.guice.GuiceApplication;
import com.cathive.fx.guice.GuiceFXMLLoader;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.name.Names;
import info.samratcliff.core.CoreModule;
import info.samratcliff.ui.controller.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Collection;

public class SEFxmlEngine extends GuiceApplication {

    private Stage main;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public Collection<Module> initModules() {
        // return Lists.<Module> newArrayList(new WorkbenchModule());
        return Lists.<Module>newArrayList(new AbstractModule() {
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
