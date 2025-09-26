package builders;

import java.util.Arrays;
import java.util.List;

import models.DownloadExcelRequest;

public class DownloadExcelRequestBuilder {

    public static List<DownloadExcelRequest> buildSampleList() {
    	DownloadExcelRequest rq1 = new DownloadExcelRequest()
                .setName("Driss Moussaoui - 10055")
                .setAssessment("RECURRENT QUESTIONNAIRE")
                .setStatus("Pending")
                .setUserScore(16)
                .setCompletionDate("Aug 29, 2024");

    	DownloadExcelRequest rq2 = new DownloadExcelRequest()
                .setName("Mohammed ALSHEHHI - 26220")
                .setAssessment("RECURRENT QUESTIONNAIRE")
                .setStatus("Pending")
                .setUserScore(40)
                .setCompletionDate("Jun 5, 2025");

    	DownloadExcelRequest rq3 = new DownloadExcelRequest()
                .setName("RODRIGUES BOBBY - 11179")
                .setAssessment("RECURRENT QUESTIONNAIRE")
                .setStatus("Pending")
                .setUserScore(null)
                .setCompletionDate("-");

    	DownloadExcelRequest rq4 = new DownloadExcelRequest()
                .setName("Shaima RASHED - 21851")
                .setAssessment("RECURRENT QUESTIONNAIRE")
                .setStatus("Pending")
                .setUserScore(null)
                .setCompletionDate("Jul 18, 2023");

    	DownloadExcelRequest rq5 = new DownloadExcelRequest()
                .setName("Suraj WEERASEKERA - 13009")
                .setAssessment("RECURRENT QUESTIONNAIRE")
                .setStatus("Pending")
                .setUserScore(52)
                .setCompletionDate("Aug 13, 2024");

        return Arrays.asList(rq1, rq2, rq3, rq4, rq5);
    }

}
