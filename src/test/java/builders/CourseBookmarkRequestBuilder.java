package builders;

import models.CourseBookmarkRequest;

public class CourseBookmarkRequestBuilder {

    public static CourseBookmarkRequest buildValidBookmark() {
        return new CourseBookmarkRequest()
                .setStaffNumber("13355")
                .setCourseId(12868)
                .setBookMark(true);
    }

    public static CourseBookmarkRequest buildBookmarkWithNull() {
        return new CourseBookmarkRequest()
                .setStaffNumber("13355")
                .setCourseId(12868)
                .setBookMark(null);
    }

    public static CourseBookmarkRequest buildInvalidStaffNumber() {
        return new CourseBookmarkRequest()
                .setStaffNumber("133@,55")
                .setCourseId(12868)
                .setBookMark(true);
    }

    public static CourseBookmarkRequest buildInvalidCourseId() {
        return new CourseBookmarkRequest()
                .setStaffNumber("13355")
                .setCourseId(231)
                .setBookMark(true);
    }
}
