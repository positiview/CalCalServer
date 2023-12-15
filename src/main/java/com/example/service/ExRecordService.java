package com.example.service;

import com.example.entity.ExRecord;
import com.example.model.ExRecordDTO;
import com.example.repository.ExRecordRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class ExRecordService {

    private ExRecordRepository exRecordRepository;

    public void saveExRecord(String userEmail, String exname, Double goalCalorie, Double calorie, Double exTime) {
        ExRecord er = new ExRecord();

        er.setEmail(userEmail);
        er.setExname(exname);
        er.setGoalCalorie(goalCalorie);
        er.setCalorie(calorie);
        er.setExTime(exTime);

        ExRecord savedEntity = exRecordRepository.save(er);

        log.info("ExRecord was successfully saved: " + savedEntity);
    }

    public ExRecordDTO getExrecord(String email) {
        ExRecord er = (ExRecord) exRecordRepository.findAllByEmail(email);

        if (er != null) {
            ExRecordDTO exrecordDTO = new ExRecordDTO();

            exrecordDTO.setUserEmail(er.getEmail());
            exrecordDTO.setExname(er.getExname());
            exrecordDTO.setGoalCalorie(er.getGoalCalorie());
            exrecordDTO.setCalorie(er.getCalorie());
            exrecordDTO.setExTime(er.getExTime());
            exrecordDTO.setRegDate(er.getRegDate());

            return exrecordDTO;
        } else {
            return null;
        }
    }

    public List<ExRecordDTO> getAllExRecords() {
        List<ExRecord> exRecords = exRecordRepository.findAll();
        List<ExRecordDTO> exRecordDTOs = new ArrayList<>();

        for (ExRecord er : exRecords) {
            ExRecordDTO exRecordDTO = new ExRecordDTO();

            exRecordDTO.setUserEmail(er.getEmail());
            exRecordDTO.setExname(er.getExname());
            exRecordDTO.setGoalCalorie(er.getGoalCalorie());
            exRecordDTO.setCalorie(er.getCalorie());
            exRecordDTO.setExTime(er.getExTime());

            exRecordDTOs.add(exRecordDTO);
        }

        return exRecordDTOs;
    }

}
