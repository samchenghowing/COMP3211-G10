package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TaskTest {
	Task testTask = new Task("Test task 1", "2023-11-07 09:30");
	@Test
	void compareTimetest() {
		assertTrue(testTask.checkTimeCondition("=2023-11-07 09:30"));
		assertTrue(testTask.checkTimeCondition(">=2023-11-07 09:30"));
		assertTrue(testTask.checkTimeCondition("<=2023-11-07 09:30"));		
	}

}
