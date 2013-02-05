import info.samratcliff.ui.controller.LoginController;
import info.samratcliff.ui.controller.LoginControllerImpl;

import java.util.Collection;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.cathive.fx.guice.GuiceApplication;
import com.cathive.fx.guice.GuiceFXMLLoader;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Module;

public class FxmlEngine extends GuiceApplication {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public Collection<Module> initModules() {
		// return Lists.<Module> newArrayList(new WorkbenchModule());
		return Lists.<Module> newArrayList(new AbstractModule() {
			@Override
			protected void configure() {
				bind(LoginController.class).to(LoginControllerImpl.class)
						.asEagerSingleton();
			}
		});
	}

	@Override
	public void start(Stage stage) throws Exception {
		GuiceFXMLLoader loader = new GuiceFXMLLoader(getInjector());
		Parent root = loader.load(getClass().getResource("fxml_example.fxml"));

		stage.setTitle("FXML Welcome");
		stage.setScene(new Scene(root, 300, 275));
		stage.show();

	}
}
