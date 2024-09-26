package com.example.demo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.example.demo.controller.exception.ExceptionHandlerClass;
import com.example.demo.customException.EmployeeIdNotFoundException;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository repository;

	@Autowired
	ExceptionHandlerClass exceptionHandle;

	public ResponseEntity<Employee> addEmployee(Employee employee) {
		Employee employeeDB = repository.save(employee);
		return new ResponseEntity<Employee>(employeeDB, HttpStatus.CREATED);
	}

	public Map<String, Long> findMaleAndFemaleInOrganization() {
		List<Employee> list = repository.findAll();
		Map<String, Long> maleAndFemaleCount = list.stream()
				.collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
		return maleAndFemaleCount;
	}

	public Employee getEmployeeById(int id) { // TODO Auto-generated method stub
		Employee employee = repository.findById(id).get();
		return employee;
	}

	// What is the avg age of male and female employees
	public Map<String, Double> getAvgAgeMaleAndFemale() {
		List<Employee> employeeList = repository.findAll();
		Map<String, Double> avgAvgOfMaleAndFemale = employeeList.stream()
				.collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingInt(Employee::getAge)));
		return avgAvgOfMaleAndFemale;
	}

	public ResponseEntity<List<Employee>> getAllEmployeesData() {
		// TODO Auto-generated method stub
		List<Employee> list = repository.findAll();
		if (list != null) {
			return new ResponseEntity<List<Employee>>(list, HttpStatus.OK);
		}

		return new ResponseEntity<List<Employee>>(list, HttpStatusCode.valueOf(204));
	}

	public String deleteEmployeeById(int id) throws NoResourceFoundException {
		Optional<Employee> employee = repository.findById(id);
		if (employee.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new EmployeeIdNotFoundException("Employee ID not Found");
		}
		return null;
	}

	public ResponseEntity<Employee> getEmployeeByIdAndName(int id, String name) {
		Employee employee = repository.findByIdAndName(id, name);
		if (employee != null) {
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		} else {
			throw new EmployeeIdNotFoundException("Please provide correct Login Details Id: " + id + " Name:" + name);
		}
	}

	public ResponseEntity<Employee> getEmpByIdAndNameAndAge(int id, String name, int age) {
		// TODO Auto-generated method stub
		Employee employee = repository.findByIdAndNameAndAge(id, name, age);
		if (employee != null) {
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);
		} else {
			throw new EmployeeIdNotFoundException("Incorrect Details: " + id + " Name:" + name + " Age: " + age);
		}
	}

	public void registerAllEmployees() { // TODO Auto-generated method stub
		List<Employee> employeeList = new ArrayList<Employee>();
		employeeList.add(new Employee(111, "Jiya Brein", 32, "Female", "HR", 2011, 25000.0));
		employeeList.add(new Employee(122, "Paul Niksui", 25, "Male", "Sales And Marketing", 2015, 13500.0));
		employeeList.add(new Employee(133, "Martin Theron", 29, "Male", "Infrastructure", 2012, 18000.0));
		employeeList.add(new Employee(144, "Murali Gowda", 28, "Male", "Product Development", 2014, 32500.0));
		employeeList.add(new Employee(155, "Nima Roy", 27, "Female", "HR", 2013, 22700.0));
		employeeList.add(new Employee(166, "Iqbal Hussain", 43, "Male", "Security And Transport", 2016, 10500.0));
		employeeList.add(new Employee(177, "Manu Sharma", 35, "Male", "Account And Finance", 2010, 27000.0));
		employeeList.add(new Employee(188, "Wang Liu", 31, "Male", "Product Development", 2015, 34500.0));
		employeeList.add(new Employee(199, "Amelia Zoe", 24, "Female", "Sales And Marketing", 2016, 11500.0));
		employeeList.add(new Employee(200, "Jaden Dough", 38, "Male", "Security And Transport", 2015, 11000.5));
		employeeList.add(new Employee(211, "Jasna Kaur", 27, "Female", "Infrastructure", 2014, 15700.0));
		employeeList.add(new Employee(222, "Nitin Joshi", 25, "Male", "Product Development", 2016, 28200.0));
		employeeList.add(new Employee(233, "Jyothi Reddy", 27, "Female", "Account And Finance", 2013, 21300.0));
		employeeList.add(new Employee(244, "Nicolus Den", 24, "Male", "Sales And Marketing", 2017, 10700.5));
		employeeList.add(new Employee(255, "Ali Baig", 23, "Male", "Infrastructure", 2018, 12700.0));
		employeeList.add(new Employee(266, "Sanvi Pandey", 26, "Female", "Product Development", 2015, 28900.0));
		employeeList.add(new Employee(277, "Anuj Chettiar", 31, "Male", "Product Development", 2012, 35700.0));
		repository.saveAll(employeeList);

	}

	public Map<String, Long> getMaleAndFemaleCount() { 
		// TODO Auto-generated method stub 
		List<Employee> employees = repository.findAll(); Map<String,
	  Long> genderCOunt = employees.stream()
	  .collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
	  return genderCOunt; }

	// Query 3.2 : Print the name of all departments in the organization? public
	public List<String> getAllDepartment() {
		List<Employee> employees = repository.findAll();
		List<String> listOfDepartments = employees.stream().map(Employee::getDepartment).distinct()
				.collect(Collectors.toList());
		return listOfDepartments;
	}

	// Query 3.3 : What is the average age of male and female employees? public
	public Map<String, Double> getAvgOfMaleAndFemaleEmp() {
		List<Employee> employees = repository.findAll();
		Map<String, Double> avgMaleAndFemale = employees.stream()
				.collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingInt(Employee::getAge)));
		return avgMaleAndFemale;
	}

	// Query 3.4 : Get the details of highest paid employee in the organization?
	public Employee getEmployeeHighestPaidSalary() {
		List<Employee> employees = repository.findAll();
		Optional<Employee> empl = employees.stream()
				.collect(Collectors.maxBy(Comparator.comparing(emp -> emp.getSalary())));
		return empl.get();
	}

	// Query 3.5 : Get the names of all employees who have joined after 2015?

	public List<Employee> getAllEmployeeJoinedAfter2015() {
		List<Employee> employees = repository.findAll();
		List<Employee> employeesData = employees.stream().filter(emp -> emp.getYearOfJoining() > 2015)
				.collect(Collectors.toList());
		return employeesData;
	}

	// Query 3.6 : Count the number of employees in each department? public
	public Map<String, Long> getNoOfEmpByDepart() {
		List<Employee> employees = repository.findAll();
		Map<String, Long> emp = employees.stream()
				.collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
		return emp;
	}

	// Query 3.7 : What is the average salary of each department? public
	public Map<String, Double> getAvgSalaryOfEachDepartment() {

		return repository.findAll().stream().collect(
				Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
	}

	// Query 3.8 : Get the details of youngest male employee in the product
	// development department?

	public Employee getYoungestMaleEmployee() {
		// TODO Auto-generated method stub
		List<Employee> employees = repository.findAll();
		Optional<Employee> emp1 = employees.stream()
				.filter(emp -> emp.getGender().equals("Male") && emp.getDepartment().equals("Product Development"))
				.min(Comparator.comparing(Employee::getAge));
		return emp1.get();
	}

	// Query 3.9 : Who has the most working experience in the organization?
	public Employee getMostExpEmployee() {
		List<Employee> employees = repository.findAll();
		Optional<Employee> employee = employees.stream()
				.collect(Collectors.minBy(Comparator.comparing(Employee::getYearOfJoining)));
		return employee.get();
	}

	// Query 3.10 : How many male and female employees are there in the sales
	// andmarketing team?

	public Map<String, Long> getMaleAndFemaleEmployeesInSalesMarketing() {
		List<Employee> employees = repository.findAll();
		return employees.stream().filter(emp -> emp.getDepartment().equals("Sales And Marketing"))
				.collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
	}

	// Query 3.11 : What is the average salary of male and female employees?
	public Map<String, Double> getAvgSalaryOfMaleAndFemaleEmployee() {
		List<Employee> employees = repository.findAll();
		return employees.stream()
				.collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingDouble(Employee::getSalary)));
	}

	// Query 3.12 : List down the names of all employees in each department?
	public Map<String, List<String>> getAllEmployeesNamesInDepartment() {
		List<Employee> employees = repository.findAll();
		return employees.stream().collect(Collectors.groupingBy(Employee::getDepartment,
				Collectors.mapping(Employee::getName, Collectors.toList())));
	}

	// Query 3.13 : What is the average salary and total salary of the whole
	// organization?

	public Map<String, Double> getAvgSalaryAndWholeSalaryOfOrganization() {
		List<Employee> employees = repository.findAll();
		DoubleSummaryStatistics statistics = employees.stream()
				.collect(Collectors.summarizingDouble(Employee::getSalary));
		Double avgSalary = statistics.getAverage();
		Double totalSalary = statistics.getSum();
		Map<String, Double> dataOfAvgAndTotalSalary = new HashMap();
		dataOfAvgAndTotalSalary.put("AvgSalary", avgSalary);
		dataOfAvgAndTotalSalary.put("TotalSalary", totalSalary);
		dataOfAvgAndTotalSalary.entrySet().stream()
				.forEach(entry -> System.out.print(entry.getKey() + ": " + entry.getValue()));
		return dataOfAvgAndTotalSalary;
	}

	// Query 3.14 : Separate the employees who are younger or equal to 25 years from
	// those employees who are older than 25 years.
	public Map<Boolean, List<Employee>> getEmployeesAgeDeatils() {
		List<Employee> employees = repository.findAll();
		Map<Boolean, List<Employee>> empData = employees.stream()
				.collect(Collectors.partitioningBy(emp -> emp.getAge() > 25));
		return empData;
	}

	// Query 3.15 : Who is the oldest employee in the organization? What is his age
	// and which department he belongs to?

	public Employee getOldestAgeEmployee() {
		List<Employee> employees = repository.findAll();
		Optional<Employee> emp = employees.stream().collect(Collectors.maxBy(Comparator.comparing(Employee::getAge)));
		return emp.get();
	}

}
