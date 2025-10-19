package anymind.bitcoinwalletservice.statistics.repository

import anymind.bitcoinwalletservice.statistics.repository.entity.WalletEntity
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface WalletRepository : JpaRepository<WalletEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM WalletEntity e WHERE e.walletId = :walletId")
    fun findByWalletIdForUpdate(walletId: String): WalletEntity
}
