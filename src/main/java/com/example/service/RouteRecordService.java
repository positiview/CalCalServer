package com.example.service;

import com.example.entity.RouteAndTime;
import com.example.entity.RouteRecord;
import com.example.model.RouteAndTimeDTO;
import com.example.model.RouteRecordDTO;
import com.example.repository.RouteRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class RouteRecordService {

    private RouteRecordRepository routeRecordRepository;

    public void saveRouteRecord(List<RouteAndTimeDTO> list, String email, String cName, Double calorie){
        RouteRecord rr =new RouteRecord();
        rr.setEmail(email);
        rr.setCourseName(cName);
        rr.setCalorie(calorie);

        RouteRecord saveEntity = routeRecordRepository.save(rr);

        for(RouteAndTimeDTO ratDTO : list){
            RouteAndTime rat = new RouteAndTime();

            rat.setLongitude(ratDTO.getLongitude());
            rat.setLatitude(ratDTO.getLatitude());
            rat.setTime(ratDTO.getTime());
            rat.setRouteRecord(saveEntity);
            rr.getRatList().add(rat);
        }


        routeRecordRepository.save(rr);

    }
    @Transactional(readOnly = true)
    public List<RouteRecordDTO> getRouteRecord(String email){
        List<RouteRecordDTO> getRecordDTOList = new ArrayList<>();


        List<RouteRecord> recordList = routeRecordRepository.findAllByEmail(email);
        for(RouteRecord rr : recordList){
            RouteRecordDTO routeRecordDTO = new RouteRecordDTO();
            List<RouteAndTimeDTO> ratList = new ArrayList<>();
            for(RouteAndTime rat : rr.getRatList()){
                RouteAndTimeDTO ratDTO = new RouteAndTimeDTO();
                ratDTO.setLatitude(rat.getLatitude());
                ratDTO.setLongitude(rat.getLongitude());
                ratDTO.setTime(rat.getTime());
                ratList.add(ratDTO);
            }
            routeRecordDTO.setRatList(ratList);
            routeRecordDTO.setCourseName(rr.getCourseName());
            routeRecordDTO.setCalorie(rr.getCalorie());
            routeRecordDTO.setRegDate(rr.getRegDate());
            getRecordDTOList.add(routeRecordDTO);
        }

        return getRecordDTOList;

    }

}
