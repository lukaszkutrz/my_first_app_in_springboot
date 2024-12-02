package badcode.spaghetti;

import java.math.BigDecimal;
import java.util.List;

public class XMain_BadCode {

	
	public static void main(String[] args) {
		// Tutaj tworzymy nasze obiekty
		
		BaseEmployee emp1 = new Employee("Joanna Kuc", 46, 10, 12, TypeOfEmployee.REGULAR,
				false, true, true, Employee.JobStage.SENIOR);
		BaseEmployee emp2 = new Employee("Jan Kolar", 33, 2, 3, TypeOfEmployee.REGULAR,
				false, false, true, Employee.JobStage.JUNIOR);
		BaseEmployee emp3 = new Employee("Max Rogacz", 28, 2, 3, TypeOfEmployee.REGULAR,
				false, false, true, Employee.JobStage.EXPERT);
		BaseEmployee emp4 = new Employee("Anna Kalka", 48, 4, 13, TypeOfEmployee.REGULAR,
				false, true, true, Employee.JobStage.MID);
		BaseEmployee emp5 = new Employee("Hanna B¹k", 48, 4, 13, TypeOfEmployee.REGULAR,
				false, true, true, Employee.JobStage.MID);

		emp5.setWrittenReprimand(true);

		BaseEmployee emp6 = new Employee("Regina Niec", 26, 3, 12, TypeOfEmployee.MANAGER,
				false, true, true, Employee.JobStage.JUNIOR);
		BaseEmployee emp7 = new Employee("Joanna D'arc", 26, 1, 14, TypeOfEmployee.CONTRACTOR,
				false, true, true, Employee.JobStage.MID);
		BaseEmployee emp8 = new Employee("Bruno Mirny", 26, 0, 0, TypeOfEmployee.INTERN,
				false, false, true, Employee.JobStage.JUNIOR);
		BaseEmployee emp9 = new Employee("Kamil Kowal", 26, 2, 1, TypeOfEmployee.PART_TIME_REGULAR,
				false, false, true, Employee.JobStage.SENIOR);
		
		emp9.setFractionOfFullTimeJob(new BigDecimal("0.8"));

		// A tutaj wyliczymy:
		// 1) dni urlopowe 
		// 2) wyplate 
		// 3) roczna premie 
		// 4) odprawe w razie zwolnienia
		
		List<BaseEmployee> employees = List.of(emp1, emp2, emp3, emp4, emp5, emp6, emp7, emp8, emp9);
		
		BigDecimal baseBonus = new BigDecimal(5000d);
		
		for(BaseEmployee employee: employees) {
			System.out.println("-----------------");
			System.out.println(employee);
			System.out.println();
			System.out.println("Zarobki: " + employee.calculateSalary());
			System.out.println("Premia: " + employee.calculateBonus(baseBonus));
			System.out.println("Dni Urlopowe: " + employee.calculateVacationDays());
			System.out.println("Ewentualna Odprawa: " + employee.calculateSeverancePayments());
		};
	}
}
