package assignment;

public enum FrontierType {
	QUEUE(1), STACK(2), PRIORITY_QUEUE(3);

	private int val;

	FrontierType(int val) {
		this.val = val;
	}

	public int get_value() {
		return val;
	}
}
