package br.com.maicon.unittests.exception;

import org.junit.jupiter.api.Test;

import br.com.maicon.exception.ExceptionResponse;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionResponseTest {

    @Test
    void testExceptionResponse() {
        // Arrange
        Date now = new Date();
        String message = "Test message";
        String details = "Test details";

        // Act
        ExceptionResponse response = new ExceptionResponse(now, message, details);

        // Assert
        assertEquals(now, response.getTimestamp());
        assertEquals(message, response.getMessage());
        assertEquals(details, response.getDetails());
    }
}
