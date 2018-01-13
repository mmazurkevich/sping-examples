package com.spring.sample.groovy.repository

import com.spring.sample.groovy.model.Film
import groovy.sql.Sql

class FilmRepository {

    def sql
    def filmDataSet

    FilmRepository() {
        sql = Sql.newInstance("jdbc:sqlite:./groovy/films.db", "org.sqlite.JDBC")
        filmDataSet = sql.dataSet("film")
    }

    def getLastId() {
        def result = sql.firstRow('select max(id) as id from film')
        return result.id
    }

    def save(Film film) {
        filmDataSet.add(title: film.title, originalTitle: film.originalTitle, year: film.year, rating: film.rating,
                genres: film.genres.toString(), director: film.director, writers: film.writers.toString(), stars: film.stars.toString(),
                duration: film.duration, storyline: film.storyline, tagline: film.tagline, country: film.country, language: film.language,
                releaseDate: film.releaseDate)
    }
}
