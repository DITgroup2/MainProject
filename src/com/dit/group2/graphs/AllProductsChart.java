package com.dit.group2.graphs;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

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


public class AllProductsChart {
	
	private RetailSystemDriver driver;
	
	public void buildPredictionChart(RetailSystemDriver driver){
		
		this.driver = driver;
		Calendar rightNow = Calendar.getInstance();

		/*creating timeSeriesCollection*/
		// Calendar c = Calendar.getInstance();
		int month = rightNow.get(Calendar.MONTH);

		TimeSeriesCollection dataset = new TimeSeriesCollection();
	
		List<TimeSeries> timeserieslist = new LinkedList<TimeSeries>();

		TimeSeries[] timeSeries = new TimeSeries[driver.getStockDB().getStockList().size()];

		for(int j = 0; j < driver.getStockDB().getStockList().size(); j++) {
			timeSeries[j] = new TimeSeries(driver.getStockDB().getStockList().get(j).getProduct().getProductName());
			timeserieslist.add(timeSeries[j]);
		
		
		}
		
		
	
		
		List<Prediction01> predictionlist = new LinkedList<Prediction01>();
		
		Prediction01[] pred = new Prediction01[driver.getStockDB().getStockList().size()];

		for(int j = 0; j < driver.getStockDB().getStockList().size(); j++) {
		    pred[j] = new Prediction01(driver.getStockDB().getStockList().get(j).getProduct(),driver.getOrderDB().getCustomerOrderList());
		    predictionlist.add(pred[j]);
		
		
		}
		
		for (int i = 0; i < 40; i++) {
			if((Calendar.DAY_OF_MONTH+i)<31){
				for (int k = 0; k < driver.getStockDB().getStockList().size(); k++) {

				timeSeries[k].add(new Day(Calendar.DAY_OF_MONTH+i,month, 2014), pred[k].getPredictionInts()[i]);
				}
			}
			if((Calendar.DAY_OF_MONTH+i)>30){

				for (int k = 0; k < driver.getStockDB().getStockList().size(); k++) {
					
				
				timeSeries[k].add(new Day(((Calendar.DAY_OF_MONTH+i)-30),month+1, 2014), pred[k].getPredictionInts()[i]);
					}
			}  
		

		}
		for (int k = 0; k < driver.getStockDB().getStockList().size(); k++) {
		dataset.addSeries(timeSeries[k]);
		
		}
		

		/*Creating the chart*/
		JFreeChart chart = ChartFactory.createTimeSeriesChart("All Products","Date","Quantity Sold",dataset,true,true,false);

		/*Altering the graph */
		XYPlot plot = (XYPlot) chart.getPlot(); 



		plot.setAxisOffset(new RectangleInsets(5.0, 10.0, 10.0, 5.0));  
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);






		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer)plot.getRenderer();
		renderer.setBaseShapesVisible(true);
		renderer.setBaseShapesFilled(true);
		NumberAxis numberAxis = (NumberAxis)plot.getRangeAxis();        
		numberAxis.setRange(new Range(0,50));   
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy"));
		axis.setAutoTickUnitSelection(false);
		axis.setVerticalTickLabels(true);
		/* Displaying the chart*/           
		ChartFrame frame = new ChartFrame("Daily Charts", chart);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String args[]){
		AllProductsChart allProductsChart = new AllProductsChart();
		//predictionchart.buildPredictionChart(Product product, ArrayList<Order> orderList);

	}

}


