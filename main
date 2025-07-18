package com.lx;

import java.util.List;
import java.util.Scanner;

public class Project2 {

	public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(System.in);
		boolean a = true;
		List<Emp> empList = null;
		while (a) {
			System.out.println("어떤 컬럼으로 입력하시겠습니까? (1.직원이름 2.입사년도 3.부서번호 4.직무 5.도시이름 6.부서장 성 7.나라이름 8.통계자료	0.종료");
			int num = scan.nextInt();
			scan.nextLine();
			if (num == 0) {
				a = false;
			}
			else if (num >8) {
				System.out.println("1~8 숫자로 다시 입력");
			}
			else {
				if (num == 1) {
					System.out.println("직원 이름을 입력하세요");
					String name = scan.nextLine();
					System.out.println("입력된 이름 : "+ name);			
					empList = getEmpInfo2.getEmpInfoByName(name);
				}
				if (num == 2) {
					System.out.println("입사년도를 입력하세요");
					int year = scan.nextInt();
					System.out.println("입사년도 : "+ year);
					empList = getEmpInfo2.getEmpInfoByHireYear(year);
				}
				if (num == 3) {
					System.out.println("부서번호를 입력하세요");
					int dep_id = scan.nextInt();
					System.out.println("부서번호 : "+ dep_id);
					empList = getEmpInfo2.getEmpInfoByDepId(dep_id);
				}
				if (num == 4) {
					System.out.println("직무를 입력하세요");
					String job_id = scan.nextLine();
					System.out.println("직무 : "+ job_id);
					empList = getEmpInfo2.getEmpInfoByJobId(job_id);
				}
				if (num == 5) {
					System.out.println("도시이름을 입력하세요");
					String city = scan.nextLine();
					System.out.println("도시 : " + city);
					empList = getEmpInfo2.getEmpInfoByCity(city);
				}
				if (num == 6) {
					System.out.println("부서장 성을 입력하세요");
					String dep_last = scan.nextLine();
					System.out.println("부서장 성 : " + dep_last);
					empList = getEmpInfo2.getEmpInfoByDepLastName(dep_last);
				}
				if (num == 7) {
					System.out.println("나라이름 입력하세요");
					String country = scan.nextLine();
					System.out.println("나라이름" + country);
					empList = getEmpInfo2.getEmpInfoByCountry(country);
				}
				if (num == 8) {
					System.out.println();
					getEmpInfo2.getStatics();
					System.out.println("더 세부적으로 통계? 1.부서별 2.입사년도 3.나라id 0.기본화면으로");
					int static_num = scan.nextInt();
					scan.nextLine();
					while (true) {
						if (static_num == 0) {
							break;
						}
					}
				}
			}
		
		}
		if (empList != null) {
			for (Emp emp : empList) {
			System.out.println(emp);
			}
		}
	}
}


