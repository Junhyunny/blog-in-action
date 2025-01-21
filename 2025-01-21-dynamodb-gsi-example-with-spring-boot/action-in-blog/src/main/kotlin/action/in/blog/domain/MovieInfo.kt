package action.`in`.blog.domain

import software.amazon.awssdk.services.dynamodb.model.AttributeValue

data class MovieInfo(
    var actor: String,
    var movie: String,
    var role: String,
    var year: Int,
    var genre: String
) {
    companion object {
        fun of(item: Map<String, AttributeValue>) = MovieInfo(
            item["Actor"]!!.s(),
            item["Movie"]!!.s(),
            item["Role"]!!.s(),
            item["Year"]!!.n().toInt(),
            item["Genre"]!!.s(),
        )
    }
}
