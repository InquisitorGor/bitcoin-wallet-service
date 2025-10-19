package anymind.bitcoinwalletservice.statistics.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table(name = "wallet")
data class WalletEntity(
    @Id
    @Column(name = "id")
    val id: Long,
    @Column(name = "balance", nullable = false)
    var balance: BigDecimal,
    @Column(name = "wallet_id", nullable = false)
    val walletId: String,
    @Column(name = "last_event_ts")
    val lastEventTs: OffsetDateTime = OffsetDateTime.now(),
)
