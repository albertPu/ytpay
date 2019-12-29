package com.yt.appcommon.utils

import com.google.gson.Gson
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import java.lang.StringBuilder
import java.lang.reflect.Field

/**
 *Create By Albert on 2019/12/28
 */
val gson = Gson()

val fieldsMaps = HashMap<String, ArrayList<Field>>()


fun <T : Table> ResultRow.toJson(table: T): String {
    val stringBuilder = StringBuilder("{")
    if (fieldsMaps.containsKey(table::class.java.name)) {
        fieldsMaps[table::class.java.name]?.forEach { field ->
            if (field.type.name == Column::class.java.name) {
                field.isAccessible = true
                val column = field.get(table)
                val value = get(column as Column<*>)
                val key = field.name
                stringBuilder.append("\"${key}\":\"${value}\",")
            }
        }
        return stringBuilder.toString().let {
            it.substring(0, it.lastIndex)
        } + "}"
    } else {
        fieldsMaps[table::class.java.name] = ArrayList()
        table::class.java.declaredFields.forEach { field ->
            if (field.type.name == Column::class.java.name) {
                field.isAccessible = true
                fieldsMaps[table::class.java.name]?.add(field)
                val column = field.get(table)
                val value = get(column as Column<*>)
                val key = field.name
                stringBuilder.append("\"${key}\":\"${value}\",")
            }
        }
        return stringBuilder.toString().let {
            it.substring(0, it.lastIndex)
        } + "}"
    }
}

fun <T : Table, V> ResultRow.toTye(table: T, clazz: Class<V>): V {
    return if (fieldsMaps.containsKey(table::class.java.name)) {
        gson.fromJson<V>(toJson(table), clazz)
    } else {
        gson.fromJson<V>(toJson(table), clazz)
    }

}