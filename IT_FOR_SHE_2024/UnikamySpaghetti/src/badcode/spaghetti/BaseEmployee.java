package badcode.spaghetti;

import java.math.BigDecimal;

public abstract class BaseEmployee {
	
	protected static enum JobStage {
		JUNIOR,
		MID,
		SENIOR,
		EXPERT
	}

	protected String name;
	protected Integer age;
	protected Integer yearsInCompany;
	protected Integer yearsOfEmployment;
	protected TypeOfEmployee type;
	// czy ma obecnie kare? Jesli ma nie dostanie premii:
	protected Boolean hasWrittenReprimand;
	protected Boolean isFemale;
	protected Boolean hasChildren;
	protected JobStage jobStage;
	// niepelny wymiar czasu pracy np. 0.8, 0.6 itp
	protected BigDecimal fractionOfFullTimeJob;

	public BaseEmployee(String name, Integer age, Integer yearsInCompany, Integer yearsOfEmployment, TypeOfEmployee type,
			Boolean hasWrittenReprimand, Boolean isFemale, Boolean hasChildren, JobStage jobStage) {
		super();
		this.name = name;
		this.age = age;
		this.yearsInCompany = yearsInCompany;
		this.yearsOfEmployment = yearsOfEmployment;
		this.type = type;
		this.hasWrittenReprimand = hasWrittenReprimand;
		this.isFemale = isFemale;
		this.hasChildren = hasChildren;
		this.jobStage = jobStage;
	}
	
	public void setWrittenReprimand(Boolean gotWrittenReprimand_) {
		hasWrittenReprimand = gotWrittenReprimand_;
	}
	
	public void setFractionOfFullTimeJob(BigDecimal fractionOfFullTimeJob) {
		this.fractionOfFullTimeJob = fractionOfFullTimeJob;
	}
	
	/**
	 * Wyliczanie wyplaty
	 */
	public abstract BigDecimal calculateSalary();

	/**
	 * Wyliczanie premii rocznej
	 */
	public abstract BigDecimal calculateBonus(BigDecimal baseBonus);

	/**
	 * Wyliczanie dni wakacji
	 */
	public abstract Integer calculateVacationDays();

	/**
	 * Wyliczanie Odprawy
	 */
	public abstract BigDecimal calculateSeverancePayments();
}
