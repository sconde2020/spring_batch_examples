package com.example.BatchForXML.step;

import com.example.BatchForXML.exceptions.ReadException;
import com.example.BatchForXML.generated.StudentsData;
import com.example.BatchForXML.generated.StudentsData.StudentData;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

@Log4j2
public class CStudentReader implements ItemReader<StudentData> {

    private int nextStudentIndex;
    private List<StudentData> studentDataList;

    public CStudentReader(String xmlFilePath) throws ReadException {
        initialize(xmlFilePath);
    }

    public void initialize(String xmlFilePath) throws ReadException {
        try {
            JAXBContext jc = JAXBContext.newInstance("com.example.BatchForXML.generated");
            Unmarshaller unmarshaller = jc.createUnmarshaller();

            StudentsData studentsData = (StudentsData) unmarshaller.unmarshal(new File(xmlFilePath));
            studentDataList = studentsData.getStudentData();
        } catch (Exception e) {
            log.error("Parsing Exception: " + e);
            throw new ReadException("Error when parsing message...");
        }
    }

    @Override
    public StudentData read() throws Exception {
        StudentData nextStudentData = null;

        if (nextStudentIndex < studentDataList.size()) {
            nextStudentData = studentDataList.get(nextStudentIndex);
            nextStudentIndex++;
        } else {
            nextStudentIndex = 0;
        }

        return nextStudentData;
    }
}
