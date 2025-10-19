package anymind.bitcoinwalletservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.function.Supplier

@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@SpringBootTest
@EmbeddedKafka(
    partitions = 1,
    topics = [
        "wallet-topup-events",
    ],
)
class BaseIT {
    @Autowired lateinit var mockMvc: MockMvc

    companion object {
        @Container
        private val postgreSqlContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:17")

        @JvmStatic
        @DynamicPropertySource
        fun addDatasourceProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", Supplier { postgreSqlContainer.getJdbcUrl() })
            registry.add("spring.datasource.password", Supplier { postgreSqlContainer.getPassword() })
            registry.add("spring.datasource.username", Supplier { postgreSqlContainer.getUsername() })
        }
    }
}
