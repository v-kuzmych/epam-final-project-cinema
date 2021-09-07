package bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class Seance {
    private int id;
    private int filmId;
    private int price;

    private LocalDateTime date;
    private String formatedTime;
    private String formatedDate;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private Film film;
    private Hall hall;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
        setFormatedTime(timeFormatter.format(date));
    }

    public String getFormatedTime() {
        return formatedTime;
    }

    public void setFormatedTime(String formatedTime) {
        this.formatedTime = formatedTime;
    }

    public String getFormatedDate() {
        return formatedDate;
    }

    public void setFormatedDate(Locale locale) {
        this.formatedDate = dateFormatter.format(date) + ", " + getDate().getDayOfWeek().getDisplayName(TextStyle.FULL, locale);
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }
}
