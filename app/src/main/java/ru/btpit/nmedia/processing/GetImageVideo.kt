package ru.btpit.nmedia.processing

fun getImageVideo(url: String):String?{
    return if(url.split("v=").lastIndex == 1){
        val vUrl = url.split("v=")[1]
        "https://img.youtube.com/vi/$vUrl/maxresdefault.jpg"
    }else{
        null
    }
}