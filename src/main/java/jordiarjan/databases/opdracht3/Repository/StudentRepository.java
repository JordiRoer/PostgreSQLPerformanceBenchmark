package jordiarjan.databases.opdracht3.Repository;

import jordiarjan.databases.opdracht3.DBManager;
import jordiarjan.databases.opdracht3.Faker.Faker;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * Created by jordi_000 on 4-6-2015.
 */
public class StudentRepository extends Repository {

    public StudentRepository(DBManager dbManager) {
        super(dbManager);
    }

    public String insertFakeStudent() throws SQLException
    {
        Faker faker = new Faker();
        String studentNumber = faker.studentNumber();

        PreparedStatement insertStatement = dbManager.getConnection().prepareStatement(
            "INSERT INTO student (age, city, firstname, insertion, lastname, postalcode, sex, phonenumber, street, studentnumber) SELECT ?,?,?,?,?,?,CAST(? AS sex ),?,?,?" +
            "WHERE NOT EXISTS (SELECT 1 FROM student WHERE studentNumber=?)"
        );
        insertStatement.setInt(1, faker.age());
        insertStatement.setString(2, faker.address().cityPrefix());
        insertStatement.setString(3, faker.name().firstName());
        insertStatement.setString(4, faker.name().prefix());
        insertStatement.setString(5, faker.name().lastName());
        insertStatement.setString(6, faker.address().zipCode());
        insertStatement.setObject(7, faker.gender());
        insertStatement.setString(8, faker.phoneNumber().phoneNumber().replaceAll("[a-zA-Z]", ""));
        insertStatement.setString(9, faker.address().streetName());
        insertStatement.setString(10, studentNumber);
        insertStatement.setString(11, studentNumber);

        insertStatement.execute();

        return studentNumber;
    }
}
