package com.example.service;


import com.example.entity.ExerciseEntity;
import com.example.entity.MemberEntity;
import com.example.model.ExerciseDTO;
import com.example.repository.ExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ExerciseService {

    private ExerciseRepository exerciseRepository;



    public void saveExercise(ExerciseDTO exerciseDTO){
        ExerciseEntity exerciseEntity = new ExerciseEntity();
        exerciseEntity.setExname(exerciseDTO.getExname());
        exerciseEntity.setExicon(String.valueOf(exerciseDTO.getExicon()));
        exerciseEntity.setExcontent(exerciseDTO.getExcontent());
        exerciseEntity.setExcal(exerciseDTO.getExcal());
        exerciseEntity.setExtime(exerciseDTO.getExtime());
        exerciseEntity.setEmail(exerciseDTO.getEmail());
        exerciseEntity.setExmove(exerciseDTO.getExmove());


        exerciseRepository.save(exerciseEntity);
    }
    public boolean updateExerciseData(ExerciseDTO exerciseDTO) {
        ExerciseEntity exercise = exerciseRepository.findByExname(exerciseDTO.getExname());

        if (exercise != null) {
            exercise.setExname(exerciseDTO.getExname());
            exercise.setExicon(String.valueOf(exerciseDTO.getExicon()));
            exercise.setExcontent(exerciseDTO.getExcontent());
            exercise.setExcal(exerciseDTO.getExcal());
            exercise.setExtime(exerciseDTO.getExtime());
            exercise.setEmail(exerciseDTO.getEmail());
            exercise.setExmove(exerciseDTO.getExmove());
            exerciseRepository.save(exercise);
            return true;
        } else {
            return false;
        }
    }

    public List<ExerciseEntity> findAllExercise() {
        return exerciseRepository.findAll();
    }

    public ExerciseDTO getExerciseData(String exname) {
        ExerciseEntity exercise = exerciseRepository.findByExname(exname);

        if (exercise != null) {
            ExerciseDTO exerciseDTO = new ExerciseDTO();

            exerciseDTO.setExname(exercise.getExname());
            exerciseDTO.setExicon(exercise.getExicon());
            exerciseDTO.setExcontent(exercise.getExcontent());
            exerciseDTO.setExcal(exercise.getExcal());
            exerciseDTO.setExtime(exercise.getExtime());
            exerciseDTO.setEmail(exercise.getEmail());
            exerciseDTO.setExmove(exerciseDTO.getExmove());


            return exerciseDTO;
        } else {

            return null;
        }
    }

    public List<ExerciseDTO> getAllExercises() {
        List<ExerciseEntity> exercises = exerciseRepository.findAll(); // 데이터베이스에서 모든 운동 데이터를 가져옵니다.
        List<ExerciseDTO> exerciseDTOs = new ArrayList<>();

        for (ExerciseEntity exercise : exercises) {
            ExerciseDTO exerciseDTO = new ExerciseDTO();

            exerciseDTO.setExname(exercise.getExname());
            exerciseDTO.setExicon(exercise.getExicon());
            exerciseDTO.setExcontent(exercise.getExcontent());
            exerciseDTO.setExcal(exercise.getExcal());
            exerciseDTO.setExtime(exercise.getExtime());
            exerciseDTO.setEmail(exercise.getEmail());
            exerciseDTO.setExmove(exercise.getExmove()); // 수정된 부분

            exerciseDTOs.add(exerciseDTO); // 리스트에 DTO 객체를 추가합니다.
        }

        return exerciseDTOs; // 리스트를 반환합니다.
    }
    public Page<ExerciseEntity> getexnameList(String keyword, Pageable pageable) {
        return exerciseRepository.getByexnameLike(keyword, pageable);
    }

    public Page<ExerciseEntity> getExerciseList(Pageable pageable) {
        return exerciseRepository.findAll(pageable);
    }
}
