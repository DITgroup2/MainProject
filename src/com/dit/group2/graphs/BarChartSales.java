package com.dit.group2.graphs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import com.dit.group2.order.Account;


/**
 * A simple demonstration application showing how to create a bar chart.
 */
public class BarChartSales extends ApplicationFrame {

	

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	Account account = new Account();

	
	/**
     * Creates a new demo instance.
     *
     * @param title  the frame title.
     */
    public BarChartSales(String title, double q1_2014, double q2_2014, double q3_2014, double q4_2014, 
    		double q1_2013, double q2_2013, double q3_2013, double q4_2013, 
    		double q1_2012, double q2_2012, double q3_2012, double q4_2012) {

        super(title);
        CategoryDataset dataset = createDataset(q1_2014, q2_2014, q3_2014, q4_2014,
        		q1_2013, q2_2013, q3_2013, q4_2013,
        		q1_2012, q2_2012, q3_2012, q4_2012);
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart, true);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
      //  chartPanel.setDefaultCloseOperatio
        
    }

    /**
     * Returns a sample dataset.
     * 
     * @return The dataset.
     */
    private static CategoryDataset createDataset(double q1_2014, double q2_2014, double q3_2014, double q4_2014, 
    		double q1_2013, double q2_2013, double q3_2013, double q4_2013, 
    		double q1_2012, double q2_2012, double q3_2012, double q4_2012) {
        
        // row keys...
        String series1 = "2014";
        String series2 = "2013";
        String series3 = "2012";

        // column keys...
        String category1 = "1st Quarter";
        String category2 = "2nd Quarter";
        String category3 = "3rd Quarter";
        String category4 = "4th Quarter";

        // create the dataset...
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(q1_2014, series1, category1);
        dataset.addValue(q2_2014, series1, category2);
        dataset.addValue(q3_2014, series1, category3);
        dataset.addValue(q4_2014, series1, category4);

        dataset.addValue(q1_2013, series2, category1);
        dataset.addValue(q2_2013, series2, category2);
        dataset.addValue(q3_2013, series2, category3);
        dataset.addValue(q4_2013, series2, category4);

        dataset.addValue(q1_2012, series3, category1);
        dataset.addValue(q2_2012, series3, category2);
        dataset.addValue(q3_2012, series3, category3);
        dataset.addValue(q4_2012, series3, category4);
        
        return dataset;
        
    }
    
    /**
     * Creates a sample chart.
     * 
     * @param dataset  the dataset.
     * 
     * @return The chart.
     */
    private static JFreeChart createChart(CategoryDataset dataset) {
        
        // create the chart...
        JFreeChart chart = ChartFactory.createBarChart(
            "Total Quarterly Sales",         // chart title
            "Category",               // domain axis label
            "Value",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.white);

        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        
        // set up gradient paints for series...
        GradientPaint gp0 = new GradientPaint(
            0.0f, 0.0f, Color.blue, 
            0.0f, 0.0f, new Color(0, 0, 64)
        );
        GradientPaint gp1 = new GradientPaint(
            0.0f, 0.0f, Color.green, 
            0.0f, 0.0f, new Color(0, 64, 0)
        );
        GradientPaint gp2 = new GradientPaint(
            0.0f, 0.0f, Color.red, 
            0.0f, 0.0f, new Color(64, 0, 0)
        );
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );
        ChartFrame frame = new ChartFrame("Charts", chart);
		frame.pack();
		frame.setVisible(true);
        // OPTIONAL CUSTOMISATION COMPLETED.
        return chart;
        
    }
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    


	

}
