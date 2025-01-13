package tasker.tasker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import tasker.tasker.configuration.HttpClientConfig;

@SpringBootApplication
@EnableConfigurationProperties(HttpClientConfig.class)
public class TaskerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskerApplication.class, args);
    }
}