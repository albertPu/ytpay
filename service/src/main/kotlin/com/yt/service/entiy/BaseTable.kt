package com.yt.service.entiy

import org.jetbrains.exposed.sql.CurrentDateTime
import org.jetbrains.exposed.sql.Table

/**
 *Create By Albert on 2019/12/29
 */
open class BaseTable : Table() {

    val createTime = datetime("create_time").defaultExpression(CurrentDateTime())

    val updateTime = datetime("update_time").defaultExpression(CurrentDateTime())

    val isDeleted = bool("is_delete").default(false)
}