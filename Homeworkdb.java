package jdbchomework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Homeworkdb {

    // DB 연결 메소드
    private Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/newhr?serverTimezone=UTC";
        String user = "root";
        String password = "rootroot";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }

    // last_name으로 직원 정보 가져오기
    public List<Emp> getEmpInfoByName(String last_name) throws Exception {
        List<Emp> result = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE last_name = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, last_name);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Emp emp = new Emp();
                emp.setEmployeeId(rs.getInt("employee_id"));
                emp.setFirstName(rs.getString("first_name"));
                emp.setLastName(rs.getString("last_name"));
                emp.setSalary(rs.getDouble("salary"));
                // 필요하면 다른 컬럼도 추가
                result.add(emp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 통계 출력 메소드
    public void printEmployeeStatistics() {
        String query = "SELECT COUNT(*) AS total_employees, "
                     + "AVG(salary) AS avg_salary, "
                     + "MAX(salary) AS max_salary, "
                     + "MIN(salary) AS min_salary "
                     + "FROM employees";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                int totalEmployees = rs.getInt("total_employees");
                double avgSalary = rs.getDouble("avg_salary");
                double maxSalary = rs.getDouble("max_salary");
                double minSalary = rs.getDouble("min_salary");

                displayStatistics(totalEmployees, avgSalary, maxSalary, minSalary);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 출력용 메소드
    private void displayStatistics(int totalEmployees, double avgSalary, double maxSalary, double minSalary) {
        System.out.println("===== 직원 통계 자료 =====");
        System.out.printf("총 직원 수: %d명\n", totalEmployees);
        System.out.printf("평균 급여: %.2f\n", avgSalary);
        System.out.printf("최고 급여: %.2f\n", maxSalary);
        System.out.printf("최저 급여: %.2f\n", minSalary);
    }
}
