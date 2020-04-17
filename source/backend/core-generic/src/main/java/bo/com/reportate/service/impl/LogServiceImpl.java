package bo.com.reportate.service.impl;

import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.MuBitacora;
import bo.com.reportate.model.dto.ProcessDto;
import bo.com.reportate.model.enums.LogLevel;
import bo.com.reportate.model.enums.Process;
import bo.com.reportate.repository.LogRepository;
import bo.com.reportate.service.LogService;
import bo.com.reportate.utils.FormatUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by :MC4
 * Autor      :Ricardo Laredo
 * Email      :rlaredo@mc4.com.bo
 * Date       :13-01-19
 * Project    :reportate
 * Package    :bo.com.reportate.service.LogServiceImpl
 * Copyright  : MC4
 *
 * Modified   :Jonathan Valdivia
 * Date       :21-01-2019
 */
@Slf4j
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogRepository repository;


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void info(Process process, String mensaje, Object... arguments) {
        if(arguments != null && arguments.length >0) {
            FormattingTuple tp = MessageFormatter.arrayFormat(mensaje, arguments);
            mensaje = tp.getMessage();
        }
        MuBitacora muBitacora = new MuBitacora(process, LogLevel.INFO,mensaje);
        this.repository.save(muBitacora);
    }



    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void warning(Process process, String mensaje) {
        MuBitacora muBitacora = new MuBitacora(process, LogLevel.WARNING,mensaje);
        this.repository.save(muBitacora);
    }

    @Override
    public void warning(Process process, String mensaje, Object... arguments) {
        if(arguments != null && arguments.length >0) {
            FormattingTuple tp = MessageFormatter.arrayFormat(mensaje, arguments);
            mensaje = tp.getMessage();
        }
        MuBitacora muBitacora = new MuBitacora(process, LogLevel.WARNING,mensaje);
        this.repository.save(muBitacora);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void error(Process process, String mensaje,Object... arguments) {
        if(arguments != null && arguments.length > 0) {
            FormattingTuple tp = MessageFormatter.arrayFormat(mensaje, arguments);
            mensaje = tp.getMessage();
        }
        MuBitacora muBitacora = new MuBitacora(process, LogLevel.ERROR,mensaje);
        this.repository.save(muBitacora);
    }


    @Override
    public MuBitacora obtener(Long id) {
        MuBitacora muBitacora =repository.getOne(id);
        if(muBitacora == null){
            throw new OperationException(FormatUtil.noRegistrado("SfeLog",id));
        }
        return muBitacora;
    }

    @Override
    public Page<MuBitacora> listPageableByDatesAndType(Date from, Date to, String mensaje, String proceso, Pageable pageable) {
        mensaje =  "%" + mensaje + "%";
        return this.repository.listByDate(from,to, Process.valueOf(proceso), mensaje.toLowerCase().trim(),pageable);
    }

    @Override
    public List<Process> listProcesos() {
       return Arrays.asList(Process.values());
    }
}
