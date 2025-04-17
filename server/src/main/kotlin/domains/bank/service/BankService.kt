package org.example.domains.bank.service

import org.example.common.logging.Logging
import org.example.common.transaction.Transactional
import org.example.domains.bank.repository.BankAccountRepository
import org.example.domains.bank.repository.BankUserRepository
import org.example.types.dto.Response
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class BankService(
    private val transaction: Transactional,
    private val logger: Logger = Logging.getLogger(BankService::class.java),
    private val bankUserRepository: BankUserRepository,
    private val bankAccountRepository: BankAccountRepository
) {
    fun createAccount(userUlid: String): Response<String> = Logging.logFor(logger) { log ->
        log["userUlid"] = userUlid
        transaction.run {  }
    }

    fun balance(userUlid: String, accountUlid: String): Response<BigDecimal> = Logging.logFor(logger) { log ->
        log["userUlid"] = userUlid
        log["accountUlid"] = accountUlid
        transaction.run {  }
    }

    fun removeAccount(userUlid: String, accountUlid: String): Response<String> = Logging.logFor(logger) { log ->
        log["userUlid"] = userUlid
        log["accountUlid"] = accountUlid
        transaction.run {  }
    }
}