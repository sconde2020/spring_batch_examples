package com.example.BatchForXML.step;

import com.example.BatchForXML.exceptions.WriteException;
import com.example.BatchForXML.repository.StudentRepository;
import com.example.BatchForXML.model.Student;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Log4j2
public class StudentWriter implements ItemWriter<Student> {

    @Autowired
    StudentRepository studentRepository;

    @SneakyThrows
    @Override
    public void write(List<? extends Student> list) throws Exception {
        list.forEach(log::info);
        studentRepository.saveAll(list);
    }
}
