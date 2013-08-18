package info.samratcliff.ui;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class GUIModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(qcevolutionfrontend.class).asEagerSingleton();
        bind(MainPanel.class).asEagerSingleton();
        bind(String.class).annotatedWith(Names.named("FrameTitle")).toInstance(
                "Test");
    }

}
