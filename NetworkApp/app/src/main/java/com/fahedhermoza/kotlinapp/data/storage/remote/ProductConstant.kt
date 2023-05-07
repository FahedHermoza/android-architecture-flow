package com.fahedhermoza.kotlinapp.data.storage.remote

/***
 * Configurar backendless
 * Crear proyecto y generar los keys
538B0AAE-3CEF-4786-FF6D-D9A971653500
9DFF3053-5796-4E74-B8EB-C7EF67E9B985

 * Crear una BD(option data):
Tabla User agregar campos(por defecto):
email: admin@admin.com
name: admin
password: 12345admin

Table Product:(Schema/Tabla editor)
name:String
description:String
cost:Double
logo:String
code:String(objecteId del User)
 */
object ProductConstant {

    const val APPLICATION_ID: String = "E1F8FC88-6A0C-0E31-FFEE-F9E875F49600"
    const val REST_API_KEY: String = "D28D463E-52B4-4B67-B73D-99C911F3858A"
}