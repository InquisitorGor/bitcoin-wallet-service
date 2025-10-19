package anymind.bitcoinwalletservice.topup.producer.dto

import java.math.BigDecimal
import java.time.OffsetDateTime

class TopUpWalletEvent(
    val eventId: String,
    val walletId: String,
    val datetime: OffsetDateTime,
    val amount: BigDecimal,
)
