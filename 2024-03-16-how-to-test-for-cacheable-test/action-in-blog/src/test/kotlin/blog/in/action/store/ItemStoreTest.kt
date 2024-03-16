package blog.`in`.action.store

import blog.`in`.action.domain.ItemEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.cache.CacheManager

@SpringBootTest
class ItemStoreTest {

    @Autowired
    lateinit var cacheManager: CacheManager

    @MockBean
    lateinit var itemRepository: ItemRepository

    @Autowired
    lateinit var itemStore: ItemStore

    @BeforeEach
    fun setUp() {
        cacheManager.getCache("items")?.clear()
    }

    @Test
    fun saveInCache() {
        `when`(itemRepository.findByCategoryIdAndSubCategoryId(1, 2))
            .thenReturn(listOf(ItemEntity(id = 1, name = "Candy", categoryId = 1, subCategoryId = 2)))


        val result = itemStore.items(1, 2)


        val cache = cacheManager.getCache("items")!!
        val resultInCache = cache.get(listOf(1, 2))?.get() as List<ItemEntity>
        assertEquals(result.size, resultInCache.size)
        assertEquals(result[0].id, resultInCache[0].id)
        assertEquals(result[0].name, resultInCache[0].name)
        assertEquals(result[0].categoryId, resultInCache[0].categoryId)
        assertEquals(result[0].subCategoryId, resultInCache[0].subCategoryId)
    }

    @Test
    fun reuseCache() {
        `when`(itemRepository.findByCategoryIdAndSubCategoryId(1, 2))
            .thenReturn(listOf(ItemEntity(id = 1, name = "Candy", categoryId = 1, subCategoryId = 2)))


        itemStore.items(1, 2)
        itemStore.items(1, 2)


        verify(itemRepository, times(1)).findByCategoryIdAndSubCategoryId(1, 2)
    }
}