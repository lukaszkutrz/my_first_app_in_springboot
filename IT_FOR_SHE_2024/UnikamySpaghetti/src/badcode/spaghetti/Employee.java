package badcode.spaghetti;

import static badcode.spaghetti.XConstants.*;
import java.math.BigDecimal;
public class Employee extends BaseEmployee {


	/**
	 * Constructor - this is just an example, in real world we would use builder
	 * design pattern, but I don't want to make this example too hard
	 * 
	 */
	public Employee(String name, Integer age, Integer yearsInCompany, Integer yearsOfEmployment, TypeOfEmployee type,
			Boolean hasWrittenReprimand, Boolean isFemale, Boolean hasChildren, JobStage jobStage) {
		super(name, age, yearsInCompany, yearsOfEmployment, type, hasWrittenReprimand, isFemale, hasChildren, jobStage);
	}

	/**
	 * Wyliczanie podstawowych zarobków
	 */
	public BigDecimal calculateSalary() {

		BigDecimal salary = new BigDecimal(0d);

		if (TypeOfEmployee.MANAGER.equals(type)) {
			salary = MANAGER_SALARY;
		}

		if (TypeOfEmployee.REGULAR.equals(type) || TypeOfEmployee.CONTRACTOR.equals(type)
				|| TypeOfEmployee.PART_TIME_REGULAR.equals(type)) {
			switch (jobStage) {
			case JUNIOR -> salary = JUNIOR_SALARY;
			case MID -> salary = MID_SALARY;
			case SENIOR -> salary = SENIOR_SALARY;
			case EXPERT -> salary = EXPERT_SALARY;
			}
		}

		if (TypeOfEmployee.PART_TIME_REGULAR.equals(type)) {
			salary = salary.multiply(fractionOfFullTimeJob);
		}

		if (TypeOfEmployee.CONTRACTOR.equals(type)) {
			salary = salary.multiply(CONTRACTOR_SALARY_MODIFIER);
		}

		if (TypeOfEmployee.INTERN.equals(type)) {
			salary = INTERN_SALARY;
		}

		if (hasWrittenReprimand && (TypeOfEmployee.REGULAR.equals(type) || TypeOfEmployee.PART_TIME_REGULAR.equals(type)
				|| TypeOfEmployee.MANAGER.equals(type))) {
			salary = salary.multiply(SALARY_PERCENTAGE_FOR_BAD_EMPLOYEE);
		}

		return salary;
	}

	/**
	 * Wyliczanie premii rocznej - baseBonus to podstawowy bonus
	 */
	public BigDecimal calculateBonus(BigDecimal baseBonus) {

		BigDecimal bonus = baseBonus;

		if (TypeOfEmployee.INTERN.equals(type)) {
			return ZERO;
		}

		if (TypeOfEmployee.CONTRACTOR.equals(type)) {
			return bonus;
		}

		if (hasWrittenReprimand && (TypeOfEmployee.MANAGER.equals(type) || TypeOfEmployee.REGULAR.equals(type)
				|| TypeOfEmployee.PART_TIME_REGULAR.equals(type))) {
			return ZERO;
		} 

		if (TypeOfEmployee.MANAGER.equals(type)) {
			return bonus.multiply(MANAGER_BONUS_MODIFIER);
		}

		if (TypeOfEmployee.REGULAR.equals(type)) {
			switch (jobStage) {
			case JUNIOR -> bonus = bonus.multiply(JUNIOR_BONUS_MODIFIER);
			case MID -> bonus = bonus.multiply(MID_BONUS_MODIFIER);
			case SENIOR -> bonus.multiply(SENIOR_BONUS_MODIFIER);
			case EXPERT -> bonus.multiply(EXPERT_BONUS_MODIFIER);
			}
		}
		
		if(TypeOfEmployee.PART_TIME_REGULAR.equals(type)) {
			bonus = bonus.multiply(fractionOfFullTimeJob);
		}

		return bonus;
	}

	/**
	 * Wyliczanie dni wakacji
	 */
	public Integer calculateVacationDays() {

		if (TypeOfEmployee.CONTRACTOR.equals(type)) {
			return VACATIONS_DAYS_FOR_CONTRACTORS;
		}

		if (TypeOfEmployee.INTERN.equals(type)) {
			return ZERO_DAYS;
		}

		Integer vacationDays = VACATION_DAYS_EMPLOYEMENT_MINIMUM;

		if (yearsOfEmployment > YEARS_OF_EMPLOYMENT_THAT_GIVES_MORE_VACATIONS) {
			vacationDays = VACATION_DAYS_EMPLOYEMENT_FULL;
		}
		
		if(TypeOfEmployee.PART_TIME_REGULAR.equals(type)) {
			vacationDays = (int) vacationDays * fractionOfFullTimeJob.shortValue();
		}

		if (isFemale && hasChildren) {
			vacationDays += BONUS_VACATION_DAYS_WOMEN_WITH_KIDS;
		}

		return vacationDays;
	}

	/**
	 * Wyliczanie Odprawy
	 */
	public BigDecimal calculateSeverancePayments() {

		BigDecimal severancePayment = calculateSalary();
		
		if (TypeOfEmployee.CONTRACTOR.equals(type) || TypeOfEmployee.INTERN.equals(type)) {
			return ZERO;
		}
		
		if (TypeOfEmployee.MANAGER.equals(type) || TypeOfEmployee.REGULAR.equals(type)
				|| TypeOfEmployee.PART_TIME_REGULAR.equals(type)) {

			if (yearsInCompany < 2) {
				return severancePayment.multiply(new BigDecimal(1.0d));
			} 
			if (yearsInCompany < 8) {
				return severancePayment.multiply(new BigDecimal(2.0d));
			}
			if (yearsInCompany >= 8) {
				return severancePayment.multiply(new BigDecimal(3.0d));
			}
		}

		return severancePayment;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ",\n yearsInCompany=" + yearsInCompany + ",\n yearsOfEmployment="
				+ yearsOfEmployment + ",\n type=" + type + ",\n hasWrittenReprimand=" + hasWrittenReprimand
				+ ",\n hasChildren=" + hasChildren + ",\n jobStage=" + jobStage + ",\n fractionOfFullTimeJob="
				+ fractionOfFullTimeJob + "]";
	}

}
