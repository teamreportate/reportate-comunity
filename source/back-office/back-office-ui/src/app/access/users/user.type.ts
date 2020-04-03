export class Department {
  constructor(
    public id: number = 0,
    public nombre: string = '',
    public asignado: boolean = false
    ) { }
}
export class Municipaly {
  constructor(
    public id: number = 0,
    public nombre: string = '',
    public departamentoId: number = 0,
    public asignado: boolean = false
    ) { }
}
export class SaludCentre {
  constructor(
    public id: number = 0,
    public nombre: string = '',
    public municipioId: number = 0,
    public direccion: string = null,
    public zona: string = null,
    public ciudad: string = null,
    public asignado: boolean = false
    ) { }
}
