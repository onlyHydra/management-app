package concurs.repo;

import concurs.model.Concurs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class RepoConcurs implements IRepoConcurs {

    private JdbcUtils utils;
    private static final Logger logger = LogManager.getLogger();

    public RepoConcurs(Properties props){
        logger.info("Initializing RepoConcurs with props: {}", props);
        utils = new JdbcUtils(props);
    }

    @Override
    public void update(Integer id, Concurs entity) {
        logger.traceEntry();
        Connection connection = utils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Concurs SET IDCursa = ?, Nume = ?, Distanta = ?, Participanti = ?, Capacitate = ? WHERE IDCursa = ?")){
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getNume());
            preparedStatement.setDouble(3, entity.getDistanta());
            preparedStatement.setInt(4, entity.getNumar());
            preparedStatement.setInt(5, entity.getCapacitate());
            preparedStatement.setInt(6, id);
            int result = preparedStatement.executeUpdate();
            System.out.println("Am updatat "+result+" elemente din Concurs");
        }catch (SQLException ex){
            logger.error(ex.getMessage());
        }
    }

    @Override
    public Iterable<Concurs> findByCap(Integer cap) {
        logger.traceEntry();
        ArrayList<Concurs> l = new ArrayList<>();
        Connection connection = utils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Concurs WHERE Capacitate = ?")){
            preparedStatement.setInt(1, cap);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Integer idCursa = resultSet.getInt("IDCursa");
                    String nume = resultSet.getString("Nume");
                    Double dist = resultSet.getDouble("Distanta");
                    Integer capacitate = resultSet.getInt("Capacitate");
                    Integer nr = resultSet.getInt("Participanti");
                    Concurs c = new Concurs(idCursa,nume,dist,capacitate,nr);
                    l.add(c);
                }
            }
        }catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return l;
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection connection = utils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS [SIZE] FROM Concurs")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    logger.traceExit("Size: "+resultSet.getInt("SIZE"));
                    return resultSet.getInt("SIZE");
                }
            }
        }catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return 0;
    }

    @Override
    public Concurs findOne(Integer integer) {
        logger.traceEntry();
        Connection connection = utils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Concurs WHERE IDCursa = ?")){
            preparedStatement.setInt(1,integer);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    Integer idCursa = resultSet.getInt("IDCursa");
                    String nume = resultSet.getString("Nume");
                    Double dist = resultSet.getDouble("Distanta");
                    Integer cap = resultSet.getInt("Capacitate");
                    Integer nr = resultSet.getInt("Participanti");
                    return new Concurs(idCursa,nume,dist,cap,nr);
                }
            }
        }catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return null;
    }

    @Override
    public Iterable<Concurs> findAll() {
        logger.traceEntry();
        ArrayList<Concurs> l = new ArrayList<>();
        Connection connection = utils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Concurs")){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    Integer idCursa = resultSet.getInt("IDCursa");
                    String nume = resultSet.getString("Nume");
                    Double dist = resultSet.getDouble("Distanta");
                    Integer cap = resultSet.getInt("Capacitate");
                    Integer nr = resultSet.getInt("Participanti");
                    Concurs c = new Concurs(idCursa,nume,dist,cap,nr);
                    l.add(c);
                }
            }
        }catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return l;
    }
}
