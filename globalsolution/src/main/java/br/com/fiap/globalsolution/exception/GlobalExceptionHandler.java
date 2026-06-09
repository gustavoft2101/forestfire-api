package br.com.fiap.globalsolution.exception;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Solução não encontrada
    @ExceptionHandler(SolucaoNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(SolucaoNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(buildError(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    // 409 - Tentativa de alterar solução CANCELADA
    @ExceptionHandler(SolucaoCanceladaException.class)
    public ResponseEntity<Map<String, Object>> handleCancelada(SolucaoCanceladaException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(buildError(HttpStatus.CONFLICT, ex.getMessage()));
    }

    // 409 - Tentativa de excluir solução VALIDADA
    @ExceptionHandler(SolucaoValidadaException.class)
    public ResponseEntity<Map<String, Object>> handleValidada(SolucaoValidadaException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(buildError(HttpStatus.CONFLICT, ex.getMessage()));
    }

    // 400 - Erros de validação Bean Validation (@NotBlank, @Min, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> erros = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            erros.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("erro", "Dados inválidos");
        body.put("campos", erros);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // 500 - Qualquer outro erro inesperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Erro interno no servidor: " + ex.getMessage()));
    }

    // Método auxiliar para montar o corpo do erro
    private Map<String, Object> buildError(HttpStatus status, String mensagem) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("erro", status.getReasonPhrase());
        body.put("mensagem", mensagem);
        return body;
    }
}
