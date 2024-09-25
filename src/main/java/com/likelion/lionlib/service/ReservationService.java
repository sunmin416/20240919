package com.likelion.lionlib.service;

import com.likelion.lionlib.domain.Book;
import com.likelion.lionlib.domain.Member;
import com.likelion.lionlib.domain.Reservation;
import com.likelion.lionlib.dto.ReservationCountResponse;
import com.likelion.lionlib.dto.ReservationRequest;
import com.likelion.lionlib.dto.ReservationResponse;
import com.likelion.lionlib.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final GlobalService globalService;

    public ReservationResponse addReservation(ReservationRequest request) {
        Member member = globalService.findMemberById(request.getMemberId());
        Book book = globalService.findBookById(request.getBookId());

        Reservation reservation = Reservation.builder()
                .member(member)
                .book(book)
                .build();

        reservationRepository.save(reservation);

        return ReservationResponse.fromEntity(reservation);
    }

    public ReservationResponse getReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(()-> new RuntimeException("Reservation not found"));
        return ReservationResponse.fromEntity(reservation);
    }

    public ReservationResponse deleteReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(()-> new RuntimeException("Reservation not found"));
        reservationRepository.delete(reservation);
        return ReservationResponse.fromEntity(reservation);
    }

    public List<Reservation> getMemberReservation(Long memberId) {
        Member member = globalService.findMemberById(memberId);
        return reservationRepository.findByMember(member);
    }

    public ReservationCountResponse getBookReservation(Long bookId) {
        Book book = globalService.findBookById(bookId);
        Long count = reservationRepository.countByBook(book);
        return new ReservationCountResponse(count);
    }
}
