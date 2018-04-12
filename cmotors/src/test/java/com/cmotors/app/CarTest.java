package com.cmotors.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import java.sql.SQLException;

import com.cmotors.repository.*;

import org.junit.After;
import org.junit.Before;

@RunWith(JUnit4.class)
public class CarTest {
    CarRepository carRepository;

    @Test
    public void getById() throws SQLException {
        int idToFind = 1;
        assertNotNull(carRepository.getById(idToFind));
    }

    @Test
    public void getAll() throws SQLException {
        assertNotNull(carRepository.getAll());
    }

    @Test
    public void addCar() throws SQLException {
        Car car = new Car();
        car.setId(1);
        car.setMake("Mazda");
        car.setModel("MX-5");
        carRepository.add(car);
        assertNotNull(carRepository.getById(car.getId()));

    }

    @Test
    public void deleteCar() throws SQLException {
        Car car = carRepository.getById(1);
        Car carToTest = carRepository.getById(2);
        carRepository.delete(car);
        assertNull(carRepository.getById(car.getId()).getMake());
        assertNotNull(carRepository.getById(carToTest.getId()));
    }

    @Test
    public void updateCar() throws SQLException {
        Car car = new Car();
        Car carToTest = carRepository.getById(2);
        car.setId(1);
        car.setMake("Mazda");
        car.setModel("MX-5 RF");
        int idToUpdate = 1;
        carRepository.update(idToUpdate, car);
        assertEquals(carRepository.getById(idToUpdate).getMake(), carRepository.getById(car.getId()).getMake());
        assertNotEquals(carToTest.make, carRepository.getById(car.getId()).getMake());
    }

    @Before
    public void initRepository() {
        carRepository = CarRepositoryFactory.getInstance();
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();

        car1.setId(1);
        car1.setMake("Mazda");
        car1.setModel("MX-5");

        car2.setId(2);
        car2.setMake("Mercedes-Benz");
        car2.setModel("S63 AMG Coupe");

        car3.setId(3);
        car3.setMake("Audi");
        car3.setModel("S3 Limousine");

        carRepository.add(car1);
        carRepository.add(car2);
        carRepository.add(car3);
    }
    @After
    public void dropRepository() {
        carRepository.dropRepository();
    }
}
