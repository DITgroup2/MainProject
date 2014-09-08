package com.dit.group2.graphs;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;

import com.dit.group2.order.Prediction01;
import com.dit.group2.retailSystem.RetailSystemDriver;

public class PredictionChart {

	private RetailSystemDriver driver;

	public void buildPredictionChart(RetailSystemDriver driver) {

		this.driver = driver;
		Calendar rightNow = Calendar.getInstance();

		/* creating timeSeriesCollection */
		// Calendar c = Calendar.getInstance();
		int month = rightNow.get(Calendar.MONTH);
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		TimeSeries timeSeries1 = new TimeSeries("Past 30 days Sales");
		TimeSeries timeSeries2 = new TimeSeries("Predicted Sales");

		Prediction01 pred = new Prediction01(driver.getStockDB().getStockList().get(
				driver.getGui().getAccountingTab().getProductCombobox().getSelectedIndex())
				.getProduct(), driver.getOrderDB().getCustomerOrderList());

		// Color myColor = Color.getColor("myColor");
		Color color = Color.blue;

		for (int i = 0; i < 40; i++) {
			if ((Calendar.DAY_OF_MONTH + i) < 31) {
				timeSeries1.add(new Day(Calendar.DAY_OF_MONTH + i, month, 2014), pred
						.getPredictionInts()[i]);
			}
			if (((Calendar.DAY_OF_MONTH + i) > 30) && ((Calendar.DAY_OF_MONTH + i) < 38)) {
				color = Color.green;
				timeSeries1.add(new Day(((Calendar.DAY_OF_MONTH + i) - 30), month + 1, 2014), pred
						.getPredictionInts()[i]);
			}
			if ((Calendar.DAY_OF_MONTH + i) > 38) {

				timeSeries2.add(new Day(((Calendar.DAY_OF_MONTH + i) - 30), month + 1, 2014), pred
						.getPredictionInts()[i]);

			}

		}
		dataset.addSeries(timeSeries1);
		dataset.addSeries(timeSeries2);

		/* Creating the chart */
		JFreeChart chart = ChartFactory
				.createTimeSeriesChart(driver.getStockDB().getStockList().get(
						driver.getGui().getAccountingTab().getProductCombobox().getSelectedIndex())
						.getProduct().getProductName(), "Date", "Quantity Sold", dataset, true,
						true, false);
		/* Altering the graph */
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setAxisOffset(new RectangleInsets(5.0, 10.0, 10.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);
		int x = 0;

		plot.getRenderer().setSeriesPaint(0, Color.blue);

		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
		renderer.setBaseShapesVisible(true);
		renderer.setBaseShapesFilled(true);
		NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
		numberAxis.setRange(new Range(0, 50));
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy"));
		axis.setAutoTickUnitSelection(false);
		axis.setVerticalTickLabels(true);
		/* Displaying the chart */
		ChartFrame frame = new ChartFrame("Daily Charts", chart);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String args[]) {
		PredictionChart predictionchart = new PredictionChart();
		// predictionchart.buildPredictionChart(Product product, ArrayList<Order> orderList);

	}

}
