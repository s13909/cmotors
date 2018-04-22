package com.cmotors.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.cmotors.*;
import com.cmotors.repository.CarRepository;
import com.cmotors.repository.CarRepositoryImpl;

import org.junit.Before;

@RunWith(MockitoJUnitRunner.class)
public class CarMockedTest {
    CarRepository carRepository;

    @Mock
    private Connection connectionMock;
    @Mock
    private PreparedStatement addCarStmt;
    @Mock
    private PreparedStatement updateCarStmt;
    @Mock
    private PreparedStatement deleteCarStmt;
    @Mock
    private PreparedStatement getAllCarsStmt;
    @Mock
    private PreparedStatement getCarByIdStmt;
    @Mock
    CarRepository carRepositoryMock;

    @Before
    public void initRepository() throws SQLException {
        when(connectionMock.prepareStatement("INSERT INTO Car (make, model) VALUES (?, ?)"))
                .thenReturn(addCarStmt);
        when(connectionMock.prepareStatement("SELECT id, make, model FROM Car")).thenReturn(getAllCarsStmt);
        when(connectionMock.prepareStatement("SELECT id, make, model FROM Car WHERE id = ?"))
                .thenReturn(getCarByIdStmt);
        when(connectionMock.prepareStatement("UPDATE Car SET make = ?, model = ? WHERE id = ?"))
                .thenReturn(updateCarStmt);
        when(connectionMock.prepareStatement("DELETE FROM Car WHERE id = ?")).thenReturn(deleteCarStmt);
        carRepository = new CarRepositoryImpl();
        carRepository.setConnection(connectionMock);
        carRepositoryMock = mock(CarRepositoryImpl.class);
        verify(connectionMock).prepareStatement("INSERT INTO Car (make, model) VALUES (?, ?)");
        verify(connectionMock).prepareStatement("SELECT id, make, model FROM Car");
        verify(connectionMock).prepareStatement("SELECT id, make, model FROM Car WHERE id = ?");
        verify(connectionMock).prepareStatement("UPDATE Car SET make = ?, model = ? WHERE id = ?");
        verify(connectionMock).prepareStatement("DELETE FROM Car WHERE id = ?");
    }

    @Test
    public void checkAdd() throws Exception {
        when(addCarStmt.executeUpdate()).thenReturn(1);

        Car car = new Car();
        car.setId(1);
        car.setMake("Mazda");
        car.setModel("MX-5");
        assertEquals(1, carRepository.add(car));
        verify(addCarStmt, times(1)).setString(1, "Mazda");
        verify(addCarStmt, times(1)).setString(2, "MX-5");
        verify(addCarStmt).executeUpdate();
    }

    @Test
    public void checkAddInOrder() throws Exception {
        InOrder inorder = inOrder(addCarStmt);
        when(addCarStmt.executeUpdate()).thenReturn(1);
        Car car = new Car();
        car.setId(1);
        car.setMake("Mazda");
        car.setModel("MX-5");
        assertEquals(1, carRepository.add(car));

        inorder.verify(addCarStmt, times(1)).setString(1, "Mazda");
        inorder.verify(addCarStmt, times(1)).setString(2, "MX-5");
        inorder.verify(addCarStmt).executeUpdate();
    }

    @Test(expected = IllegalStateException.class)
    public void checkExceptionWhenAddNullAdding() throws Exception {
        when(addCarStmt.executeUpdate()).thenThrow(new SQLException());
        Car car = new Car();
        car.setId(1);
        car.setMake(null);
        car.setModel("MX-5");
        assertEquals(1, carRepository.add(car));
    }

    @Test
    public void checkDelete() throws Exception {
        when(deleteCarStmt.executeUpdate()).thenReturn(1);
        Car car = new Car();
        car.setId(1);
        car.setMake("Mazda");
        car.setModel("MX-5");
        assertEquals(1, carRepository.delete(car));
        verify(deleteCarStmt, times(1)).setInt(1, car.getId());
        verify(deleteCarStmt).executeUpdate();
    }

    @Test
    public void checkGetAll() throws Exception {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getInt("id")).thenCallRealMethod();
        when(mockedResultSet.getString("make")).thenCallRealMethod();
        when(mockedResultSet.getString("model")).thenCallRealMethod();
        when(getAllCarsStmt.executeQuery()).thenReturn(mockedResultSet);
        assertEquals(1, carRepository.getAll().size());
        verify(getAllCarsStmt, times(1)).executeQuery();
        verify(mockedResultSet, times(1)).getInt("id");
        verify(mockedResultSet, times(1)).getString("make");
        verify(mockedResultSet, times(1)).getString("model");
        verify(mockedResultSet, times(2)).next();
    }

    @Test
    public void checkGetById() throws Exception {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getInt("id")).thenCallRealMethod();
        when(mockedResultSet.getString("make")).thenCallRealMethod();
        when(mockedResultSet.getString("model")).thenCallRealMethod();
        when(getCarByIdStmt.executeQuery()).thenReturn(mockedResultSet);
        assertNotNull(carRepository.getById(1));
        verify(getCarByIdStmt, times(1)).executeQuery();
        verify(mockedResultSet, times(1)).getInt("id");
        verify(mockedResultSet, times(1)).getString("make");
        verify(mockedResultSet, times(1)).getString("model");
        verify(mockedResultSet, times(2)).next();
    }

    @Test
    public void checkUpdate() throws Exception {
        Car car = new Car();
        car.setId(1);
        car.setMake("Mazda");
        car.setModel("MX-5");
        doReturn(car).when(carRepositoryMock).getById(isA(int.class));
        int idToUpdate = 1;
        carRepository.update(idToUpdate , car);
        assertEquals(carRepositoryMock.getById(1).getMake(), car.getMake());
        verify(updateCarStmt, times(1)).setString(1, "Mazda");
        verify(updateCarStmt, times(1)).setString(2, "MX-5");
        verify(updateCarStmt).executeUpdate();
    }

    abstract class AbstractResultSet implements ResultSet {
        int i = 0;

        @Override
        public boolean next() throws SQLException {
            if (i == 1)
                return false;
            i++;
            return true;
        }

        @Override
        public int getInt(String s) throws SQLException{
            return 1;
        }

        @Override
        public String getString(String columnLabel) throws SQLException{
            switch (columnLabel) {
                case "make":
                    return "Mazda";
                case "model":
                    return "MX-5";
                default:
                    return "";
            }
        }
    }

}