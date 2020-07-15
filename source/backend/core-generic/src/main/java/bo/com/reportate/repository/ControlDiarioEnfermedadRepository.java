package bo.com.reportate.repository;

import bo.com.reportate.model.ControlDiario;
import bo.com.reportate.model.ControlDiarioEnfermedad;
import bo.com.reportate.model.Enfermedad;
import bo.com.reportate.model.Paciente;
import bo.com.reportate.model.dto.response.EnfermedadResponse;
import bo.com.reportate.model.enums.EstadoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Created by :Reportate
 * @Autor :Ricardo Laredo
 * @Email :rllayus@gmail.com
 * @Date :2020-04-01
 * @Project :reportate
 * @Package :bo.com.reportate.repository
 * @Copyright :Reportate
 */
public interface ControlDiarioEnfermedadRepository extends JpaRepository<ControlDiarioEnfermedad, Long> {
    @Query("SELECT new bo.com.reportate.model.dto.response.EnfermedadResponse(cde.enfermedad) " +
            "FROM ControlDiarioEnfermedad cde " +
            "WHERE cde.controlDiario IN " +
            "  (SELECT cd" +
            "   FROM ControlDiario cd INNER JOIN cd.paciente p " +
            "   WHERE p=:paciente " +
            "   AND p.estado =bo.com.reportate.model.enums.EstadoEnum.ACTIVO " +
            "   AND cd.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO) " +
            " AND cde.estado = bo.com.reportate.model.enums.EstadoEnum.ACTIVO")
    List<EnfermedadResponse> listarEnfermedadesByPaciente(@Param("paciente") Paciente paciente);

    @Query("SELECT count(cde) FROM ControlDiarioEnfermedad cde INNER JOIN cde.controlDiario cd INNER JOIN cd.paciente p " +
            "WHERE p=:paciente")
    int cantidadEnfermedadBase(@Param("paciente") Paciente paciente);

    boolean existsByControlDiarioPacienteAndEstado(Paciente paciente, EstadoEnum estadoEnum);

    boolean existsByControlDiarioAndEnfermedadAndEstado(ControlDiario controlDiario, Enfermedad enfermedad, EstadoEnum estadoEnum);

    @Modifying
    @Query( " UPDATE ControlDiarioEnfermedad  cde " +
            "SET cde.estado = bo.com.reportate.model.enums.EstadoEnum.ELIMINADO WHERE cde.enfermedad =:enfermedad AND cde.controlDiario =:controlDiario ")
    void eliminarEnfermeda(@Param("enfermedad") Enfermedad enfermedad, @Param("controlDiario") ControlDiario controlDiario);

}
