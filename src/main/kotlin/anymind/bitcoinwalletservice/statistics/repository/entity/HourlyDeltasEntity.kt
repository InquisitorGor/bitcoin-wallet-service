package anymind.bitcoinwalletservice.statistics.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table(name = "hourly_deltas")
data class HourlyDeltasEntity(
    @Id
    @Column(name = "hour_start")
    val hourStart: OffsetDateTime,
    @Column(name = "delta", nullable = false, precision = 30, scale = 8)
    val delta: BigDecimal = BigDecimal.ZERO,
)
