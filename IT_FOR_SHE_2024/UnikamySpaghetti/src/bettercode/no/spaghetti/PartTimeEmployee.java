package bettercode.no.spaghetti;

import java.math.BigDecimal;

public class PartTimeEmployee extends RegularEmployee {

	public PartTimeEmployee(String name, Integer age, Integer yearsInCompany, Integer yearsOfEmployment,
			 Boolean hasWrittenReprimand, Boolean isFemale, Boolean hasChildren, JobStage jobStage) {
		super(name, age, yearsInCompany, yearsOfEmployment, hasWrittenReprimand, isFemale, hasChildren, jobStage);
	}

	@Override
	public BigDecimal calculateSalary() {
		BigDecimal fullTimeSalary = super.calculateSalary();
		return fullTimeSalary.multiply(fractionOfFullTimeJob);
	}

	@Override
	public BigDecimal calculateBonus(BigDecimal baseBonus) {
		BigDecimal bonus = super.calculateBonus(baseBonus);
		return bonus.multiply(fractionOfFullTimeJob);
	}

	@Override
	public Integer calculateVacationDays() {
		return (int) super.calculateVacationDays() * fractionOfFullTimeJob.shortValue();
	}

	@Override
	public BigDecimal calculateSeverancePayments() {
		return super.calculateSeverancePayments();
	}

}