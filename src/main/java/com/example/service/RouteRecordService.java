package com.example.service;

import com.example.entity.RouteAndTime;
import com.example.entity.RouteRecord;
import com.example.model.RouteAndTimeDTO;
import com.example.repository.RouteRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class RouteRecordService {

    private RouteRecordRepository routeRecordRepository;

    public void saveRouteRecord(List<RouteAndTimeDTO> list, String email){
        RouteRecord rr =new RouteRecord();
        rr.setEmail(email);

        for(RouteAndTimeDTO ratDTO : list){
            RouteAndTime rat = new RouteAndTime();

            rat.setLongitude(ratDTO.getLongitude());
            rat.setLatitude(ratDTO.getLatitude());
            rat.setTime(ratDTO.getTime());
            rr.getRouteRecordList().add(rat);
        }

        routeRecordRepository.save(rr);

    }

    public List<RouteAndTimeDTO> getRouteRecord(String email){

        Optional<RouteRecord> rr = routeRecordRepository.findByEmail(email);

        if(!rr.isEmpty()){
            return Collections.emptyList();
        }

        List<RouteAndTimeDTO> ratList = new ArrayList<>();
        for(RouteAndTime rat :rr.get().getRouteRecordList()){
            RouteAndTimeDTO dto = new RouteAndTimeDTO();
            dto.setLatitude(rat.getLatitude());
            dto.setLongitude(rat.getLongitude());
            dto.setTime(rat.getTime());
            ratList.add(dto);
        }

        return ratList;
    }

}
