package model;

@SuppressWarnings("serial")
public class ContactNote extends Note {
	private String address;
	private String mobileNumbers;
	private static int contactNotesCount=0;

	public ContactNote(String string, String addr, String mobile) {
		super(string);
		contactNotesCount++;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNumbers() {
		return mobileNumbers;
	}

	public void setMobileNumbers(String mobileNumbers) {
		this.mobileNumbers = mobileNumbers;
	}

	public static int getContactNotesCount() {
		return contactNotesCount;
	}

	@Override
	public String toString() {
		return "ContactNote [address=" + address + ", mobileNumbers=" + mobileNumbers + "]";
	}

}
