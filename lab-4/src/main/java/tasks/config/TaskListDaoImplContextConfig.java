package tasks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tasks.dao.TaskListDao;
import tasks.dao.impl.TaskListDaoImpl;

@Configuration
public class TaskListDaoImplContextConfig {
    @Bean
    public TaskListDao taskListDao() {
        return new TaskListDaoImpl();
    }
}
