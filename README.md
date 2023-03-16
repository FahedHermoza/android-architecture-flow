# Ejemplo de arquitectura Android basado en Flow
Ejemplo de arquitectura Android basado en Flow, LiveData, Mapper, SafeCall, y otros.

[![forthebadge](https://forthebadge.com/images/badges/built-for-android.svg)](https://forthebadge.com)[![forthebadge](https://forthebadge.com/images/badges/built-by-codebabes.svg)](https://forthebadge.com)

## Proyecto
[KoinNetworkKotlinApp](https://github.com/FahedHermoza/reviewArchitectures/tree/main/KoinNetworkKotlinApp): Construido con arquitectura Clean, flow, patron MVVM y algunas librerias (navigation, retrofit, preferences, coroutines, livedata, lifecycle).

[KoinStorageKotlinApp](https://github.com/FahedHermoza/ArchitectureAndroid-Flow/tree/main/KoinStorageKotlinApp): Construido con arquitectura Clean, flow, patron MVVM y algunas librerias (navigation, room, koin, coroutines, livedata, lifecycle).

### Diagrama de Arquitectura:
<div align="center">
        <img width="80%" src="screenshots/FlowArchitecture-01.png" alt="About screen" title="About screen"</img>
        <img height="0" width="16px">
</div>

[Variante 1 - Unidirectional data flow architecture](https://github.com/FahedHermoza/ArchitectureAndroid-Flow/tree/main/%20Variante-1): Implementacion intermedia entre MVVM y MVI, utilizando ViewState y Event en el ViewModel, tambien se utilizo StateFlow/MutableStateFlow en vez de LiveData/MutableStateFlow.(Ejemplo de KoinStorageKotlinApp)

## Diapositivas
Puedes encontrar las diapositivas en el siguiente [enlace](https://docs.google.com/presentation/d/1Vaa4ZGt65bbbijyIJlr7RYH1Llod4wq1aoitug7k2Js/edit?usp=sharing).

## Créditos
Este repositorio es posible gracias a **Eduardo Medina** de hecho el repositorio esta basado en uno de sus proyectos base y **Jhonatan Sandoval** por la guia de la arquitectura basada en flows.