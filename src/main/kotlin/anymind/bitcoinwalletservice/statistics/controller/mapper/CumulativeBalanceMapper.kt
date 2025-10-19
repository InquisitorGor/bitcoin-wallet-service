package anymind.bitcoinwalletservice.statistics.controller.mapper

import anymind.bitcoinwalletservice.statistics.controller.dto.CumulativeBalanceResponse
import anymind.bitcoinwalletservice.statistics.repository.entity.HourlyDeltasEntity
import org.springframework.stereotype.Component

@Component
class CumulativeBalanceMapper {
    fun toResponse(entity: HourlyDeltasEntity): CumulativeBalanceResponse =
        CumulativeBalanceResponse(
            datetime = entity.hourStart,
            amount = entity.delta,
        )

    fun toResponseList(entities: List<HourlyDeltasEntity>): List<CumulativeBalanceResponse> = entities.map { toResponse(it) }
}
