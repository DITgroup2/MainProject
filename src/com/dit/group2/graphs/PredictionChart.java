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

import com.dit.group2.order.OrderDB;
import com.dit.group2.order.Prediction01;
import com.dit.group2.stock.StockDB;

public class PredictionChart {

	private StockDB stockDBControls;
	private OrderDB orderDB;

	public void buildPredictionChart(StockDB stockDBControl, OrderDB orderDB) {
		Calendar rightNow = Calendar.getInstance();

		/* creating timeSeriesCollection */
		// Calendar c = Calendar.getInstance();
		int month = rightNow.get(Calendar.MONTH);
		System.out.println(rightNow.get(Calendar.MONTH));
		System.out.println(rightNow.get(Calendar.DAY_OF_YEAR));
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		TimeSeries timeSeries1 = new TimeSeries("Sample 1");
		TimeSeries timeSeries2 = new TimeSeries("Sample 2");

		Prediction01 pred = new Prediction01(stockDBControl.getStockList().get(
				orderDB.getChartCustomerIndex()).getProduct(), orderDB.getCustomerOrderList());

		// Color myColor = Color.getColor("myColor");
		Color color = Color.blue;

		for (int i = 0; i < 40; i++) {

			// System.out.println("prediciton douubles: " + pred.getPredictionInts()[i]);
			// Prediction01 pred = new
			// Prediction01(stockDBControl.getStockList().get(0).getProduct(),orderDB.getCustomerOrderList());
			System.out.println("dayyy of month...."
					+ Calendar.DAY_OF_MONTH
					+ " "
					+ stockDBControl.getStockList().get(orderDB.getChartCustomerIndex())
							.getProduct().getProductName());

			if ((Calendar.DAY_OF_MONTH + i) < 31) {
				timeSeries1.add(new Day(Calendar.DAY_OF_MONTH + i, month, 2014), pred
						.getPredictionInts()[i]);
			}
			if ((Calendar.DAY_OF_MONTH + i) > 30) {
				color = Color.green;
				timeSeries2.add(new Day(((Calendar.DAY_OF_MONTH + i) - 30), month + 1, 2014), pred
						.getPredictionInts()[i]);
			}

		}
		dataset.addSeries(timeSeries1);
		dataset.addSeries(timeSeries2);

		/* Creating the chart */
		JFreeChart chart = ChartFactory.createTimeSeriesChart(stockDBControl.getStockList().get(
				orderDB.getChartCustomerIndex()).getProduct().getProductName(), "Date",
				"Quantity Sold", dataset, true, true, false);
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
		numberAxis.setRange(new Range(0, 25));
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
