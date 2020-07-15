package bo.com.reportate.controller;

import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.dto.DiagnosticoDto;
import bo.com.reportate.model.dto.response.*;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import bo.com.reportate.service.DiagnosticoResumenEstadoService;
import bo.com.reportate.service.DiagnosticoResumenTotalEstadoService;
import bo.com.reportate.service.DiagnosticoService;
import bo.com.reportate.util.CustomErrorType;
import bo.com.reportate.utils.DateUtil;
import bo.com.reportate.web.ActualizacionDiagnosticoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-01
 * @Project :reportate
 * @Package :bo.com.reportate.controller
 * @Copyright :Reportate
 */
@RestController
@RequestMapping("/api/diagnosticos")
@Slf4j
@Tag(name = "diagnostico", description = "API de diagnosticos")
public class DiagnosticoController {
    @Autowired private DiagnosticoService diagnosticoService;
    @Autowired private DiagnosticoResumenEstadoService diagnosticoResumenEstadoService;
    @Autowired private DiagnosticoResumenTotalEstadoService diagnosticoResumenTotalEstadoService;

    @RequestMapping(value = "/listar-filtro/{page}/{size}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar los diagnosticos", description = "Listar los diagnosticos", tags = { "diagnostico" })
    public ResponseEntity<Page<DiagnosticoResponseDto>> controlDiario(
            @AuthenticationPrincipal Authentication authentication,
            @Parameter(description = "Indice de página", required = true)
            @PathVariable("page") Integer page,
            @Parameter(description = "Tamaño de página", required = true)
            @PathVariable("size") Integer size,
            @Parameter(description = "Fecha inicio para el filtro", required = true)
            @RequestParam("from") @DateTimeFormat(pattern = DateUtil.FORMAT_DATE_PARAM_URL) Date from,
            @Parameter(description = "Fecha fin para el filtro", required = true)
            @RequestParam("to") @DateTimeFormat(pattern = DateUtil.FORMAT_DATE_PARAM_URL) Date to,
            @Parameter(description = "Identificador de Departamento", required = true)
            @RequestParam("departamentoId") Long departamentoId,
            @Parameter(description = "Identificador de Municipio", required = true)
            @RequestParam("municipioID") Long municipioId,
            @Parameter(description = "Identificador de Centro Salud", required = true)
            @RequestParam("centroSaludId") Long centroSaludId,
            @Parameter(description = "Nombre de paciente", required = true)
            @RequestParam("nombrePaciente") String nombrePaciente,
            @Parameter(description = "Clasificacion de diagnostico", required = true)
            @RequestParam("clasificacion")EstadoDiagnosticoEnum clasificacion,
            @Parameter(description = "Identificador de Enfermedad", required = true)
            @RequestParam("enfermedadId") Long enfermedadId) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return ok(this.diagnosticoService.listarDiagnostico(authentication, DateUtil.formatToStart(from), DateUtil.formatToEnd(to),
                    departamentoId, municipioId, centroSaludId, nombrePaciente, clasificacion, enfermedadId, pageable));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al listar los diagnosticos: Causa. {}",e.getMessage());
            return CustomErrorType.badRequest("Listar Diagnóstico", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al listar los diagnosticos:",e);
            return CustomErrorType.serverError("Listar Diagnóstico", "Se genero un error al listar los diagnósticos");
        }
    }
    @RequestMapping(value = "/tacometro-por-clasificacion",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Contabilizar los diagnosticos por clasificación", description = "Contabilizar los diagnosticos por clasificación", tags = { "cantidad diagnosticos por clasificación" })
    public ResponseEntity<TotalResponse> cantidadDiagnosticoPorEstadoDiagnostico(
    		@AuthenticationPrincipal Authentication authentication,
    		@Parameter(description = "Identificador de Departamento", required = true)
    		@RequestParam("departamentoId") Long departamentoId,
            @Parameter(description = "Identificador de Municipio", required = true)
            @RequestParam("municipioId") Long municipioId,
            @Parameter(description = "Identificador de Centro Salud", required = true)
            @RequestParam("centroSaludId") Long centroSaludId,
            @Parameter(description = "Identificador de Enfermedad", required = true)
            @RequestParam("enfermedadId") Long enfermedadId){
        try {
        	Integer sospechosos =this.diagnosticoService.cantidadDiagnosticoPorFiltros(authentication,departamentoId, municipioId,centroSaludId,null,
            		null,null,EstadoDiagnosticoEnum.SOSPECHOSO, enfermedadId);
        	Integer positivos =this.diagnosticoService.cantidadDiagnosticoPorFiltros(authentication, departamentoId, municipioId,centroSaludId,null,
            		null,null,EstadoDiagnosticoEnum.POSITIVO, enfermedadId);
        	Integer recuperados =this.diagnosticoService.cantidadDiagnosticoPorFiltros(authentication,departamentoId, municipioId,centroSaludId,null,
            		null,null,EstadoDiagnosticoEnum.RECUPERADO, enfermedadId);
        	Integer fallecidos =this.diagnosticoService.cantidadDiagnosticoPorFiltros(authentication, departamentoId, municipioId,centroSaludId,null,
            		null,null,EstadoDiagnosticoEnum.DECESO, enfermedadId);
        	Integer negativos =this.diagnosticoService.cantidadDiagnosticoPorFiltros(authentication, departamentoId, municipioId,centroSaludId,null,
            		null,null,EstadoDiagnosticoEnum.DESCARTADO, enfermedadId);
        	
        	TotalResponse totalResponse = new TotalResponse(sospechosos,negativos,positivos,recuperados,fallecidos,positivos+recuperados+fallecidos);
            return ok(totalResponse);
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al contabilizar los diagnosticos: Causa. {}",e.getMessage());
            return CustomErrorType.badRequest("Contabilizar Diagnosticos", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al contabilizar los diagnosticos:",e);
            return CustomErrorType.serverError("Contabilizar Diagnosticos", "Se genero un error al contabilizar los diagnosticos");
        }
    }
    @RequestMapping(value = "/listar-por-valoracion",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Agrupar los diagnosticos por valoración", description = "Agrupar los diagnosticos por valoración", tags = { "grupos de diagnosticos por valoración" })
    public ResponseEntity<NivelValoracionListDto> listarPorNivelValoracion(
    		@Parameter(description = "Fecha inicio para el filtro", required = true)
            @RequestParam("from") @DateTimeFormat(pattern = DateUtil.FORMAT_DATE_PARAM_URL) Date from,
            @Parameter(description = "Fecha fin para el filtro", required = true)
            @RequestParam("to") @DateTimeFormat(pattern = DateUtil.FORMAT_DATE_PARAM_URL) Date to) {
        try {
            return ok(new NivelValoracionListDto(this.diagnosticoService.listarPorNivelValoracion(from, to)));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al agrupar los diagnosticos: Causa. {}",e.getMessage());
            return CustomErrorType.badRequest("Agrupar Diagnosticos", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al agrupar los diagnosticos:",e);
            return CustomErrorType.serverError("Agrupar Diagnosticos", "Se genero un error al agrupar los diagnosticos");
        }
    }

    @RequestMapping(value = "/{diagnosticoId}/sintomas",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtiene los síntomas del diagnostico", description = "Obtiene los síntomas del diagnostico ", tags = { "diagnostico" })
    public ResponseEntity<List<DiagnosticoSintomaResponse>> getSintomas(
            @Parameter(description = "Identificador de Diagnostico", required = true)
            @PathVariable("diagnosticoId") Long diagnosticoId) {
        try {
            return ok(this.diagnosticoService.listarSintomas(diagnosticoId));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al obtener los sintomas del diagnostico {}. Causa. {} ", diagnosticoId, e.getMessage());
            return CustomErrorType.badRequest("Obtener Sintomas", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al obtener los sintomas del diagnostico {}",diagnosticoId,e);
            return CustomErrorType.serverError("Obtener Sintomas", "Se genero un error al obtener los síntomas del diagnostico");
        }
    }
    
    @RequestMapping(value = "/listar-por-estado-diagnostico",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Agrupar los diagnosticos por estado", description = "Agrupar los diagnosticos por estado", tags = { "grupos de diagnosticos por estado" })
    public ResponseEntity<GraficoLineaResponse> listarPorEstadoDiagnostico(
    		@AuthenticationPrincipal Authentication authentication,
    		@Parameter(description = "Fecha inicio para el filtro", required = true)
            @RequestParam("from") @DateTimeFormat(pattern = DateUtil.FORMAT_DATE_PARAM_URL) Date from,
            @Parameter(description = "Fecha fin para el filtro", required = true)
            @RequestParam("to") @DateTimeFormat(pattern = DateUtil.FORMAT_DATE_PARAM_URL) Date to,
            @Parameter(description = "Identificador de Departamento", required = true)
    		@RequestParam("departamentoId") Long departamentoId,
            @Parameter(description = "Identificador de Municipio", required = true)
            @RequestParam("municipioId") Long municipioId,
            @Parameter(description = "Identificador de Centro Salud", required = true)
            @RequestParam("centroSaludId") Long centroSaludId,
            @Parameter(description = "Identificador de Enfermedad", required = true)
            @RequestParam("enfermedadId") Long enfermedadId) {
        try {
        	GraficoLineaResponse response = new GraficoLineaResponse();
        	List<ResumenDto> resumenDtos= this.diagnosticoResumenEstadoService.cantidadDiagnosticoPorFiltros(authentication, from, to, departamentoId, municipioId, centroSaludId, enfermedadId);
        	
        	for (ResumenDto resumenDto : resumenDtos) {
				response.add(DateUtil.toString(DateUtil.FORMAT_DATE, resumenDto.getNombreGrafico()), resumenDto.getSospechoso(), resumenDto.getDescartado(),resumenDto.getPositivo(),resumenDto.getRecuperado(),resumenDto.getDeceso());
			}
            return ok(response);
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al listar por estado los diagnosticos: Causa. {}",e.getMessage());
            return CustomErrorType.badRequest("Listar por estado Diagnosticos", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al agrupar los diagnosticos:",e);
            return CustomErrorType.serverError("Listar por estado Diagnosticos", "Se genero un error al listar por estado los diagnosticos");
        }
    }
    
    @RequestMapping(value = "/listar-total-lugar",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Agrupar los diagnosticos por estado y lugar", description = "Agrupar los diagnosticos por estado y lugar", tags = { "grupos de diagnosticos por estado y lugar" })
    public ResponseEntity<TablaResponse> listarTotalPorLugar(
    		@AuthenticationPrincipal Authentication authentication,
            @Parameter(description = "Identificador de Departamento", required = true)
    		@RequestParam("departamentoId") Long departamentoId,
            @Parameter(description = "Identificador de Municipio", required = true)
            @RequestParam("municipioId") Long municipioId,
            @Parameter(description = "Identificador de Centro Salud", required = true)
            @RequestParam("centroSaludId") Long centroSaludId,
            @Parameter(description = "Identificador de Enfermedad", required = true)
            @RequestParam("enfermedadId") Long enfermedadId) {
        try {
            return ok(this.diagnosticoResumenTotalEstadoService.cantidadDiagnosticoPorLugar(authentication, new Date(), new Date(), departamentoId, municipioId, centroSaludId, enfermedadId));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al listar por estado los diagnosticos: Causa. {}",e.getMessage());
            return CustomErrorType.badRequest("Listar por estado Diagnosticos", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al agrupar los diagnosticos:",e);
            return CustomErrorType.serverError("Listar por estado Diagnosticos", "Se genero un error al listar por estado los diagnosticos");
        }
    }


    @RequestMapping(value = "/listar-mapa",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Agrupar los diagnosticos por estado y lugar", description = "Agrupar los diagnosticos por estado y lugar", tags = { "grupos de diagnosticos por estado y lugar" })
    public ResponseEntity<List<MapResponse>> listarMapa(
            @AuthenticationPrincipal Authentication authentication,
            @Parameter(description = "Fecha inicio para el filtro", required = true)
            @RequestParam("from") @DateTimeFormat(pattern = DateUtil.FORMAT_DATE_PARAM_URL) Date from,
            @Parameter(description = "Fecha fin para el filtro", required = true)
            @RequestParam("to") @DateTimeFormat(pattern = DateUtil.FORMAT_DATE_PARAM_URL) Date to,
            @Parameter(description = "Identificador de Departamento", required = true)
            @RequestParam("departamentoId") Long departamentoId,
            @Parameter(description = "Identificador de Municipio", required = true)
            @RequestParam("municipioId") Long municipioId,
            @Parameter(description = "Identificador de Centro Salud", required = true)
            @RequestParam("centroSaludId") Long centroSaludId,
            @Parameter(description = "Identificador de Enfermedad", required = true)
            @RequestParam("enfermedadId") Long enfermedadId,
            @Parameter(description = "Clasificación", required = true)
            @RequestParam("clasificacion") EstadoDiagnosticoEnum estadoDiagnostico) {
        try {
            return ok(this.diagnosticoService.listarPacientesPor(authentication, from, to, departamentoId, municipioId, centroSaludId, enfermedadId,estadoDiagnostico));
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al listar por estado los diagnosticos: Causa. {}",e.getMessage());
            return CustomErrorType.badRequest("Listar por estado Diagnosticos", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al agrupar los diagnosticos:",e);
            return CustomErrorType.serverError("Listar por estado Diagnosticos", "Se genero un error al listar por estado los diagnosticos");
        }
    }
    
    @RequestMapping(value = "/listar-total-por-estado-diagnostico",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Agrupar los diagnosticos por estado", description = "Agrupar los diagnosticos por estado", tags = { "grupos de diagnosticos por estado" })
    public ResponseEntity<GraficoLineaResponse> listarTotalPorEstadoDiagnostico(
    		@AuthenticationPrincipal Authentication authentication,
    		@Parameter(description = "Fecha inicio para el filtro", required = true)
            @RequestParam("from") @DateTimeFormat(pattern = DateUtil.FORMAT_DATE_PARAM_URL) Date from,
            @Parameter(description = "Fecha fin para el filtro", required = true)
            @RequestParam("to") @DateTimeFormat(pattern = DateUtil.FORMAT_DATE_PARAM_URL) Date to,
            @Parameter(description = "Identificador de Departamento", required = true)
    		@RequestParam("departamentoId") Long departamentoId,
            @Parameter(description = "Identificador de Municipio", required = true)
            @RequestParam("municipioId") Long municipioId,
            @Parameter(description = "Identificador de Centro Salud", required = true)
            @RequestParam("centroSaludId") Long centroSaludId,
            @Parameter(description = "Identificador de Enfermedad", required = true)
            @RequestParam("enfermedadId") Long enfermedadId) {
        try {
        	GraficoLineaResponse response = new GraficoLineaResponse();
        	List<ResumenDto> resumenDtos= this.diagnosticoResumenTotalEstadoService.cantidadDiagnosticoPorFiltros(authentication, from, to, departamentoId, municipioId, centroSaludId, enfermedadId);
        	
        	for (ResumenDto resumenDto : resumenDtos) {
				response.add(DateUtil.toString(DateUtil.FORMAT_DATE, resumenDto.getNombreGrafico()), resumenDto.getSospechoso(), resumenDto.getDescartado(),resumenDto.getPositivo(),resumenDto.getRecuperado(),resumenDto.getDeceso());
			}
            return ok(response);
        }catch (NotDataFoundException | OperationException e){
            log.error("Se genero un error al listar por estado los diagnosticos: Causa. {}",e.getMessage());
            return CustomErrorType.badRequest("Listar por estado Diagnosticos", e.getMessage());
        }catch (Exception e){
            log.error("Se genero un error al agrupar los diagnosticos:",e);
            return CustomErrorType.serverError("Listar por estado Diagnosticos", "Se genero un error al listar por estado los diagnosticos");
        }
    }

    @RequestMapping(value = "/{diagnosticoId}/actualizar-diagnostico",method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes =  MediaType.APPLICATION_JSON_VALUE )
    @Operation(summary = "Actualiza el estado de un diagnóstico", description = "Actualiza el estado de un diagnóstico, este método sólo es permitido para médicos", tags = { "diagnostico" })
    public ResponseEntity<DiagnosticoDto> actualizarDiagnostico(
            @AuthenticationPrincipal Authentication authentication,
            @PathVariable("diagnosticoId") Long diagnosticoId,
            @RequestBody ActualizacionDiagnosticoRequest diagnosticoRequest){
        try{
            DiagnosticoDto diagnosticoDto = this.diagnosticoService.actualizarDiagnostico(authentication, diagnosticoId, diagnosticoRequest.getEstadoDiagnostico(), diagnosticoRequest.getObservacion());
            return ok(diagnosticoDto);
        }catch (NotDataFoundException | OperationException e){
            log.error("No se logro actualizar diagnostico con id {}. Causa. {}", diagnosticoId, e.getMessage());
            return CustomErrorType.badRequest("Actualizar Diagnóstico",e.getMessage());
        }catch (Exception e){
            log.error("No se logro actualizar diagnostico con id {}", diagnosticoId, e);
            return CustomErrorType.badRequest("Actualizar Diagnóstico","No se logro actualizar diagnóstico");
        }
    }

}
