package endpoints;

import static io.restassured.RestAssured.given;

import base.AdminBaseTest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.AdminAnalysisEndPointsReader;

public class AdminAnalysisEndPoints extends AdminBaseTest {
	public static Response courseUtilizationSummary(boolean includeAuth, String tokenOverride, boolean includeCourse_id,
			String course_id) {
		String courseUtilizationSummary = AdminAnalysisEndPointsReader.get("courseUtilizationSummary");
		RequestSpecification req = given();
		if (includeAuth) {
			if (tokenOverride != null) {
				req.spec(requestSpecNoAuth) // avoid double auth header
						.header("Authorization", "Bearer " + tokenOverride);
			} else {
				req.spec(requestSpecAuth);
			}
		} else {
			// =11681
			req.spec(requestSpecNoAuth);
		}
		if (includeCourse_id) {
			if (course_id != null) {
				req.queryParam("course_id", course_id);
			} else {
				req.queryParam("course_id", 11681);
			}
		}
		return req.when().get(courseUtilizationSummary);
	}

	public static Response incomleteAssessments(boolean includeAuth, String tokenOverride) {
		String incomleteAssessments = AdminAnalysisEndPointsReader.get("incomleteAssessments");
		RequestSpecification req = given();
		if (includeAuth) {
			if (tokenOverride != null) {
				req.spec(requestSpecNoAuth) // avoid double auth header
						.header("Authorization", "Bearer " + tokenOverride);
			} else {
				req.spec(requestSpecAuth);
			}
		} else {
			req.spec(requestSpecNoAuth);
		}
		return req.when().get(incomleteAssessments);
	}

	public static Response failureReport(boolean includeAuth, String tokenOverride) {
		String failureReport = AdminAnalysisEndPointsReader.get("failureReport");
		RequestSpecification req = given();
		if (includeAuth) {
			if (tokenOverride != null) {
				req.spec(requestSpecNoAuth) // avoid double auth header
						.header("Authorization", "Bearer " + tokenOverride);
			} else {
				req.spec(requestSpecAuth);
			}
		} else {
			req.spec(requestSpecNoAuth);
		}
		return req.when().get(failureReport);
	}

	public static Response userCourseExpiry(boolean includeAuth, String tokenOverride) {
		String userCourseExpiry = AdminAnalysisEndPointsReader.get("userCourseExpiry");
		RequestSpecification req = given();
		if (includeAuth) {
			if (tokenOverride != null) {
				req.spec(requestSpecNoAuth) // avoid double auth header
						.header("Authorization", "Bearer " + tokenOverride);
			} else {
				req.spec(requestSpecAuth);
			}
		} else {
			req.spec(requestSpecNoAuth);
		}
		return req.when().get(userCourseExpiry);
	}

	public static Response assessmentAnalysis(boolean includeAuth, String tokenOverride, boolean includeAssessment_id,
			String assessment_id) {
		String assessmentAnalysis = AdminAnalysisEndPointsReader.get("assessmentAnalysis");
		RequestSpecification req = given();
		if (includeAuth) {
			if (tokenOverride != null) {
				req.spec(requestSpecNoAuth) // avoid double auth header
						.header("Authorization", "Bearer " + tokenOverride);
			} else {
				req.spec(requestSpecAuth);
			}
		} else {
			// =11681
			req.spec(requestSpecNoAuth);
		}
		if (includeAssessment_id) {
			if (assessment_id != null) {
				req.queryParam("assessment_id", assessment_id);
			} else {
				req.queryParam("assessment_id", 2104420);
			}
		}
		return req.when().get(assessmentAnalysis);
	}

	public static Response questionAccuracy(boolean includeAuth, String tokenOverride, boolean includeAssessment_id,
			String assessment_id) {
		String questionAccuracy = AdminAnalysisEndPointsReader.get("questionAccuracy");
		RequestSpecification req = given();
		if (includeAuth) {
			if (tokenOverride != null) {
				req.spec(requestSpecNoAuth) // avoid double auth header
						.header("Authorization", "Bearer " + tokenOverride);
			} else {
				req.spec(requestSpecAuth);
			}
		} else {
			// =11681
			req.spec(requestSpecNoAuth);
		}
		if (includeAssessment_id) {
			if (assessment_id != null) {
				req.queryParam("assessment_id", assessment_id);
			} else {
				req.queryParam("assessment_id", 2104422);
			}
		}
		return req.when().get(questionAccuracy);
	}

	public static Response moduleAssessmentCourseList(boolean includeAuth, String tokenOverride) {
		String moduleAssessmentCourseList = AdminAnalysisEndPointsReader.get("moduleAssessmentCourseList");
		RequestSpecification req = given();
		if (includeAuth) {
			if (tokenOverride != null) {
				req.spec(requestSpecNoAuth) // avoid double auth header
						.header("Authorization", "Bearer " + tokenOverride);
			} else {
				req.spec(requestSpecAuth);
			}
		} else {
			req.spec(requestSpecNoAuth);
		}
		return req.when().get(moduleAssessmentCourseList);
	}
}
