export const environment = {
  production: true,
  util: {
    authData: 'data',
    loginDialogWidth: 'WD'
  },
  services: {
    baseUri : 'https://reportate.mc4.com.bo:8443/reportate-api/',
    apiKeyMaps: 'pk.eyJ1IjoicmxhcmVkbyIsImEiOiJjazh4bDV5ZG8weWQ4M2xsZHh4NWh2ZmY5In0.LVSLJJhRrkULhFV0Nq6A0Q',
    baseUriFact : 'http://localhost:8080/facturacion-core/',
  }
};
export const Constantes = environment.services;
export const AUTH_DATA = environment.util.authData;
export const LOGIN_DIALOG_WIDTH = environment.util.loginDialogWidth;

