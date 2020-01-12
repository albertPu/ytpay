package com.yt.appcommon.utils

import com.google.gson.Gson
import com.yt.appcommon.annotation.DataMapping
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import java.lang.StringBuilder
import java.lang.reflect.Field

/**
 *Create By Albert on 2019/12/28
 */
val gson = Gson()

val fieldsMaps = HashMap<String, ArrayList<Field>>()


fun <T : Table> ResultRow.toJson(tables: Array<T>): String {
    val stringBuilder = StringBuilder("{")
    tables.forEach { table ->
        if (fieldsMaps.containsKey(table::class.java.name)) {
            fieldsMaps[table::class.java.name]?.forEach { field ->
                    if (field.type.name == Column::class.java.name) {
                        field.isAccessible = true
                        val column = field.get(table)
                        val value = get(column as Column<*>)
                        val key = "\"${field.name}\""
                        if (!stringBuilder.contains(key)) {
                            stringBuilder.append("${key}:\"${value}\",")
                        }
                    }
            }

        } else {
            fieldsMaps[table::class.java.name] = ArrayList()
            table::class.java.declaredFields.forEach { field ->
                if (field.type.name == Column::class.java.name) {
                    field.isAccessible = true
                    fieldsMaps[table::class.java.name]?.add(field)
                    val column = field.get(table)
                    val value = get(column as Column<*>)
                    val key = "\"${field.name}\""
                    if (!stringBuilder.contains(key)) {
                        stringBuilder.append("${key}:\"${value}\",")
                    }
                }
            }
        }
    }

    return stringBuilder.toString().let {
        it.substring(0, it.lastIndex)
    } + "}"
}

fun <T : Table, V> ResultRow.toTye(clazz: Class<V>, vararg tables: T): V {
    return gson.fromJson<V>(toJson(tables), clazz)
}


fun <T : Table> UpdateBuilder<Number>.toTable(table: T, value: Any) {

    var tabFields = fieldsMaps[table::class.java.name]
    if (tabFields == null) {
        tabFields = ArrayList()
        table::class.java.declaredFields?.filter { it.type.name == Column::class.java.name }?.forEach {
            it.isAccessible = true
            tabFields.add(it)
        }
    }
    fieldsMaps[table::class.java.name] = tabFields

    var valueFields = fieldsMaps[value::class.java.name]
    if (valueFields == null) {
        valueFields = ArrayList()
        fieldsMaps[value::class.java.name] = valueFields
        value::class.java.declaredFields.forEach {
            it.isAccessible = true
            valueFields.add(it)
        }
    }
    fieldsMaps[value::class.java.name] = valueFields

    tabFields.forEach { tableField ->
        val valuefield = valueFields.firstOrNull { it.name == tableField.name }
        val dataMapping = valuefield?.getAnnotation(DataMapping::class.java)
        if (dataMapping==null || !dataMapping.ignoreVo2Data){
            val column = tableField.get(table)
            if (column != null) {
                val nullable = (column as Column<Any>).columnType.nullable
                var invokeValue = valuefield?.get(value)
                if (invokeValue != null && invokeValue::class.java.isEnum) {
                    invokeValue = (invokeValue as Enum<*>).ordinal
                }
                if (!(invokeValue == null && !nullable) && invokeValue != null) {
                    set(column, invokeValue)
                }
            }
        }
    }

}