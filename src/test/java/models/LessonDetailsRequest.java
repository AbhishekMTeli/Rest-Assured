package models;

public class LessonDetailsRequest {

	private int lessonId;
	private int courseId;
	private int moduleId;
	private int percentage;
	private int score;
	private String scormDetails;
	private String status;
	private String timeSpent;
	private String totalStatus;
	private String userId;

	public int getLessonId() {
		return lessonId;
	}

	public int getCourseId() {
		return courseId;
	}

	public int getModuleId() {
		return moduleId;
	}

	public int getPercentage() {
		return percentage;
	}

	public int getScore() {
		return score;
	}

	public String getScormDetails() {
		return scormDetails;
	}

	public String getStatus() {
		return status;
	}

	public String getTimeSpent() {
		return timeSpent;
	}

	public String getTotalStatus() {
		return totalStatus;
	}

	public String getUserId() {
		return userId;
	}

	// Setters with chaining
	public LessonDetailsRequest setLessonId(int lessonId) {
		this.lessonId = lessonId;
		return this;
	}

	public LessonDetailsRequest setCourseId(int courseId) {
		this.courseId = courseId;
		return this;
	}

	public LessonDetailsRequest setModuleId(int moduleId) {
		this.moduleId = moduleId;
		return this;
	}

	public LessonDetailsRequest setPercentage(int percentage) {
		this.percentage = percentage;
		return this;
	}

	public LessonDetailsRequest setScore(int score) {
		this.score = score;
		return this;
	}

	public LessonDetailsRequest setScormDetails(String scormDetails) {
		this.scormDetails = scormDetails;
		return this;
	}

	public LessonDetailsRequest setStatus(String status) {
		this.status = status;
		return this;
	}

	public LessonDetailsRequest setTimeSpent(String timeSpent) {
		this.timeSpent = timeSpent;
		return this;
	}

	public LessonDetailsRequest setTotalStatus(String totalStatus) {
		this.totalStatus = totalStatus;
		return this;
	}

	public LessonDetailsRequest setUserId(String userId) {
		this.userId = userId;
		return this;
	}
}
