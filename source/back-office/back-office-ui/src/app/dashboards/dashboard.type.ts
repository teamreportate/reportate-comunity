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

export class DataTotal {
  constructor(
    public nivelLugar: number = null,
    public items: DataTotalItem[] = []
    ) { }
}

export class DataTotalItem {
  constructor(
    public nombre: number = null,
    public sospechoso: number = 0,
    public negativo: number = 0,
    public confirmado: number = 0,
    public curado: number = 0,
    public fallecido: number = 0
    ) { }
}
