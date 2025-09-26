package builders;

import java.util.Arrays;

import models.SaveExamResultsRequest;

public class SaveExamResultsRequestBuilder {

    public static SaveExamResultsRequest buildSampleRequest() {
        SaveExamResultsRequest.QuestionText qt1 = new SaveExamResultsRequest.QuestionText()
                .setPubQuestionTextId(577329)
                .setQuestionText("<p>What is the picture Represent? <img src=\"data:image/jpeg;base64,...\" style=\"width:217px;\"></p>")
                .setSequence(1);

        SaveExamResultsRequest.Answer answer1 = new SaveExamResultsRequest.Answer()
                .setPubAnswerId(1198487)
                .setText("Sky")
                .setScore(1)
                .setCorrect(true);

        SaveExamResultsRequest.SelectedAnswer selectedAnswer1 = new SaveExamResultsRequest.SelectedAnswer()
                .setPubAnswerId(1198487)
                .setText("Sky")
                .setScore(1)
                .setCorrect(true)
                .setUserText("Sky");

        SaveExamResultsRequest.Question question1 = new SaveExamResultsRequest.Question()
                .setTotalOverrideScore(0)
                .setAttemptsRemaining(0)
                .setPubQuestionId(335457)
                .setUserScore(1)
                .setQtmodel(new SaveExamResultsRequest.QtModel().setId(3).setQuestionType("Fill In The Blanks"))
                .setQuestionTextList(Arrays.asList(qt1))
                .setAnswerList(Arrays.asList(answer1))
                .setSelectedAnswers(Arrays.asList(selectedAnswer1));

        SaveExamResultsRequest.QuestionText qt2 = new SaveExamResultsRequest.QuestionText()
                .setPubQuestionTextId(577330)
                .setQuestionText("<p>Who won the ICC Mens World Cup 2023?</p>")
                .setSequence(2);

        SaveExamResultsRequest.Answer answer2a = new SaveExamResultsRequest.Answer()
                .setPubAnswerId(1198488)
                .setText("<p>Australia</p>")
                .setScore(1)
                .setCorrect(true);

        SaveExamResultsRequest.Answer answer2b = new SaveExamResultsRequest.Answer()
                .setPubAnswerId(1198489)
                .setText("<p>India</p>")
                .setScore(1)
                .setCorrect(false);

        SaveExamResultsRequest.SelectedAnswer selectedAnswer2 = new SaveExamResultsRequest.SelectedAnswer()
                .setPubAnswerId(1198488)
                .setText("<p>Australia</p>")
                .setScore(1)
                .setCorrect(true);

        SaveExamResultsRequest.Question question2 = new SaveExamResultsRequest.Question()
                .setTotalOverrideScore(0)
                .setAttemptsRemaining(0)
                .setPubQuestionId(335458)
                .setUserScore(1)
                .setQtmodel(new SaveExamResultsRequest.QtModel().setId(1).setQuestionType("Multiple Choice"))
                .setQuestionTextList(Arrays.asList(qt2))
                .setAnswerList(Arrays.asList(answer2a, answer2b))
                .setSelectedAnswers(Arrays.asList(selectedAnswer2));

        return new SaveExamResultsRequest()
                .setPubAssessmentId(258016)
                .setUserScore(2)
                .setTimeSpent(70)
                .setIpAddress("")
                .setTotalScore(2)
                .setUserId(0)
                .setIsLate(0)
                .setForGrade(0)
                .setStatus(1)
                .setIsAutoSubmitted(0)
                .setHasAutoSubmissionRun(0)
                .setTotalOverrideScore(0)
                .setAttemptsRemaining(0)
                .setQuestionsList(Arrays.asList(question1, question2));
    }

}
