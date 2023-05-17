package com.example.JavaWithDocker.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import com.example.JavaWithDocker.model.Target;

@Repository
public interface TargetRepository extends JpaRepository<Target, UUID>{
     
    public Target findFirstByDeviceId(String deviceId);
}
