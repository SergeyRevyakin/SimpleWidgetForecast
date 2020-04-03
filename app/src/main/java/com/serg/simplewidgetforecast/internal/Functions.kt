package com.serg.simplewidgetforecast.internal

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.*

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}

fun showWindDirection(degrees: Int?): String{
    return if (degrees==null) "ERROR"
    else {
        val n =((degrees/22.5)+.5).toInt()
        val arrayOfDirections = arrayListOf("N","NNE","NE","ENE","E","ESE", "SE", "SSE","S","SSW","SW","WSW","W","WNW","NW","NNW")
        arrayOfDirections[n]
    }
}

fun <T> Task<T>.asDeferred(): Deferred<T>{
    val deferred = CompletableDeferred<T>()

    this.addOnSuccessListener {
        deferred.complete(it)
    }

    this.addOnFailureListener {
        deferred.completeExceptionally(it)
    }

    return deferred
}