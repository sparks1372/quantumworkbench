package GUI;

import java.awt.EventQueue;

import com.google.inject.Inject;

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
