package anymind.bitcoinwalletservice.topup.producer.dto

import anymind.bitcoinwalletservice.topup.service.dto.TopUpWalletEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.UUID

private const val WALLET_ID = "walletId"

@Service
class TopUpEventPublisher(
    val kafkaTemplate: KafkaTemplate<String, Any>,
    @Value("\${topics.topUpEvent}") val topUpEventTopic: String,
) {
    fun sendTopUpRecord(topUpWalletEvent: TopUpWalletEvent) =
        kafkaTemplate
            .send(
                topUpEventTopic,
                WALLET_ID,
                TopUpWalletEvent(
                    UUID.randomUUID().toString(),
                    WALLET_ID,
                    topUpWalletEvent.datetime,
                    topUpWalletEvent.amount,
                ),
            ).get()
}
