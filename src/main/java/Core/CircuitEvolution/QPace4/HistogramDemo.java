/**
 * @Author = Sam Ratcliff
 */
package Core.CircuitEvolution.QPace4;

/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ------------------
 * HistogramDemo.java
 * ------------------
 * (C) Copyright 2003, 2004, by Jelai Wang and Contributors.
 *
 * Original Author:  Jelai Wang (jelaiw AT mindspring.com);
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * $Id: HistogramDemo.java,v 1.1 2005/04/28 16:29:17 harrym_nu Exp $
 *
 * Changes
 * -------
 * 06-Jul-2003 : Version 1, contributed by Jelai Wang (DG);
 * 07-Jul-2003 : Modified to display chart on screen, consistent with the other JFreeChart demo
 *               applications (DG);
 * 01-Mar-2004 : Modified for updates to HistogramDataset (DG);
 *
 */

import java.io.IOException;
import java.security.SecureRandom;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A demo of the {@link HistogramDataset} class.
 * 
 * @author Jelai Wang, jelaiw AT mindspring.com
 */
public class HistogramDemo extends ApplicationFrame {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 7194675587377505202L;
	/** For generating random numbers. */
	private static SecureRandom	random				= new SecureRandom();

	/**
	 * Generates an array of sample data.
	 * 
	 * @param size
	 *            the array size.
	 * @param shift
	 *            the shift from zero.
	 * 
	 * @return The array of sample data.
	 */
	private static double[] gaussianData(int size, double shift) {
		double[] d = new double[size];
		for (int i = 0; i < d.length; i++) {
			d[i] = random.nextGaussian() + shift;
		}
		return d;
	}

	/**
	 * The starting point for the demo.
	 * 
	 * @param args
	 *            ignored.
	 * 
	 * @throws IOException
	 *             if there is a problem saving the file.
	 */
	public static void main(String[] args) throws IOException {

		HistogramDemo demo = new HistogramDemo("Histogram Demo");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

	}

	// ****************************************************************************
	// * JFREECHART DEVELOPER GUIDE *
	// * The JFreeChart Developer Guide, written by David Gilbert, is available
	// *
	// * to purchase from Object Refinery Limited: *
	// * *
	// * http://www.object-refinery.com/jfreechart/guide.html *
	// * *
	// * Sales are used to provide funding for the JFreeChart project - please *
	// * support us so that we can continue developing free software. *
	// ****************************************************************************

	/**
	 * Creates a new demo.
	 * 
	 * @param title
	 *            the frame title.
	 */
	public HistogramDemo(String title) {
		super(title);
		IntervalXYDataset dataset = createDataset();
		JFreeChart chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		chartPanel.setMouseZoomable(true, false);
		setContentPane(chartPanel);
	}

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            a dataset.
	 * 
	 * @return The chart.
	 */
	private JFreeChart createChart(IntervalXYDataset dataset) {
		JFreeChart chart = ChartFactory.createHistogram("Histogram Demo", null,
				null, dataset, PlotOrientation.VERTICAL, true, false, false);
		chart.getXYPlot().setForegroundAlpha(0.75f);
		return chart;
	}

	/**
	 * Creates a sample {@link HistogramDataset}.
	 * 
	 * @return The dataset.
	 */
	private IntervalXYDataset createDataset() {
		HistogramDataset dataset = new HistogramDataset();
		dataset.setType(HistogramType.RELATIVE_FREQUENCY);
		dataset.addSeries("H1", gaussianData(1000, 3.0), 20);
		dataset.addSeries("H0", gaussianData(1000, 0), 20);
		return dataset;
	}

}