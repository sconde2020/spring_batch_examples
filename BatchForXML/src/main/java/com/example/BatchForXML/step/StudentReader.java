package com.example.BatchForXML.step;

import com.example.BatchForXML.model.Student;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Log4j2
public class StudentReader {

    public static StaxEventItemReader<Student> getItemReader(String inputPath) {
         log.info("input file: " + inputPath);
        Jaxb2Marshaller studentMarshaller = new Jaxb2Marshaller();
        studentMarshaller.setClassesToBeBound(Student.class);

        return new StaxEventItemReaderBuilder<Student>()
                .name("studentReader")
                .resource(new FileSystemResource(inputPath))
                .addFragmentRootElements("student")
                .unmarshaller(studentMarshaller)
                .build();
    }
}
