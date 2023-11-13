package model;

public class Event implements PIR {
	private Task eventTask;
	private String alarm;
	private static int eventsCount=0; 
	
	public Event(String description, String startingTime, String alarmStr) {
		createEvent(description, startingTime, alarmStr);
		eventsCount++;
	}

	private void createEvent(String description, String startingTime, String alarmStr) {
		eventTask = new Task(description, startingTime);
		setAlarm(alarmStr);
	}

	public String getAlarm() {
		return alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}

	public static int getEventsCount() {
		return eventsCount;
	}

	@Override
	public String toString() {
		return "Event [eventTask=" + eventTask + ", alarm=" + alarm + "]";
	}

	@Override
	public boolean checkTimeCondition(String timeCondition) {
		return eventTask.checkTimeCondition(timeCondition);
	}
	
}
