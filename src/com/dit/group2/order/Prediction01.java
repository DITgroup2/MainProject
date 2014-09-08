package com.dit.group2.order;

import java.util.Date;
import java.util.ArrayList;

import com.dit.group2.stock.Product;
import com.dit.group2.stock.StockItem;



public class Prediction01 {
	
	private double [] y = new double[40];
	private double [] dy = new double[40];
	private Product product;
	private ArrayList<Order> orderList;
	private Date today = new Date();
	
	public Prediction01(){
		System.out.println(today);
	}
	
	public Prediction01(Product product, ArrayList<Order> orderList){
		this.product = product;
		this.orderList = orderList;
		
		
		// give default value 0s
		for (int i = 0; i< y.length; i++){
			y[i] = 0;
			dy[i] = 0;
		}
	
		//fill up y[];		
		
		for (Order order : orderList){
			for (int i = 29; i>=0 ; i--){
				if ((order.getDate().getTime() > (today.getTime() - (i+1)*24*60*60*1000)) && (order.getDate().getTime() < (today.getTime() - i*24*60*60*1000))) {								
					for (StockItem stockItem : order.getOrderEntryList()){
						if (stockItem.getProduct().equals(product)){
							y[29-i]+=stockItem.getQuantity();
						}
					}
				}
			}
		}			
		
		predict();
		
	}
	
	
	
	
	
	private void predict(){
	
		/*
		// calculate dy
		for (int i = 1; i < y.length; i++){
			dy[i]= y[i]-y[i-1];
			//System.out.println("dy" + i + " = " + dy[i]);
		}
			
		double a;
		double b;
			
		// prediction calculation
		for (int i=30; i<40; i++){
			a = average(y, i-30, i-1);			
			b = average(dy, i-30, i-1);			
			y[i] = (a+b);
			dy[i]= b;
			//System.out.println("y" + i + " = " + y[i]);
			//System.out.println("dy" + i + " = " + dy[i]);
		}
		*/
		
		//Holt's Linear Trend Method
		
		//Declare Smoothing Parameters
		double alpha = 0.8;
		double beta = 0.2;
		
		//Declare Level Estimates and Trend Estimates
		double [] l = new double[30];
		double [] b = new double[30];
		
		//Fill l and b with zeros
		for (int i = 0; i < 30; i++){
			l[i] = 0;
			b[i] = 0;
		}
		
		//Declare y_hat matrix
		double [] y_hat = new double[40];
		
		//Fill y_hat with zeros
		for (int i = 0; i < 40; i++){
			y_hat[i] = 0;
		}
		
		//Calculate initial l and b values
		
		l[0] = y[0];
		b[0] = y[1] - y[0];
		
		
		double lZero = l[0];
		double bZero = b[0];
		
		//Fill l, b and y_hat matrices
		
		l[0] = (alpha*y[0]) + (1 - alpha)*(lZero + bZero);
		b[0] = (beta*(l[0] - lZero)) + ((1 - beta)*bZero);
		y_hat[0] = lZero + bZero;
		
		for(int t = 1; t <= 29; t++){
		l[t] = (alpha*y[t]) + ((1 - alpha)*(l[t-1]+b[t-1]));
		b[t] = (beta*(l[t] - l[t-1])) + ((1 - beta)*b[t-1]);
		y_hat[t] = l[t-1] + b[t-1];
		}
		
		for(int h = 1; h <= 10; h++){
		y_hat[h + 29] = l[29] + (h*b[29]);
		y[h + 29] = y_hat[h + 29];
		}
	}
	
	
	private double average(double[] array ,int n1, int n2){
		double avr=0;
		for (int i = n1; i< n2+1; i++){
			avr += array[i];
		}
		avr /= (n2-n1+1);
		return avr;
	}
	
	public int[] getPredictionInts(){
		int [] yInt = new int[40];
		for (int i=0; i<40; i++){
			yInt[i] = (int)y[i];
		}
		return yInt; 
	}
	
	public double[] getPredictionDoubles(){
		return y; 
	}
	
}
