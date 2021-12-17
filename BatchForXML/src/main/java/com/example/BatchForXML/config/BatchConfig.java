package com.example.BatchForXML.config;


import com.example.BatchForXML.exceptions.ReadException;
import com.example.BatchForXML.generated.StudentsData.StudentData;
import com.example.BatchForXML.listener.*;
import com.example.BatchForXML.model.Student;
import com.example.BatchForXML.step.CStudentReader;
import com.example.BatchForXML.step.StudentProcessor;
import com.example.BatchForXML.step.StudentWriter;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.StepListenerFailedException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class BatchConfig {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Bean
    @StepScope
    public CStudentReader itemReader(@Value("#{jobParameters['inputPath']}") String inputPath) throws Exception, ReadException {
        return new CStudentReader(inputPath);
    }

    @Bean
    public ItemProcessor<StudentData, Student> itemProcessor() {
        return new StudentProcessor();
    }

    @Bean
    public ItemWriter<Student> itemWriter() {
       return new StudentWriter();
    }

    @Bean
    public Step step1(ItemReader<StudentData> reader,
                      ItemProcessor<StudentData, Student> processor,
                      ItemWriter<Student> writer,
                      CReaderListener readerListener,
                      CProcessorListener processorListener,
                      CWriterListener writerListener)
            throws Exception
    {
        return stepBuilderFactory.get("step1")
                .<StudentData, Student> chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(readerListener)
                .listener(processorListener)
                .listener(writerListener)
                .build();
    }

    @Bean
    public Job job1(Step step1, CJobListener listener) throws Exception {
        return jobBuilderFactory.get("job1")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step1)
                .build();
    }
}
