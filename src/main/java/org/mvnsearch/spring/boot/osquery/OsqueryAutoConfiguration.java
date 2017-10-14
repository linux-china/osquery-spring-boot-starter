package org.mvnsearch.spring.boot.osquery;


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
    public OsqueryEndpoint osqueryEndpoint() {
        return new OsqueryEndpoint();
    }

    @Bean
    public OsqueryOperationEndpoint osqueryOperationEndpoint() {
        return new OsqueryOperationEndpoint(osqueryEndpoint());
    }
}