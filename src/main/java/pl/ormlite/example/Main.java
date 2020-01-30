package pl.ormlite.example;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;

public class Main {


    public static void main(String[] args) throws SQLException, IOException {

        // this uses h2 but you can change it to match your database
        // String databaseUrl = "jdbc:h2:mem:account";


        String databaseUrl = "jdbc:sqlite:database.db";  //ścieżka do bazy danych w katalogu głównym
        String databaseUrlH2 = "jdbc:H2:./database";  //ścieżka do bazy danych H2 w katalogu głównym

// create a connection source to our database
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);

        //Baza H2 wymaga dodania do POM dependency nowych
        // ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrlH2);


// instantiate the DAO to handle Account with String id
        Dao<Account, String> accountDao = DaoManager.createDao(connectionSource, Account.class);
        TableUtils.createTable(connectionSource, Account.class);

        Account account = new Account();
        account.setName("Jimi");
        account.setPassword("1234");
        accountDao.create(account);

        connectionSource.close();  //zamyka połączenie z bazą danych, odciąża pamięć
    }

}
