package com.tripl3dev.kotlintest

sealed class Either<out E, out D> {

// first data class from type Error which return Either<E , nothing>

    data class Error<out E>(val error: E) : Either<E, Nothing>()

    //second data class from type Data which returns Either<nothing , Data>
    data class Data<out D>(val data: D) : Either<Nothing, D>()




}


fun <D> getData(data: D): Either<Nothing, D> = Either.Data(data)
fun <E> getError(error: E): Either<E, Nothing> = Either.Error(error)

fun <D> either(action: () -> D): Either<Exception, D> =
        try {
            Either.Data(action())
        } catch (e: Exception) {
            println("it has error")
            Either.Error(e)
        }
