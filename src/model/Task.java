package model;

public class Task extends PIR implements ManagePIR {
	private static int count=0;
	
	public Task() {
		count++;
	}

	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modify() {
		// TODO Auto-generated method stub
		
	}

	public static int getCount() {
		return count;
	}

}
