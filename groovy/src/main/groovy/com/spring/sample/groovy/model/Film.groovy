package com.spring.sample.groovy.model

import java.time.LocalDateTime

class Film {
    private String title
    private String originalTitle
    private int year
    private String director
    private String description
    private List<Genre> genres
    private String contentRating
    private LocalDateTime releaseDate
    private int duration
}
