package model;

/**
 * Course class, contains course data
 */
public class Course {
	private String name;
	private int capacity;
	private String year;
	private String delivery;
	private String day;
	private String time;
	private double duration;

	public Course() {
	}

	public Course(String name, int capacity, String year, String delivery, String day, String time, double duration) {
		this.name = name;
		this.capacity = capacity;
		this.year = year;
		this.delivery = delivery;
		this.day = day;
		this.time = time;
		this.duration = duration;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
