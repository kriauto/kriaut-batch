package ma.kriauto.rest.domain;

public class Course {
	private String totalCourse;
	private String day;
	
	public Course() {
		super();
	}

	public Course(String totalCourse, String day) {
		super();
		this.totalCourse = totalCourse;
		this.day = day;
	}

	public String getTotalCourse() {
		return totalCourse;
	}

	public void setTotalCourse(String totalCourse) {
		this.totalCourse = totalCourse;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return "Course [totalCourse=" + totalCourse + ", day=" + day + "]";
	}
}
