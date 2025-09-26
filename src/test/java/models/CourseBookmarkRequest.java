package models;

public class CourseBookmarkRequest {
    private String staffNumber;
    private int courseId;
    private Boolean bookMark;

    public String getStaffNumber() {
        return staffNumber;
    }

    public CourseBookmarkRequest setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
        return this;
    }

    public int getCourseId() {
        return courseId;
    }

    public CourseBookmarkRequest setCourseId(int courseId) {
        this.courseId = courseId;
        return this;
    }

    public Boolean getBookMark() {
        return bookMark;
    }

    public CourseBookmarkRequest setBookMark(Boolean bookMark) {
        this.bookMark = bookMark;
        return this;
    }
}
