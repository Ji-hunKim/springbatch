package io.springbatch.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class HelloJobConfiguration {

    @Bean
    public Job helloJob(JobRepository jobRepository, Step step){
        return new JobBuilder("helloJob", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Tasklet helloTasklet(){
        return ((contribution, chunkContext) -> {
            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    public Step helloStep(JobRepository jobRepository, Tasklet helloTasklet, PlatformTransactionManager transactionManager) {
        return new StepBuilder("helloStep", jobRepository)
                .tasklet(helloTasklet, transactionManager)
                .build();
    }

}
