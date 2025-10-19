package anymind.bitcoinwalletservice.statistics.repository

import anymind.bitcoinwalletservice.statistics.repository.entity.HourlyDeltasEntity
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

interface HourlyDeltasRepository :
    JpaRepository<HourlyDeltasEntity, OffsetDateTime>,
    HourlyDeltasCustomRepository {
    fun findByHourStartBetweenOrderByHourStartAsc(
        from: OffsetDateTime,
        to: OffsetDateTime,
    ): List<HourlyDeltasEntity>
}

interface HourlyDeltasCustomRepository {
    fun upsertAll(deltas: List<HourlyDeltasEntity>): Int
}

@Repository
class HourlyDeltasRepositoryImpl(
    @PersistenceContext private val entityManager: EntityManager,
) : HourlyDeltasCustomRepository {
    @Transactional
    override fun upsertAll(deltas: List<HourlyDeltasEntity>): Int {
        if (deltas.isEmpty()) return 0

        val sql =
            """
            INSERT INTO hourly_deltas (hour_start, delta)
            VALUES (?, ?)
            ON CONFLICT (hour_start)
            DO UPDATE SET delta = hourly_deltas.delta + EXCLUDED.delta
            """.trimIndent()

        val query = entityManager.createNativeQuery(sql)
        deltas.forEach { delta ->
            query.setParameter(1, delta.hourStart)
            query.setParameter(2, delta.delta)
            query.executeUpdate()
        }

        entityManager.flush()
        return deltas.size
    }
}
