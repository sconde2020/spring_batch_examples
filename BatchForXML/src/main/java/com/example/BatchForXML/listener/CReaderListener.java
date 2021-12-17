package com.example.BatchForXML.listener;

import com.example.BatchForXML.exceptions.ReadException;
import com.example.BatchForXML.generated.StudentsData.StudentData;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CReaderListener implements ItemReadListener<StudentData> {
    @Override
    public void beforeRead() {
        log.info("before read");
    }

    @Override
    public void afterRead(StudentData studentData) {
        log.info("after read");
    }

    @Override
    public void onReadError(Exception e) {
        log.error("On Read Error: " + e);
        throw new ReadException("Error when reading StudentData...");
    }
}
