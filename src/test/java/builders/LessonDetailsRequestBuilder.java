package builders;

import models.LessonDetailsRequest;

public class LessonDetailsRequestBuilder {

    public static LessonDetailsRequest buildSampleLessonDetails() {
        // Create and populate LessonDetailsRequest object
        return new LessonDetailsRequest()
                .setLessonId(78018)
                .setCourseId(71484)
                .setModuleId(8820)
                .setPercentage(36)
                .setScore(10)
                .setScormDetails("SCORM data here")
                .setStatus("incomplete")
                .setTimeSpent("0:0")
                .setTotalStatus("Passed")
                .setUserId("23077");
    }
}
