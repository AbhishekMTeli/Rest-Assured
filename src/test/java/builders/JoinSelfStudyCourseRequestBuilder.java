package builders;

import models.JoinSelfStudyCourseRequest;

public class JoinSelfStudyCourseRequestBuilder {

    public static JoinSelfStudyCourseRequest buildValidRequest() {
        return new JoinSelfStudyCourseRequest()
                .setCourseId(100000)
                .setRole("tester")
                .setStaffName("Athreya, ASanjana")
                .setStaffNumber("1234567")
                .setStatus("Active")
                .setEmail(null)
                .setPercentage(null)
                .setBookMark(true);
    }

    public static JoinSelfStudyCourseRequest buildInvalidCourseId() {
        return new JoinSelfStudyCourseRequest()
                .setCourseId(0) // invalid
                .setRole("tester")
                .setStaffName("Athreya, ASanjana")
                .setStaffNumber("1234567")
                .setStatus("Active")
                .setBookMark(true);
    }

    public static JoinSelfStudyCourseRequest buildInvalidStaffNumber() {
        return new JoinSelfStudyCourseRequest()
                .setCourseId(100000)
                .setRole("tester")
                .setStaffName("Athreya, ASanjana")
                .setStaffNumber("invalid@#") // invalid format
                .setStatus("Active")
                .setBookMark(true);
    }

    public static JoinSelfStudyCourseRequest buildWithNullBookmark() {
        return new JoinSelfStudyCourseRequest()
                .setCourseId(100000)
                .setRole("tester")
                .setStaffName("Athreya, ASanjana")
                .setStaffNumber("1234567")
                .setStatus("Active")
                .setBookMark(null); // null value
    }
}
