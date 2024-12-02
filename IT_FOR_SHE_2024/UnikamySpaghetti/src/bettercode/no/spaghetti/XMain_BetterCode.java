package bettercode.no.spaghetti;

import java.math.BigDecimal;
import java.util.List;

public class XMain_BetterCode {

	
	public static void main(String[] args) {
		
		// Tutaj tworzymy nasze obiekty
		
		BaseEmployee emp1 = new RegularEmployee("Joanna Kuc", 46, 10, 12,
				false, true, true, BaseEmployee.JobStage.SENIOR);
		BaseEmployee emp2 = new RegularEmployee("Jan Kolar", 33, 2, 3, 
				false, false, true, BaseEmployee.JobStage.JUNIOR);
		BaseEmployee emp3 = new RegularEmployee("Max Rogacz", 28, 2, 3,
				false, false, true, BaseEmployee.JobStage.EXPERT);
		BaseEmployee emp4 = new RegularEmployee("Anna Kalka", 48, 4, 13, 
				false, true, true, BaseEmployee.JobStage.MID);
		BaseEmployee emp5 = new RegularEmployee("Hanna B¹k", 48, 4, 13, 
				false, true, true, BaseEmployee.JobStage.MID);

		emp5.setWrittenReprimand(true);

		BaseEmployee emp6 = new Manager("Regina Niec", 26, 3, 12, 
				false, true, true, BaseEmployee.JobStage.JUNIOR);
		BaseEmployee emp7 = new Contractor("Joanna D'arc", 26, 1, 14, 
				false, true, true, BaseEmployee.JobStage.MID);
		BaseEmployee emp8 = new Intern("Bruno Mirny", 26, 0, 0, 
				false, false, true, BaseEmployee.JobStage.JUNIOR);
		BaseEmployee emp9 = new PartTimeEmployee("Kamil Kowal", 26, 2, 1,
				false, false, true, BaseEmployee.JobStage.SENIOR);
		
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
