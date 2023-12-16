package com.example.repository;

import com.example.entity.ExRecord;
import com.example.entity.RouteRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExRecordRepository extends JpaRepository<ExRecord,Long> {

   List<ExRecord> findAllByEmail(String email);


   boolean existsByEmail(String email);





}
