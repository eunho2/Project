package com.lx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class getEmpInfo2 {
	public static String sql = null; // 문자열  sql이라는 값은 빈값이다 초기화 선언 
	public PreparedStatement stmt = null; 
	public static String a= null;
	
	//1번 어디서나 쓸수있는 전역의 리스트를 제작 Emp 타입으로 받고 문자열 NAME이라는 변수로 함수 값을 넣어준다 예외처리  
	public static List<Emp> getEmpInfoByName(String name) throws Exception {
		a = name; // String type 의 변수 name 을 할당 
		sql = "select * from employees where concat(last_name,' ',first_name) like +?";
		// 쿼리문 입력  employees라는 테이블에서 가져와 어떤 조건으로? 라스트네임이랑 성 합친걸로 그리고 검색된 값과 
		// 같은 값만 출력 
		return add(sql);
	}
	//2번
	public static List<Emp> getEmpInfoByHireYear(int year) throws Exception {
		a = Integer.toString(year); // 강제형변환 문자열 -> 정수형  
		sql = "select * from employees where year(hire_date) like +?";
		// 쿼리문 입력  employees라는 테이블에서 가져와 어떤 조건으로? year이라는 년도로 변환그리고 검색된 값과 
		// 같은 값만 출력 
		return add(sql); // 리스트는 emp 타입으로 정수형 변환한 year로 넣음  그리고 예외처리한 이유는 sql쿼리문은 예외처리를 기본적으로 갖고 있기 때문이다 
	}
	//3번
	public static List<Emp> getEmpInfoByDepId(int dep_id) throws Exception {
		a = Integer.toString(dep_id); //
		sql = "select * from employees where department_id like +?";
		return add(sql);
	}
	//4번
	public static List<Emp> getEmpInfoByJobId(String job_id) throws Exception {
		a = job_id;
		sql = "select * from employees where job_id = ?";
		return add(sql);
	}
	//5번
	public static List<Emp> getEmpInfoByCity(String city) throws Exception {
		a = city;
		sql = "select * from employees e where e.department_id in (select d.department_id from departments d where d.location_id in "
				+ "(select location_id  from locations l where city = ?))";
		return add(sql);
	}
	//6번
	public static List<Emp> getEmpInfoByDepLastName(String dep_last) throws Exception {
		a = dep_last;
		sql = "select * from employees where (department_id in (select department_id from departments where manager_id in "
				+ "(select employee_id from employees where last_name = ?)))";
		return add(sql);
	}
	//7번
	public static List<Emp> getEmpInfoByCountry(String country) throws Exception {
		a = country;
		sql = "select * from employees e where (e.department_id in (select d.department_id from departments d "
				+ "where d.location_id in (select l.location_id from locations l where l.country_id in "
				+ "(select c.country_id from countries c where c.country_name = ?))))";
		return add(sql);
	}
	//8번
	public static void getStatics(int static_num) throws Exception {
	    Class.forName("com.mysql.cj.jdbc.Driver");
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/newhr", "root", "1234");

	    String sql = "";

	    if (static_num == 1) { // 1번 이라고 입력받으면 부서 아이디에 따라서 통계값 출력
	        sql = "SELECT department_id, COUNT(*) AS total_employees, AVG(salary) AS avg_salary, MAX(salary) AS max_salary, MIN(salary) AS min_salary FROM employees GROUP BY department_id";
	    } else if (static_num == 2) {// 2번이라고 입력 받으면 입사 날짜에 따라서 출력 
	        sql = "SELECT FLOOR(DATEDIFF(CURDATE(), hire_date) / 365) AS year_of_service, COUNT(*) AS total_employees, AVG(salary) AS avg_salary, MAX(salary) AS max_salary, MIN(salary) AS min_salary FROM employees GROUP BY year_of_service ORDER BY year_of_service";
	    } else if (static_num == 3) { // 3번이라고 입력 받으면 평균 최대 최솟값  나라별 아이디를 사용할수 있게 3번 조인 
	        sql = "SELECT c.country_id, COUNT(*) AS total_employees, AVG(e.salary) AS avg_salary, MAX(e.salary) AS max_salary, MIN(e.salary) AS min_salary FROM employees e JOIN departments d ON e.department_id = d.department_id JOIN locations l ON d.location_id = l.location_id JOIN countries c ON l.country_id = c.country_id GROUP BY c.country_id";
	    }

	    PreparedStatement stmt = conn.prepareStatement(sql);
	    ResultSet rs = stmt.executeQuery();

	    while (rs.next()) {
	        if (static_num == 1) { //거기서 1 2 3 각각 선택 rs 로 하니까 
	            int departmentId = rs.getInt("department_id");
	            int totalEmployees = rs.getInt("total_employees");
	            int avgSalary = rs.getInt("avg_salary");
	            int maxSalary = rs.getInt("max_salary");
	            int minSalary = rs.getInt("min_salary");
	            System.out.println("부서번호: " + departmentId + ", 사원수: " + totalEmployees + ", 평균급여: " + avgSalary + ", 최고급여: " + maxSalary + ", 최저급여: " + minSalary);
	        } else if (static_num == 2) {
	            int yearOfService = rs.getInt("year_of_service");
	            int totalEmployees = rs.getInt("total_employees");
	            int avgSalary = rs.getInt("avg_salary");
	            int maxSalary = rs.getInt("max_salary");
	            int minSalary = rs.getInt("min_salary");
	            System.out.println("근속연차: " + yearOfService + "년, 사원수: " + totalEmployees + ", 평균급여: " + avgSalary + ", 최고급여: " + maxSalary + ", 최저급여: " + minSalary);
	        } else if (static_num == 3) {
	            String countryId = rs.getString("country_id");
	            int totalEmployees = rs.getInt("total_employees");
	            int avgSalary = rs.getInt("avg_salary");
	            int maxSalary = rs.getInt("max_salary");
	            int minSalary = rs.getInt("min_salary");
	            System.out.println("나라ID: " + countryId + ", 사원수: " + totalEmployees + ", 평균급여: " + avgSalary + ", 최고급여: " + maxSalary + ", 최저급여: " + minSalary);
	        }
	    }

	    conn.close();
	}


	
	//사원출력함수
	public static List<Emp> add(String sql) throws Exception {
		List<Emp> result = new ArrayList<Emp>();
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/newhr", "root", "1234");
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, a);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Emp emp = new Emp();
			emp.employee_id = rs.getInt("employee_id");
			emp.first_name = rs.getString("first_name");
			emp.last_name = rs.getString("last_name");
			emp.email = rs.getString("email");
			emp.phone_number = rs.getString("phone_number");
			emp.hire_date = rs.getString("hire_date");
			emp.job_id = rs.getString("job_id");
			emp.salary= rs.getInt("salary");
			emp.commission_pct = rs.getDouble("commission_pct");
			emp.manager_id = rs.getInt("manager_id");
			emp.department_id = rs.getInt("department_id");
			result.add(emp);
		}
		return result;
	}
	
}
