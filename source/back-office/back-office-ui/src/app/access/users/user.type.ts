export class Single {
  constructor(
    public id: number = 0,
    public name: string = '',
    ) { }
}

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

export class Group {
  constructor(
    public id: number = 0,
    public nombre: string = null,
    public descripcion: string = null,
    public estadoGrupo: string = null,
    public version: number = null,

    public createdDate: any = null,
    public lastModifiedDate: any = null,
    public createdBy: string = null,
    public lastModifiedBy: string = null,
    public estadoUsuario: string = null,
    public estado: string = null,

    public roles: Single[] = [],
    public muUsuarioGrupos: Single[] = []

    ) { }
}

