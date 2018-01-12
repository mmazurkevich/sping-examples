package com.spring.sample.groovy.repository

import groovy.sql.Sql

class FilmRepository {

    def sql

    FilmRepository() {
        sql = Sql.newInstance("jdbc:sqlite:./groovy/films.db", "org.sqlite.JDBC")
        sql.execute("drop table if exists person")
        sql.execute("create table person (id integer, name string)")

        def people = sql.dataSet("person")
        people.add(id:1, name:"leo")
        people.add(id:2,name:'yui')

        sql.eachRow("select * from person") {
            println("id=${it.id}, name= ${it.name}")
        }
    }
}
