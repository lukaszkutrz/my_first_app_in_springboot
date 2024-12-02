package bettercode.no.spaghetti;

import java.math.BigDecimal;

import static bettercode.no.spaghetti.XConstants.*;

public class Manager extends RegularEmployee {

	public Manager(String name, Integer age, Integer yearsInCompany, Integer yearsOfEmployment
			, Boolean hasWrittenReprimand, Boolean isFemale, Boolean hasChildren, JobStage jobStage) {
		super(name, age, yearsInCompany, yearsOfEmployment, hasWrittenReprimand, isFemale, hasChildren, jobStage);
	}

	@Override
	public BigDecimal calculateSalary() {

		BigDecimal salary = MANAGER_SALARY;

		if (hasWrittenReprimand) {
			salary = salary.multiply(SALARY_PERCENTAGE_FOR_BAD_EMPLOYEE);
		}

		return salary;
	}

	@Override
	public BigDecimal calculateBonus(BigDecimal baseBonus) {

		if (hasWrittenReprimand) {
			return ZERO;
		} 

		return baseBonus.multiply(MANAGER_BONUS_MODIFIER);
	}

	@Override
	public Integer calculateVacationDays() {
		return super.calculateVacationDays();
	}

	@Override
	public BigDecimal calculateSeverancePayments() {
		return super.calculateSeverancePayments();
	}

}