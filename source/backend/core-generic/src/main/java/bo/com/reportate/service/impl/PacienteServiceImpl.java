package bo.com.reportate.service.impl;

import bo.com.reportate.cache.CacheService;
import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.*;
import bo.com.reportate.model.dto.DiagnosticoDto;
import bo.com.reportate.model.dto.PacienteDto;
import bo.com.reportate.model.dto.PaisVisitadoDto;
import bo.com.reportate.model.dto.request.EnfermedadRequest;
import bo.com.reportate.model.dto.request.PaisRequest;
import bo.com.reportate.model.dto.request.SintomaRequest;
import bo.com.reportate.model.dto.response.EnfermedadResponse;
import bo.com.reportate.model.dto.response.FichaEpidemiologicaResponse;
import bo.com.reportate.model.dto.response.MovilControlDiario;
import bo.com.reportate.model.dto.response.SintomaResponse;
import bo.com.reportate.model.enums.EstadoDiagnosticoEnum;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.model.enums.GeneroEnum;
import bo.com.reportate.repository.*;
import bo.com.reportate.service.LogService;
import bo.com.reportate.service.NotificacionService;
import bo.com.reportate.service.PacienteService;
import bo.com.reportate.util.ValidationUtil;
import bo.com.reportate.utils.DateUtil;
import bo.com.reportate.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Created by :MC4
 * @Autor :Ricardo Laredo
 * @Email :rlaredo@mc4.com.bo
 * @Date :2020-04-02
 * @Project :reportate
 * @Package :bo.com.reportate.service.impl
 * @Copyright :MC4
 */
@Service
@Slf4j
public class PacienteServiceImpl implements PacienteService {
    @Autowired
    private FamiliaRepository familiaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private LogService logService;
    @Autowired
    private ControlDiarioRepository controlDiarioRepository;
    @Autowired
    private ControlDiarioSintomaRepository controlDiarioSintomaRepository;
    @Autowired
    private ControlDiarioPaisRepository controlDiarioPaisRepository;
    @Autowired
    private ControlDiarioEnfermedadRepository controlDiarioEnfermedadRepository;
    @Autowired
    private SintomaRepository sintomaRepository;
    @Autowired
    private PaisRepository paisRepository;
    @Autowired
    private EnfermedadRepository enfermedadRepository;
    @Autowired
    private DiagnosticoRepository diagnosticoRepository;
    @Autowired
    private MatrizDiagnosticoRepository matrizDiagnosticoRepository;
    @Autowired
    private NotificacionService notificacionService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private DiagnosticoSintomaRepository diagnosticoSintomaRepository;
    @Autowired
    private CacheService cacheService;

    @Override
    public PacienteDto save(Authentication userDetails, String nombre, Integer edad, GeneroEnum genero, Boolean gestacion, Integer tiempoGestacion, String ocupacion) {
        ValidationUtil.throwExceptionIfInvalidText("nombre", nombre, true, 100);
        ValidationUtil.throwExceptionIfInvalidNumber("edad", edad, true, -1, 120);
        ValidationUtil.throwExceptionIfInvalidNumber("tiempo de gestación", tiempoGestacion, false, -1, 41);
        ValidationUtil.throwExceptionIfInvalidText("ocupación", ocupacion, false, 50);
        MuUsuario user = (MuUsuario) userDetails.getPrincipal();
        Familia familia = this.familiaRepository.findFirstByUsuarioIdAndEstadoOrderByIdDesc(user.getId(), EstadoEnum.ACTIVO).orElseThrow(() -> new OperationException("No existe ningún registro de familia para el usuario"));

        if (this.pacienteRepository.existsByFamiliaAndNombreIgnoreCaseAndEstado(familia, nombre, EstadoEnum.ACTIVO)) {
            throw new OperationException("Ya existe un miembro de tu familia con el nombre: " + nombre);
        }
        Paciente paciente = Paciente.builder()
                .nombre(nombre)
                .edad(edad)
                .genero(genero)
                .gestacion(gestacion)
                .tiempoGestacion(tiempoGestacion)
                .ocupacion(ocupacion)
                .familia(familia)
                .build();
        this.pacienteRepository.save(paciente);
        PacienteDto pacienteDto = new PacienteDto();
        BeanUtils.copyProperties(paciente, pacienteDto);
        return pacienteDto;
    }

    @Override
    public PacienteDto agregarContacto(
            Long pacienteId, String nombre, Integer edad, GeneroEnum genero, Boolean gestacion, Integer tiempoGestacion,
            String ocupacion, String ci, String fechaNacimiento, String seguro, String codigoSeguro) {
        ValidationUtil.throwExceptionIfInvalidText("nombre", nombre, true, 100);
        ValidationUtil.throwExceptionIfInvalidNumber("edad", edad, true, -1, 120);
        ValidationUtil.throwExceptionIfInvalidNumber("tiempo de gestación", tiempoGestacion, false, -1, 41);
        ValidationUtil.throwExceptionIfInvalidText("ocupación", ocupacion, false, 50);
        ValidationUtil.throwExceptionIfInvalidText("ci", ci, false, 20);
        ValidationUtil.throwExceptionIfInvalidText("fechaNamiento", fechaNacimiento, false, 10);
        Date fechNacimient = null;
        if (!StringUtil.isEmptyOrNull(fechaNacimiento)) {
            fechNacimient = DateUtil.toDate(DateUtil.FORMAT_DATE, fechaNacimiento);
            if (fechNacimient == null) {
                throw new OperationException("No se logró convertir a formato dd/mm/yyyy la fecha: " + fechaNacimiento);
            }
        }
        ValidationUtil.throwExceptionIfInvalidText("seguro", seguro, false, 50);
        ValidationUtil.throwExceptionIfInvalidText("codigoSeguro", codigoSeguro, false, 30);
        Paciente paciente = this.pacienteRepository.findByIdAndEstado(pacienteId, EstadoEnum.ACTIVO).orElseThrow(() -> new NotDataFoundException("No se encontro el paciente"));


        if (this.pacienteRepository.existsByFamiliaAndNombreIgnoreCaseAndEstado(paciente.getFamilia(), nombre, EstadoEnum.ACTIVO)) {
            throw new OperationException("Ya existe un miembro de tu familia con el nombre: " + nombre);
        }
        Paciente contacto = Paciente.builder()
                .nombre(nombre)
                .edad(edad)
                .genero(genero)
                .gestacion(gestacion)
                .tiempoGestacion(tiempoGestacion)
                .ocupacion(ocupacion)
                .fechaNacimiento(fechNacimient)
                .ci(ci)
                .seguro(seguro)
                .codigoSeguro(codigoSeguro)
                .familia(paciente.getFamilia())
                .build();
        this.pacienteRepository.save(contacto);
        PacienteDto pacienteDto = new PacienteDto();
        BeanUtils.copyProperties(contacto, pacienteDto);
        return pacienteDto;
    }

    @Override
    public PacienteDto update(Authentication userDetails, Long id, String nombre, Integer edad, GeneroEnum genero, Boolean gestacion, Integer tiempoGestacion, String ocupacion, String ci, String fechaNacimiento, String seguro, String codigoSeguro) {
        ValidationUtil.throwExceptionIfInvalidNumber("pacienteId", id, true, 0L);
        ValidationUtil.throwExceptionIfInvalidText("nombre", nombre, true, 100);
        ValidationUtil.throwExceptionIfInvalidNumber("edad", edad, true, -1, 120);
        ValidationUtil.throwExceptionIfInvalidNumber("tiempo de gestación", tiempoGestacion, false, -1, 41);

        ValidationUtil.throwExceptionIfInvalidText("ocupación", ocupacion, false, 50);
        ValidationUtil.throwExceptionIfInvalidText("ci", ci, false, 20);
        ValidationUtil.throwExceptionIfInvalidText("fechaNamiento", fechaNacimiento, false, 10);
        Date fechNacimient = null;
        if (!StringUtil.isEmptyOrNull(fechaNacimiento)) {
            fechNacimient = DateUtil.toDate(DateUtil.FORMAT_DATE, fechaNacimiento);
            if (fechNacimient == null) {
                throw new OperationException("No se logró convertir a formato dd/mm/yyyy la fecha: " + fechaNacimiento);
            }
        }
        ValidationUtil.throwExceptionIfInvalidText("seguro", seguro, false, 50);
        ValidationUtil.throwExceptionIfInvalidText("codigoSeguro", codigoSeguro, false, 30);
        MuUsuario user = (MuUsuario) userDetails.getPrincipal();
        Familia familia = this.familiaRepository.findFirstByUsuarioIdAndEstadoOrderByIdDesc(user.getId(), EstadoEnum.ACTIVO).orElseThrow(() -> new OperationException("No existe ningún registro de familia para el usuario"));

        if (this.pacienteRepository.existsByFamiliaAndIdNotAndNombreIgnoreCaseAndEstado(familia, id, nombre, EstadoEnum.ACTIVO)) {
            throw new OperationException("Ya existe un miembro de tu familia con el nombre: " + nombre);
        }
        Paciente paciente = this.pacienteRepository.findByIdAndEstado(id, EstadoEnum.ACTIVO).orElseThrow(() -> new NotDataFoundException("No existe ningún paciente registrado con el id: " + id));
        paciente.setNombre(nombre);
        paciente.setEdad(edad);
        paciente.setGenero(genero);
        paciente.setGestacion(gestacion);
        paciente.setTiempoGestacion(tiempoGestacion);
        paciente.setOcupacion(ocupacion);
        paciente.setCi(ci);
        paciente.setFechaNacimiento(fechNacimient);
        paciente.setSeguro(seguro);
        paciente.setCodigoSeguro(codigoSeguro);
        this.pacienteRepository.save(paciente);
        PacienteDto pacienteDto = new PacienteDto();
        BeanUtils.copyProperties(paciente, pacienteDto);
        return pacienteDto;
    }

    @Override
    public PacienteDto update(Long id, String nombre, Integer edad, GeneroEnum genero, Boolean gestacion, Integer tiempoGestacion, String ocupacion, String ci, String fechaNacimiento, String seguro, String codigoSeguro) {
        ValidationUtil.throwExceptionIfInvalidNumber("pacienteId", id, true, 0L);
        ValidationUtil.throwExceptionIfInvalidText("nombre", nombre, true, 100);
        ValidationUtil.throwExceptionIfInvalidNumber("edad", edad, true, -1, 120);
        ValidationUtil.throwExceptionIfInvalidNumber("tiempo de gestación", tiempoGestacion, false, -1, 41);

        ValidationUtil.throwExceptionIfInvalidText("ocupación", ocupacion, false, 50);
        ValidationUtil.throwExceptionIfInvalidText("ci", ci, false, 20);
        ValidationUtil.throwExceptionIfInvalidText("fechaNamiento", fechaNacimiento, false, 10);
        Date fechNacimient = null;
        if (StringUtil.isEmptyOrNull(fechaNacimiento)) {
            fechNacimient = DateUtil.toDate(DateUtil.FORMAT_DATE, fechaNacimiento);
            if (fechNacimient == null) {
                throw new OperationException("No se logró convertir a formato dd/mm/yyyy la fecha: " + fechaNacimiento);
            }
        }
        ValidationUtil.throwExceptionIfInvalidText("seguro", seguro, false, 50);
        ValidationUtil.throwExceptionIfInvalidText("codigoSeguro", codigoSeguro, false, 30);

        Familia familia = this.familiaRepository.getFamilia(id).orElseThrow(() -> new OperationException("No existe ningún registro de familia para el usuario"));

        if (this.pacienteRepository.existsByFamiliaAndIdNotAndNombreIgnoreCaseAndEstado(familia, id, nombre, EstadoEnum.ACTIVO)) {
            throw new OperationException("Ya existe un miembro de tu familia con el nombre: " + nombre);
        }
        Paciente paciente = this.pacienteRepository.findByIdAndEstado(id, EstadoEnum.ACTIVO).orElseThrow(() -> new NotDataFoundException("No existe ningún paciente registrado con el id: " + id));
        paciente.setNombre(nombre);
        paciente.setEdad(edad);
        paciente.setGenero(genero);
        paciente.setGestacion(gestacion);
        paciente.setTiempoGestacion(tiempoGestacion);
        paciente.setOcupacion(ocupacion);
        paciente.setCi(ci);
        paciente.setFechaNacimiento(fechNacimient);
        paciente.setSeguro(seguro);
        paciente.setCodigoSeguro(codigoSeguro);
        this.pacienteRepository.save(paciente);
        PacienteDto pacienteDto = new PacienteDto();
        BeanUtils.copyProperties(paciente, pacienteDto);
        return pacienteDto;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String controlDiario(Long pacienteId, List<EnfermedadRequest> enfermedadesBase, List<PaisRequest> paisesVisitados, List<SintomaRequest> sintomas) {
        log.info("Inician el registro del control diario.");
        ValidationUtil.throwExceptionIfInvalidNumber("paciente", pacienteId, true, 0L);
        Paciente paciente = this.pacienteRepository.findByIdAndEstado(pacienteId, EstadoEnum.ACTIVO).orElseThrow(() -> new NotDataFoundException("No existe el paciente registrado"));

        Integer cantidadControles = cacheService.getIntegerParam(Constants.Parameters.CANTIDAD_MAXIMA_CONTROL);
        if(paciente.getCantidadControles() != null){
            cantidadControles = paciente.getCantidadControles();
        }
        if(this.controlDiarioRepository.countByPacienteAndCreatedDateBetween(paciente,
                DateUtil.formatToStart(new Date()),DateUtil.formatToEnd(new Date())) >= cantidadControles){
            log.error("la cantidad máxima de controles es {}",cantidadControles);
            return cacheService.getStringParam(Constants.Parameters.MENSAJE_CANTIDAD_MAXIMA_REGISTRO).replace("${CANTIDAD}",String.valueOf(cantidadControles));
        }

        ControlDiario controlDiario = ControlDiario.builder()
                .paciente(paciente)
                .primerControl(!this.controlDiarioRepository.existsByPrimerControlTrueAndPaciente(paciente))
                .build();


        if (sintomas == null || sintomas.isEmpty()) {
            log.error("No existe sintomas para registrar en el control diario");
            controlDiario.setRecomendacion(cacheService.getStringParam(Constants.Parameters.MENSAJE_SIN_SINTOMAS));
            this.controlDiarioRepository.save(controlDiario);
            return controlDiario.getRecomendacion();
        }

        this.controlDiarioRepository.saveAndFlush(controlDiario);

        log.info("Registrando sintomas...");
        List<Sintoma> sintomasRecibidos = new ArrayList<>();
        for (SintomaRequest sintAux : sintomas) {
            ValidationUtil.throwExceptionIfInvalidNumber("sintoma", sintAux.getId(), true, 0L);
            Sintoma sintoma = this.sintomaRepository.findByIdAndEstado(sintAux.getId(), EstadoEnum.ACTIVO).orElseThrow(() -> new NotDataFoundException("No se encontro el sintoma que quiere registrar"));
            this.controlDiarioSintomaRepository.save(ControlDiarioSintoma.builder()
                    .controlDiario(controlDiario)
                    .respuesta(sintAux.getRespuesta())
                    .observacion(sintAux.getObservacion())
                    .sintoma(sintoma).build());
            sintomasRecibidos.add(sintoma);
        }


        if (enfermedadesBase != null && !enfermedadesBase.isEmpty()) {
            log.info("Registrando enfermedades..");
            for (EnfermedadRequest enferAux : enfermedadesBase) {
                ValidationUtil.throwExceptionIfInvalidNumber("enfermedad", enferAux.getId(), true, 0L);
                Enfermedad enfermedad = this.enfermedadRepository.findByIdAndEstado(enferAux.getId(), EstadoEnum.ACTIVO).orElseThrow(() -> new NotDataFoundException("No se encontro la enfermedad que quiere reportar"));
                this.controlDiarioEnfermedadRepository.save(ControlDiarioEnfermedad.builder().controlDiario(controlDiario).enfermedad(enfermedad).build());
            }
        }

        if (paisesVisitados != null && !paisesVisitados.isEmpty()) {
            log.info("Registrando paises..");
            for (PaisRequest paisAux : paisesVisitados) {
                ValidationUtil.throwExceptionIfInvalidNumber("pais", paisAux.getId(), true, 0L);
                Pais pais = this.paisRepository.findByIdAndEstado(paisAux.getId(), EstadoEnum.ACTIVO).orElseThrow(() -> new NotDataFoundException("No se encontro el pais que quiere reportar"));
                this.controlDiarioPaisRepository.save(ControlDiarioPais.builder().controlDiario(controlDiario).pais(pais).build());
            }
        }

        log.info("Calculando diagnostico ...");
        List<Enfermedad> enfermedades = this.matrizDiagnosticoRepository.listarEnfermedades();
        for (Enfermedad enfermedad : enfermedades) {
            List<MatrizDiagnostico> matrizDiagnosticos = this.matrizDiagnosticoRepository.findByEnfermedadAndEstado(enfermedad, EstadoEnum.ACTIVO);
            List<String> sintomasMail = new ArrayList<>();
            List<DiagnosticoSintoma> sintomasDignostico = new ArrayList<>();

            Diagnostico diagnostico = Diagnostico.builder().controlDiario(controlDiario).enfermedad(enfermedad).departamento(paciente.getFamilia().getDepartamento()).municipio(paciente.getFamilia().getMunicipio()).centroSalud(paciente.getFamilia().getCentroSalud()).build();
            BigDecimal valoracion = BigDecimal.ZERO;
            for (MatrizDiagnostico matrizDiagnostico : matrizDiagnosticos) {
                if (sintomasRecibidos.contains(matrizDiagnostico.getSintoma())) {
                    valoracion = valoracion.add(matrizDiagnostico.getPeso());
                    sintomasMail.add(matrizDiagnostico.getSintoma().getNombre());
                    sintomasDignostico.add(DiagnosticoSintoma.builder().diagnostico(diagnostico).sintoma(matrizDiagnostico.getSintoma()).valoracion(matrizDiagnostico.getPeso()).build());
                }
            }

            if (valoracion.compareTo(BigDecimal.ZERO) > 0) {// Sintomas de la enfermedad
                log.info("Resultado enfermedad:{}  del calculo:{}", enfermedad.getNombre(), valoracion);
                EstadoDiagnosticoEnum estadoDiagnostico = EstadoDiagnosticoEnum.NEGATIVO;
                if (valoracion.compareTo(cacheService.getNumberParam(Constants.Parameters.INDICADOR_SOSPECHOSO)) > 0) {
                    estadoDiagnostico = EstadoDiagnosticoEnum.SOSPECHOSO;
                    List<MuUsuario> medicos = this.usuarioRepository.obtenerMedicoPordepartamento(controlDiario.getPaciente().getFamilia().getDepartamento());
                    for (MuUsuario medico : medicos) {
                        notificacionService.notificacionSospechosoSintomas("Dr. " + medico.getNombre(), medico.getEmail(), "Caso sospechoso " + enfermedad.getNombre(), "Existe un nuevo caso sospechoso de " + enfermedad.getNombre() + " con una valoración de " + valoracion.toPlainString(), sintomasMail);
                    }
                }

                log.info("Registrando diagnostico del paciente {} con estado {}  de la enfermedad {} con {} sintomas.", paciente.getNombre(), estadoDiagnostico, enfermedad.getNombre(), sintomasDignostico.size());
                diagnostico.setResultadoValoracion(valoracion);
                //Si el diagnostico actual del paciente es CONFIRMADO ó ACTIVO el diagnostico actual será ACTIVO
                if (paciente.getDiagnostico() != null && (paciente.getDiagnostico().getEstadoDiagnostico().equals(EstadoDiagnosticoEnum.CONFIRMADO) ||
                        paciente.getDiagnostico().getEstadoDiagnostico().equals(EstadoDiagnosticoEnum.ACTIVO))) {
                    diagnostico.setEstadoDiagnostico(EstadoDiagnosticoEnum.ACTIVO);
                    diagnostico.setObservacion(cacheService.getStringParam(Constants.Parameters.MENSAJE_SINTOMAS_ACTIVO));
                    this.diagnosticoRepository.save(diagnostico);
                } else {
                    if(estadoDiagnostico.equals(EstadoDiagnosticoEnum.SOSPECHOSO)) {
                        diagnostico.setObservacion(cacheService.getStringParam(Constants.Parameters.MENSAJE_SINTOMAS_SOSPECHOSO));
                    }else{
                        diagnostico.setObservacion(cacheService.getStringParam(Constants.Parameters.MENSAJE_SINTOMAS_LEVES));
                    }
                    diagnostico.setEstadoDiagnostico(estadoDiagnostico);
                    this.diagnosticoRepository.save(diagnostico);
                }
                this.diagnosticoSintomaRepository.saveAll(sintomasDignostico);

                if (paciente.getDiagnostico() == null) {
                    paciente.setDiagnostico(diagnostico);
                    this.pacienteRepository.save(paciente);
                } else if (!(paciente.getDiagnostico().getEstadoDiagnostico().equals(EstadoDiagnosticoEnum.SOSPECHOSO) &&
                        diagnostico.getEstadoDiagnostico().equals(EstadoDiagnosticoEnum.NEGATIVO))) {
                    paciente.setDiagnostico(diagnostico);
                    this.pacienteRepository.save(paciente);

                }
            }
        }

        String recomendacion;
        if(paciente.getDiagnostico() == null){
            recomendacion = cacheService.getStringParam(Constants.Parameters.MENSAJE_SINTOMAS_LEVES);
            this.controlDiarioRepository.agregarRecomendacion(controlDiario,recomendacion);
            controlDiario.setRecomendacion(recomendacion);
            return recomendacion;
        }
        if(paciente.getDiagnostico().getEstadoDiagnostico().equals(EstadoDiagnosticoEnum.NEGATIVO)){
            recomendacion = cacheService.getStringParam(Constants.Parameters.MENSAJE_SINTOMAS_LEVES);
            this.controlDiarioRepository.agregarRecomendacion(controlDiario,recomendacion);
            controlDiario.setRecomendacion(recomendacion);
            return recomendacion;
        }
        if(paciente.getDiagnostico().getEstadoDiagnostico().equals(EstadoDiagnosticoEnum.ACTIVO)){
            recomendacion = cacheService.getStringParam(Constants.Parameters.MENSAJE_SINTOMAS_ACTIVO);
            this.controlDiarioRepository.agregarRecomendacion(controlDiario,recomendacion);
            controlDiario.setRecomendacion(recomendacion);
            return recomendacion;
        }
        if(paciente.getDiagnostico().getEstadoDiagnostico().equals(EstadoDiagnosticoEnum.SOSPECHOSO)){
            recomendacion = cacheService.getStringParam(Constants.Parameters.MENSAJE_SINTOMAS_SOSPECHOSO);
            controlDiario.setRecomendacion(recomendacion);
            this.controlDiarioRepository.agregarRecomendacion(controlDiario,recomendacion);
            return recomendacion;
        }

        recomendacion = cacheService.getStringParam(Constants.Parameters.MENSAJE_DEFECTO);
        this.controlDiarioRepository.agregarRecomendacion(controlDiario,recomendacion);
        controlDiario.setRecomendacion(recomendacion);
        return recomendacion;
    }

    @Transactional(readOnly = true)
    public FichaEpidemiologicaResponse getFichaEpidemiologica(Long pacienteId) {
        FichaEpidemiologicaResponse fichaEpidemiologicaResponse = new FichaEpidemiologicaResponse();
        log.info("iniciando busqueda del paciente");
        Paciente paciente = this.pacienteRepository.findByIdAndEstado(pacienteId, EstadoEnum.ACTIVO).orElseThrow(() -> new NotDataFoundException("No existe ningún paciente registrado con el id: " + pacienteId));
        BeanUtils.copyProperties(paciente, fichaEpidemiologicaResponse);
        fichaEpidemiologicaResponse.setDepartamento(paciente.getFamilia().getDepartamento().getNombre());
        fichaEpidemiologicaResponse.setMunicipio(paciente.getFamilia().getMunicipio().getNombre());
        fichaEpidemiologicaResponse.setZona(paciente.getFamilia().getZona());
        fichaEpidemiologicaResponse.setDireccion(paciente.getFamilia().getDireccion());
        fichaEpidemiologicaResponse.setTelefono(paciente.getFamilia().getTelefono());
        fichaEpidemiologicaResponse.setUbicacion("https://maps.google.com/?q=" + paciente.getFamilia().getLatitud() + "," + paciente.getFamilia().getLongitud());
        try {
            log.info("iniciando busqueda de paises visitados");
            List<PaisVisitadoDto> paisesViajados = this.controlDiarioPaisRepository.listarPaisesVisitados(paciente);
            fichaEpidemiologicaResponse.setPaisesVisitados(paisesViajados);

            log.info("obteniendo enfermedades base");
            List<EnfermedadResponse> enfermedadesBase = this.controlDiarioEnfermedadRepository.listarEnfermedadesByPaciente(paciente);
            fichaEpidemiologicaResponse.setEnfermedadesBase(enfermedadesBase);

            log.info("obteniendo diagnosticos");
            Pageable firstPage = PageRequest.of(0, 10);
            List<DiagnosticoDto> diagnosticos = this.diagnosticoRepository.listarDiagnosticoByPaciente(paciente, firstPage);
            fichaEpidemiologicaResponse.setDiagnosticos(diagnosticos);

            log.info("obteniendo contactos");
            List<PacienteDto> contactos = this.pacienteRepository.listarPacienteByFamilia(paciente.getFamilia(), paciente.getId());
            fichaEpidemiologicaResponse.setContactos(contactos);

        } catch (Exception e) {
            log.error("Búsqueda finalizada con errores. Cause{}", e.getMessage());
            throw new OperationException("Búsqueda finalizada con errores");
        }
        return fichaEpidemiologicaResponse;
    }

    @Override
    public EnfermedadResponse agregarEnfermedadBase(Long pacienteId, Long enfermedadId) {
        Paciente paciente = this.pacienteRepository.findByIdAndEstado(pacienteId, EstadoEnum.ACTIVO).orElseThrow(() -> new NotDataFoundException("No se encontró el paciente"));
        Enfermedad enfermedad = this.enfermedadRepository.findByIdAndEstado(enfermedadId, EstadoEnum.ACTIVO).orElseThrow(() -> new NotDataFoundException("No se encontró la enfermedad seleccionada"));
        ControlDiario controlDiario = this.controlDiarioRepository.findByPrimerControlTrueAndPacienteAndEstado(paciente, EstadoEnum.ACTIVO).orElseThrow(() -> new OperationException("No se encontró el primer control del paciente"));
        if (this.controlDiarioEnfermedadRepository.existsByControlDiarioAndEnfermedadAndEstado(controlDiario, enfermedad, EstadoEnum.ACTIVO)) {
            throw new OperationException("El paciente ya tiene agregado la enfermedad: " + enfermedad.getNombre());
        }
        this.controlDiarioEnfermedadRepository.save(ControlDiarioEnfermedad.builder().controlDiario(controlDiario).enfermedad(enfermedad).build());
        return new EnfermedadResponse(enfermedad);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarEnfermedadBase(Long pacienteId, Long enfermedadId) {
        Paciente paciente = this.pacienteRepository.findByIdAndEstado(pacienteId, EstadoEnum.ACTIVO).orElseThrow(() -> new NotDataFoundException("No se encontró el paciente"));
        Enfermedad enfermedad = this.enfermedadRepository.findByIdAndEstado(enfermedadId, EstadoEnum.ACTIVO).orElseThrow(() -> new NotDataFoundException("No se encontró la enfermedad seleccionada"));
        ControlDiario controlDiario = this.controlDiarioRepository.findByPrimerControlTrueAndPacienteAndEstado(paciente, EstadoEnum.ACTIVO).orElseThrow(() -> new OperationException("No se encontró el primer control del paciente"));
        this.controlDiarioEnfermedadRepository.eliminarEnfermeda(enfermedad, controlDiario);
    }

    @Override
    public PaisVisitadoDto agregarPais(Long pacienteId, Long paisId, Date fechaLlegada, Date fechaSalida, String ciudades) {
        ValidationUtil.throwExceptionIfInvalidText("ciudades", ciudades, true, 500);
        Paciente paciente = this.pacienteRepository.findByIdAndEstado(pacienteId, EstadoEnum.ACTIVO).orElseThrow(() -> new NotDataFoundException("No se encontró el paciente"));
        Pais pais = this.paisRepository.findByIdAndEstado(paisId, EstadoEnum.ACTIVO).orElseThrow(() -> new NotDataFoundException("No se encontro el país seleccionado"));
        ControlDiario controlDiario = this.controlDiarioRepository.findByPrimerControlTrueAndPacienteAndEstado(paciente, EstadoEnum.ACTIVO).orElseThrow(() -> new OperationException("No se encontró el primer control del paciente"));
        if (this.controlDiarioPaisRepository.existsByControlDiarioAndPaisAndEstado(controlDiario, pais, EstadoEnum.ACTIVO)) {
            throw new OperationException("El paciente ya tiene agregado el país: " + pais.getNombre());
        }
        ControlDiarioPais controlDiarioPais = ControlDiarioPais.builder()
                .pais(pais)
                .controlDiario(controlDiario)
                .fechaLlegada(fechaLlegada)
                .fechaSalida(fechaSalida)
                .ciudades(ciudades)
                .build();
        this.controlDiarioPaisRepository.save(controlDiarioPais);
        return new PaisVisitadoDto(controlDiarioPais);
    }

    @Override
    public PaisVisitadoDto editarPaisesVisitados(Long controlPaisId, Date fechaLlegada, Date fechaSalida, String ciudades) {
        ValidationUtil.throwExceptionIfInvalidText("ciudades", ciudades, true, 500);
        ControlDiarioPais controlDiarioPais = this.controlDiarioPaisRepository.findByIdAndEstado(controlPaisId, EstadoEnum.ACTIVO).orElseThrow(() -> new NotDataFoundException("No se encontro el control diario"));
        controlDiarioPais.setFechaLlegada(fechaLlegada);
        controlDiarioPais.setFechaSalida(fechaSalida);
        controlDiarioPais.setCiudades(ciudades);
        this.controlDiarioPaisRepository.save(controlDiarioPais);
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarPais(Long pacienteId, Long paisId) {
        Paciente paciente = this.pacienteRepository.findByIdAndEstado(pacienteId, EstadoEnum.ACTIVO).orElseThrow(() -> new NotDataFoundException("No se encontró el paciente"));
        Pais pais = this.paisRepository.findByIdAndEstado(paisId, EstadoEnum.ACTIVO).orElseThrow(() -> new NotDataFoundException("No se encontro el país seleccionado"));
        ControlDiario controlDiario = this.controlDiarioRepository.findByPrimerControlTrueAndPacienteAndEstado(paciente, EstadoEnum.ACTIVO).orElseThrow(() -> new OperationException("No se encontró el primer control del paciente"));
        this.controlDiarioPaisRepository.eliminarPais(pais, controlDiario);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarContacto(Long contactoId) {
        this.pacienteRepository.eliminarContacto(contactoId);

    }

    @Override
    @Transactional(readOnly = true)
    public List<MovilControlDiario> getControlDiario(Long pacienteId) {
        Paciente paciente = this.pacienteRepository.findByIdAndEstado(pacienteId,EstadoEnum.ACTIVO).orElseThrow(()-> new NotDataFoundException("No se encontró el paciente"));
        Pageable pageable = PageRequest.of(0, 10);
        List<ControlDiario> controlDiarios = this.controlDiarioRepository.listarControlDiario(paciente, pageable);
        List<MovilControlDiario> list = new ArrayList<>();
        for (ControlDiario cd: controlDiarios){
            MovilControlDiario resp = new MovilControlDiario();
            resp.setId(cd.getId());
            resp.setFechaRegistro(DateUtil.toString(DateUtil.FORMAT_DATE_MINUTE,cd.getCreatedDate()));
            resp.setRecomendacion(cd.getRecomendacion());
            List<ControlDiarioSintoma> diarioSintomaList = this.controlDiarioSintomaRepository.findByControlDiario(cd);
            List<SintomaResponse> sintomaResponses = new ArrayList<>();
            diarioSintomaList.forEach(controlDiarioSintoma ->
                    sintomaResponses.add(
                            new SintomaResponse(controlDiarioSintoma.getSintoma().getId(),controlDiarioSintoma.getSintoma().getNombre(),"",false,"")));
            resp.setSintomas(sintomaResponses);
            list.add(resp);
        }
        return list;
    }
}
