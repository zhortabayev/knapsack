
public class Item {

	private int weight;
	private int value;
	private double ratio;

	public Item(int value, int weight) {
		this.value = value;
		this.weight = weight;
		this.ratio = (double)value/(double)weight;
	}
	
	public double getRatio() {
		return ratio;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public int getValue() {
		return value;
	}
}
