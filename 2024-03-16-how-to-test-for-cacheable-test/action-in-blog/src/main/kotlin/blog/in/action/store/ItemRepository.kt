package blog.`in`.action.store

import blog.`in`.action.domain.ItemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository : JpaRepository<ItemEntity, Long> {
    fun findByCategoryIdAndSubCategoryId(categoryId: Int, subCategoryId: Int): List<ItemEntity>
}