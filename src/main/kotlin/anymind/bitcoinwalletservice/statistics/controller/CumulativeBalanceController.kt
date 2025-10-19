package anymind.bitcoinwalletservice.statistics.controller

import anymind.bitcoinwalletservice.statistics.controller.dto.CumulativeBalanceResponse
import anymind.bitcoinwalletservice.statistics.controller.mapper.CumulativeBalanceMapper
import anymind.bitcoinwalletservice.statistics.service.WalletDeltaService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime

@RestController
@RequestMapping("/api/statistics")
class CumulativeBalanceController(
    private val walletDeltaService: WalletDeltaService,
    private val cumulativeBalanceMapper: CumulativeBalanceMapper,
) {
    @GetMapping("/cumulative-balance")
    fun getCumulativeBalance(
        @RequestParam from: OffsetDateTime,
        @RequestParam to: OffsetDateTime,
    ): List<CumulativeBalanceResponse> = cumulativeBalanceMapper.toResponseList(walletDeltaService.getCumulativeBalance(from, to))
}
