package hr.ingemark.webshop.enums;

public enum StatusEnum {

	DRAFT("DRAFT"), SUBMITTED("SUBMITTED");
	
	private String iStatus;
	
	private StatusEnum(String pStatus) {
		this.iStatus = pStatus;
	}
	
	public static StatusEnum getByStatus(String pStatus) {
		for(StatusEnum tStatusEnum : StatusEnum.values()) {
			if(tStatusEnum.iStatus.equalsIgnoreCase(pStatus)) {
				return tStatusEnum;
			}
		}
		return null;
	}
	
}
