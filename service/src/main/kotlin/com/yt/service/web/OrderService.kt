package com.yt.service.web

import com.yt.appcommon.utils.toTable
import com.yt.appcommon.utils.toTye
import com.yt.appcommon.vo.OrderVO
import com.yt.service.entiy.Banks
import com.yt.service.entiy.Orders
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

/**
 *Create By Albert on 2019/12/29
 */
@Suppress("IMPLICIT_CAST_TO_ANY")
@Service
class OrderService {

    fun getOrderList(ordervo: OrderVO?): ArrayList<OrderVO> {
        val order = ArrayList<OrderVO>()
        transaction {
            val query = Orders.innerJoin(Banks, { bankId }, { id }).selectAll()
            ordervo?.orderNo?.let {
                if (it != "") query.andWhere { Orders.orderNo like "%${it}%" }
            }

            ordervo?.payState?.let {
                query.andWhere { Orders.payState eq it }
            }

            query.toSet().forEach {
                val ordervo = it.toTye(OrderVO::class.java, Orders, Banks)
                order.add(ordervo)
            }
        }
        return order
    }

    fun saveOrUpdate(order: OrderVO) {
        transaction {
            if (order.id != null) {
                Orders.update({ Orders.id eq (order.id ?: 0) }) {
                    it.toTable(this, order)
                    //it[payState] = order.payState!!
                }
            } else {
                Orders.insert {
                    it.toTable(this, order)
                }
            }

        }
    }
}