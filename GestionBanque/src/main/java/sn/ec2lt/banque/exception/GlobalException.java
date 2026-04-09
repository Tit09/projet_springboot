package sn.ec2lt.banque.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(CompteInexistant.class)
    public ResponseEntity<Map<String, Object>> CompteInexistant(CompteInexistant compteInexistant){
        Map<String, Object> ErrorResponse = new HashMap<>();
        ErrorResponse.put("message", compteInexistant.getMessage());
        ErrorResponse.put("status", HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse);
    }

    @ExceptionHandler(MontantInvalide.class)
    public ResponseEntity<Map<String, Object>> MontantInvalide(MontantInvalide montantInvalide){
        Map<String, Object> ErrorResponse = new HashMap<>();
        ErrorResponse.put("message", montantInvalide.getMessage());
        ErrorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse);
    }

    @ExceptionHandler(SoldeInsuffisantException.class)
    public ResponseEntity<Map<String, Object>> SoldeInsuffisantException(SoldeInsuffisantException soldeInsuffisantException){
        Map<String, Object> ErrorResponse = new HashMap<>();
        ErrorResponse.put("message", soldeInsuffisantException.getMessage());
        ErrorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> exception(Exception exception){
        Map<String, Object> ErrorResponse = new HashMap<>();
        ErrorResponse.put("message", exception.getMessage());
        ErrorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse);
    }
}
