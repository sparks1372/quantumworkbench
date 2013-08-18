package info.samratcliff.core.CircuitEvolution.QPace4;

import com.google.common.eventbus.Subscribe;
import info.samratcliff.core.CircuitEvolution.QPace4.Stats.RunStatistics;
import info.samratcliff.ui.MainPanel;

import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel {
    private final static String genStr = "Generations";
    private final JLabel timeL;
    private final JLabel timeTA;
    private static String timeStr = "Time Taken";
    private final JLabel numGenL;
    private final JLabel numGenTA;
    private final JLabel fitL;
    private final JLabel fitTA;
    private static String fitStr = "Best Fitness";
    private final JLabel iterL;
    private final JLabel iterTA;
    private static String iterStr = "Iteration #";
    private final JProgressBar progressBar;

    public StatsPanel(JProgressBar in) {
        progressBar = in;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setMaximumSize(new Dimension(
                (int) (screenSize.width * MainPanel.right_perc),
                screenSize.height / 2));
        timeL = new JLabel(timeStr);
        timeTA = new JLabel();
        numGenL = new JLabel(genStr);
        numGenTA = new JLabel();
        fitL = new JLabel(fitStr);
        fitTA = new JLabel();
        iterL = new JLabel(iterStr);
        iterTA = new JLabel("0");

        JPanel pPanel = new JPanel();
        pPanel.setLayout(new FlowLayout());
        pPanel.add(progressBar);

        JPanel tPanel = new JPanel();
        tPanel.setLayout(new FlowLayout());
        tPanel.add(timeL);
        tPanel.add(timeTA);

        JPanel ngPanel = new JPanel();
        ngPanel.setLayout(new FlowLayout());
        ngPanel.add(numGenTA);
        ngPanel.add(numGenL);

        JPanel fPanel = new JPanel();
        fPanel.setLayout(new FlowLayout());
        fPanel.add(fitL);
        fPanel.add(fitTA);

        JPanel iterPanel = new JPanel();
        iterPanel.setLayout(new FlowLayout());
        iterPanel.add(iterL);
        iterPanel.add(iterTA);

        this.add(pPanel);
        this.add(tPanel);
        this.add(ngPanel);
        this.add(fPanel);
        this.add(iterPanel);
    }

    /**
     * @return the progressBar
     */
    public JProgressBar getProgressBar() {
        return progressBar;
    }

    /**
     *
     */
    public synchronized void incIter() {
        iterTA.setText(Integer.toString(Integer.parseInt(iterTA.getText()) + 1));
    }

    public void resetStatsPanel() {
        setIter(-1);
        setTime(-1);
        setGens(-1);
        fitTA.setText("");
    }

    public synchronized void setFitness(float fit) {
        fitTA.setText(Float.toString(fit));
    }

    public synchronized void setGens(long gens) {
        numGenTA.setText(Long.toString(gens + 1));
    }

    public synchronized void setIter(int iter) {
        iterTA.setText(Integer.toString(iter + 1));
    }

    public synchronized void setTime(long td) {
        long dif = td;
        int days = (int) Math.floor(dif / (24 * 60 * 60 * 1000.0));
        dif = dif - days * (24 * 60 * 60 * 1000);
        int hours = (int) Math.floor(dif / (60 * 60 * 1000.0));
        dif = dif - hours * (60 * 60 * 1000);
        int mins = (int) Math.floor(dif / (60 * 1000.0));
        dif = dif - mins * (60 * 1000);
        int secs = (int) Math.floor(dif / (1000.0));
        dif = dif - secs * (1000);
        int millisecs = (int) dif;
        timeTA.setText(days + "::" + hours + "::" + mins + "::" + secs + "."
                + millisecs);
    }

    @Subscribe
    public void handleStatisticsMessage(RunStatistics stats) {
        if (stats.getFitness() != null) {
            setFitness(stats.getFitness().floatValue());
        }
        setGens(stats.getGenerationCount());
    }
}
