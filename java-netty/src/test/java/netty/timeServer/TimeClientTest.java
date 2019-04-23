package netty.timeServer;


import org.testng.annotations.Test;

/**
 * Created by https://github.com/kuangcp
 *
 * @author kuangcp
 */
public class TimeClientTest {

  private TimeClient timeClient = new TimeClient();

  @Test(threadPoolSize = 5, invocationCount = 20)
//  @Test
  public void testClient() throws Exception {
    timeClient.connectLocal(TimeServer.port);
  }
}