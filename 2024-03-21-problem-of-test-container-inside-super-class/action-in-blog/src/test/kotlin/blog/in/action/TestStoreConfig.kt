package blog.`in`.action

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
abstract class TestStoreConfig : TestContainerDatabase() {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    fun flushAndClear() {
        this.entityManager.flush()
        this.entityManager.clear()
    }
}