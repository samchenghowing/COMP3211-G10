package model;

public class Note implements PIR {
	private String noteName;
	private static int notesCount=0;

	public Note(String string) {
		createNote(string);
		notesCount++;
	}

	public static int getNotesCount() {
		return notesCount;
	}

	public String getNoteString() {
		return noteName;
	}

	public void createNote(String noteName) {
		this.noteName = noteName;
	}

	@Override
	public String toString() {
		return "Note [noteName=" + noteName + "]";
	}
	
}
