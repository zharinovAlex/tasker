package tasker.tasker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tasker.tasker.dto.task.TaskReportDto;
import tasker.tasker.entity.Task;
import tasker.tasker.model.TaskStatus;

@Service
@RequiredArgsConstructor
public class ReportingService {

    private final RestTemplate restTemplate;

    void taskStatusChangedReport(TaskStatus previousStatus, Task task) {
        this.restTemplate.postForEntity(
                ((RootUriTemplateHandler) restTemplate.getUriTemplateHandler()).getRootUri(),
                new TaskReportDto(previousStatus, task),
                String.class
        );
    }
}