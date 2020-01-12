package com.yt.service.datasoucre

import com.yt.appcommon.utils.toTable
import com.yt.appcommon.utils.toTye
import com.yt.appcommon.vo.OrderVO
import com.yt.appcommon.annotation.Operate
import com.yt.appcommon.annotation.RedisCache
import com.yt.service.entiy.Banks
import com.yt.service.entiy.Merchant
import com.yt.service.entiy.Orders
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service
import java.math.BigDecimal

/**
 *Create By Albert on 2019/12/29
 */
@Suppress("IMPLICIT_CAST_TO_ANY")
@Service
@RedisCache(nameSpace = "OrderService")
class OrderService {

   /* @Autowired
    lateinit var mqSender: Sender*/


    @RedisCache
    fun getOrderList(orderVo: OrderVO?): ArrayList<OrderVO> {
        val order = ArrayList<OrderVO>()
        transaction {

            val query = (Orders innerJoin Banks).selectAll()
            orderVo?.orderNo?.let {
                if (it != "") query.andWhere { Orders.orderNo like "%${it}%" }
            }

            orderVo?.payState?.let {
                query.andWhere { Orders.payState eq it }
            }

            orderVo?.bankEndNo?.let {
                query.andWhere { Banks.bankNo like "%$it" }
            }
            query.toSet().forEach {
                val ordervo = it.toTye(OrderVO::class.java, Orders, Banks)
                order.add(ordervo)
            }
        }
        return order
    }

    @RedisCache(operate = Operate.INSET)
    fun saveOrUpdate(order: OrderVO?) {
        if (order==null)return
        transaction {
            if (order.id != null) {
                Orders.update({ Orders.id eq (order.id ?: 0) }) {
                    it.toTable(this, order)
                }
            } else {
                Orders.insert {
                    it.toTable(this, order)
                }
              //  mqSender.sendDelayMessageByPlugins(order.orderNo, 10000)
            }
        }

    }

    @RedisCache
    fun getByOrderNo(orderNO: String): OrderVO? {
        return transaction {
            val join = Orders.innerJoin(Banks).innerJoin(Merchant)
            join.select {
                Orders.orderNo eq orderNO
            }.firstOrNull()?.toTye(OrderVO::class.java, Orders, Banks, Merchant)
        }
    }

    @RedisCache
    fun getByRealMoneyAndBankCode(realMoney: BigDecimal, bankNo: String, bankName: String): OrderVO? {
        return transaction {
            val join = Orders.innerJoin(Banks).innerJoin(Merchant)
            join.select {
                Orders.realMoney eq realMoney and (Banks.bankNo eq bankNo) and (Banks.bankName eq bankName)
            }.firstOrNull()?.toTye(OrderVO::class.java, Orders, Banks, Merchant)
        }
    }
}