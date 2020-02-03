package pl.ormlite.example.Main;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import pl.ormlite.example.Dao.AuthorDao;
import pl.ormlite.example.Dao.BookDao;
import pl.ormlite.example.Model.Author;
import pl.ormlite.example.Model.Book;
import pl.ormlite.example.Utils.DataCreator;
import pl.ormlite.example.Utils.DbManager;

import java.sql.SQLException;
import java.util.List;

public class MainDbManager {

    public static void main(String[] args) throws SQLException {
        DbManager.initDatabase();

        Author author = DataCreator.author();
        AuthorDao authorDao = new AuthorDao(DbManager.getConnectionSource());
        authorDao.createOrUpdate(author);
        List<Author> lista = authorDao.queryForAll(Author.class);
        lista.forEach(e-> {
            System.out.println(e);
        });

        Book book  = DataCreator.firstBook();
        BookDao bookDao = new BookDao(DbManager.getConnectionSource());
        bookDao.createOrUpdate(book);
        bookDao.queryWhere("TITLE", "Władca pierścieni");


//        //przekazujemy informacje o autorze, ze w ID uzywamy integer
//        Dao<Author, Integer> daoAuthor = DaoManager.createDao(DbManager.getConnectionSource(), Author.class);


//        daoAuthor.createOrUpdate(author);
        //^^niepotrzebne z uwagi na AuthorDao i CommonDao
        DbManager.closeConnectionSource();
    }
}
