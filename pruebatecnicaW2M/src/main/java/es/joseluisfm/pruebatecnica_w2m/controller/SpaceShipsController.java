package es.joseluisfm.pruebatecnica_w2m.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
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
import es.joseluisfm.pruebatecnica_w2m.dto.in.PageableINDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.out.SpaceShipOUTDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.out.SpaceShipsOUTDTO;
import es.joseluisfm.pruebatecnica_w2m.exception.RestException;
import es.joseluisfm.pruebatecnica_w2m.service.SpaceShipService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/spaceShips")
@Validated
public class SpaceShipsController {

	private SpaceShipService spaceShipService;

	public SpaceShipsController(SpaceShipService spaceShipService) {
		this.spaceShipService = spaceShipService;
	}

	@PostMapping("/getAll")
	public SpaceShipsOUTDTO getAllSpaceShips(@RequestBody @Valid PageableINDTO pageableINDTO) throws RestException {
		try {
			return spaceShipService.getAllSpaceShips(pageableINDTO);
		} catch (Exception e) {
			throw new RestException("Error getting SpaceShips", e);
		}
	}

	@GetMapping("/findById")
	public SpaceShipOUTDTO findById(
			@RequestParam @NotNull @Min(1) @Max(999999999999999999L) Long id) throws RestException {
		try {
			return spaceShipService.findById(id);
		} catch (Exception e) {
			throw new RestException("Error getting SpaceShip", e);
		}
	}

	@GetMapping("/findByName")
	public List<SpaceShipOUTDTO> findByName(@RequestParam @NotBlank @Size(min = 3, max = 50) String name) throws RestException {
		try {
			return spaceShipService.findByName(name);
		} catch (Exception e) {
			throw new RestException("Error getting SpaceShip", e);
		}
	}

	@PostMapping("/new")
	public SpaceShipOUTDTO newSpaceShip(@RequestBody @Valid NewSpaceShipINDTO newSpaceShipINDTO) throws RestException {
		try {
			return spaceShipService.newSpaceShip(newSpaceShipINDTO);
		} catch (Exception e) {
			throw new RestException("Error creating new SpaceShip", e);
		}
	}

	@PutMapping("/edit")
	public SpaceShipOUTDTO editSpaceShip(@RequestBody @Valid EditSpaceShipINDTO editSpaceShipINDTO) throws RestException {
		try {
			return spaceShipService.editSpaceShip(editSpaceShipINDTO);
		} catch (Exception e) {
			throw new RestException("Error editing SpaceShip", e);
		}
	}

	@DeleteMapping("/delete")
	public void deleteSpaceShip(@RequestParam @NotNull @Min(1) @Max(999999999999999999L) Long id) throws RestException {
		try {
			spaceShipService.deleteSpaceShip(id);
		} catch (Exception e) {
			throw new RestException("Error editing SpaceShip", e);
		}
	}

}
