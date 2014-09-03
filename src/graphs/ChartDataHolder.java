package graphs;

public class ChartDataHolder {

	private double sales;

	public ChartDataHolder() {
		// No need to call a super constructor if the class doesn't extend another!
		// super();
	}

	public ChartDataHolder(double sales) {
		// super();
		this.sales = sales;
	}

	public double getSales() {
		return sales;
	}

	public void setSales(double sales) {
		this.sales = sales;
	}

}
