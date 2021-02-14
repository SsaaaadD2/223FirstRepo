fun main(args: Array<String>){
    // val wholeNum : Int = 5;
    // println("The value of my variable is $wholeNum");


    // val userInput = readLine();
    // println("You entered $userInput");


    // val myArray = arrayOf(3,2,6)
    // for(i in myArray){
    //     println(i)
    // }

    // for (i in 10 downTo 5 step 2){
    //     println(i)
    // }


    // val list = mutableListOf(1,true,"several");
    // list[2] = 3;
    // list.add(7);
    // list.remove(1);
    // list.removeAt(2);
    //val list2 = mutableListOf<String>("new", "string");


    var elem = 3;
    when(elem) {
        in 1..5 -> println("Nice")
        in 6..12 -> println("Other")
        19, 20 -> println("third")
        45 -> {
            println("firstline")
            println("secondline")
        }
        else -> println("not found")
    }
}

