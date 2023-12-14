package com.example.repository;

import com.example.entity.RouteRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RouteRecordRepository extends JpaRepository<RouteRecord,Long> {

   List<RouteRecord> findAllByEmail(String email);



}
