package bettercode.no.spaghetti;

import static bettercode.no.spaghetti.XConstants.*;

import java.math.BigDecimal;

public class RegularEmployee extends BaseEmployee {

	public RegularEmployee(String name, Integer age, Integer yearsInCompany, Integer yearsOfEmployment,
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

		if (hasWrittenReprimand) {
			salary = salary.multiply(SALARY_PERCENTAGE_FOR_BAD_EMPLOYEE);
		}
		return salary;
	}

	@Override
	public BigDecimal calculateBonus(BigDecimal baseBonus) {

		BigDecimal bonus = baseBonus;

		if (hasWrittenReprimand) {
			return ZERO;
		}

		switch (jobStage) {
		case JUNIOR -> bonus = bonus.multiply(JUNIOR_BONUS_MODIFIER);
		case MID -> bonus = bonus.multiply(MID_BONUS_MODIFIER);
		case SENIOR -> bonus.multiply(SENIOR_BONUS_MODIFIER);
		case EXPERT -> bonus.multiply(EXPERT_BONUS_MODIFIER);
		}

		return bonus;
	}

	@Override
	public Integer calculateVacationDays() {
		Integer vacationDays = VACATION_DAYS_EMPLOYEMENT_MINIMUM;

		if (yearsOfEmployment > YEARS_OF_EMPLOYMENT_THAT_GIVES_MORE_VACATIONS) {
			vacationDays = VACATION_DAYS_EMPLOYEMENT_FULL;
		}

		if (isFemale && hasChildren) {
			vacationDays += BONUS_VACATION_DAYS_WOMEN_WITH_KIDS;
		}

		return vacationDays;
	}

	@Override
	public BigDecimal calculateSeverancePayments() {

		BigDecimal severance = calculateSalary();

		if (yearsInCompany < 2) {
			return severance.multiply(new BigDecimal(1.0d));
		}

		if (yearsInCompany < 8) {
			return severance.multiply(new BigDecimal(2.0d));
		}

		if (yearsInCompany >= 8) {
			return severance.multiply(new BigDecimal(3.0d));
		}

		return severance;
	}

}