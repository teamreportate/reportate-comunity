package bo.com.reportate.service.impl;


import bo.com.reportate.exception.NotDataFoundException;
import bo.com.reportate.exception.OperationException;
import bo.com.reportate.model.MuRecurso;
import bo.com.reportate.model.MuUsuario;
import bo.com.reportate.model.dto.RecursoDto;
import bo.com.reportate.model.enums.EstadoEnum;
import bo.com.reportate.repository.RecursoRepository;
import bo.com.reportate.repository.UsuarioRepository;
import bo.com.reportate.service.RecursoService;
import bo.com.reportate.utils.FormatUtil;
import bo.com.reportate.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rfernandez on 20/12/2017.
 */
@Service
@Transactional
@Slf4j
public class RecursoServiceImpl implements RecursoService {
    @Autowired private RecursoRepository recursoRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MuRecurso> listHijosByUsuario(MuUsuario usuario) {
        try {
            return recursoRepository.listHijosByUsuario(usuario);
        } catch (Exception e) {
            throw new OperationException("Error al obtener el listado ", e);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<MuRecurso> listPadresByUsuario(MuUsuario usuario) {
        try {
            return recursoRepository.listPadresByUsuario(usuario);
        } catch (Exception e) {
            throw new OperationException("Error al obtener el listado ", e);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<RecursoDto> listMenu(String username) {
        MuUsuario muUsuario = usuarioRepository.findByEstadoAndUsername(EstadoEnum.ACTIVO, username).orElseThrow(() -> new BadCredentialsException("Username " + username + "no encontrado."));
        List<MuRecurso> listaRecursosPadres = recursoRepository.listPadresByUsuario(muUsuario);
        List<MuRecurso> listaRecursosHijos = recursoRepository.listHijosByUsuario(muUsuario);
        //Generando menu con los atributos utilizados para el response
        List<RecursoDto> listaMenu = new ArrayList<>();
        for (MuRecurso muRecursoPadre : listaRecursosPadres) {
            RecursoDto recursoPadre = new RecursoDto(muRecursoPadre);
            for (MuRecurso muRecursoHijo : listaRecursosHijos) {
                if (muRecursoPadre.getId().equals(muRecursoHijo.getIdRecursoPadre().getId())) {
                    recursoPadre.getListaRecursos().add(new RecursoDto(muRecursoHijo));
                }
            }
            listaMenu.add(recursoPadre);
        }
        return listaMenu;
    }

    @Override
    public List<RecursoDto> listar() {
        return this.recursoRepository.listAll();
    }

    @Override
    public List<RecursoDto> listarBusqueda(String criterioBusqueda) {
        if (StringUtil.isEmptyOrNull(criterioBusqueda)) {
            criterioBusqueda = "";
        }
        criterioBusqueda = "%" + criterioBusqueda + "%";
        return recursoRepository.searchAll(criterioBusqueda);
    }

    @Override
    public RecursoDto obtener(Long id) {
        return recursoRepository.obtenerPorId(id, RecursoDto.class).orElseThrow(()->new NotDataFoundException(FormatUtil.noRegistrado("AuthRecurso", id)));
    }

    @Override
    public Long crear(MuRecurso recurso) {
        log.info("Validaciones");
        if (recurso == null) {
            throw new OperationException(FormatUtil.requerido("AuthRecurso"));
        }
        if (StringUtil.isEmptyOrNull(recurso.getNombre())) {
            throw new OperationException(FormatUtil.requerido("Nombre"));
        }
        if (StringUtil.isEmptyOrNull(recurso.getUrl())) {
            throw new OperationException(FormatUtil.requerido("Url"));
        }
        if (StringUtil.isEmptyOrNull(recurso.getIcon())) {
            throw new OperationException(FormatUtil.requerido("Icon"));
        }
        if (StringUtil.isEmptyOrNull(recurso.getDescripcion())) {
            throw new OperationException(FormatUtil.requerido("Descripción"));
        }
        if (recurso.getOrdenMenu() == null) {
            throw new OperationException(FormatUtil.requerido("Orden Menú"));
        }
        List<RecursoDto> listaRegistrados = recursoRepository.listByNombre(recurso.getNombre());
        if (!listaRegistrados.isEmpty()) {
            throw new OperationException(FormatUtil.yaRegistrado("AuthRecurso", "Nombre", recurso.getNombre()));
        }
        if (recurso.getIdRecursoPadre() != null) {
            MuRecurso muRecursoPadre = recursoRepository.getOne(recurso.getIdRecursoPadre().getId());
            if (muRecursoPadre == null) {
                throw new OperationException(FormatUtil.noRegistrado("Id Recurso Padre", recurso.getIdRecursoPadre().getId()));
            }
            recurso.setIdRecursoPadre(muRecursoPadre);
        }
        log.info("Persistiendo el objeto");
        recurso.setEstado(EstadoEnum.ACTIVO);
        recursoRepository.save(recurso);
        return recurso.getId();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RecursoDto modificar(RecursoDto recursoDto, Long id) {
        log.info("Validaciones");
        if (StringUtil.isEmptyOrNull(recursoDto.getDescripcion())) {
            throw new OperationException(FormatUtil.requerido("Descripción"));
        }

        log.info("Persistiendo el objeto");
        recursoRepository.updateSave(id, recursoDto.getNombre()
                , StringUtil.trimUpperCase(recursoDto.getDescripcion())
                , recursoDto.getOrdenMenu());
        return recursoDto;
    }

    @Override
    public boolean cambiarEstado(Long id) {
        log.info("Validaciones");
        MuRecurso recursoRegistrado = recursoRepository.getOne(id);
        if (recursoRegistrado == null) {
            throw new OperationException(FormatUtil.noRegistrado("AuthRecurso", id));
        }
        log.info("Persistiendo el objeto");
        EstadoEnum estado = recursoRegistrado.getEstado() == EstadoEnum.ACTIVO ? EstadoEnum.INACTIVO : EstadoEnum.ACTIVO;
        recursoRegistrado.setEstado(estado);

        recursoRepository.save(recursoRegistrado);
        return recursoRegistrado.getEstado() == EstadoEnum.ACTIVO;
    }


}
