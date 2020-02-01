package pl.ormlite.example;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "books")
public class Book {

//bezparametrowy konstruktor Alt+Insert
    public Book() {
    }

    //ID w tabeli (niepowtarzające się)
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "TITLE")
    private String title;

    @DatabaseField(columnName = "PRICE")
    private double price;

}
