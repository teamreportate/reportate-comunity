package bo.com.reportate.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

/**
 * Created by :MC4
 * Autor      :Ricardo Laredo
 * Email      :rlaredo@mc4.com.bo
 * Date       :14-02-19
 * Project    :reportate
 * Package    :bo.com.reportate.util
 * Copyright  : MC4
 */
@AllArgsConstructor
@Getter
public class CustomErrorType implements Serializable{
    private String title;
    private HttpStatus status;
    private String detail;
    public static ResponseEntity badRequest(String title, String detail){
        return new ResponseEntity<>(new CustomErrorType(title, HttpStatus.BAD_REQUEST,detail),HttpStatus.BAD_REQUEST);
    }
    public static ResponseEntity serverError(String title, String detail){
        return new ResponseEntity<>(new CustomErrorType(title, HttpStatus.INTERNAL_SERVER_ERROR,detail),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public static ResponseEntity<?> notContent(String title, String detail){
        return new ResponseEntity<>(new CustomErrorType(title, HttpStatus.NO_CONTENT,detail),HttpStatus.NO_CONTENT);
    }
    public static ResponseEntity<?> notFound(String title, String detail){
        return new ResponseEntity<>(new CustomErrorType(title, HttpStatus.NOT_FOUND,detail),HttpStatus.NOT_FOUND);
    }
}

