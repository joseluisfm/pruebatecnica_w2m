package es.joseluisfm.pruebatecnica_w2m.service;

import java.util.List;

import es.joseluisfm.pruebatecnica_w2m.dto.in.EditSpaceShipINDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.in.NewSpaceShipINDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.in.SpaceShipsINDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.out.SpaceShipOUTDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.out.SpaceShipsOUTDTO;
import es.joseluisfm.pruebatecnica_w2m.exception.LogicException;

public interface SpaceShipService {
	
	public SpaceShipsOUTDTO getAllSpaceShips(SpaceShipsINDTO spaceShipsINDTO);
	
	public SpaceShipOUTDTO findById(long id) throws LogicException;
	
	public List<SpaceShipOUTDTO> findByName(String name);

	public SpaceShipOUTDTO newSpaceShip(NewSpaceShipINDTO newSpaceShipINDTO) throws LogicException;

	public SpaceShipOUTDTO editSpaceShip(EditSpaceShipINDTO editSpaceShipINDTO) throws LogicException;

	public void deleteSpaceShip(Long id) throws LogicException;

}
