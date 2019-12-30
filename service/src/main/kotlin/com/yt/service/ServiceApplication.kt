package com.yt.service

import com.alibaba.druid.pool.DruidDataSource
import com.yt.service.entiy.Banks
import com.yt.service.entiy.Merchant
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import javax.annotation.Resource

@SpringBootApplication
class ServiceApplication

fun main(args: Array<String>) {
    runApplication<ServiceApplication>(*args)
}


@Component
class PublishEndpoint : ApplicationRunner {

    @Resource
    var dataSour: DruidDataSource? = null

    override fun run(args: ApplicationArguments?) {
        Database.connect(dataSour!!)
        transaction {
           // SchemaUtils.drop(Merchant)
            SchemaUtils.create(Merchant, Banks)
        }
    }

}