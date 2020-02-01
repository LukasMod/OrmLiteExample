package pl.ormlite.example;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.query.In;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainQuery {


    public static void main(String[] args) throws SQLException, IOException, ParseException {

        String databaseUrl = "jdbc:sqlite:database.db";
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);


        TableUtils.dropTable(connectionSource, Book.class, true);
        TableUtils.createTableIfNotExists(connectionSource, Book.class);

        Book book = new Book();
        book.setTitle("Władca Pierścieni");
        book.setIsbn("11111");
        book.setAddedDate(new Date());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String dateInString = "2012/11/11";
        Date date = sdf.parse(dateInString);

        book.setDateRelease(date);
        book.setRating("1");
        book.setBorrowed(true);
        book.setPrice(33.99);

        Book book2 = new Book();
        book2.setTitle("Krew Elfów");
        book2.setIsbn("22222");
        book2.setAddedDate(new Date());

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
        String dateInString2 = "2011/10/09";
        Date date2 = sdf.parse(dateInString);

        book2.setDateRelease(date);
        book2.setRating("4");
        book2.setPrice(12.99);

        Book book3 = new Book();
        book3.setTitle("Metro");
        book3.setIsbn("33333");
        book3.setAddedDate(new Date());

        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy/MM/dd");
        String dateInString3 = "2017/05/06";
        Date date3 = sdf.parse(dateInString);

        book3.setDateRelease(date);
        book3.setRating("3");
        book3.setBorrowed(true);
        book3.setPrice(50.99);

        Dao<Book, Integer> dao = DaoManager.createDao(connectionSource, Book.class);

        dao.create(book);
        dao.create(book2);
        dao.create(book3);

        // służy do skomplikowanych zapytań do bazy danych
        QueryBuilder<Book, Integer> queryBuilder = dao.queryBuilder(); //budujemy query
        queryBuilder.where().eq("TITLE", "Metro"); //definiujemy co ma robić (znajdź w TITLE słowo Metro
        PreparedQuery<Book> prepare = queryBuilder.prepare(); //przygotuj
        List<Book> result = dao.query(prepare); //z przygotowanego zrób listę
        result.forEach(e -> {
            System.out.println();
            System.out.println(e);     //wydrukuj
            System.out.println();
        });

        //druga, prostsza opcja. To samo zapytanie w jednej linii
        List<Book> result2 = dao.query(dao.queryBuilder().
                where().
                eq("TITLE", "Władca Pierścieni").
                prepare());
        result2.forEach(e -> {
            System.out.println();
            System.out.println(e);     //wydrukuj
            System.out.println();
        });

//trudniejsze zapytanie
        List<Book> result3 = dao.query(dao.queryBuilder().
                where().
                eq("TITLE", "Władca Pierścieni").
                and().
                eq("PRICE", "33.99").
                prepare());
        result3.forEach(e -> {
            System.out.println();
            System.out.println(e);     //wydrukuj
            System.out.println();
        });

//UpdateBuilder

        UpdateBuilder<Book, Integer> updateBuilder = dao.updateBuilder();
        updateBuilder.updateColumnValue("DESCRIPTION", "Część 2");
        updateBuilder.where().isNull("DESCRIPTION").and().eq("TITLE", "Metro");
        int booksUpdate = updateBuilder.update();
        System.out.println(booksUpdate);
        System.out.println();


        connectionSource.close();  //zamyka połączenie z bazą danych, odciąża pamięć
    }

}
