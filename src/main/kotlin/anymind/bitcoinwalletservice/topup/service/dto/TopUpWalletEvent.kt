package anymind.bitcoinwalletservice.topup.service.dto

import java.math.BigDecimal
import java.time.OffsetDateTime

class TopUpWalletEvent(
    val datetime: OffsetDateTime,
    val amount: BigDecimal,
)
