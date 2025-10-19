package anymind.bitcoinwalletservice.statistics.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "top_up_event")
data class TopUpEventEntity(
    @Id
    @Column(name = "event_id")
    val eventId: UUID,
    @Column(name = "wallet_id", nullable = false)
    val walletId: String,
    @Column(name = "event_ts", nullable = false)
    val eventTs: OffsetDateTime,
    @Column(name = "amount", nullable = false, precision = 30, scale = 8)
    val amount: BigDecimal,
    @Column(name = "payload", columnDefinition = "jsonb")
    val payload: String? = null,
    @Column(name = "created_at")
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
)
