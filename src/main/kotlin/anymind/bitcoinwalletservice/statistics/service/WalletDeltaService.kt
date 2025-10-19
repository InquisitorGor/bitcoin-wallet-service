package anymind.bitcoinwalletservice.statistics.service

import anymind.bitcoinwalletservice.statistics.repository.HourlyDeltasRepository
import anymind.bitcoinwalletservice.statistics.repository.entity.HourlyDeltasEntity
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class WalletDeltaService(
    private val hourlyDeltasRepository: HourlyDeltasRepository,
) {
    fun getCumulativeBalance(
        from: OffsetDateTime,
        to: OffsetDateTime,
    ): List<HourlyDeltasEntity> = hourlyDeltasRepository.findByHourStartBetweenOrderByHourStartAsc(from, to)
}
