package bo.com.reportate.model.enums;

/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.model.enums
 * date:    08-08-19
 * author:  fmontero
 **/
public enum EstadoGrupo {
    ACTIVO("ACTIVO"), BLOQUEADO("BLOQUEADO");
    private String estado;
    EstadoGrupo(String estado){
        this.estado = estado;
    }
    @Override
    public String toString() {
        return estado;
    }
}
