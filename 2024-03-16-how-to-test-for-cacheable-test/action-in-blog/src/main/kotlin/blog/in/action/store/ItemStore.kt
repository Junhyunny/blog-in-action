package blog.`in`.action.store

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository

@Repository
class ItemStore(private val itemRepository: ItemRepository) {

    @Cacheable(value = ["items"], key = "{#categoryId, #subCategoryId}")
    fun items(categoryId: Int, subCategoryId: Int) = itemRepository.findByCategoryIdAndSubCategoryId(categoryId, subCategoryId)
}