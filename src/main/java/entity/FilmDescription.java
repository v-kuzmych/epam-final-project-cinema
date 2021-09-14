package entity;

import java.util.Objects;

public class FilmDescription {
    private int filmId;
    private int languageId;
    private String name;
    private String description;

    public FilmDescription() {}

    public FilmDescription(int languageId, String name, String description) {
        this.languageId = languageId;
        this.name = name;
        this.description = description;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
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
        FilmDescription that = (FilmDescription) o;
        return filmId == that.filmId && languageId == that.languageId && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, languageId, name, description);
    }
}
