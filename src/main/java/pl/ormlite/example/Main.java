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

public class Main {


    public static void main(String[] args) throws SQLException, IOException, ParseException {

        // this uses h2 but you can change it to match your database
        // String databaseUrl = "jdbc:h2:mem:account";


         String databaseUrlH2 = "jdbc:h2:./database";  //ścieżka do bazy danych H2 w katalogu głównym
        //Baza H2 wymaga dodania do POM dependency nowych
        // ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrlH2);
//
// instantiate the DAO to handle Account with String id
//        Dao<Account, String> accountDao = DaoManager.createDao(connectionSource, Account.class);
//        TableUtils.createTable(connectionSource, Account.class);
//        Account account = new Account();
//        account.setName("Jimi");
//        account.setPassword("1234");
//        accountDao.create(account);

        String databaseUrl = "jdbc:sqlite:database.db";  //ścieżka do bazy danych w katalogu głównym
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrlH2);


        //najpierw usuwa tabele, pozniej tworzy nowe. 'true' na koncu pomija bledy
        // na wypadek usuniecia tabeli ktora nie istnieje
        TableUtils.dropTable(connectionSource, Book.class, true);
        //chroni przed nadpisaniem tablicy, chroni przed wyjatkiem jesli tabela istnieje
        TableUtils.createTableIfNotExists(connectionSource, Book.class);
        //  TableUtils.createTable(connectionSource, Book.class);

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

        Dao<Book, ?> dao = DaoManager.createDao(connectionSource, Book.class);

        dao.create(book);
        connectionSource.close();  //zamyka połączenie z bazą danych, odciąża pamięć
    }

}
