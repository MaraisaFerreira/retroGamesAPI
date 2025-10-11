package com.study.mf.data.dto;

import com.study.mf.model.Game;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class GameDTO extends RepresentationModel<GameDTO> {

    private Long id;
    private String name;
    private String console;
    private Integer year;

    public GameDTO(){}

    public GameDTO(Long id, String name, String console, Integer year) {
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
