package badcode.spaghetti;

import java.math.BigDecimal;

public class XConstants {

	static final BigDecimal INTERN_SALARY = new BigDecimal("2000.0");
	static final BigDecimal MANAGER_SALARY = new BigDecimal("10000.0");
	static final BigDecimal SALARY_PERCENTAGE_FOR_BAD_EMPLOYEE = new BigDecimal("0.8");

	static final BigDecimal JUNIOR_SALARY = new BigDecimal("4000.0");
	static final BigDecimal MID_SALARY = new BigDecimal("5000.0");
	static final BigDecimal SENIOR_SALARY = new BigDecimal("7000.0");
	static final BigDecimal EXPERT_SALARY = new BigDecimal("9000");
	static final BigDecimal ZERO = new BigDecimal("0.0");

	static final BigDecimal JUNIOR_BONUS_MODIFIER = new BigDecimal("0.7");
	static final BigDecimal MID_BONUS_MODIFIER = new BigDecimal("1.0");
	static final BigDecimal SENIOR_BONUS_MODIFIER = new BigDecimal("1.5");
	static final BigDecimal EXPERT_BONUS_MODIFIER = new BigDecimal("2.0");
	static final BigDecimal MANAGER_BONUS_MODIFIER = new BigDecimal("2.0");

	static final BigDecimal CONTRACTOR_SALARY_MODIFIER = new BigDecimal("1.2");

	static final Integer VACATION_DAYS_EMPLOYEMENT_MINIMUM = 20;
	static final Integer VACATION_DAYS_EMPLOYEMENT_FULL = 26;
	static final Integer VACATIONS_DAYS_FOR_CONTRACTORS = 15;
	static final Integer ZERO_DAYS = 0;
	static final Integer YEARS_OF_EMPLOYMENT_THAT_GIVES_MORE_VACATIONS = 0;
	static final Integer BONUS_VACATION_DAYS_WOMEN_WITH_KIDS = 2;
}
