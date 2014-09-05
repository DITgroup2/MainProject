package com.dit.group2.order;

import java.util.ArrayList;
import java.util.Date;

import com.dit.group2.stock.Product;
import com.dit.group2.stock.StockItem;

public class Prediction01 {

	private double[] y = new double[40];
	private double[] dy = new double[40];
	private Product product;
	private ArrayList<Order> orderList;
	private Date today = new Date();

	public Prediction01() {
		System.out.println(today);
	}

	public Prediction01(Product product, ArrayList<Order> orderList) {
		this.product = product;
		this.orderList = orderList;

		// give default value 0s
		for (int i = 0; i < y.length; i++) {
			y[i] = 0;
			dy[i] = 0;
		}

		// fill up y[];

		for (Order order : orderList) {
			for (int i = 29; i >= 0; i--) {
				if ((order.getDate().getTime() > (today.getTime() - (i + 1) * 24 * 60 * 60 * 1000))
						&& (order.getDate().getTime() < (today.getTime() - i * 24 * 60 * 60 * 1000))) {
					for (StockItem stockItem : order.getOrderEntryList()) {
						if (stockItem.getProduct().equals(product)) {
							y[29 - i] += stockItem.getQuantity();
						}
					}
				}
			}
		}

		predict();

	}

	private void predict() {

		// calculate dy
		for (int i = 1; i < y.length; i++) {
			dy[i] = y[i] - y[i - 1];
			// System.out.println("dy" + i + " = " + dy[i]);
		}

		double a;
		double b;

		// prediction calculation
		for (int i = 30; i < 40; i++) {
			a = average(y, i - 30, i - 1);
			b = average(dy, i - 30, i - 1);
			y[i] = (a + b);
			dy[i] = b;
			// System.out.println("y" + i + " = " + y[i]);
			// System.out.println("dy" + i + " = " + dy[i]);
		}
	}

	private double average(double[] array, int n1, int n2) {
		double avr = 0;
		for (int i = n1; i < n2 + 1; i++) {
			avr += array[i];
		}
		avr /= (n2 - n1 + 1);
		return avr;
	}

	public int[] getPredictionInts() {
		int[] yInt = new int[40];
		for (int i = 0; i < 40; i++) {
			yInt[i] = (int) y[i];
		}
		return yInt;
	}

	public double[] getPredictionDoubles() {
		return y;
	}

}
