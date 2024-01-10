package io.springbatch.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class DBJobConfiguration {

    @Bean
    public Job newJob(JobRepository jobRepository, Step step1, Step step2){
        return new JobBuilder("newJob", jobRepository)
                .start(step1)
                .next(step2)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, Tasklet tasklet1, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .tasklet(tasklet1, transactionManager)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step step2(JobRepository jobRepository, Tasklet tasklet2, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step2", jobRepository)
                .tasklet(tasklet2, transactionManager)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Tasklet tasklet1(){
        return ((contribution, chunkContext) -> {
            System.out.println("step1 was executed!");
            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    public Tasklet tasklet2(){
        return ((contribution, chunkContext) -> {
            System.out.println("step2 was executed!");
            return RepeatStatus.FINISHED;
        });
    }
}
