import { UbicacionDiagnostico } from './ubicacion-diagnostico';

export class MapFormat {
        type: string;
        properties: {
          description: string;
          icon: string;
        };
        geometry: {
          type: string;
          coordinates: [number, number] // long , lat
        };

    constructor(object: UbicacionDiagnostico) {
        this.type = 'Feature';
        // tslint:disable-next-line:max-line-length
        this.properties = { description: '<strong>' + object.enfermedad + '</strong>' + '<p>' + object.nombrePaciente + '</p> <p>' + object.estadoDiagnostico + '</p>', icon: ''};
    //    this.properties. = '';
        this.geometry = {type: 'Point', coordinates: [object.longitud, object.latitud]};
    }
}
