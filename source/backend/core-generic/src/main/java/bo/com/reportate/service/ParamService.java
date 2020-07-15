package bo.com.reportate.service;

import bo.com.reportate.model.MuParametro;
import bo.com.reportate.model.dto.MuGrupoParametroDto;
import bo.com.reportate.model.dto.MuParametroDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by :Reportate
 * Autor      :Ricardo Laredo
 * Email      :rllayus@gmail.com
 * Date       :12-01-19
 * Project    :reportate
 * Package    :bo.com.reportate.service
 * Copyright  : Reportate
 * <p>
 * Modified     : Jonathan Valdivia
 * Date         : 18/01/2019
 */

public interface ParamService {
   String getValue(String codigo);
   boolean getBoolean(String codigo);
   BigDecimal getNumber(String codigo);
   Date getDate(String codigo);
   int getInt(String codigo);
   void saveParametro(MuParametro muParametro);
   List<MuParametroDto> listar(Long grupoId);
   List<MuParametro> listarBusqueda(String criterioBusqueda);
   void actualizarParametro(Long parametroId, MuParametroDto muParametroDto);
   List<MuGrupoParametroDto> listaGruposParametro();
   MuParametroDto getParametro(String codigo);
   Long modificar(MuParametro muParametro, Long id);
}
