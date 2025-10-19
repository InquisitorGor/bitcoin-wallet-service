package anymind.bitcoinwalletservice.statistics.service

import anymind.bitcoinwalletservice.statistics.repository.HourlyDeltasRepository
import anymind.bitcoinwalletservice.statistics.repository.TopUpEventRepository
import anymind.bitcoinwalletservice.statistics.repository.WalletRepository
import anymind.bitcoinwalletservice.statistics.repository.entity.HourlyDeltasEntity
import anymind.bitcoinwalletservice.statistics.repository.entity.TopUpEventEntity
import anymind.bitcoinwalletservice.topup.producer.dto.TopUpWalletEvent
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.UUID

private const val WALLET_ID = "walletId"

@Service
class WalletTopUpService(
    val topUpEventRepository: TopUpEventRepository,
    val walletRepository: WalletRepository,
    val hourlyDeltasRepository: HourlyDeltasRepository,
) {
    @Transactional
    fun processTopUp(events: List<TopUpWalletEvent>) {
        if (events.isEmpty()) return

        saveEvents(events)

        updateWalletState(events)

        computeHourlyDeltas(events)
    }

    private fun updateWalletState(events: List<TopUpWalletEvent>) {
        val balance = events.sumOf { it.amount }

        val walletEntity = walletRepository.findByWalletIdForUpdate(WALLET_ID)
        walletEntity.balance = walletEntity.balance.add(balance)
    }

    private fun saveEvents(events: List<TopUpWalletEvent>) {
        val entities =
            events
                .distinctBy { event -> event.eventId }
                .map { event ->
                    TopUpEventEntity(
                        eventId = UUID.fromString(event.eventId),
                        walletId = event.walletId,
                        eventTs = event.datetime,
                        amount = event.amount,
                        payload = null,
                        createdAt = OffsetDateTime.now(),
                    )
                }

        val savedCount = topUpEventRepository.saveAll(entities)
        println("Successfully saved $savedCount out of ${events.size} events to database")
    }

    private fun getCurrentWalletBalance(): BigDecimal =
        try {
            walletRepository.findByWalletIdForUpdate(WALLET_ID).balance
        } catch (e: Exception) {
            BigDecimal.ZERO
        }

    private fun computeHourlyDeltas(events: List<TopUpWalletEvent>) {
        val currentWalletBalance = getCurrentWalletBalance()

        val eventsByHour =
            events.groupBy { event ->
                event.datetime
                    .withOffsetSameInstant(ZoneOffset.UTC)
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0)
            }

        val sortedHours = eventsByHour.keys.sorted()

        var runningBalance = currentWalletBalance
        val hourlyDeltas = mutableListOf<HourlyDeltasEntity>()

        sortedHours.forEach { hourStart ->
            val hourEvents = eventsByHour[hourStart]!!
            val hourDelta = hourEvents.sumOf { it.amount }
            runningBalance = runningBalance.add(hourDelta)

            hourlyDeltas.add(
                HourlyDeltasEntity(
                    hourStart = hourStart,
                    delta = runningBalance,
                ),
            )
        }

        if (hourlyDeltas.isNotEmpty()) {
            val savedCount = hourlyDeltasRepository.upsertAll(hourlyDeltas)
            println("Successfully saved $savedCount hourly deltas to database")
        }
    }
}
