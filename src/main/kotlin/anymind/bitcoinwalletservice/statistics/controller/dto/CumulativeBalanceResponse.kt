package anymind.bitcoinwalletservice.statistics.controller.dto

import java.math.BigDecimal
import java.time.OffsetDateTime

data class CumulativeBalanceResponse(
    val datetime: OffsetDateTime,
    val amount: BigDecimal,
)
