## servicio /registrar-familia 
### `parametros`
departamento y ciudad ids de la lista que sale del backend
```json
{
    "nombre"         : "familia 1",
    "telefono"       : "70003332",
    "direcion"       : "direccion",
    "latitud"        : "-17.6655443",
    "longitud"       : "-63.1231234",
    "zona"           : "zona",
    "ciudad"         : "Santa Cruz",
    "municipioId"    : 1,
    "departamentoId" : 1
} 
```

### `resultado`
estado 0 todo bien

estato 1 error

mensaje solo si hay error

```json
{
  "status" : 200,
  "data"   : {
      "id"             : 2,
      "nombre"         : "familia 1",
      "telefono"       : "70003332",
      "direcion"       : "direccion",
      "latitud"        : "-17.6655443",
      "longitud"       : "-63.1231234",
      "zona"           : "zona",
      "ciudad"         : "Santa Cruz",
      "municipio"      : [object:municipio],
      "departamento"   : [object:departamento]
  }
}
```

## servicio /obtener-familia
### `parametros`
sin parametros para obtener familia.
```json
{
}
```

### `resultado`
arreglo de familiares

```json
{
  "status" : 200,
  "data"   : [
    {
      "id": 1,
      "nombre": "juan",
      "edad": 20,
      "sexo": "masculino",
      "notificacion": true
    },
    {
      "id": 2,
      "nombre": "marco",
      "edad": 25,
      "sexo": "masculino",
      "notificacion": true
    }
  ]
}
```

## servicio /registrar-familiar
### `parametros`
```json
{
  "nombre"       : "juan",
  "edad"         : 20,
  "sexo"         : "masculino",
  "notificacion" : true
}
```

### `resultado`
```json
{
  "status" : 200,
  "data"   : {
      "id"           : 1,
      "nombre"       : "juan",
      "edad"         : 20,
      "sexo"         : "masculino",
      "notificacion" : true
  }
}
```

## servicio /actualizar-familiar
### `parametros`
```json
{
  "id"           : 1,
  "nombre"       : "juan",
  "edad"         : 20,
  "sexo"         : "masculino",
  "notificacion" : true
}
```

### `resultado`
```json
{
  "status" : 200
}
```

## servicio /eliminar-familiar
### `parametros`
```json
{
  "id" : 1
}
```

### `resultado`
```json
{
  "status" : 200
}
```


## servicio /actualizar-familiar-gestacion
### `parametros`
```json
{
  "id"           : 1,
  "gestante"     : true,
  "semanas"      : 24
}
```

### `resultado`
```json
{
  "status" : 200
}
```

## servicio /actualizar-familiar-contacto-extranjero
### `parametros`
```json
{
  "id"        : 1,
  "contactos" : [
    {
      "quien": "conocido",
      "pais": "ES",
      "fecha": "2020-02-14"
    },
    {
      "quien": "yo",
      "pais": "MX",
      "fecha": "2020-02-14"
    } 
  ]
}
```
### `resultado`
```json
{
  "status" : 200
}
```
## servicio /actualizar-familiar-emfermedad-base
### `parametros`
```json
{
  "id"        : 1,
  "enfermedad" : [
    {
      "enfermedadId": 1
    },
    {
      "enfermedadId": 2
    },
    {
      "enfermedadId": 3
    }
     
  ]
}
```
### `resultado`
```json
{
  "status" : 200
}
```

