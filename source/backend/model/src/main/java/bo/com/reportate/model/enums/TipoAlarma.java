package bo.com.reportate.model.enums;

/**
 * Reportate SRL
 * Santa Cruz - Bolivia
 * project: reportate
 * package: bo.com.reportate.model.enums
 * date:    14-06-19
 * author:  fmontero
 **/
public enum TipoAlarma {
    CAMBIO_CONTRASENA("CAMBIO_CONTRASENA");
    private String tipoAlarma;
    TipoAlarma(String tipoAlarma) {
        this.tipoAlarma = tipoAlarma;
    }

    @Override
    public String toString() {
        return tipoAlarma;
    }
}
