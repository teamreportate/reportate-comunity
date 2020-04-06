/**
 * MC4
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
  static departmentApi = 'api/departamentos';
  static diagnosticoApi = 'api/diagnosticos';
  static userApi = 'api/usuarios';
  static departamentosApi = 'api/departamentos';
  static municipiosApi = 'api/municipios';
  static centrosApi = 'api/centros-de-salud';
  static listDiagnostico = 'api/departamentos/departamento-municipio-centro-salud';

// CLASIFICACION ENFERMEDAD
  static CLASIFICACION_ENFERMEDAD = [{key: 'TODOS', value: 'Todos'}, {key: 'SOSPECHOSO', value: 'SOSPECHOSO'},
    {key: 'NEGATIVO', value: 'NEGATIVO'}, {
    key: 'CONFIRMADO',
    value: 'CONFIRMADO'

  }];

}

