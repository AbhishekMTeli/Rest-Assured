package models;

import java.util.List;

public class SaveExamResultsRequest {
	private int pubAssessmentId;
	private int userScore;
	private int timeSpent;
	private String ipAddress;
	private int totalScore;
	private int userId;
	private int isLate;
	private int forGrade;
	private int status;
	private int isAutoSubmitted;
	private int hasAutoSubmissionRun;
	private int totalOverrideScore;
	private int attemptsRemaining;
	private List<Question> questionsList;

	// Getters and setters with chaining
	public int getPubAssessmentId() {
		return pubAssessmentId;
	}

	public SaveExamResultsRequest setPubAssessmentId(int pubAssessmentId) {
		this.pubAssessmentId = pubAssessmentId;
		return this;
	}

	public int getUserScore() {
		return userScore;
	}

	public SaveExamResultsRequest setUserScore(int userScore) {
		this.userScore = userScore;
		return this;
	}

	public int getTimeSpent() {
		return timeSpent;
	}

	public SaveExamResultsRequest setTimeSpent(int timeSpent) {
		this.timeSpent = timeSpent;
		return this;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public SaveExamResultsRequest setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		return this;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public SaveExamResultsRequest setTotalScore(int totalScore) {
		this.totalScore = totalScore;
		return this;
	}

	public int getUserId() {
		return userId;
	}

	public SaveExamResultsRequest setUserId(int userId) {
		this.userId = userId;
		return this;
	}

	public int getIsLate() {
		return isLate;
	}

	public SaveExamResultsRequest setIsLate(int isLate) {
		this.isLate = isLate;
		return this;
	}

	public int getForGrade() {
		return forGrade;
	}

	public SaveExamResultsRequest setForGrade(int forGrade) {
		this.forGrade = forGrade;
		return this;
	}

	public int getStatus() {
		return status;
	}

	public SaveExamResultsRequest setStatus(int status) {
		this.status = status;
		return this;
	}

	public int getIsAutoSubmitted() {
		return isAutoSubmitted;
	}

	public SaveExamResultsRequest setIsAutoSubmitted(int isAutoSubmitted) {
		this.isAutoSubmitted = isAutoSubmitted;
		return this;
	}

	public int getHasAutoSubmissionRun() {
		return hasAutoSubmissionRun;
	}

	public SaveExamResultsRequest setHasAutoSubmissionRun(int hasAutoSubmissionRun) {
		this.hasAutoSubmissionRun = hasAutoSubmissionRun;
		return this;
	}

	public int getTotalOverrideScore() {
		return totalOverrideScore;
	}

	public SaveExamResultsRequest setTotalOverrideScore(int totalOverrideScore) {
		this.totalOverrideScore = totalOverrideScore;
		return this;
	}

	public int getAttemptsRemaining() {
		return attemptsRemaining;
	}

	public SaveExamResultsRequest setAttemptsRemaining(int attemptsRemaining) {
		this.attemptsRemaining = attemptsRemaining;
		return this;
	}

	public List<Question> getQuestionsList() {
		return questionsList;
	}

	public SaveExamResultsRequest setQuestionsList(List<Question> questionsList) {
		this.questionsList = questionsList;
		return this;
	}

	// Nested Classes

	public static class Question {
		private int totalOverrideScore;
		private int attemptsRemaining;
		private int pubQuestionId;
		private int userScore;
		private QtModel qtmodel;
		private List<QuestionText> questionTextList;
		private List<Answer> answerList;
		private List<SelectedAnswer> selectedAnswers;

		public int getTotalOverrideScore() {
			return totalOverrideScore;
		}

		public Question setTotalOverrideScore(int totalOverrideScore) {
			this.totalOverrideScore = totalOverrideScore;
			return this;
		}

		public int getAttemptsRemaining() {
			return attemptsRemaining;
		}

		public Question setAttemptsRemaining(int attemptsRemaining) {
			this.attemptsRemaining = attemptsRemaining;
			return this;
		}

		public int getPubQuestionId() {
			return pubQuestionId;
		}

		public Question setPubQuestionId(int pubQuestionId) {
			this.pubQuestionId = pubQuestionId;
			return this;
		}

		public int getUserScore() {
			return userScore;
		}

		public Question setUserScore(int userScore) {
			this.userScore = userScore;
			return this;
		}

		public QtModel getQtmodel() {
			return qtmodel;
		}

		public Question setQtmodel(QtModel qtmodel) {
			this.qtmodel = qtmodel;
			return this;
		}

		public List<QuestionText> getQuestionTextList() {
			return questionTextList;
		}

		public Question setQuestionTextList(List<QuestionText> questionTextList) {
			this.questionTextList = questionTextList;
			return this;
		}

		public List<Answer> getAnswerList() {
			return answerList;
		}

		public Question setAnswerList(List<Answer> answerList) {
			this.answerList = answerList;
			return this;
		}

		public List<SelectedAnswer> getSelectedAnswers() {
			return selectedAnswers;
		}

		public Question setSelectedAnswers(List<SelectedAnswer> selectedAnswers) {
			this.selectedAnswers = selectedAnswers;
			return this;
		}
	}

	public static class QtModel {
		private int id;
		private String questionType;

		public int getId() {
			return id;
		}

		public QtModel setId(int id) {
			this.id = id;
			return this;
		}

		public String getQuestionType() {
			return questionType;
		}

		public QtModel setQuestionType(String questionType) {
			this.questionType = questionType;
			return this;
		}
	}

	public static class QuestionText {
		private int pubQuestionTextId;
		private String questionText;
		private int sequence;

		public int getPubQuestionTextId() {
			return pubQuestionTextId;
		}

		public QuestionText setPubQuestionTextId(int pubQuestionTextId) {
			this.pubQuestionTextId = pubQuestionTextId;
			return this;
		}

		public String getQuestionText() {
			return questionText;
		}

		public QuestionText setQuestionText(String questionText) {
			this.questionText = questionText;
			return this;
		}

		public int getSequence() {
			return sequence;
		}

		public QuestionText setSequence(int sequence) {
			this.sequence = sequence;
			return this;
		}
	}

	public static class Answer {
		private int pubAnswerId;
		private String text;
		private int score;
		private boolean correct;

		public int getPubAnswerId() {
			return pubAnswerId;
		}

		public Answer setPubAnswerId(int pubAnswerId) {
			this.pubAnswerId = pubAnswerId;
			return this;
		}

		public String getText() {
			return text;
		}

		public Answer setText(String text) {
			this.text = text;
			return this;
		}

		public int getScore() {
			return score;
		}

		public Answer setScore(int score) {
			this.score = score;
			return this;
		}

		public boolean isCorrect() {
			return correct;
		}

		public Answer setCorrect(boolean correct) {
			this.correct = correct;
			return this;
		}
	}

	public static class SelectedAnswer {
		private int pubAnswerId;
		private String text;
		private int score;
		private boolean correct;
		private String userText;

		public int getPubAnswerId() {
			return pubAnswerId;
		}

		public SelectedAnswer setPubAnswerId(int pubAnswerId) {
			this.pubAnswerId = pubAnswerId;
			return this;
		}

		public String getText() {
			return text;
		}

		public SelectedAnswer setText(String text) {
			this.text = text;
			return this;
		}

		public int getScore() {
			return score;
		}

		public SelectedAnswer setScore(int score) {
			this.score = score;
			return this;
		}

		public boolean isCorrect() {
			return correct;
		}

		public SelectedAnswer setCorrect(boolean correct) {
			this.correct = correct;
			return this;
		}

		public String getUserText() {
			return userText;
		}

		public SelectedAnswer setUserText(String userText) {
			this.userText = userText;
			return this;
		}
	}
}
