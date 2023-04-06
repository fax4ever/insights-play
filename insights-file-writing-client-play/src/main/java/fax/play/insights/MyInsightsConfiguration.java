package fax.play.insights;

import com.redhat.insights.config.InsightsConfiguration;

public class MyInsightsConfiguration implements InsightsConfiguration {

   private final String applicationName;
   private final String archiveUploadDir;

   public MyInsightsConfiguration(String applicationName, String archiveUploadDir) {
      this.applicationName = applicationName;
      this.archiveUploadDir = archiveUploadDir;
   }

   public String getIdentificationName() {
      return applicationName;
   }

   @Override
   public String getArchiveUploadDir() {
      return archiveUploadDir;
   }
}
