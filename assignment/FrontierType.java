package assignment;

public enum FrontierType {
	QUEUE(1, "BFS"), STACK(2, "DFS"), PRIORITY_QUEUE(3, "GBFS");

	private int val;
	private String name;

	FrontierType(int val, String name) {
		this.val = val;
		this.name = name;
	}

	public int get_value() {
		return val;
	}
	
	public String to_string() {
		return name;
	}
}
