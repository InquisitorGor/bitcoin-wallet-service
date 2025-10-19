package anymind.bitcoinwalletservice.topup.controller

import anymind.bitcoinwalletservice.topup.controller.dto.WalletTopUpRequest
import anymind.bitcoinwalletservice.topup.controller.mapper.TopUpWalletDtoMapper
import anymind.bitcoinwalletservice.topup.producer.dto.TopUpEventPublisher
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wallet/balance")
class WalletTopUpController(
    val topUpEventPublisher: TopUpEventPublisher,
    val topUpWalletDtoMapper: TopUpWalletDtoMapper,
) {
    @PostMapping("/api/v1/topUp")
    fun topUp(
        @RequestBody @Valid request: WalletTopUpRequest,
    ) = topUpEventPublisher.sendTopUpRecord(topUpWalletDtoMapper.toTopUpWalletEvent(request))
}
