package model;

public class Task extends PIR implements ManagePIR {
	private static int count=0;
	private String descriptionString;
	private int deadline;
	
	public Task() {
		count++;
	}
	public Task(String descriptionString, int deadline) {
		this.descriptionString = descriptionString;
		this.deadline = deadline;
		count++;
	}

	@Override
	public void create() {
	}
	@Override
	public void modify() {
	}
	public static int getCount() {
		return count;
	}
	public String getDescriptionString() {
		return descriptionString;
	}
	public void setDescriptionString(String descriptionString) {
		this.descriptionString = descriptionString;
	}
	public int getDeadline() {
		return deadline;
	}
	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}

}
