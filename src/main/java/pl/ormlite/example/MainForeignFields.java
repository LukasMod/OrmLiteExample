package pl.ormlite.example;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainForeignFields {


    public static void main(String[] args) throws SQLException, IOException, ParseException {

        String databaseUrl = "jdbc:sqlite:database.db";
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);


        TableUtils.dropTable(connectionSource, Book.class, true);
        TableUtils.dropTable(connectionSource, Author.class, true);
        TableUtils.createTableIfNotExists(connectionSource, Book.class);
        TableUtils.createTableIfNotExists(connectionSource, Author.class);


//
//        Book book2 = new Book();
//        book2.setTitle("Krew Elfów");
//        book2.setIsbn("22222");
//        book2.setAddedDate(new Date());
//
//        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
//        String dateInString2 = "2011/10/09";
//        Date date2 = sdf.parse(dateInString);
//
//        book2.setDateRelease(date);
//        book2.setRating("4");
//        book2.setPrice(12.99);
//
//        Book book3 = new Book();
//        book3.setTitle("Metro");
//        book3.setIsbn("33333");
//        book3.setAddedDate(new Date());
//
//        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy/MM/dd");
//        String dateInString3 = "2017/05/06";
//        Date date3 = sdf.parse(dateInString);
//
//        book3.setDateRelease(date);
//        book3.setRating("3");
//        book3.setBorrowed(true);
//        book3.setPrice(50.99);
        Dao<Book, Integer> daoBooks = DaoManager.createDao(connectionSource, Book.class);
        Dao<Author, Integer> daoAuthor = DaoManager.createDao(connectionSource, Author.class);

        Author author = new Author();
        author.setName("Tolkien");

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

        //dodajemy autora do bazy danych i nadajemy ID
        //  daoAuthor.create(author);
        // - nie trzeba, z uwagi na dodatkowe parametry w klasie Book( foreignAutoRefresh i Create)

        //przypisujemy książce autora
        book.setAuthor(author);
        //dodajemy książkę do bazy danych
        daoBooks.create(book);

        //  daoAuthor.refresh(book.getAuthor());
        // - nie trzeba, z uwagi na dodatkowe parametry w klasie Book( foreignAutoRefresh i Create)
        System.out.println("Po zapisie do bazy danych: " + book);


        Book book2 = daoBooks.queryForId(1);
        // daoAuthor.refresh(book2.getAuthor());
        // - nie trzeba, z uwagi na dodatkowe parametry w klasie Book( foreignAutoRefresh i Create)
        System.out.println("Po zapytaniu do bazy danych " + book2);


        book2.getAuthor().setName("Nieznany");
        daoAuthor.createOrUpdate(book2.getAuthor());
        book2 = daoBooks.queryForId(1);
        System.out.println("Po zmianie autora " + book2);

        author = daoAuthor.queryForId(1);
        author.getBooks().forEach(e -> {
            e.setTitle("Blablabla");
            try {
                daoBooks.createOrUpdate(e);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
        daoAuthor.refresh(author);
        author.getBooks().forEach(e -> {
            System.out.println("Zmiana tytułu:  " + e);
        });


        Book book3 = new Book();
        book3.setTitle("Metro");
        book3.setIsbn("33333");
        book3.setAddedDate(new Date());
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy/MM/dd");
        String dateInString3 = "2017/05/06";
        Date date3 = sdf3.parse(dateInString3);
        book3.setDateRelease(date3);
        book3.setRating("3");
        book3.setBorrowed(true);
        book3.setPrice(50.99);

        author.getBooks().add(book3);
        daoAuthor.createOrUpdate(author);


//        dao.create(book);
//        dao.create(book2);
//        dao.create(book3);


        connectionSource.close();  //zamyka połączenie z bazą danych, odciąża pamięć
    }

}
