# Ejemplo de arquitectura Android basado en Flow
Ejemplo de arquitectura Android basado en Flow, LiveData, Mapper, SafeCall, y otros.

[![forthebadge](https://forthebadge.com/images/badges/built-for-android.svg)](https://forthebadge.com)[![forthebadge](https://forthebadge.com/images/badges/built-by-codebabes.svg)](https://forthebadge.com)

## Proyecto Notas
[KoinNetworkKotlinApp](https://github.com/FahedHermoza/reviewArchitectures/tree/main/KoinNetworkKotlinApp): Construido con arquitectura Clean, flow, patron MVVM y algunas librerias (navigation, retrofit, preferences, coroutines, livedata, lifecycle).

[KoinStorageKotlinApp](https://github.com/FahedHermoza/ArchitectureAndroid-Flow/tree/main/KoinStorageKotlinApp): Construido con arquitectura Clean, flow, patron MVVM y algunas librerias (navigation, room, koin, coroutines, livedata, lifecycle).

### Diagrama de Arquitectura:
<div align="center">
        <img width="100%" src="screenshots/FlowArquitecture-Notes.jpg" alt="About screen" title="About screen"</img>
        <img height="0" width="16px">
</div>

## Variante: Unidirectional data flow
[KoinStorageKotlinApp](https://github.com/FahedHermoza/ArchitectureAndroid-Flow/tree/main/%20Variante-1): Implementacion intermedia entre MVVM y MVI, utilizando ViewState y Event en el ViewModel, tambien se utilizo StateFlow en vez de LiveData.

**ViewState:** Clase que almacena el estado actual de su vista. Lo emites a traves de un LiveData o State Flow (state).

Representado por:
- Data Class
- Sealed Class

**Event:** Los eventos son acciones que desencadena la UI.

Representado por:
- Sealed Class

**StateFlow:** Flujo observable contenedor de estados que emite actualizaciones de estados actuales y nuevas a sus recopiladores.

| Descripción | LiveData | StateFlow |
|:-------------------:|---|---|
| Contenedor de datos observables | YES | YES |
| Patron de diseño: OBSERVER | YES | YES |
| Requiere estado inicial en el constructor | NO | YES |
| Dar de baja al consumidor cuando la View pasa a STOPPED | AUTOMATIC | MANUAL (lifecycle.repeatOnLifecycle) |

### Diagrama de Arquitectura:
<div align="center">
        <img width="100%" src="screenshots/FlowArquitecture-Variante01.jpg" alt="About screen" title="About screen"</img>
        <img height="0" width="16px">
</div>

## Diapositivas
Puedes encontrar las diapositivas en el siguiente [enlace](https://docs.google.com/presentation/d/1Vaa4ZGt65bbbijyIJlr7RYH1Llod4wq1aoitug7k2Js/edit?usp=sharing).

## Créditos
Este repositorio es posible gracias a **Eduardo Medina** de hecho el repositorio esta basado en uno de sus proyectos base y **Jhonatan Sandoval** por la guia de la arquitectura basada en flows.