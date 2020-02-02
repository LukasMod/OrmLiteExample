package pl.ormlite.example;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.h2.jdbc.JdbcConnection;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainPostgres {


    public static void main(String[] args) throws SQLException, IOException, ParseException {
        String url = "jdbc:postgresql://localhost:5432/ormlite";
        ConnectionSource connectionSource = new JdbcConnectionSource(url, "postgres", "admin");

        TableUtils.dropTable(connectionSource, Book.class, true);
        TableUtils.createTableIfNotExists(connectionSource, Book.class);

        TableUtils.dropTable(connectionSource, Author.class, true);
        TableUtils.createTableIfNotExists(connectionSource, Author.class);

        Dao<Book, Integer> daoBook = DaoManager.createDao(connectionSource, Book.class);
        Dao<Author, Integer> daoAuthor = DaoManager.createDao(connectionSource, Author.class);

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

        Author author = new Author();
        author.setName("Tolkien");

        book.setAuthor(author);
        daoBook.createOrUpdate(book);






        connectionSource.close();

    }

}
