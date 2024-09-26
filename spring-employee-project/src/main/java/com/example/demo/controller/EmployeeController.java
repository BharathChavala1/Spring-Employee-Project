package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;

import jakarta.validation.Valid;

@Controller
@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	// Get all employees
	@GetMapping(path = "/getAllEmployees", produces = { "application/json", "application/xml" })
	public ResponseEntity<?> getAllEmployees() {
		HttpHeaders httpheaders = new HttpHeaders();
		httpheaders.add("security-token", "bharath@123");
		httpheaders.add("key-name", "Bharath");
		ResponseEntity<List<Employee>> list = employeeService.getAllEmployeesData();
		return new ResponseEntity<>(list, httpheaders, HttpStatus.OK);
	}

	// GetEmloyeeDetails by id and name using path variable
	@ResponseBody

	@RequestMapping(path = "/getEmpById/{Id}/Name/{Name}", method = RequestMethod.GET)
	public ResponseEntity<Employee> getEmpByIdAndName(@PathVariable(name = "Id") int id,

			@PathVariable(name = "Name") String name) {
		return employeeService.getEmployeeByIdAndName(id, name);
	}

	// RestAPI service Integration with ICICI application

	@ResponseBody
	@RequestMapping(path = "/getCustomerDetailsFromEmployeeProject", method = RequestMethod.POST)
	public ResponseEntity<?> getCusDetails(@RequestBody Customer details) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Customer> customer = restTemplate.getForEntity("http://localhost:8080/ICICI/getCustomerDetails",
				Customer.class);
		Customer customerData = customer.getBody();
		return new ResponseEntity<>(customerData, HttpStatus.OK);
	}

	// Get Employee Details By id and name and age

	@ResponseBody
	@RequestMapping(path = "/getEmpByIdAndNameAndAge/", method = RequestMethod.GET)
	public ResponseEntity<Employee> getEmpByIdAndNameAndAge(@RequestParam(name = "id") int id,

			@RequestParam(name = "name") String name, @RequestParam(name = "age") int age) {
		return employeeService.getEmpByIdAndNameAndAge(id, name, age);
	}

	// addEmployee
	@PostMapping(path = "/addEmployee", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Employee> addEmployee(@RequestHeader(required = false, name = "key-name") String key,

			@RequestBody @Valid Employee employee) {
		System.out.println("Key-Name: " + key);
		return employeeService.addEmployee(employee);
	}

	// Finding male and female employees in Organization
	@GetMapping("/maleAndFemaleCount")
	public Map<String, Long> findingMaleAndFemaleEmployee() {
		Map<String, Long> maleAndFemaleCount = employeeService.findMaleAndFemaleInOrganization();
		return maleAndFemaleCount;
	}

	@GetMapping("/getEmployeeById/{employeeID}")
	public Employee getEmployeeById(@PathVariable(name = "employeeID") int id) {
		Employee employee = employeeService.getEmployeeById(id);
		return employee;
	}

	// What is the avg age of male and female employees
	@GetMapping("/getEmployeeAvgAge")
	public Map<String, Double> getEmployeeAvgAge() {
		return employeeService.getAvgAgeMaleAndFemale();
	}

	// delete record by Id
	@ResponseBody
	@RequestMapping(path = "/deleteById/{id}", method = RequestMethod.DELETE)
	public String deleteById(@PathVariable int id) throws NoResourceFoundException {
		String message = employeeService.deleteEmployeeById(id);
		return message;
	}

	@ResponseBody
	@RequestMapping(path = "/saveListOfEmployees", method = RequestMethod.POST)
	public ResponseEntity<String> saveAllEmployees() {
		employeeService.registerAllEmployees();
		return new ResponseEntity("Employess saved successfully", HttpStatusCode.valueOf(200));
	}

	// How many male and female are their in the organization
	@ResponseBody
	@RequestMapping(path = "/getMaleAndFemaleCount", method = RequestMethod.GET)
	public ResponseEntity<?> getMaleAndFemaleCount() {
		Map<String, Long> genderCount = employeeService.getMaleAndFemaleCount();
		return new ResponseEntity(genderCount, HttpStatus.OK);
	}

	// Query 3.2 : Print the name of all departments in the organization?
	@ResponseBody
	@RequestMapping(path = "/getDepartmentDetails", method = RequestMethod.GET)
	public ResponseEntity<?> getAllDepartmentDetails() {
		List<String> departDetails = employeeService.getAllDepartment();
		return new ResponseEntity(departDetails, HttpStatus.OK);
	}

	// Query 3.3 : What is the average age of male and female employees?
	@ResponseBody
	@RequestMapping(path = "/getAvgAgeOfMaleAndFemale", method = RequestMethod.GET)
	public ResponseEntity<?> getAvgAgeOfMaleAndFemale() {
		Map<String, Double> empAvgAge = employeeService.getAvgOfMaleAndFemaleEmp();
		return new ResponseEntity(empAvgAge, HttpStatus.OK);
	}

	// Query 3.4 : Get the details of highest paid employee in the organization?
	@ResponseBody
	@RequestMapping(path = "/getHighestPainEmpDetails", method = RequestMethod.GET)
	public ResponseEntity<?> getHighestPaidEmp() {
		Employee emp = employeeService.getEmployeeHighestPaidSalary();
		return new ResponseEntity(emp, HttpStatus.OK);
	}

	// Query 3.5 : Get the names of all employees who have joined after 2015?
	@ResponseBody
	@RequestMapping(path = "/getEmployeesJoinedAfter2015", method = RequestMethod.GET)
	public ResponseEntity<?> getEmpJoinedAfter2015() {
		List<Employee> empList = employeeService.getAllEmployeeJoinedAfter2015();
		return new ResponseEntity(empList, HttpStatus.OK);
	}

	// Query 3.6 : Count the number of employees in each department?
	@ResponseBody
	@RequestMapping(path = "/getEmployeesCountInDepartment", method = RequestMethod.GET)
	public ResponseEntity<?> getEmployeesCountInDepartment() {
		Map<String, Long> empList = employeeService.getNoOfEmpByDepart();
		return new ResponseEntity(empList, HttpStatus.OK);

	}

	// Query 3.7 : What is the average salary of each department?

	@ResponseBody

	@RequestMapping(path = "/getAvgSalaryDepartment", method = RequestMethod.GET)
	public ResponseEntity<?> getAvgAgeSalaryDepartment() {
		Map<String, Double> empList = employeeService.getAvgSalaryOfEachDepartment();
		return new ResponseEntity(empList, HttpStatus.OK);

	}

	// Query 3.8 : Get the details of youngest male employee in the product development department?

	@ResponseBody
	@RequestMapping(path = "/getDetailsOfYoungestMaleEmployee", method = RequestMethod.GET)
	public ResponseEntity<?> getDetailsOfYoungestMaleEmployee() {
		Employee emp = employeeService.getYoungestMaleEmployee();

		return new ResponseEntity(emp, HttpStatus.OK);
	}

	// Query 3.9 : Who has the most working experience in the organization?

	@ResponseBody

	@RequestMapping(path = "/getMostExpEmployeeInOrganization", method = RequestMethod.GET)
	public ResponseEntity<?> getMostExperienceEmployeeInOrganization() {
		Employee emp = employeeService.getMostExpEmployee();
		return new ResponseEntity(emp, HttpStatus.OK);
	}

	// Query 3.10 : How many male and female employees are there in the sales and
	// marketing team?

	@ResponseBody

	@RequestMapping(path = "/getMaleAndFemaleEmployeeCountInSales", method = RequestMethod.GET)
	public ResponseEntity<?> getMaleAndFemaleEmployeeCountInSales() {

		return new ResponseEntity(employeeService.getMaleAndFemaleEmployeesInSalesMarketing(), HttpStatus.OK);
	}

	// Query 3.11 : What is the average salary of male and female employees?

	@ResponseBody

	@RequestMapping(path = "/getAvgSalaryOfMaleAndFemaleEmployee", method = RequestMethod.GET)
	public ResponseEntity<?> getAvgSalaryOfMaleAndFemaleEmployee() {

		return new ResponseEntity(employeeService.getAvgSalaryOfMaleAndFemaleEmployee(), HttpStatus.OK);
	}

	@ResponseBody

	@RequestMapping(path = "/getAllEmployeesNamesInDepartment", method = RequestMethod.GET)
	public ResponseEntity<?> getAllEmployeesNamesInDepartment() {

		Map<String, List<String>> emp = employeeService.getAllEmployeesNamesInDepartment();
		return new ResponseEntity(emp, HttpStatus.OK);
	}

	@ResponseBody

	@RequestMapping(path = "/getAvgSalaryAndWholeSalaryOfOrganization", method = RequestMethod.GET)
	public ResponseEntity<?> getAvgSalaryAndWholeSalaryOfOrganization() {
		return new ResponseEntity(employeeService.getAvgSalaryAndWholeSalaryOfOrganization(), HttpStatus.OK);
	}

	// Query 3.14 : Separate the employees who are younger or equal to 25 yearsfrom those employees whoare older than 25 years.

	@ResponseBody

	@RequestMapping(path="/getEmployeesAgeDeatils",method=RequestMethod.GET)

	public ResponseEntity<?> getEmployeesAgeDeatils() {
		return new ResponseEntity(employeeService.getEmployeesAgeDeatils(), HttpStatus.OK);
	}

	// Query 3.15 : Who is the oldest employee in the organization? What is his age
	// and which department he belongs to?

	@ResponseBody
	@RequestMapping(path = "/getOldestAgeEmployee", method = RequestMethod.GET)
	public ResponseEntity<?> getOldestAgeEmployee() {
		return new ResponseEntity(employeeService.getOldestAgeEmployee(), HttpStatus.OK);
	}

}
