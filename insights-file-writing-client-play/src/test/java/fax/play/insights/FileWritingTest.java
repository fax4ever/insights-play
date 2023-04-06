package fax.play.insights;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.redhat.insights.core.TopLevelReportBaseImpl;
import com.redhat.insights.http.InsightsFileWritingClient;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileWritingTest {

   private Path tmpdir;
   private MyInsightsFactory factory;

   @BeforeAll
   public void beforeAll() throws Exception {
      tmpdir = Files.createTempDirectory("tmpDirPrefix");
      factory = new MyInsightsFactory(tmpdir.toString());
   }

   @AfterAll
   public void afterAll() throws Exception {
      if (tmpdir != null) {
         for (File file : tmpdir.toFile().listFiles()) {
            Files.delete(file.toPath());
         }
         Files.delete(tmpdir);
      }
   }

   @Test
   public void test() throws Exception {
      TopLevelReportBaseImpl report = factory.topLevelReport(Collections.emptyMap());
      InsightsFileWritingClient client = factory.writingClient();
      client.sendInsightsReport("names", report);

      File[] files = tmpdir.toFile().listFiles();
      assertEquals(1, files.length);
      assertEquals("names.json", files[0].getName());

      String content = new String(Files.readAllBytes(files[0].toPath()));
      assertThat(content).isEqualTo(
            "{\n" +
            "  \"version\" : \"1.0.0\",\n" +
            "  \"basic\" : {\n" +
            "    \"app.transport.type.file\" : \"rhel\"\n" +
            "  }\n" +
            "}");
   }
}
