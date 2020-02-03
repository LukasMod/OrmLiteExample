package pl.ormlite.example.Main;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import pl.ormlite.example.Model.Book;

import java.io.IOException;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {


    public static void main(String[] args) throws SQLException, IOException, ParseException {

        // this uses h2 but you can change it to match your database
        // String databaseUrl = "jdbc:h2:mem:account";


        // String databaseUrlH2 = "jdbc:h2:./database";  //ścieżka do bazy danych H2 w katalogu głównym
        //Baza H2 wymaga dodania do POM dependency nowych
        // ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrlH2);
//
// instantiate the DAO to handle Account with String id
//        pl.ormlite.example.Dao<Account, String> accountDao = DaoManager.createDao(connectionSource, Account.class);
//        TableUtils.createTable(connectionSource, Account.class);
//        Account account = new Account();
//        account.setName("Jimi");
//        account.setPassword("1234");
//        accountDao.create(account);

        String databaseUrl = "jdbc:sqlite:database.db";  //ścieżka do bazy danych w katalogu głównym
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);


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

        Book book2 = new Book();
        book2.setTitle("Krew Elfów");
        book2.setIsbn("22222");
        book2.setAddedDate(new Date());

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
        String dateInString2 = "2011/10/09";
        Date date2 = sdf.parse(dateInString);

        book2.setDateRelease(date);
        book2.setRating("4");
//        book2.setBorrowed();  //przyjmie domyślną (false)
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
//pl.ormlite.example.Dao<Parametr klasy, ID>


//        System.out.println(book);
//
//        //zmiana wybranej wartości z pozycji
//        book.setTitle("Hobbit");
//        dao.update(book);
//        System.out.println("After update: " + book.getTitle());
//
//        //niszczenie obiektu
//        dao.delete(book);
//        System.out.println("After delete " + book);
//
//        //wyszukiwanie, jeśli nie znajdzie, zwroci Nulla
//      book = dao.queryForId(book.getId());
//        System.out.println("After query " + book);


        dao.create(book);
        dao.create(book2);
        dao.create(book3);

        // '*'  oznacza wszystko. Wybierz wszystko z książek (nazwa bazy danych)
        //Tworzy listę zawierającą tablicę Stringów
        GenericRawResults<String[]> rawResults = dao.queryRaw("SELECT * FROM books");
        //z tego obiektu ^^ wyciągamy wynik
        List<String[]> result = rawResults.getResults();
        result.forEach(e -> {
            for (String s : e) {
                System.out.println(s);   //wypisuje wszystkie książki
            }
        });

        System.out.println();
        System.out.println();
        GenericRawResults<String[]> where = dao.queryRaw("SELECT * FROM books WHERE title = 'Metro'");
        //z tego obiektu ^^ wyciągamy wynik
        List<String[]> resultsWhere = where.getResults();
        resultsWhere.forEach(e -> {
            for (String s : e) {
                System.out.println(s);   //wypisuje wszystkie książki
            }
        });
        System.out.println();
        System.out.println();
        GenericRawResults<String[]> selectMinMax = dao.queryRaw("SELECT MIN(price), MAX(price) FROM books");
        //z tego obiektu ^^ wyciągamy wynik
        List<String[]> resultsMinMax = selectMinMax.getResults();
        resultsMinMax.forEach(e -> {
            for (String s : e) {
                System.out.println(s);   //wypisuje wszystkie książki
            }
        });
        System.out.println();
        System.out.println();
        GenericRawResults<String[]> selectCount = dao.queryRaw("SELECT count(*) FROM books where borrowed = 1");
        //sqlite obsługuje boolean jako 0 / 1, ale H2 już rozróżnia false / true
        //z tego obiektu ^^ wyciągamy wynik
        List<String[]> resultsCount = selectCount.getResults();
        resultsCount.forEach(e -> {
            for (String s : e) {
                System.out.println(s);   //wypisuje wszystkie książki
            }
        });
        System.out.println();
        System.out.println();

        //Inny sposób zapytania OrmLite
        double maxUnits = dao.queryRawValue("select AVG(price) from books");
        System.out.println(maxUnits);




        connectionSource.close();  //zamyka połączenie z bazą danych, odciąża pamięć
    }

}
