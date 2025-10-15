package anymind.bitcoinwalletservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BitcoinWalletServiceApplication

fun main(args: Array<String>) {
    runApplication<BitcoinWalletServiceApplication>(*args)
}
