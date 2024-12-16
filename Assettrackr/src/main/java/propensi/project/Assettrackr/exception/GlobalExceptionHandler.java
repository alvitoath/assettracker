package propensi.project.Assettrackr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import propensi.project.Assettrackr.model.dto.response.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        String errorMessage = ex.getMessage();
        System.out.println("pesan error " + errorMessage);
        if (errorMessage.contains("Username")) {
//            System.out.println("ada usernamw");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("username", "Username sudah terdaftar"));

        }
        else if (errorMessage.contains("Email")) {
//            System.out.println("ada usernamw");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("email", "Email sudah terdaftar"));
        }
        else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(null, "Terjadi kesalahan!"));
        }
    }
}
