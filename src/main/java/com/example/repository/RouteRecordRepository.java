package com.example.repository;

import com.example.entity.RouteRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteRecordRepository extends JpaRepository<RouteRecord,Long> {

   Optional<RouteRecord> findByEmail(String email);
}
