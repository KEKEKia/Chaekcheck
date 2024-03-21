package com.cc.business.global.exception;

import com.cc.business.global.common.response.EnvelopeResponse;
import com.cc.business.global.exception.auth.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    /* 사용자 인증 에러 */
    @ExceptionHandler(value = {AuthException.class})
    public ResponseEntity<EnvelopeResponse<Object>> authErrorHandler(AuthException e) {
        log.error("사용자 인증 에러 발생");
        e.printStackTrace();

        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(EnvelopeResponse.builder()
                        .code(e.getErrorCode().getHttpStatus().value())
                        .message(e.getErrorCode().getMessage())
                        .build());
    }


    /* TA 에러 */
    @ExceptionHandler(value = {TAException.class})
    public ResponseEntity<EnvelopeResponse<HashMap<String, Object>>> TAErrorHandler(TAException e) {
        log.error("TA API 에러 발생");
        EnvelopeResponse result = new EnvelopeResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());

        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* SEARCH 에러 */
    @ExceptionHandler(value = {SearchException.class})
    public ResponseEntity<EnvelopeResponse<HashMap<String, Object>>> searchErrorHandler(SearchException e) {
        log.error("Search API 에러 발생");
        EnvelopeResponse result = new EnvelopeResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());

        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* SC 에러 */
    @ExceptionHandler(value = {SCException.class})
    public ResponseEntity<EnvelopeResponse<HashMap<String, Object>>> SCErrorHandler(SCException e) {
        log.error("SC API 에러 발생");
        EnvelopeResponse result = new EnvelopeResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());

        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* ANS 에러 */
    @ExceptionHandler(value = {AnsException.class})
    public ResponseEntity<EnvelopeResponse<HashMap<String, Object>>> AnsErrorHandler(AnsException e) {
        log.error("ANS API 에러 발생");
        EnvelopeResponse result = new EnvelopeResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());

        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
