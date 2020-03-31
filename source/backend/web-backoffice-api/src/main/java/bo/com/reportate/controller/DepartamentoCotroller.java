package bo.com.reportate.controller;

import bo.com.reportate.model.Departamento;
import bo.com.reportate.model.dto.AlarmaDto;
import bo.com.reportate.model.dto.DepartamentoDto;
import bo.com.reportate.service.DepartamentoService;
import bo.com.reportate.util.CustomErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-03-30
 * @Project :reportate
 * @Package :bo.com.reportate.controller
 * @Copyright :MC4
 */
@Slf4j
@RestController
@RequestMapping("/api/departamentos")
public class DepartamentoCotroller {
    @Autowired
    private DepartamentoService departamentoService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DepartamentoDto>> listAll() {
        try {
            return ok(this.departamentoService.findAllConMunicipio());
        }catch (Exception e){
            log.error("Se genero un error al listar los departamentos",e);
            return CustomErrorType.serverError("Listar Departamentos", "Ocurrió un error al listar los departamentos");
        }
    }

}
