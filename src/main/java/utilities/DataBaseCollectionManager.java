package utilities;

import data.*;
import exceptions.DataBaseAuthorizationException;
import exceptions.DatabaseHandlingException;
import exceptions.IncorrectValuesForGroupException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayDeque;

import static config.ConfigDataBase.*;

public class DataBaseCollectionManager {
    private DataBaseHandler dataBaseHandler;
    private DataBaseUserManager dataBaseUserManager;

    public DataBaseCollectionManager(DataBaseHandler dataBaseHandler, DataBaseUserManager dataBaseUserManager) {
        this.dataBaseHandler = dataBaseHandler;
        this.dataBaseUserManager = dataBaseUserManager;
    }

    private StudyGroup createStudyGroup(ResultSet resultSet)  {
        try {
            Integer id = resultSet.getInt(STUDY_GROUP_TABLE_ID_COLUMN);
            String name = resultSet.getString(STUDY_GROUP_TABLE_NAME_COLUMN);
            Coordinates coordinates = new Coordinates(resultSet.getDouble(STUDY_GROUP_TABLE_X_COLUMN), resultSet.getFloat(STUDY_GROUP_TABLE_Y_COLUMN));
            LocalDateTime creationTime= resultSet.getTimestamp(STUDY_GROUP_TABLE_CREATION_DATE_COLUMN).toLocalDateTime();
            int studentCount=resultSet.getInt(STUDY_GROUP_TABLE_STUDENT_COUNT_COLUMN);
            Integer shouldBeExpelled=resultSet.getInt(STUDY_GROUP_TABLE_SHOULD_BE_EXPELLED_COLUMN);
            double averageMark=resultSet.getDouble(STUDY_GROUP_TABLE_AVERAGE_MARK_COLUMN);
            Semester semester=(Semester) resultSet.getObject(STUDY_GROUP_TABLE_SEMESTER_COLUMN);
            Person admin=new Person(resultSet.getString(GROUP_ADMIN_TABLE_NAME_COLUMN),resultSet.getTime(GROUP_ADMIN_TABLE_BIRTHDAY_COLUMN),
                    (ColorEye) resultSet.getObject(GROUP_ADMIN_TABLE_EYE_COLOR_COLUMN),(ColorHair) resultSet.getObject(GROUP_ADMIN_TABLE_HAIR_COLOR_COLUMN),
                    (Country)   resultSet.getObject(GROUP_ADMIN_TABLE_NATIONALITY_COLUMN));

            User owner = new User(resultSet.getString(USER_TABLE_USERNAME_COLUMN),resultSet.getString(USER_TABLE_PASSWORD_COLUMN),true);
            return new StudyGroup(id,
                    name,
                    coordinates,
                    creationTime,
                    studentCount,
                    shouldBeExpelled,
                    averageMark,
                    semester,
                    admin,owner);


        }catch ( SQLException e){
            return null;
        }
    }

        public ArrayDeque<StudyGroup> getCollection () {
            ArrayDeque<StudyGroup> studyGroups = new ArrayDeque<>();
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = dataBaseHandler.getPreparedStatement(SELECT_ALL_STUDY_GROUPS, false);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    studyGroups.add(new StudyGroup());
                }
            }catch (SQLException e){
                e.printStackTrace();
            }finally {
                dataBaseHandler.closePreparedStatement(preparedStatement);
            }
            return studyGroups;


        }

        public DataBaseUserManager getDataBaseUserManager () {
            return dataBaseUserManager;
        }
        public void removeStudyGroupById (Integer id) throws DatabaseHandlingException {
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = dataBaseHandler.getPreparedStatement(DELETE_MOVIE_BY_ID, false);
                preparedStatement.setInt(1, id);
                if (preparedStatement.executeUpdate() == 0) throw new DatabaseHandlingException();
            } catch (SQLException e) {
                throw new DatabaseHandlingException();
            } finally {
                dataBaseHandler.closePreparedStatement(preparedStatement);
            }
        }
        public StudyGroup insertStudyGroup(StudyGroup studyGroup, User user){
        PreparedStatement preparedStatement=null;
        try {
            dataBaseHandler.setCommitMode();
            dataBaseHandler.setSavepoint();
            preparedStatement=dataBaseHandler.getPreparedStatement(INSERT_STUDY_GROUP, true);
            preparedStatement.setInt(1,studyGroup.getId());
            preparedStatement.setString(2,studyGroup.getName());
            preparedStatement.setDouble(3,studyGroup.getCoordinates().getCoordinatesX());
            preparedStatement.setFloat(4,studyGroup.getCoordinates().getCoordinatesY());
            preparedStatement.setTimestamp(5,Timestamp.valueOf(studyGroup.getCreationDate()));
            preparedStatement.setInt(6,studyGroup.getStudentsCount());
            preparedStatement.setInt(7,studyGroup.getShouldBeExpelled());
            preparedStatement.setDouble(8,studyGroup.getAverageMark());
            preparedStatement.setObject(9,studyGroup.getSemesterEnum());
            preparedStatement.setObject(10, );

        }
        return null;
        }
    }
