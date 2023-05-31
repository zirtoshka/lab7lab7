package utilities;

import data.StudyGroup;

import java.util.ArrayDeque;

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
}
