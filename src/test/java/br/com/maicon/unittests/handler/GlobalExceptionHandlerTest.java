package br.com.maicon.unittests.handler;

import br.com.maicon.exception.ExceptionResponse;
import br.com.maicon.exception.ResourceNotFoundException;
import br.com.maicon.handler.GlobalExceptionHandler;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleResourceNotFoundException() {
        // Arrange
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("uri=/test");

        // Act
        ResponseEntity<ExceptionResponse> response = handler.handleResourceNotFoundException(exception, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Resource not found", response.getBody().getMessage());
    }

    @Test
    void testHandleGlobalException() {
        // Arrange
        Exception exception = new Exception("Internal server error");
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("uri=/test");

        // Act
        ResponseEntity<ExceptionResponse> response = handler.handleGlobalException(exception, request);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal server error", response.getBody().getMessage());
    }
}
