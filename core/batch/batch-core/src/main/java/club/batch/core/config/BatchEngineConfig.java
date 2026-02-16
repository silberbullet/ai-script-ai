package club.batch.core.config;

import club.batch.core.runner.BatchJobRunner;
import club.batch.core.factory.JobParametersFactory;
import club.batch.core.runner.SpringBatchJobRunner;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchEngineConfig {
    
    @Bean
    public JobParametersFactory jobParametersFactory() {
        return new JobParametersFactory();
    }
    
    @Bean
    public BatchJobRunner batchJobRunner(
            JobRegistry jobRegistry,
            JobLauncher jobLauncher,
            JobParametersFactory jobParametersFactory
    ) {
        return new SpringBatchJobRunner(jobRegistry, jobLauncher, jobParametersFactory);
    }
}
