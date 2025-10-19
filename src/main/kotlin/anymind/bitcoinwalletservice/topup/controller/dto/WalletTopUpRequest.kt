package anymind.bitcoinwalletservice.topup.controller.dto

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.OffsetDateTime

class WalletTopUpRequest(
    @NotNull val datetime: OffsetDateTime,
    @NotNull @DecimalMin(value = "0.0", inclusive = false) val amount: BigDecimal,
)
