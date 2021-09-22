package com.vasilisa.cinema.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Seance {
    private int id;
    private int filmId;
    private int price;
    private int freeSeats;

    private LocalDateTime date;
    private String formattedTime;
    private String formattedTimeEnd;
    private String formattedDate;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
        setFormattedTime(timeFormatter.format(date));

        // обраховуємо час закінчення фільму
        if (this.film != null) {
            LocalDateTime endDateTime = date.plus(Duration.of(this.film.getDuration(), ChronoUnit.MINUTES));
            setFormattedTimeEnd(timeFormatter.format(endDateTime));
        }
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }

    public String getFormattedTimeEnd() {
        return formattedTimeEnd;
    }

    public void setFormattedTimeEnd(String formattedTimeEnd) {
        this.formattedTimeEnd = formattedTimeEnd;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(Locale locale) {
        this.formattedDate = dateFormatter.format(date) + ", " + getDate().getDayOfWeek().getDisplayName(TextStyle.FULL, locale);
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }
}
