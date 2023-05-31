package config;

public class ConfigDataBase {
    public static final String STUDY_GROUP_TABLE = "study_groups";
    public static final String GROUP_ADMIN_TABLE = "group_admins";
    public static final String USER_TABLE = "users";
    public static final String STUDY_GROUP_TABLE_ID_COLUMN = "id";
    public static final String STUDY_GROUP_TABLE_NAME_COLUMN = "name";
    public static final String STUDY_GROUP_TABLE_X_COLUMN = "coordinates_x";
    public static final String STUDY_GROUP_TABLE_Y_COLUMN = "coordinates_y";
    public static final String STUDY_GROUP_TABLE_CREATION_DATE_COLUMN = "creation_date";
    public static final String STUDY_GROUP_TABLE_STUDENT_COUNT_COLUMN = "student_count";
    public static final String STUDY_GROUP_TABLE_SHOULD_BE_EXPELLED_COLUMN = "should_be_expelled";
    public static final String STUDY_GROUP_TABLE_AVERAGE_MARK_COLUMN = "average_mark";
    public static final String STUDY_GROUP_TABLE_SEMESTER_COLUMN = "semester";
    public static final String GROUP_ADMIN_TABLE_ID_GROUP_COLUMN = "group_id";
    public static final String GROUP_ADMIN_TABLE_NAME_COLUMN = "name";
    public static final String GROUP_ADMIN_TABLE_EYE_COLOR_COLUMN = "eye_color";
    public static final String GROUP_ADMIN_TABLE_HAIR_COLOR_COLUMN = "hair_color";
    public static final String GROUP_ADMIN_TABLE_NATIONALITY_COLUMN = "nationality";
    public static final String USER_TABLE_ID_COLUMN = "id";
    public static final String USER_TABLE_USERNAME_COLUMN = "username";
    public static final String USER_TABLE_PASSWORD_COLUMN = "password";
    public static final String JDBC_DRIVER = "org.postgresql.Driver";
    public static final String SELECT_ALL_FROM = "SELECT * FROM ";
    public static final String WHERE = " WHERE ";
    public static final int WRONG_ID = -1;
    public static final String GET_USER_COUNT = "SELECT COUNT(1) FROM " + USER_TABLE;
    public static final String INSERT_USER = "INSERT INTO " +
            USER_TABLE + " (" +
            USER_TABLE_ID_COLUMN + ", " +
            USER_TABLE_USERNAME_COLUMN + ", " +
            USER_TABLE_PASSWORD_COLUMN + ") VALUES (?, ?, ?)";
    public static final String SELECT_USER_BY_USERNAME = "SELECT * FROM " + USER_TABLE +
            " WHERE " + USER_TABLE_USERNAME_COLUMN + " = ?";
    public static final String SELECT_USER_BY_USERNAME_AND_PASSWORD = SELECT_USER_BY_USERNAME + " AND " +
            USER_TABLE_PASSWORD_COLUMN + " = ?";



}
