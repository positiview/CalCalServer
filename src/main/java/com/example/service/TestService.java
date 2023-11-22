package com.example.service;

import com.example.entity.TestEntity;
import com.example.model.TestDTO;
import com.example.repository.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TestService {

    private TestRepository testRepository;


    public void saveTest(TestDTO testDTO){
        TestEntity testEntity = new TestEntity();
        testEntity.setName(testDTO.getName());
        testEntity.setMainTitle(testDTO.getMainTitle());
        testEntity.setContactNum(testDTO.getContactNum());

        testRepository.save(testEntity);
    }

}
