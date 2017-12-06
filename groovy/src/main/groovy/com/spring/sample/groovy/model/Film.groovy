package com.spring.sample.groovy.model

import groovy.transform.ToString

import java.time.LocalDateTime

@ToString class Film {
    String title
    String originalTitle
    String year
    String director
    String description
    List<Genre> genres
    String contentRating
    LocalDateTime releaseDate
    int duration
}
