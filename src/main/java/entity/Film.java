package entity;

import java.util.List;
import java.util.Objects;

public class Film {
    private int id;
    private List<FilmDescription> filmDescriptions;
    private String img;
    private int duration;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<FilmDescription> getFilmDescriptions() {
        return filmDescriptions;
    }

    public void setFilmDescriptions(List<FilmDescription> filmDescriptions) {
        this.filmDescriptions = filmDescriptions;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id && Objects.equals(filmDescriptions, film.filmDescriptions) && Objects.equals(img, film.img);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filmDescriptions, img);
    }
}
