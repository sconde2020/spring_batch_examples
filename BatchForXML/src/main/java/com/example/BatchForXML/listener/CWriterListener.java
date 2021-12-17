package com.example.BatchForXML.listener;

import com.example.BatchForXML.exceptions.ReadException;
import com.example.BatchForXML.exceptions.WriteException;
import com.example.BatchForXML.model.Student;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class CWriterListener implements ItemWriteListener<Student> {
    @Override
    public void beforeWrite(List<? extends Student> list) {
        log.info("before write");
    }

    @Override
    public void afterWrite(List<? extends Student> list) {
        log.info("after write");
    }

    @Override
    public void onWriteError(Exception e, List<? extends Student> list) {
        log.error("On Write Error" + e);
        throw new WriteException("Error when writing students...");
    }
}
