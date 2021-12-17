package com.example.BatchForXML.step;

import com.example.BatchForXML.exceptions.ProcessException;
import com.example.BatchForXML.generated.StudentsData.StudentData;
import com.example.BatchForXML.model.Student;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;


@Log4j2
public class StudentProcessor implements ItemProcessor<StudentData, Student> {

    @SneakyThrows
    @Override
    public Student process(StudentData studentData) throws Exception {
        try {
            Student student = new Student();
            student.setName(studentData.getName());
            student.setEmailAddress(studentData.getEmailAddress());
            student.setPurchasedPackage(studentData.getPurchasedPackage());
            log.info(student);
            return student;
        } catch (Exception e) {
            throw new ProcessException("Error in processing");
        }
    }
}
