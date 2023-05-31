package utilities;

import data.StudyGroup;
import exceptions.DataBaseAuthorizationException;
import exceptions.DatabaseHandlingException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayDeque;

import static config.ConfigDataBase.DELETE_MOVIE_BY_ID;

public class DataBaseCollectionManager {
    private DataBaseHandler dataBaseHandler;
    private DataBaseUserManager dataBaseUserManager;
    public DataBaseCollectionManager(DataBaseHandler dataBaseHandler, DataBaseUserManager dataBaseUserManager){
        this.dataBaseHandler=dataBaseHandler;
        this.dataBaseUserManager=dataBaseUserManager;
    }

    public ArrayDeque<StudyGroup> getCollection(){
        ArrayDeque<StudyGroup> studyGroups=new ArrayDeque<>();
        return studyGroups;
    }

    public DataBaseUserManager getDataBaseUserManager() {
        return dataBaseUserManager;
    }
    public void removeStudyGroupById(Integer id) throws DatabaseHandlingException {
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement=dataBaseHandler.getPreparedStatement(DELETE_MOVIE_BY_ID, false);
            preparedStatement.setInt(1, id);
            if(preparedStatement.executeUpdate()==0) throw new DatabaseHandlingException();
        }catch (SQLException e){
            throw new DatabaseHandlingException();
        }finally {
            dataBaseHandler.closePreparedStatement(preparedStatement);
        }
    }
}
