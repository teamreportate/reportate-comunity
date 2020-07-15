/**
 * Reportate
 * Santa Cruz - Bolivia
 * project:
 * package:
 * date:    26-01-19
 * author:  fmontero
 **/
export class Constants {
  static groupApi = 'api/grupos';
  static roleApi = 'api/roles';
  static resourceApi = 'api/recursos';
  static parameterApi = 'api/parametros';
  static domainApi = 'api/dominios';
  static logApi = 'api/logs';
  static plannerApi = 'api/planificador-masivo';
  static plannerOfflineApi = 'api/planificador-offline';
  static externalIntegration = 'api/integracion-externa';
  static fiscalDocumentApi = 'api/documentosFiscales';
  static healthSystemApi = 'api/saludSistema';
  static jobApi = 'scheduler/';
  static alarmApi = 'api/alarmas';
  static LOGIN_PATH = '/authentication/login';
  static paisApi = 'api/pais';
  static sintomasApi = 'api/sintoma';
  static enfermedadApi = 'api/enfermedades';
  static pacienteApi = 'api/pacientes';
  static departmentApi = 'api/departamentos';
  static diagnosticoApi = 'api/diagnosticos';
  static ocupaciones = 'api/dominios';
  static userApi = 'api/usuarios';
  static departamentosApi = 'api/departamentos';
  static municipiosApi = 'api/municipios';
  static centrosApi = 'api/centros-de-salud';
  static listDiagnostico = 'api/departamentos/departamento-municipio-centro-salud-asignados';
  static reporteApi = 'api/reportes/';

// CLASIFICACION ENFERMEDAD
  static CLASIFICACION_ENFERMEDAD = [
    {key: 'TODOS', value: 'Todos'},
    {key: 'SOSPECHOSO', value: 'SOSPECHOSO'},
    {key: 'DESCARTADO', value: 'DESCARTADO'},
    {key: 'POSITIVO', value: 'POSITIVO'},
    {key: 'RECUPERADO', value: 'RECUPERADO'},
    {key: 'DECESO', value: 'FALLECIDO'}];
  static SOS: 'SOSPECHOSO';
  static NEG: 'NEGATIVO';
  static CONF: 'CONFIRMADO';
  static CLASIFICACION_ENFERMEDAD2 = [
    {key: 'SOSPECHOSO', value: 'SOSPECHOSO'},
    {key: 'DESCARTADO', value: 'DESCARTADO'},
    {key: 'POSITIVO', value: 'POSITIVO'},
    {key: 'RECUPERADO', value: 'RECUPERADO'},
    {key: 'DECESO', value: 'FALLECIDO'}];

  static SEXOS = [{key: 'MASCULINO', value: 'MASCULINO'},
    {key: 'FEMENINO', value: 'FEMENINO'}];

  static COLOR_CONFIRMADO ='#FF0000';
  static COLOR_ACTIVO = '#FFA500';
  static COLOR_SOSPECHOSO = '#FFFF00';
  static COLOR_RECUPERADO = '#008000';
  static COLOR_DECESO = '#000000';
  static COLOR_DESCARTADO = '#4682B4'
}

