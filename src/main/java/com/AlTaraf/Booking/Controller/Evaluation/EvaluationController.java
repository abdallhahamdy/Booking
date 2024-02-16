package com.AlTaraf.Booking.Controller.Evaluation;

import com.AlTaraf.Booking.Entity.Evaluation.Evaluation;
import com.AlTaraf.Booking.Entity.unit.roomAvailable.RoomAvailable;
import com.AlTaraf.Booking.Payload.response.ApiResponse;
import com.AlTaraf.Booking.Service.Evaluation.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/Evaluation")
public class EvaluationController {

    @Autowired
    EvaluationService evaluationService;

    @GetMapping()
    public ResponseEntity<?> getAllEvaluation() {
        List<Evaluation> evaluationList = evaluationService.getAllEvaluation();
        if (!evaluationList.isEmpty()) {
            return new ResponseEntity<>(evaluationList, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(204, "No Content for Evaluation !"));
        }
    }

}
