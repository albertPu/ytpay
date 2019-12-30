package com.yt.service.web

import com.yt.appcommon.utils.toTable
import com.yt.appcommon.utils.toTye
import com.yt.appcommon.vo.BankListResponse
import com.yt.service.entiy.Banks
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Service

/**
 *Create By Albert on 2019/12/29
 */
@Suppress("IMPLICIT_CAST_TO_ANY")
@Service
class BankService {

    fun getBankList(): ArrayList<BankListResponse> {
        val banks = ArrayList<BankListResponse>()
        transaction {
            Banks.selectAll().forEach {
                val bank = it.toTye(Banks, BankListResponse::class.java)
                banks.add(bank)
            }
        }
        return banks
    }

    fun saveOrUpdate(bank: BankListResponse) {
        transaction {
            if (bank.id != null) {
                Banks.update({ Banks.id eq (bank.id ?: 0) }) {
                    it.toTable(this, bank)
                }
            } else {
                Banks.insert {
                    it.toTable(this, bank)
                }
            }

        }
    }
}