package bo.com.reportate.repository;

import bo.com.reportate.model.MuGrupoParametro;
import bo.com.reportate.model.dto.MuGrupoParametroDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Created by    : Vinx J. Guzman Martinez.
 * Date          :18/12/2019
 * Project       :reportate
 * Package       :bo.com.mc4.reportate.repository
 **/
public interface GrupoParametroRepository extends JpaRepository<MuGrupoParametro, Long> {

    @Query( "   SELECT gp " +
            "   FROM MuGrupoParametro gp " +
            "   WHERE gp.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO ")
    List<MuGrupoParametroDto> listaGruposParametro();


    Optional<MuGrupoParametro> findByGrupo(String grupo);

}
