package anymind.bitcoinwalletservice.statistics.consumer

import anymind.bitcoinwalletservice.statistics.service.WalletTopUpService
import anymind.bitcoinwalletservice.topup.producer.dto.TopUpWalletEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class WalletTopUpConsumer(
    val walletTopUpService: WalletTopUpService,
) {
    @KafkaListener(
        topics = ["\${topics.topUpEvent}"],
        containerFactory = "kafkaListenerContainerFactory",
    )
    @Transactional
    fun listen(
        @Payload events: List<TopUpWalletEvent>,
        @Header(KafkaHeaders.RECEIVED_TOPIC) topic: String,
    ) {
        try {
            println("Received ${events.size} events from topic: $topic")
            walletTopUpService.processTopUp(events)
            println("Successfully processed ${events.size} events")
        } catch (e: Exception) {
            println("Failed to process events: ${e.message}")
            throw e
        }
    }
}
