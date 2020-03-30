export const environment = {
  production: true,
  util: {
    authData: 'data',
    loginDialogWidth: 'WD'
  },
  services: {
    baseUri : 'http://localhost:8080/',
    baseUriFact : 'http://localhost:8080/facturacion-core/',
  }
};
export const Constantes = environment.services;
export const AUTH_DATA = environment.util.authData;
export const LOGIN_DIALOG_WIDTH = environment.util.loginDialogWidth;

