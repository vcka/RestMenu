package ml.penkisimtai.restMenu.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    private static final Logger logger = LoggerFactory.getLogger("ExceptionHandlerAdvice.class");

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity handleException(ResourceException e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity maxSizeError(MultipartException e, RedirectAttributes redirectAttributes) {
        logger.info("Max File Size Exception Occurs");
        redirectAttributes.addFlashAttribute("message", "Excced Max File Size Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}