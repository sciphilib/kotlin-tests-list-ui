package com.nstu.lab1.dataStructures

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import dataStructures.MyList
import java.io.IOException

class ListDeserializer<T : Any>(private val valueType: JavaType) :
    JsonDeserializer<MyList<*>>() {
    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(jsonParser: JsonParser, context: DeserializationContext): MyList<T> {
        val list: MyList<T> = MyList()
        val oc = jsonParser.codec
        val nodes = oc.readTree<JsonNode>(jsonParser)
        if (nodes.isArray) {
            for (node in nodes) {
                val value = oc.treeToValue(node, valueType.rawClass) as T
                list.add(value)
            }
        }
        return list
    }
}