package models;

public class JoinSelfStudyCourseRequest {
	private int courseId;
	private String role;
	private String staffName;
	private String staffNumber;
	private String status;
	private String email;
	private Integer percentage;
	private Boolean bookMark;

	public int getCourseId() {
		return courseId;
	}

	public JoinSelfStudyCourseRequest setCourseId(int courseId) {
		this.courseId = courseId;
		return this;
	}

	public String getRole() {
		return role;
	}

	public JoinSelfStudyCourseRequest setRole(String role) {
		this.role = role;
		return this;
	}

	public String getStaffName() {
		return staffName;
	}

	public JoinSelfStudyCourseRequest setStaffName(String staffName) {
		this.staffName = staffName;
		return this;
	}

	public String getStaffNumber() {
		return staffNumber;
	}

	public JoinSelfStudyCourseRequest setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public JoinSelfStudyCourseRequest setStatus(String status) {
		this.status = status;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public JoinSelfStudyCourseRequest setEmail(String email) {
		this.email = email;
		return this;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public JoinSelfStudyCourseRequest setPercentage(Integer percentage) {
		this.percentage = percentage;
		return this;
	}

	public Boolean getBookMark() {
		return bookMark;
	}

	public JoinSelfStudyCourseRequest setBookMark(Boolean bookMark) {
		this.bookMark = bookMark;
		return this;
	}
}
