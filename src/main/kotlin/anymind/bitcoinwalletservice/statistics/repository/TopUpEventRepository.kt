package anymind.bitcoinwalletservice.statistics.repository

import anymind.bitcoinwalletservice.statistics.repository.entity.TopUpEventEntity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

private const val SQL = """
            INSERT INTO top_up_event (event_id, wallet_id, event_ts, amount, payload, created_at)
            VALUES (?, ?, ?, ?, ?, ?)
            ON CONFLICT (event_id) DO NOTHING
            """

@Repository
class TopUpEventRepository(
    @PersistenceContext
    private val entityManager: EntityManager,
) {
    @Transactional
    fun saveAll(events: List<TopUpEventEntity>): Int {
        if (events.isEmpty()) return 0

        val query = entityManager.createNativeQuery(SQL)
        var batchCount = 0

        events.forEach { event ->
            query.setParameter(1, event.eventId)
            query.setParameter(2, event.walletId)
            query.setParameter(3, event.eventTs)
            query.setParameter(4, event.amount)
            query.setParameter(5, event.payload)
            query.setParameter(6, event.createdAt)
            query.executeUpdate()
            batchCount++
        }

        entityManager.flush()
        return batchCount
    }
}
