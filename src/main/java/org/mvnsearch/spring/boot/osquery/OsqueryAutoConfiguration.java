package org.mvnsearch.spring.boot.osquery;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * osquery auto configuration
 *
 * @author linux_china
 */
@Configuration
public class OsqueryAutoConfiguration {

  @Bean
  public OsqueryEndpoint osqueryEndpoint(@Value("${osqueryi.path:osqueryi}") String osqueryiPath) {
    return new OsqueryEndpoint(osqueryiPath);
  }
}
