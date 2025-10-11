package com.study.mf.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "games")
public class Game implements Serializable {
    private static Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 50)
    private String console;
    @Column(nullable = false)
    private Integer year;

    public Game(){}

    public Game(Long id, String name, String console, Integer year) {
        this.id = id;
        this.name = name;
        this.console = console;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Game game)) return false;
        return Objects.equals(getId(), game.getId()) && Objects.equals(getName(), game.getName()) && Objects.equals(getConsole(), game.getConsole()) && Objects.equals(getYear(), game.getYear());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getConsole(), getYear());
    }

    @Override
    public String toString() {
        return "Game { " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", console='" + console + '\'' +
                ", year=" + year +
                " }";
    }
}
