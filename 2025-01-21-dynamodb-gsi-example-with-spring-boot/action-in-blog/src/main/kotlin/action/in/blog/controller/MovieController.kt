package action.`in`.blog.controller

import action.`in`.blog.repository.MovieRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/movies")
class MovieController(
    private val movieRepository: MovieRepository
) {
    @GetMapping("/genres")
    fun getGenres(
        @RequestParam genre: String,
        @RequestParam startYear: Int,
        @RequestParam endYear: Int
    ) = movieRepository.findByGenre(genre, startYear, endYear)
}