package es.joseluisfm.pruebatecnica_w2m.service;

import java.util.List;

import es.joseluisfm.pruebatecnica_w2m.dto.in.EditSpaceShipINDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.in.NewSpaceShipINDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.in.PageableINDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.out.SpaceShipOUTDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.out.SpaceShipsOUTDTO;
import es.joseluisfm.pruebatecnica_w2m.exception.LogicException;

public interface SpaceShipService {
	
	/**
	 * Gets all space ships paginated.
	 *
	 * @param pageableINDTO the pageable INDTO
	 * @return the all space ships
	 */
	public SpaceShipsOUTDTO getAllSpaceShips(PageableINDTO pageableINDTO);
	
	/**
	 * Find SpaceShip by id.
	 *
	 * @param id the id
	 * @return the space ship OUTDTO
	 * @throws LogicException the logic exception
	 */
	public SpaceShipOUTDTO findById(long id) throws LogicException;
	
	/**
	 * Find SpaceShips by name.
	 *
	 * @param name the name
	 * @return the spaceShip list 
	 */
	public List<SpaceShipOUTDTO> findByName(String name);

	/**
	 * To create a new space ship.
	 *
	 * @param newSpaceShipINDTO the new space ship INDTO
	 * @return the space ship OUTDTO
	 * @throws LogicException the logic exception
	 */
	public SpaceShipOUTDTO newSpaceShip(NewSpaceShipINDTO newSpaceShipINDTO) throws LogicException;

	/**
	 * To edits the space ship.
	 *
	 * @param editSpaceShipINDTO the edit space ship INDTO
	 * @return the space ship OUTDTO
	 * @throws LogicException the logic exception
	 */
	public SpaceShipOUTDTO editSpaceShip(EditSpaceShipINDTO editSpaceShipINDTO) throws LogicException;

	/**
	 * To delete space ship.
	 *
	 * @param id the SpaceShip Id to delete
	 * @throws LogicException the logic exception
	 */
	public void deleteSpaceShip(Long id) throws LogicException;

}
