package com.cmotors.repository;

public class CarRepositoryFactory {
    public static CarRepository getInstance() {
        return null;
    }
}

package com.cmotors.repository;

import java.sql.DriverManager;
import java.sql.SQLException;

public class CarRepositoryFactory {
    public static CarRepository getInstance(){
        try {
            String url = "jdbc:hsqldb:hsql://localhost/workdb";
            return new CarRepositoryImpl(DriverManager.getConnection(url));
        }
        catch (SQLException e){
            return null;
        }
    }
}