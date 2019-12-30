package com.yt.service.controller

import com.yt.appcommon.WebPath
import com.yt.appcommon.base.BaseController
import com.yt.appcommon.vo.OrderVO
import com.yt.service.web.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 *Create By Albert on 2019/12/29
 */
@RestController
class OrderController : BaseController() {

    @Autowired
    lateinit var orderService: OrderService

    @RequestMapping(WebPath.orderServiceList, method = [RequestMethod.POST])
    fun orderList(@RequestBody(required = false) order: OrderVO?): Any {
        val row = orderService.getOrderList(order)
        return succeesData(row)
    }

    @RequestMapping(WebPath.orderServiceListSave, method = [RequestMethod.POST])
    fun orderSave(@RequestBody order: OrderVO): Any {
        orderService.saveOrUpdate(order)
        return succeesData()
    }


}