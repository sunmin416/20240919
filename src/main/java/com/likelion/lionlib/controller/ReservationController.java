package com.likelion.lionlib.controller;

import com.likelion.lionlib.domain.Reservation;
import com.likelion.lionlib.dto.ReservationCountResponse;
import com.likelion.lionlib.dto.ReservationRequest;
import com.likelion.lionlib.dto.ReservationResponse;
import com.likelion.lionlib.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<ReservationResponse> addReservation(@RequestBody ReservationRequest reservationRequest) {
        log.info("Request POST a reservation: {}", reservationRequest);
        ReservationResponse savedReservation = reservationService.addReservation(reservationRequest);
        log.info("Response POST a reservation: {}", savedReservation);
        return ResponseEntity.ok(savedReservation);
    }

    @GetMapping("/reservations/{reservationId}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable("reservationId") Long reservationId) {
        log.info("Request GET a reservation with ID: {}", reservationId);
        ReservationResponse response = reservationService.getReservation(reservationId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<ReservationResponse> deleteReservation(@PathVariable("reservationId") Long reservationId) {
        log.info("Request DELETE reservation with ID: {}", reservationId);
        ReservationResponse response = reservationService.deleteReservation(reservationId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/members/{memberId}/reservations")
    public ResponseEntity<List<ReservationResponse>> getReservationsByMember(@PathVariable("memberId") Long memberId) {
        log.info("Request GET Reservations with memberId: {}", memberId);
        List<Reservation> reservations = reservationService.getMemberReservation(memberId);
        List<ReservationResponse> responseList = reservations.stream()
                .map(ReservationResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/books/{bookId}/reservations")
    public ResponseEntity<ReservationCountResponse> countBookReservations(@PathVariable("bookId") Long bookId) {
        log.info("Request GET the count of reservations with bookId: {}", bookId);
        ReservationCountResponse reservationCount = reservationService.getBookReservation(bookId);
        return ResponseEntity.ok(reservationCount);
    }

}