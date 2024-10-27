package es.joseluisfm.pruebatecnica_w2m.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.joseluisfm.pruebatecnica_w2m.dto.in.EditSpaceShipINDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.in.NewSpaceShipINDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.in.SpaceShipsINDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.out.SpaceShipOUTDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.out.SpaceShipsOUTDTO;
import es.joseluisfm.pruebatecnica_w2m.exception.RestException;
import es.joseluisfm.pruebatecnica_w2m.service.SpaceShipService;

@RestController
@RequestMapping("/spaceShips")
public class SpaceShipsController {

	private SpaceShipService spaceShipService;

	public SpaceShipsController(SpaceShipService spaceShipService) {
		this.spaceShipService = spaceShipService;
	}

	@PostMapping("/getAll")
	public SpaceShipsOUTDTO getAllSpaceShips(@RequestBody SpaceShipsINDTO spaceShipsINDTO) throws RestException {
		try {
			return spaceShipService.getAllSpaceShips(spaceShipsINDTO);
		} catch (Exception e) {
			throw new RestException("Error getting SpaceShips", e);
		}
	}

	@GetMapping("/findById")
	public SpaceShipOUTDTO findById(@RequestParam long id) throws RestException {
		try {
			return spaceShipService.findById(id);
		} catch (Exception e) {
			throw new RestException("Error getting SpaceShips", e);
		}
	}

	@GetMapping("/findByName")
	public List<SpaceShipOUTDTO> findByName(@RequestParam String name) throws RestException {
		try {
			return spaceShipService.findByName(name);
		} catch (Exception e) {
			throw new RestException("Error getting SpaceShips", e);
		}
	}

	@PostMapping("/new")
	public SpaceShipOUTDTO newSpaceShip(@RequestBody NewSpaceShipINDTO newSpaceShipINDTO) throws RestException {
		try {
			return spaceShipService.newSpaceShip(newSpaceShipINDTO);
		} catch (Exception e) {
			throw new RestException("Error creating new SpaceShip", e);
		}
	}

	@PutMapping("/edit")
	public SpaceShipOUTDTO editSpaceShip(@RequestBody EditSpaceShipINDTO editSpaceShipINDTO) throws RestException {
		try {
			return spaceShipService.editSpaceShip(editSpaceShipINDTO);
		} catch (Exception e) {
			throw new RestException("Error editing SpaceShip", e);
		}
	}

	@DeleteMapping("/delete")
	public void deleteSpaceShip(@RequestParam Long id) throws RestException {
		try {
			spaceShipService.deleteSpaceShip(id);
		} catch (Exception e) {
			throw new RestException("Error editing SpaceShip", e);
		}
	}

}
