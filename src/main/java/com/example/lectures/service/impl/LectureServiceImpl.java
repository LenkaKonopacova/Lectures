package com.example.lectures.service.impl;

import com.example.lectures.DTO.LectureDTO;
import exceptions.LectureNotFoundException;
import com.example.lectures.model.Lecture;
import com.example.lectures.model.Lecturer;
import com.example.lectures.repository.LectureRepository;
import com.example.lectures.repository.LecturerRepository;
import com.example.lectures.service.LectureService;
import exceptions.LecturerNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final LecturerRepository lecturerRepository;

    @Autowired
    public LectureServiceImpl(LectureRepository lectureRepository, LecturerRepository lecturerRepository) {
        this.lectureRepository = lectureRepository;
        this.lecturerRepository = lecturerRepository;
    }

    @Override
    public LectureDTO createLecture(LectureDTO lectureDTO) {
        // Convert DTO to Entity
        Lecture lecture = convertToEntity(lectureDTO);

        // Fetch the Lecturer and set it
        Lecturer lecturer = lecturerRepository.findById(lectureDTO.getLecturerId())
                .orElseThrow(() -> new LecturerNotFoundException("Lecturer not found with id: " + lectureDTO.getLecturerId()));
        lecture.setLecturer(lecturer);

        // Save and convert back to DTO
        Lecture savedLecture = lectureRepository.save(lecture);
        return convertToDTO(savedLecture);
    }

    @Transactional
    public LectureDTO getLectureById(Long id) {
        // Fetch the Lecture entity or throw an exception if not found
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new LectureNotFoundException("Lecture not found with id: " + id));
        Hibernate.initialize(lecture.getLecturer());
        return convertToDTO(lecture);
    }

    @Transactional
    @Override
    public List<LectureDTO> getAllLectures() {
        // Fetch all Lecture entities and convert to DTOs
        List<Lecture> lectures = lectureRepository.findAll();
        return convertToDTOList(lectures); // Convert and return DTOs
    }

    @Transactional
    public LectureDTO updateLectureById(Long id, LectureDTO lectureDTO) {
        // Fetch the existing Lecture entity or throw an exception if not found
        Lecture existingLecture = lectureRepository.findById(id)
                .orElseThrow(() -> new LectureNotFoundException("Lecture not found with id: " + id));
        Hibernate.initialize(existingLecture.getLecturer());
        // Update fields from DTO
        existingLecture.setTitle(lectureDTO.getTitle());
        existingLecture.setCode(lectureDTO.getCode()); // Assuming code is still used

        // Save the updated Lecture and return DTO
        Lecture updatedLecture = lectureRepository.save(existingLecture);

        return convertToDTO(updatedLecture);
    }

    @Override
    public void deleteLectureById(Long id) {
        // Check if the lecture exists before attempting to delete
        if (!lectureRepository.existsById(id)) {
            throw new LectureNotFoundException("Lecture not found with id: " + id);
        }
        lectureRepository.deleteById(id);
    }

    // Convert LectureDTO to Lecture entity
    private Lecture convertToEntity(LectureDTO lectureDTO) {
        Lecture lecture = new Lecture();
        lecture.setId(lectureDTO.getId()); // Use for updates (can be null for new entities)
        lecture.setTitle(lectureDTO.getTitle());
        lecture.setCode(lectureDTO.getCode());
        // Note: No need to set lecturer here as it's fetched in createLecture method
        return lecture; // Return the Lecture entity
    }

    // Method to convert a single Lecture to LectureDTO
    private LectureDTO convertToDTO(Lecture lecture) {
        LectureDTO lectureDTO = new LectureDTO();
        lectureDTO.setId(lecture.getId());
        lectureDTO.setTitle(lecture.getTitle());
        lectureDTO.setCode(lecture.getCode());

        if (lecture.getLecturer() != null) { // Safely access Lecturer details
            lectureDTO.setLecturerId(lecture.getLecturer().getLecturerId());
            lectureDTO.setLecturerName(lecture.getLecturer().getLecturerName());
        }
        return lectureDTO; // Return the LectureDTO
    }

    // Method to convert a List of Lectures to List of LectureDTOs
    private List<LectureDTO> convertToDTOList(List<Lecture> lectures) {
        return lectures.stream()
                .map(this::convertToDTO) // Convert each Lecture to LectureDTO
                .collect(Collectors.toList()); // Collect and return the list
    }
}