package anymind.bitcoinwalletservice.topup.controller.mapper

import anymind.bitcoinwalletservice.topup.controller.dto.WalletTopUpRequest
import anymind.bitcoinwalletservice.topup.service.dto.TopUpWalletEvent
import org.mapstruct.Mapper

@Mapper
abstract class TopUpWalletDtoMapper {
    abstract fun toTopUpWalletEvent(dto: WalletTopUpRequest): TopUpWalletEvent
}
