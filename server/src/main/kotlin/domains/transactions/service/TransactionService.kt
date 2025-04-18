package org.example.domains.transactions.service

import org.example.cache.RedisClient
import org.example.cache.RedisKeyProvider
import org.example.common.exception.CustomException
import org.example.common.exception.ErrorCode
import org.example.common.logging.Logging
import org.example.common.transaction.Transactional
import org.example.domains.transactions.model.DepositResponse
import org.example.domains.transactions.repository.TransactionAccount
import org.example.domains.transactions.repository.TransactionUser
import org.example.types.dto.Response
import org.example.types.dto.ResponseProvider
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class TransactionService(
    private val transactionUser: TransactionUser,
    private val transactionAccount: TransactionAccount,
    private val redisClient: RedisClient,
    private val transactional: Transactional,
    private val logger: Logger = Logging.getLogger(TransactionService::class.java)
) {
    fun deposit(userUlid: String, accountId: String, value: BigDecimal): Response<DepositResponse> = Logging.logFor(logger) { it

        it["userUlid"] = userUlid
        it["accountId"] = accountId
        it["value"] = value

        val key = RedisKeyProvider.bankMutexKey(userUlid, accountId)

        return@logFor redisClient.invokeWithMutex(key) {
            return@invokeWithMutex transactional.run {
                val user = transactionUser.findByUlid(userUlid)

                val account = transactionAccount.findByUlidAndUser(accountId, user)?: throw CustomException(ErrorCode.FAILED_TO_FIND_ACCOUNT)

                account.balance = account.balance.add(value)
                account.updatedAt = LocalDateTime.now()
                transactionAccount.save(account)

                ResponseProvider.success(DepositResponse(afterBalance = account.balance))
            }
        }

    }
}