package com.nstu.lab1.dataStructures

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import dataStructures.MyList
import java.io.IOException

class ListSerializer<T> : StdSerializer<MyList<*>?>(MyList::class.java, false) {
    @Throws(IOException::class)
    override fun serialize(value: MyList<*>?, gen: JsonGenerator?, provider: SerializerProvider?) {
        gen?.writeStartObject()
        gen?.writeFieldName("elements")
        gen?.writeStartArray()
        for (element in value?.toList()!!) {
            gen?.writeObject(element)
        }
        gen?.writeEndArray()
        gen?.writeEndObject()
    }


}