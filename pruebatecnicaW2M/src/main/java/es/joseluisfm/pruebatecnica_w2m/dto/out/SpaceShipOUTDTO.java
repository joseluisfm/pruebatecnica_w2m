package es.joseluisfm.pruebatecnica_w2m.dto.out;

import es.joseluisfm.pruebatecnica_w2m.entity.SpaceShipEntity;

public class SpaceShipOUTDTO {

	private Long id;
	private String name;
	private Double speed;
	private String type;
	
	public SpaceShipOUTDTO() {
		
	}
	
	public SpaceShipOUTDTO(SpaceShipEntity spaceShipEntity) {
		id = spaceShipEntity.getId();
		name = spaceShipEntity.getName();
		speed = spaceShipEntity.getSpeed();
		type = spaceShipEntity.getType().getName();
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
