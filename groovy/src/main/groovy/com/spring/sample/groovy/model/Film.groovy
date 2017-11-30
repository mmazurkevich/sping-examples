package com.spring.sample.groovy.model

import java.time.LocalDateTime

class Film {
    String title
    String originalTitle
    int year
    String director
    String description
    List<Genre> genres
    String contentRating
    LocalDateTime releaseDate
    int duration
}
