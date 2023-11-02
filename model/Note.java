/**
 * 
 */
package model;

/**
 * 
 */
public class Note implements PIR {
	private String noteString;
	private static int notesCount=0;

	public Note(String string) {
		setNoteString(string);
		notesCount++;
	}

	public static int getNotesCount() {
		return notesCount;
	}

	public String getNoteString() {
		return noteString;
	}

	public void setNoteString(String noteString) {
		this.noteString = noteString;
	}

	public void create() {
		// TODO Auto-generated method stub

	}

	public void modify() {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return "Note [noteString=" + noteString + "]";
	}

	@Override
	public PIR searchText(String s) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
