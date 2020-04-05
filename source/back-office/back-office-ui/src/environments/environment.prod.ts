export const environment = {
  production: true,
  util: {
    authData: 'data',
    loginDialogWidth: 'WD'
  },
  services: {
    baseUri : 'https://dev.mc4.com.bo:10581/back-office/',
    baseUriFact : 'http://localhost:8080/facturacion-core/',
  }
};
export const Constantes = environment.services;
export const AUTH_DATA = environment.util.authData;
export const LOGIN_DIALOG_WIDTH = environment.util.loginDialogWidth;

