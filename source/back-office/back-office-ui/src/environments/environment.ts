// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  util: {
    authData: 'data',
    loginDialogWidth: 'WD'
  },
  services: {
    // baseUri : 'https://reportate.Reportate.com.bo:8443/reportate-api/',
    apiKeyMaps: 'pk.eyJ1IjoicmxhcmVkbyIsImEiOiJjazh4bDV5ZG8weWQ4M2xsZHh4NWh2ZmY5In0.LVSLJJhRrkULhFV0Nq6A0Q',
    // baseUri: 'https://localhost:9443/',
    baseUri: 'https://dev.Reportate.com.bo:9443/',

    baseUriFact: 'https://localhost:9443/' +
      'acturacion-core/',
  }

};

export const Constantes = environment.services;
export const AUTH_DATA = environment.util.authData;
export const LOGIN_DIALOG_WIDTH = environment.util.loginDialogWidth;

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error'; // Included with Angular CLI.
