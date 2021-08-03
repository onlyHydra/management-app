package concurs.repo;

import concurs.model.Participant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class RepoParticip implements IRepoParticip {

    private JdbcUtils utils;
    private static final Logger logger = LogManager.getLogger();

    public RepoParticip(Properties props){
        logger.info("Initializing RepoParticip with props: {}", props);
        utils = new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection connection = utils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT (*) AS [SIZE] FROM Participanti;")){
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    logger.traceExit(resultSet.getInt("SIZE"));
                    return resultSet.getInt("SIZE");
                }
            }
        }catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return 0;
    }

    @Override
    public void save(Participant entity) {
        logger.traceEntry();
        Connection connection = utils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Participanti VALUES (?,?,?,?,?);")){
            //preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getNume());
            preparedStatement.setString(3,entity.getEchipa());
            preparedStatement.setInt(4,entity.getCapacitate());
            preparedStatement.setInt(5,entity.getIdCursa());
            int result = preparedStatement.executeUpdate();
            System.out.println("Am adaugat "+result+" elemente in Participanti");
        }catch (SQLException ex){
            logger.error(ex.getMessage());
        }

    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry();
        Connection connection = utils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Participanti WHERE IDParticipant = ?")){
            preparedStatement.setInt(1, integer);
            int result = preparedStatement.executeUpdate();
            System.out.println("Am sters "+result+" elemente din Participanti");
        }catch (SQLException ex){
            logger.error(ex.getMessage());

        }

    }

    @Override
    public void update(Integer integer, Participant entity) {
        logger.traceEntry();
        Connection connection = utils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Participanti SET IDParticipant = ?, Nume = ?, Echipa = ?, Capacitate = ?, IDCursa = ? WHERE IDParticipant = ?")){
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getNume());
            preparedStatement.setString(3,entity.getEchipa());
            preparedStatement.setInt(4,entity.getCapacitate());
            preparedStatement.setInt(5,entity.getIdCursa());
            preparedStatement.setInt(6, integer);
            preparedStatement.executeUpdate();
            int result = preparedStatement.executeUpdate();
            System.out.println("Am updatat "+result+" elemente din Participanti");
        }catch (SQLException ex){
            logger.traceEntry(ex.getMessage());
        }
    }

    @Override
    public Iterable<Participant> findByTeam(String name) {
        logger.traceEntry();
        Connection connection = utils.getConnection();
        ArrayList<Participant> l = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Participanti WHERE Echipa Like ?")){
            preparedStatement.setString(1,name+"%");
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    Integer idParticipant = resultSet.getInt("IDParticipant");
                    String nume = resultSet.getString("Nume");
                    String echipa = resultSet.getString("Echipa");
                    Integer capacitate = resultSet.getInt("Capacitate");
                    Integer idCursa = resultSet.getInt("IDCursa");
                    Participant p = new Participant(idParticipant,nume,echipa,capacitate,idCursa);
                    l.add(p);
                }
            }
        }catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return l;
    }

    @Override
    public Participant findOne(Integer integer) {
        logger.traceEntry();
        Connection connection = utils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Participanti WHERE IDParticipant = ?")){
            preparedStatement.setInt(1, integer);
            try (ResultSet result = preparedStatement.executeQuery()){
                if(result.next()){
                    Integer idParticipant = result.getInt("IDParticipant");
                    String nume = result.getString("Nume");
                    String echipa = result.getString("Echipa");
                    Integer capacitate = result.getInt("Capacitate");
                    Integer idCursa = result.getInt("IDCursa");
                    return new Participant(idParticipant,nume,echipa,capacitate,idCursa);
                }
            }
        }catch (SQLException ex){
            logger.traceEntry(ex.getMessage());
        }
        return null;
    }

    @Override
    public Iterable<Participant> findAll() {
        logger.traceEntry();
        Connection connection = utils.getConnection();
        ArrayList<Participant> l = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Participanti")){
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    Integer idParticipant = resultSet.getInt("IDParticipant");
                    String nume = resultSet.getString("Nume");
                    String echipa = resultSet.getString("Echipa");
                    Integer capacitate = resultSet.getInt("Capacitate");
                    Integer idCursa = resultSet.getInt("IDCursa");
                    Participant p = new Participant(idParticipant,nume,echipa,capacitate,idCursa);
                    l.add(p);
                }
            }
        }catch (SQLException ex){
            logger.error(ex.getMessage());
        }
        return l;
    }
}
