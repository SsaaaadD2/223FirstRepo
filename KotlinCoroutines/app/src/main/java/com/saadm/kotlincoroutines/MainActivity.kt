package com.saadm.kotlincoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    val tag = "NewTask";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        GlobalScope.launch{
           val networkCall = doNetworkCall();
           Log.d(tag, networkCall);
        }*/

        /*
        //Dispatch network call thread
        GlobalScope.launch(Dispatchers.IO){
            Log.d(tag, "Starting coroutine in ${Thread.currentThread().name}");
            val networkCall = doNetworkCall();

            //This block is now executed in the main thread
            withContext(Dispatchers.Main){
                Log.d(tag, "Switching coroutine to ${Thread.currentThread().name}");
                Log.d(tag, networkCall);
            }
            */

        /*
        //Any delays placed inside this function will delay the entire thread
        runBlocking{
            //Don't need GlobalScope since we are within runBlocking scope
            //This function runs async to runBlocking, so it will not be blocked
            //if it is dispatched in a different thread
            launch(Dispatchers.IO){

            }
        } */

        /*
        //Coroutines return jobs, which can be called later using join();
        //Note that join does not start the job, the job has already started
        val job = GlobalScope.launch{
            repeat(5){
                Log.d(tag, "Test...");
                delay(1000);
            }

            //Coroutine is automatically cancelled after timeout
            withTimeout(5){

            }
        }

        runBlocking {
            //job.join();

            //Can cancel any job
            //job.cancel();
        }
        }
        */

        GlobalScope.launch(Dispatchers.IO){
            val time = measureTimeMillis {
                val answer1 = async{doNetworkCall()}
                val answer2 = async{networkCall2()};
                Log.d(tag, "Answer 1 is ${answer1.await()}");
                Log.d(tag, "Answer 2 is ${answer2.await()}");
            }
        }
    }

    suspend fun doNetworkCall():String{
        delay(3000L);
        return "Answer";
    }

    suspend fun networkCall2(): String{
        delay(3000);
        return "Answer2";
    }
}