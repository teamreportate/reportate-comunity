package bo.com.reportate.controller;

import bo.com.reportate.exception.OperationException;
import bo.com.reportate.service.LogService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.utils.DateUtil;
import bo.com.reportate.utils.FormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/logs")
@Slf4j
public class LogController {
    @Autowired
    private LogService logService;

    @GetMapping("/listar/{page}/{size}")
    public ResponseEntity listPageableByDates(@PathVariable("page") Integer page, @PathVariable("size") Integer size,
                                              @RequestParam("fechaInicio") @DateTimeFormat(pattern = DateUtil.FORMAT_DATE_PARAM_URL) Date from,
                                              @RequestParam("fechaFin") @DateTimeFormat(pattern = DateUtil.FORMAT_DATE_PARAM_URL) Date to,
                                              @RequestParam("q") String q,
                                              @RequestParam("proceso") String proceso) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return ok(logService.listPageableByDatesAndType(DateUtil.formatToStart(from), DateUtil.formatToEnd(to), q, proceso ,pageable));
        } catch (Exception e) {
            log.error(FormatUtil.MSG_TITLE_ERROR, e);
            return CustomErrorType.serverError(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }

    /**
     * Metodo que permite obtener un log segun su id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity obtener(@PathVariable("id") Long id) {
        try {
            return ok(logService.obtener(id));
        } catch (OperationException e) {
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, e.getMessage());
        } catch (Exception e) {
            log.error(FormatUtil.MSG_TITLE_ERROR, e);
            return CustomErrorType.badRequest(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }

    @GetMapping("/listar/procesos")
    public ResponseEntity listPageableByDates() {
        try {
            return ok(this.logService.listProcesos());
        } catch (Exception e) {
            log.error(FormatUtil.MSG_TITLE_ERROR, e);
            return CustomErrorType.serverError(FormatUtil.MSG_TITLE_ERROR, FormatUtil.defaultError());
        }
    }

}
