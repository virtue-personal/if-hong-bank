package org.example.domains.transactions.model

import java.math.BigDecimal

data class DepositResponse(
    val afterBalance: BigDecimal
)