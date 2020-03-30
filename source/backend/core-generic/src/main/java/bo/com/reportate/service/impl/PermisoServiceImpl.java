package bo.com.reportate.service.impl;


import bo.com.reportate.model.MuPermiso;
import bo.com.reportate.model.MuRecurso;
import bo.com.reportate.model.MuRol;
import bo.com.reportate.repository.PermisoRepository;
import bo.com.reportate.repository.RolRepository;
import bo.com.reportate.service.PermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rfernandez on 20/12/2017.
 */
@Service
@Transactional
public class PermisoServiceImpl implements PermisoService {



    @Autowired
    PermisoRepository permisoRepository;
    @Autowired private RolRepository rolRepository;


    public List<MuPermiso> listarPermisos(Long idUsuario){
        List<MuPermiso> lista=permisoRepository.listByUsuario(idUsuario);
        //Recuperando solo los atributos necesarios
        List<MuPermiso> listaAux=new ArrayList<>();
        for(MuPermiso muPermiso :lista){
            MuRol muRolAux =new MuRol();
            muRolAux.setNombre(muPermiso.getIdRol().getNombre());
            muRolAux.setDescripcion(muPermiso.getIdRol().getDescripcion());

            MuRecurso muRecursoAux =new MuRecurso();
            muRecursoAux.setNombre(muPermiso.getIdRecurso().getNombre());
            muRecursoAux.setDescripcion(muPermiso.getIdRecurso().getDescripcion());
            muRecursoAux.setUrl(muPermiso.getIdRecurso().getUrl());
            muRecursoAux.setIcon(muPermiso.getIdRecurso().getIcon());
            muRecursoAux.setOrdenMenu(muPermiso.getIdRecurso().getOrdenMenu());

            MuPermiso muPermisoAux =new MuPermiso();
            muPermisoAux.setIdRol(muRolAux);
            muPermisoAux.setIdRecurso(muRecursoAux);
            muPermisoAux.setLectura(muPermiso.getLectura());
            muPermisoAux.setEscritura(muPermiso.getEscritura());
            muPermisoAux.setEliminacion(muPermiso.getEliminacion());
            muPermisoAux.setSolicitud(muPermiso.getSolicitud());
            muPermisoAux.setAutorizacion(muPermiso.getAutorizacion());
            listaAux.add(muPermisoAux);
        }
        return listaAux;
    }

}
