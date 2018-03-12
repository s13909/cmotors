package com.cmotors.repository;

import java.util.List;

import com.cmotors.app.Car;

public interface CarRepository {
	public void initDatabase();

	public Car getById(int id);

	public List<Car> getAll();

	public void add(Car car);

	public void delete(Car car);

	public void update(int oldCarId, int newCarId);
}