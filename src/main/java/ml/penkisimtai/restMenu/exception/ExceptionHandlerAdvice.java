package ml.penkisimtai.restMenu.exception;

import ml.penkisimtai.restMenu.model.MenuItem;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity handleException(ResourceException e) {
        // log exception
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

//    @ExceptionHandler(value = { MaxUploadSizeExceededException.class })
//    protected ResponseEntity handleMaxUploadSizeExceededException(Exception ex, WebRequest request) {
//        MaxUploadSizeExceededException musee = (MaxUploadSizeExceededException)ex;
//        FileUploadBase.SizeLimitExceededException slee = musee.getCause() instanceof FileUploadBase.SizeLimitExceededException ? (FileUploadBase.SizeLimitExceededException) musee.getCause() : null;
//        Long maxSize = slee == null ? musee.getMaxUploadSize() : slee.getPermittedSize();
//        Long actualSize = slee == null ? Long.parseLong(request.getHeader("Content-Length")) : slee.getActualSize();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Location", "/admin");
//        return new ResponseEntity<String>(headers,HttpStatus.FOUND);
//    }

    private static final Logger logger = LoggerFactory.getLogger("ExceptionHandlerAdvice.class");

    @ExceptionHandler
    public ResponseEntity maxSizeError(MultipartException e, RedirectAttributes redirectAttributes) {
        logger.info("Max File Size Exception Occurs");
        redirectAttributes.addFlashAttribute("message", "Excced Max File Size Error");
        Error error = new Error();
//        error.setCode(HttpStatus.NOT_FOUND);
//        error.setMessage("ID not found OR Your custom message or e.getMessage()");
//        return new ResponseEntity<Error>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}