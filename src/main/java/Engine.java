import GUI.ViewMaster;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Engine {
	public static void main(String[] args) {
		Injector inj = Guice.createInjector(new WorkbenchModule());
		inj.getInstance(ViewMaster.class).start();
	}
}
