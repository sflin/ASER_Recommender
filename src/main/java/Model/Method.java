package Model;

public class Method {
	

	private String name;
	
	private boolean isConstructor;
	
	private int count;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isConstructor() {
		return isConstructor;
	}

	public void setConstructor(boolean isConstructor) {
		this.isConstructor = isConstructor;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
