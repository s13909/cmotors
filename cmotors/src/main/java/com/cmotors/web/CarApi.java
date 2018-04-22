package com.cmotors.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.cmotors.app.Car;
import com.cmotors.repository.CarRepository;
import com.cmotors.repository.CarRepositoryFactory;

@RestController
public class CarApi {

    @Autowired
    CarRepository carRepository;

    @RequestMapping("/")
    public String index() {
        return "This is non rest, just checking if everything works.";
    }

    @RequestMapping(value = "/cmotors/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Car getCar(@PathVariable("id") int id) throws SQLException {
        carRepository = CarRepositoryFactory.getInstance();
        return carRepository.getById(id);
    }

    @RequestMapping(value = "/cmotors", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Car> getCars() throws SQLException {
        carRepository = CarRepositoryFactory.getInstance();
        List<Car> cars = new LinkedList<Car>();
        for (Car c : carRepository.getAll()) {
                cars.add(c);
        }
        return cars;
    }

    @RequestMapping(value = "/cmotors", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long addCar(@RequestBody Car c) {
        carRepository = CarRepositoryFactory.getInstance();
        return new Long(carRepository.add(c));
    }

    @RequestMapping(value = "/cmotors/{id}", method = RequestMethod.DELETE)
    public Long deleteAccount(@PathVariable("id") int id) throws SQLException {
        carRepository = CarRepositoryFactory.getInstance();
        return new Long(carRepository.delete(carRepository.getById(id)));
    }

    @RequestMapping(value = "/cmotors/{id}", method = RequestMethod.PUT)
    public Long updateAccount(@PathVariable("id") int id, @RequestBody Car c) throws SQLException {
        carRepository = CarRepositoryFactory.getInstance();
        return new Long(carRepository.update(id, c));
    }

}