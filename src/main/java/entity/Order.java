package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Order {
    private int id;
    private int seanceId;
    private int userId;
    private LocalDateTime date;
    private int price;
    private int ticketsCount;

    private List<OrderItem> orderItems;
    private Seance seance;
    private User user;
    private String formattedDateTime;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeanceId() {
        return seanceId;
    }

    public void setSeanceId(int seanceId) {
        this.seanceId = seanceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
        setFormattedDateTime(dateTimeFormatter.format(date));
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTicketsCount() {
        return ticketsCount;
    }

    public void setTicketsCount(int ticketsCount) {
        this.ticketsCount = ticketsCount;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFormattedDateTime() {
        return formattedDateTime;
    }

    public void setFormattedDateTime(String formattedDateTime) {
        this.formattedDateTime = formattedDateTime;
    }
}
