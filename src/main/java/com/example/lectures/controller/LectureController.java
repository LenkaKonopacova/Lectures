package com.example.lectures.controller;

import com.example.lectures.DTO.LectureDTO;
import com.example.lectures.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lectures")
public class LectureController {

    @Autowired
    private LectureService lectureService;

    @PostMapping
    public ResponseEntity<LectureDTO> createLecture(@RequestBody LectureDTO lectureDTO) {
        LectureDTO createdLectureDTO = lectureService.createLecture(lectureDTO);
        return new ResponseEntity<>(createdLectureDTO, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<LectureDTO> getLectureById(@PathVariable Long id) {
        LectureDTO lectureDTO = lectureService.getLectureById(id);

        if (lectureDTO != null) {
            return new ResponseEntity<>(lectureDTO, HttpStatus.OK); //
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //
        }
    }
    @GetMapping
    public ResponseEntity<List<LectureDTO>> getAllLectures() {
        List<LectureDTO> lectures = lectureService.getAllLectures();
        return new ResponseEntity<>(lectures, HttpStatus.OK); // Return 200 OK
    }

    @PutMapping("/{id}")
    public ResponseEntity<LectureDTO> updateLectureById(@PathVariable Long id, @Validated @RequestBody LectureDTO lectureDTO) {
        LectureDTO updatedLectureDTO = lectureService.updateLectureById(id, lectureDTO);

        if (updatedLectureDTO != null) {
            return new ResponseEntity<>(updatedLectureDTO, HttpStatus.OK); // Return 200 OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 Not Found
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLectureById(@PathVariable Long id) {
        // Check if the lecture is found for deletion
        if (lectureService.getLectureById(id) != null) {
            lectureService.deleteLectureById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 Not Found
        }
    }
}
