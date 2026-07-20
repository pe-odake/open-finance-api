package com.pedroodake.openfinanceapi.exception.handler;

import com.pedroodake.openfinanceapi.exception.dto.DadosErroGeral;
import com.pedroodake.openfinanceapi.exception.dto.DadosErroValidacao;
import com.pedroodake.openfinanceapi.exception.type.conta.ContaNotFoundException;
import com.pedroodake.openfinanceapi.exception.type.usuario.SenhaIncorretaException;
import com.pedroodake.openfinanceapi.exception.type.usuario.UsuarioNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<DadosErroGeral> handleUsuarioNotFound(UsuarioNotFoundException ex, HttpServletRequest request) {
        DadosErroGeral erro = new DadosErroGeral(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(ContaNotFoundException.class)
    public ResponseEntity<DadosErroGeral> handleContaNotFound(ContaNotFoundException ex, HttpServletRequest request) {
        DadosErroGeral erro = new DadosErroGeral(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(SenhaIncorretaException.class)
    public ResponseEntity<DadosErroGeral> handleSenhaIncorreta(SenhaIncorretaException ex, HttpServletRequest request) {
        DadosErroGeral erro = new DadosErroGeral(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> handleTratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DadosErroGeral> handleExceptionGeral(Exception ex, HttpServletRequest request) {
        DadosErroGeral erro = new DadosErroGeral(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Ocorreu um erro interno inesperado no servidor.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
