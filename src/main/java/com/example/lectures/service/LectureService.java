package com.example.lectures.service;

import com.example.lectures.DTO.LectureDTO;
import com.example.lectures.model.Lecture;

import java.util.List;

public interface LectureService {
    LectureDTO createLecture(LectureDTO lectureDTO);

    LectureDTO getLectureById(Long id);

    List<LectureDTO> getAllLectures();

    LectureDTO updateLectureById(Long id, LectureDTO lectureDTO);

    void deleteLectureById(Long id);
}

