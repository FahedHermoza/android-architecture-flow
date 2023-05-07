package com.fahedhermoza.kotlinapp.core.utils.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

internal class ContextScope(context: CoroutineContext) : CoroutineScope {
    override val coroutineContext: CoroutineContext = context
}

fun ioScope(): CoroutineScope = ContextScope(SupervisorJob() + Dispatchers.IO)