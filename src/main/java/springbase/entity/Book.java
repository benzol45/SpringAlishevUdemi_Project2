package springbase.entity;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "author")
    @NotBlank(message = "You must type author")
    private String author;

    @Column(name = "title")
    @NotBlank(message = "You must type book title")
    private String title;

    @Column(name = "year")
    @Min(value = 1000, message = "Incorrect year")
    @Max(value = 3000, message = "Incorrect year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "people_id", referencedColumnName = "id")
    private People person;

    @Column(name = "time_of_given")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfGiven;

    @Transient
    private boolean expired;

    public Book() {
    }

    public Book(int id, String author, String title, int year, People person, Date dateOfGiven, boolean expired) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.year = year;
        this.person = person;
        this.dateOfGiven = dateOfGiven;
        this.expired = expired;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public People getPerson() {
        return person;
    }

    public void setPerson(People person) {
        this.person = person;
    }

    public Date getDateOfGiven() {
        return dateOfGiven;
    }

    public void setDateOfGiven(Date dateOfGiven) {
        this.dateOfGiven = dateOfGiven;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
