package model;

public class Note extends PIR implements ManagePIR {

	private String note;
	
	public Note(String n) {
		this.setNote(n);
	}
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modify() {
		// TODO Auto-generated method stub
		
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	@Override
	public String toString() {
		return note;
	}

}
