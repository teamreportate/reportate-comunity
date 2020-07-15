package bo.com.reportate.model.enums;

/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.model.enums
 * date:    15-08-19
 * author:  fmontero
 **/
public enum EstadoRol {
    ACTIVO("ACTIVO"), BLOQUEADO("BLOQUEADO");

    private String estado;
    EstadoRol(String estado){
        this.estado = estado;
    }

    @Override
    public String toString() {
        return estado;
    }
}
