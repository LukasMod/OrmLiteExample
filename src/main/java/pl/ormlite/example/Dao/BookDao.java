package pl.ormlite.example.Dao;

import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import pl.ormlite.example.Model.Book;

import java.sql.SQLException;
import java.util.List;

public class BookDao extends CommonDao {
    public BookDao(ConnectionSource connectionSource) {
        super(connectionSource);
    }

    //będziemy przygotowywać tutaj nietypowe zapytania, dotyczące np. tylko książek


    //Bierze getDao i wywołuje zapytanie queryRow
    public List<String[]> queryRaw() throws SQLException {
        GenericRawResults<String[]> where = getDao(Book.class).queryRaw("SELECT * FROM books WHERE title = 'Hobbit'");
        return where.getResults();
    }

    //przekazujemy nazwę kolumny, wartość
    public List<Book> queryWhere(String columnName, String value) throws SQLException {
        QueryBuilder<Book, Integer> queryBuilder = getQueryBuilder(Book.class); //budowanie queryBuilder
        queryBuilder.where().eq(columnName, value); //zapytanie - znajdź elementy w których w danej kolumnie jest ta wartość
        PreparedQuery<Book> preparedQuery = queryBuilder.prepare(); //wywołanie zapytania na bazie danych
        return getDao(Book.class).query(preparedQuery); //wynik

    }

}
