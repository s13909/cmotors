package com.cmotors.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.cmotors.app.Car;

public interface CarRepository {
	public Connection getConnection();

	public void setConnection(Connection connection) throws SQLException;	

	public void initDatabase() throws SQLException;

	public void dropRepository();

	public Car getById(int id);

	public List<Car> getAll();

	public void add(Car car);

	public void delete(Car car);

	public void update(int oldCarId, int newCarId);
}