package com.tripl3dev.kotlintest

import org.junit.Test

class TestingEither {

    val wrapped :Either<Exception,Int> = either{
        oddIntegers(4)
    }


        fun oddIntegers(random :Int):Int{
            if (random % 2 !=0)
                return random
            else throw(Exception("it isn't odd"))
        }


    @Test
    fun testIt(){
        when (wrapped){
            is Either.Data ->{
                print("Success")
            }
            is Either.Error ->{
                print("Error")
            }

        }
    }
    }



