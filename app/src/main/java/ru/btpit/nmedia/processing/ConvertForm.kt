package ru.btpit.nmedia.processing

fun convertForm(x: Int): String{
    var result: String = ""
    when(x){
        in 0..999-> result = x.toString()
        in 999..9_999-> result = ((x/100).toDouble()/10).toString()  + "K"
        in 10_000..999_999-> result = (x/1_000).toString() + "K"
        in 1_000_000..9_999_999-> result = ((x/100_000).toDouble()/10).toString() + "M"
        in 10_000_000..Int.MAX_VALUE -> result = (x/1_000_000).toString() + "M"
    }
    return result
}