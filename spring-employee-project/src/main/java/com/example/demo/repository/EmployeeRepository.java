package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


	Employee findByIdAndNameAndAge(int id, String name, int age);

	Employee findByIdAndName(int id, String name);

}
