package bettercode.no.spaghetti;

import static bettercode.no.spaghetti.XConstants.*;

import java.math.BigDecimal;

public class Intern extends BaseEmployee {

	public Intern(String name, Integer age, Integer yearsInCompany, Integer yearsOfEmployment, Boolean hasWrittenReprimand, Boolean isFemale, Boolean hasChildren, JobStage jobStage) {
		super(name, age, yearsInCompany, yearsOfEmployment, hasWrittenReprimand, isFemale, hasChildren, jobStage);
	}

	@Override
	public BigDecimal calculateSalary() {
		return INTERN_SALARY;
	}

	@Override
	public BigDecimal calculateBonus(BigDecimal baseBonus) {
		return ZERO;
	}

	@Override
	public Integer calculateVacationDays() {
		return ZERO_DAYS;
	}

	@Override
	public BigDecimal calculateSeverancePayments() {
		return ZERO;
	}

}