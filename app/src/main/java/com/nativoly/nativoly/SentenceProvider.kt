package com.nativoly.nativoly

import java.io.InputStream

/**
 * Created by guerlich on 6/4/17.
 */

class SentenceProvider {
    val sentences=ArrayList<Sentence>()

    val count:Int
    get() = sentences.size

    fun load(sin: InputStream,langs:List<String>) {
        sin.bufferedReader().forEachLine { line ->
            val fields= line.split("\t")

            val s=Sentence(fields,langs)
            if(s.hasTranslation) sentences.add(s)
        }
    }



    fun next():Sentence
    {
        val idx= (Math.random()*sentences.size).toInt()
        return sentences[idx]
    }
}

class Sentence(fields:List<String>,langs:List<String>) {
    val id:String
    val lang:String
    val text:String
    var translation=""
    var hasTranslation=false

    init {
        id=fields[0]
        lang=fields[1]
        text=fields[2]

        var minLang=999
        var i=3
        while(i<fields.size)
        {
            val tid=fields[i]
            val tlang=fields[i+1]
            val hasAudio=fields[i+2]=="t"
            val ttext=fields[i+3]

            val pos=langs.indexOf(tlang)

            if(pos>=0&&pos<minLang)
               {
                   translation=ttext
                   hasTranslation=true
                   minLang=pos
               }

            i+=4
        }

    }

}

