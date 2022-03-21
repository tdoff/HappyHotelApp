package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class Test14StaticMethods {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private PaymentService paymentServiceMock;

    @Mock
    private RoomService roomServiceMock;

    @Mock
    private BookingDAO bookingDAOMock;

    @Mock
    private MailSender mailSenderMock;

    @Captor
    private ArgumentCaptor<Double> doubleCaptor;

    @Test
    void should_InvokePayment_When_Prepaid() {

        try (MockedStatic<CurrencyConverter> mockedConverter = mockStatic(CurrencyConverter.class)) {
            // given
            LocalDate startDate = LocalDate.of(2022, 3, 21);
            LocalDate endDate = LocalDate.of(2022, 3, 24);

            BookingRequest bookingRequest = new BookingRequest("1", startDate, endDate, 2, false);

            double expected = 400.0;
            mockedConverter.when(() -> CurrencyConverter.toEuro(anyDouble())).thenReturn(400.0);

            // when
            double actual = bookingService.calculatePriceEuro(bookingRequest);

            // then
            assertEquals(expected, actual);
        }

    }

}