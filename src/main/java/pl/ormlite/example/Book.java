package pl.ormlite.example;


import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "books")
public class Book {

//bezparametrowy konstruktor Alt+Insert
    public Book() {
    }

    // TWORZYMY POLE OBCE
@DatabaseField (columnName = "AUTHOR_ID", foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
private Author author;
    //utworzy się kolumna z nazwą author_id. Lepiej nazwąć jawną naze, aby uniknąć pomyłek
    //foreignAutoCreate - pomagają w obsłudze
    //foreignAutoRefresh - pomagają w obsłudze

    //ID w tabeli (niepowtarzające się)
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "TITLE", canBeNull = false) //nigdy nie może być nullem
    private String title;

    @DatabaseField(columnName = "DESCRIPTION", dataType = DataType.LONG_STRING) //dla Stringów powyżej 256 znaków
    private String description;

    @DatabaseField(columnName = "ISBN", unique = true) //zawsze unikalne elementy
    private String isbn;

    @DatabaseField(columnName = "ADDED_DATE")
    private Date addedDate;

    @DatabaseField(columnName = "DATE_RELEASE", dataType = DataType.DATE_STRING, format = "yyyy-MM-DD")
    private Date dateRelease;

    @DatabaseField(columnName = "RATING",width = 1) //maksymalna ilość znaków (nie działa np. na sqlite, ale na h2 działa)
    private String rating;

    @DatabaseField(columnName = "BORROWED", defaultValue = "false") //domyślna wartość
    private boolean borrowed;

    @DatabaseField(columnName = "PRICE")
    private double price;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Date getDateRelease() {
        return dateRelease;
    }

    public void setDateRelease(Date dateRelease) {
        this.dateRelease = dateRelease;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author=" + author +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isbn='" + isbn + '\'' +
                ", addedDate=" + addedDate +
                ", dateRelease=" + dateRelease +
                ", rating='" + rating + '\'' +
                ", borrowed=" + borrowed +
                ", price=" + price +
                '}';
    }
}
