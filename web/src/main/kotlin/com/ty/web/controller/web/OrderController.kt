package com.ty.web.controller.web

import com.ty.web.remote.ServiceFeign
import com.yt.appcommon.WebPath
import com.yt.appcommon.base.BaseController
import com.yt.appcommon.utils.gson
import com.yt.appcommon.vo.OrderVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

/**
 *Create By Albert on 2019/12/30
 */
@Controller
class OrderController : BaseController() {

    @RequestMapping(WebPath.orderList, method = [RequestMethod.GET])
    fun orderList(model: Model): String {
        model.addAttribute("orderList", WebPath.orderListJson)
        model.addAttribute("orderListSave", WebPath.orderListSave)
        return "page/orderlist"
    }
}

@RestController
class OrderRestfulController : BaseController() {

    @Autowired
    lateinit var remoteService: ServiceFeign

    @GetMapping(WebPath.orderListJson)
    fun orderList(@RequestParam page: Int, @RequestParam limit: Int, @RequestParam(required = false) searchParams: String?): Any {
        var orderListResponse: OrderVO? = OrderVO()
        if (searchParams != null) {
            orderListResponse = gson.fromJson<OrderVO>(searchParams, OrderVO::class.java)
        }
        val orders = remoteService.orderList(orderListResponse)
        orders.data?.forEach {
            it.change()
        }
        return orders
    }

    @PostMapping(WebPath.orderListSave)
    fun orderSave(@RequestBody orderVO: OrderVO): Any {
        remoteService.orderSaveOrUpdate(orderVO)
        return succees()
    }

}