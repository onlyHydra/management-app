package concurs.repo;

import concurs.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class RepoUsers implements IRepoUsers {

    private JdbcUtils utils;
    private static final Logger logger = LogManager.getLogger();

    public RepoUsers(Properties properties){
        utils = new JdbcUtils(properties);
    }

    @Override
    public User findOne(String username, String password) {
        logger.traceEntry();
        Connection connection = utils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Users WHERE Username = ? AND Password = ?")){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()){
                    String name = resultSet.getString("Username");
                    String pass = resultSet.getString("Password");
                    return new User(name, pass);
                }
            }
        }catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        logger.traceEntry();
        ArrayList<User> l = new ArrayList<>();
        Connection connection = utils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Users")){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    String name = resultSet.getString("Username");
                    String pass = resultSet.getString("Password");
                    User user = new User(name, pass);
                    l.add(user);
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
        }
        return l;
    }
}
