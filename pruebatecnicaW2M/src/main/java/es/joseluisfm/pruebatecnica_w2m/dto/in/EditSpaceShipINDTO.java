package es.joseluisfm.pruebatecnica_w2m.dto.in;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EditSpaceShipINDTO {

	@NotNull
	@Min(1)
	@Max(999999999999999999L)
	private Long id;

	@NotBlank
	@Size(min = 3, max = 50)
	private String name;

	@NotNull
	@Min(1)
	@Max(999999)
	private Double speed;

	@NotNull
	@Min(1)
	@Max(999999999999999999L)
	private Long fIdType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
