package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class Test07VerifyingBehaviour {

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
    void should_InvokePayment_When_Prepaid() {
        // given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 3, 21),
                LocalDate.of(2022, 3, 24), 2, true);

        // when
        bookingService.makeBooking(bookingRequest);

        // then
        verify(paymentServiceMock, times(1)).pay(bookingRequest, 300.0);
        verifyNoMoreInteractions(paymentServiceMock);

    }

    @Test
    void should_NotInvokePayment_When_NotPrepaid() {
        // given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2022, 3, 21),
                LocalDate.of(2022, 3, 24), 2, false);

        // when
        bookingService.makeBooking(bookingRequest);

        // then
        verify(paymentServiceMock, never()).pay(any(), anyDouble());

    }
}