package models;

public class DownloadExcelRequest {
	private String name;
	private String assessment;
	private String status;
	private Integer userScore; // nullable field
	private String completionDate;

	// Getters and setters with chaining

	public String getName() {
		return name;
	}

	public DownloadExcelRequest setName(String name) {
		this.name = name;
		return this;
	}

	public String getAssessment() {
		return assessment;
	}

	public DownloadExcelRequest setAssessment(String assessment) {
		this.assessment = assessment;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public DownloadExcelRequest setStatus(String status) {
		this.status = status;
		return this;
	}

	public Integer getUserScore() {
		return userScore;
	}

	public DownloadExcelRequest setUserScore(Integer userScore) {
		this.userScore = userScore;
		return this;
	}

	public String getCompletionDate() {
		return completionDate;
	}

	public DownloadExcelRequest setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
		return this;
	}
}
