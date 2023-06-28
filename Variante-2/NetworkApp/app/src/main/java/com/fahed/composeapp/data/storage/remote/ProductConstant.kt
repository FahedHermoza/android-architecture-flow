package com.fahed.composeapp.data.storage.remote

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

    const val APPLICATION_ID: String = "8251EB16-134F-F83A-FF5D-9CC3BC034E00"
    const val REST_API_KEY: String = "035B7EA6-C485-4EC3-B8BD-64FD467F371C"
}