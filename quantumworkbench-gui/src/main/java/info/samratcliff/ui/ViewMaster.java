package info.samratcliff.ui;

import com.google.inject.Inject;

import java.awt.*;

public class ViewMaster {
    final private qcevolutionfrontend fe;

    @Inject
    public ViewMaster(qcevolutionfrontend fe) {
        super();
        this.fe = fe;
    }

    public void start() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                fe.setVisible(true);
            }
        });
    }

}
