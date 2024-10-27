package es.joseluisfm.pruebatecnica_w2m.dto.out;

import java.util.List;

public class SpaceShipsOUTDTO {
	private List<SpaceShipOUTDTO> spaceShips;
	private PageableOUTDTO pageable;

	public List<SpaceShipOUTDTO> getSpaceShips() {
		return spaceShips;
	}

	public void setSpaceShips(List<SpaceShipOUTDTO> spaceShips) {
		this.spaceShips = spaceShips;
	}

	public PageableOUTDTO getPageable() {
		return pageable;
	}

	public void setPageable(PageableOUTDTO pageable) {
		this.pageable = pageable;
	}

}
