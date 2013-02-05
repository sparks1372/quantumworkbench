/**
 * @Author = Sam Ratcliff
 */
package GUI.StateVisualiser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import Jama.Matrix;

/**
 * @author Sam Ratcliff
 * 
 */
public class columnchartvisualiser extends JPanel {
	/**
	 * 
	 */
	private static final long		serialVersionUID	= -848552828462067165L;
	private static String			zero_string			= "0";
	private DefaultCategoryDataset	stateDataSet;
	private JFreeChart				stateChart;
	private final ChartPanel		chartPanel;
	public static int				chartWidth;
	public static int				chartHeight;

	/**
	 * 
	 */
	public columnchartvisualiser(Matrix initialState, int labelLength,
			String name) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		chartWidth = (int) (screenSize.width * 0.45);
		chartHeight = (int) ((screenSize.height * 0.25));
		stateDataSet = new DefaultCategoryDataset();
		stateChart = createChart(name);
		chartPanel = new ChartPanel(stateChart);
		chartPanel.setPreferredSize(new Dimension(chartWidth - 10,
				chartHeight - 15));
		this.setPreferredSize(new Dimension(chartWidth, chartHeight));
		add(chartPanel);

	}

	/**
	 * Creates a sample chart.
	 * 
	 * @return The chart.
	 */
	private JFreeChart createChart(String name) {

		// create the chart...
		final JFreeChart chart = ChartFactory.createBarChart(name, // chart
																	// title
				"State", // domain axis label
				"Probability Amplitude", // range axis label
				stateDataSet, // data
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
				);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

		// set the background color for the chart...
		chart.setBackgroundPaint(Color.white);

		// get a reference to the plot for further customisation...
		final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		// set the range axis to display integers only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
		rangeAxis.setRange(-1, 1);

		// disable bar outlines...
		final BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);

		// set up paints for series...
		renderer.setSeriesPaint(0, Color.blue);
		renderer.setSeriesPaint(1, Color.green);

		final CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(Math.PI / 6.0));
		// OPTIONAL CUSTOMISATION COMPLETED.

		return chart;

	}

	public void updateState(Matrix initialState, int labelLength) {

		// create the dataset...
		stateDataSet = new DefaultCategoryDataset();
		for (int index = 0; index < initialState.getRowDimension(); index++) {

			String b_str = Integer.toBinaryString(index);
			while (b_str.length() != labelLength) {
				b_str = zero_string.concat(b_str);
			}
			stateDataSet.addValue(initialState.get(index, 0).real(), "Real",
					b_str);
			stateDataSet.addValue(initialState.get(index, 0).imag(), "Imag",
					b_str);

		}

		stateChart = createChart(stateChart.getTitle().getText());
		chartPanel.setChart(stateChart);

	}
}
