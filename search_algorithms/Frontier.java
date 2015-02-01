package search_algorithms;

public abstract class Frontier {
	public abstract Node pop();

	public abstract boolean is_empty();

	public abstract boolean push(Node node);
	
	public abstract int get_size();
}
