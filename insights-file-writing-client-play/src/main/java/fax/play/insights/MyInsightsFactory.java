package fax.play.insights;

import java.util.Map;

import com.redhat.insights.InsightsSubreport;
import com.redhat.insights.core.TopLevelReportBaseImpl;
import com.redhat.insights.http.InsightsFileWritingClient;
import com.redhat.insights.logging.InsightsLogger;
import com.redhat.insights.logging.JulLogger;

public class MyInsightsFactory {

   private static final String APPLICATION_NAME = "my-my-my";

   private final MyInsightsConfiguration config;
   private final InsightsLogger logger;

   public MyInsightsFactory(String archiveUploadDir) {
      config = new MyInsightsConfiguration(APPLICATION_NAME, archiveUploadDir);
      logger = new JulLogger(APPLICATION_NAME);
   }

   public TopLevelReportBaseImpl topLevelReport(Map<String, InsightsSubreport> subReports) {
      return new TopLevelReportBaseImpl(logger, config, subReports);
   }

   public InsightsFileWritingClient writingClient() {
      return new InsightsFileWritingClient(logger, config);
   }

}
