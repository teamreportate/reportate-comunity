export class Basic {
  constructor(
    public id: number = 0,
    public nombre: number = null
    ) { }
}

export class Filter {
  constructor(
    public departamentoId: number = 0,
    public municipioId: number = 0,
    public centroSaludId: number = 0,
    public enfermedadId: number = 0
    ) { }
}

export class Data {
  constructor(
    public dias: string[] = [],
    public sospechosos: number[] = [],
    public negativos: number[] = [],
    public confirmados: number[] = [],
    public curados: number[] = [],
    public fallecidos: number[] = []
    ) { }
}
