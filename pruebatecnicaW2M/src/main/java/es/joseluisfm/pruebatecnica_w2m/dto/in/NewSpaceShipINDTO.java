package es.joseluisfm.pruebatecnica_w2m.dto.in;

public class NewSpaceShipINDTO {

	private String name;
	private Double speed;
	private Long fIdType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Long getfIdType() {
		return fIdType;
	}

	public void setfIdType(Long fIdType) {
		this.fIdType = fIdType;
	}

}
