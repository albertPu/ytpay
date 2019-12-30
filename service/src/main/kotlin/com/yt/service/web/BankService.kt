package com.yt.service.web

import com.yt.appcommon.utils.toTable
import com.yt.appcommon.utils.toTye
import com.yt.appcommon.vo.BankVO
import com.yt.service.entiy.Banks
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Service

/**
 *Create By Albert on 2019/12/29
 */
@Suppress("IMPLICIT_CAST_TO_ANY")
@Service
class BankService {

    fun getBankList(bank: BankVO?): ArrayList<BankVO> {
        val banks = ArrayList<BankVO>()
        transaction {
            if (bank == null) {
                Banks.selectAll().forEach {
                    val bank = it.toTye(BankVO::class.java, Banks)
                    banks.add(bank)
                }
            } else {
                val query = Banks.selectAll()
                bank.userName?.let {
                    if (it != "") query.andWhere { Banks.userName like "%${it}%" }
                }

                bank.bankNo?.let {
                    if (it != "") query.andWhere { Banks.bankNo like "%${it}%" }
                }

                bank.bankName?.let {
                    if (it != "") query.andWhere { Banks.bankName like "%${it}%" }
                }
                bank.bankAddress?.let {
                    if (it != "") query.andWhere { Banks.bankAddress like "%${it}%" }
                }
                query.toSet().forEach {
                    val bank = it.toTye(BankVO::class.java, Banks)
                    banks.add(bank)
                }
            }
        }
        return banks
    }

    fun saveOrUpdate(bank: BankVO) {
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