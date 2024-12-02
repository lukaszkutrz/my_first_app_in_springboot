package bettercode.no.spaghetti;

import java.math.BigDecimal;

import static bettercode.no.spaghetti.XConstants.*;

public class Contractor extends BaseEmployee {

	public Contractor(String name, Integer age, Integer yearsInCompany, Integer yearsOfEmployment,
			Boolean hasWrittenReprimand, Boolean isFemale, Boolean hasChildren, JobStage jobStage) {
		super(name, age, yearsInCompany, yearsOfEmployment, hasWrittenReprimand, isFemale, hasChildren, jobStage);
	}

	@Override
	public BigDecimal calculateSalary() {
		
		BigDecimal salary = new BigDecimal(0d);

		switch (jobStage) {
		case JUNIOR -> salary = JUNIOR_SALARY;
		case MID -> salary = MID_SALARY;
		case SENIOR -> salary = SENIOR_SALARY;
		case EXPERT -> salary = EXPERT_SALARY;
		}
		
		return salary.multiply(CONTRACTOR_SALARY_MODIFIER);
	}

	@Override
	public BigDecimal calculateBonus(BigDecimal baseBonus) {
		
		return baseBonus;
	}

	@Override
	public Integer calculateVacationDays() {
		return VACATIONS_DAYS_FOR_CONTRACTORS;
	}

	@Override
	public BigDecimal calculateSeverancePayments() {
		return ZERO;
	}

}
