package info.samratcliff.ui;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import info.samratcliff.core.qcevolutionbackend;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

class ClockLabel extends JLabel implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 5343051834033522827L;

    public ClockLabel() {
        super("" + new Date());
        Timer t = new Timer(1000, this);
        t.start();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        setText((new Date()).toString());
    }
}

/**
 * @uml.dependency supplier="info.samratcliff.core.qcevolutionbackend"
 */
public class qcevolutionfrontend extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -8691589783757188933L;

    // public static void main(final String[] args) {
    //
    // PropertyConfigurator.configure("log4j.properties");
    //
    // EventQueue.invokeLater(new Runnable() {
    // @Override
    // public void run() {
    // qcevolutionfrontend frame = new qcevolutionfrontend("Test",
    // args[0], args[1], args[2], Boolean
    // .parseBoolean(args[3]));
    // frame.setVisible(true);
    // }
    // });
    //
    // }

    /**
     * @uml.property name="backend"
     */
    private final qcevolutionbackend backend;

    private static final Logger logger = Logger
            .getLogger(qcevolutionfrontend.class.getClass());

    @Inject
    public qcevolutionfrontend(qcevolutionbackend backend, MainPanel mp,
                               @Named("FrameTitle") String title) {
        super(title);
        this.backend = backend;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // try {
        // UIManager.setLookAndFeel(new SeaGlassLookAndFeel());
        // } catch (UnsupportedLookAndFeelException ex) {
        // logger.info("Error when setting the look and feal to sea glass");
        // }

        // backend.setCirbui(new basiccircuitbuilder());
        // if (parsimonious) {
        // backend.setCireval(new parsimoniouscircuitevaluator(backend
        // .getCirbui()));
        // } else {
        // backend.setCireval(new basiccircuitevaluator(backend.getCirbui()));
        // }
        // backend.setFfmanager(new SuitabilityMeasureManager(ff_file));
        // backend.setSemanager(new searchenginemanager(se_file));
        // backend.setProbmanager(new Problem_Manager(prob_file));

        setUndecorated(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Close button Pressed");
                qcevolutionfrontend.this.setVisible(false);
                System.exit(0);
            }
        });
        JLabel userLabel = new JLabel();
        userLabel.setText("Currently running as "
                + System.getProperty("user.name"));
        ClockLabel clock = new ClockLabel();
        JPanel lowerPanel = new JPanel();
        JPanel closePanel = new JPanel();
        closePanel.add(closeButton);
        lowerPanel.setLayout(new BorderLayout());
        lowerPanel.add(userLabel, BorderLayout.LINE_START);
        lowerPanel.add(closePanel, BorderLayout.CENTER);
        lowerPanel.add(clock, BorderLayout.LINE_END);
        getContentPane().add(mp, BorderLayout.CENTER);
        getContentPane().add(lowerPanel, BorderLayout.PAGE_END);
    }

    /**
     * Getter of the property <tt>backend</tt>
     *
     * @return Returns the backend.
     * @uml.property name="backend"
     */
    public qcevolutionbackend getBackend() {
        return backend;
    }

}