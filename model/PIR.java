package model;

import java.io.Serializable;

/**
 * 
 */
public interface PIR extends Serializable {
	public static final long serialVersionUID = 1L;
	
	public void create();
	public void modify();
	public PIR searchText(String s);
}
