package model;

import java.io.Serializable;

/**
 * 
 */
public interface PIR extends Serializable {
	public static final long serialVersionUID = 1L;

	public boolean checkTimeCondition(String timeCondition);
}
