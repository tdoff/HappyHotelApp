package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

class Test09MockingVoidMethods {

    private BookingService bookingService;

    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setup() {
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);
    }

    @Test
    void should_ThrowException_When_MailNotReady() {
        // given
        LocalDate dateFrom = LocalDate.of(2022, 3, 21);
        LocalDate dateTo = LocalDate.of(2022, 3, 24);
        BookingRequest bookingRequest = new BookingRequest("1", dateFrom, dateTo, 2, false);
        doThrow(new UnsupportedOperationException("Not implemented yet")).when(mailSenderMock).sendBookingConfirmation(any());

        // when
        Executable executable = () -> bookingService.makeBooking(bookingRequest);

        // then
        assertThrows(UnsupportedOperationException.class, executable);

    }

    @Test
    void should_NotThrowException_When_MailNotReady() {
        // given
        LocalDate dateFrom = LocalDate.of(2022, 3, 21);
        LocalDate dateTo = LocalDate.of(2022, 3, 24);

        BookingRequest bookingRequest = new BookingRequest("1", dateFrom, dateTo, 2, false);
        doNothing().when(mailSenderMock).sendBookingConfirmation(any());

        // when
        bookingService.makeBooking(bookingRequest);

        // then
//        assertThrows(BusinessException.class, executable);

    }
}