package com.example.BatchForXML.listener;

import com.example.BatchForXML.exceptions.ProcessException;
import com.example.BatchForXML.exceptions.ReadException;
import com.example.BatchForXML.generated.StudentsData.StudentData;
import com.example.BatchForXML.model.Student;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CProcessorListener implements ItemProcessListener<StudentData, Student> {

    @Override
    public void beforeProcess(StudentData studentData) {
        log.info("before process");
    }

    @Override
    public void afterProcess(StudentData studentData, Student student) {
        log.info("after process");
    }

    @Override
    public void onProcessError(StudentData studentData, Exception e) {
        log.error("On process Error: " + e);
        throw new ProcessException("Error when processing StudentData...");
    }
}
