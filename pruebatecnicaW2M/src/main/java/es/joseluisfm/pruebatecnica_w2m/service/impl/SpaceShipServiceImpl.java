package es.joseluisfm.pruebatecnica_w2m.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import es.joseluisfm.pruebatecnica_w2m.dto.in.EditSpaceShipINDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.in.NewSpaceShipINDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.in.SpaceShipsINDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.out.PageableOUTDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.out.SpaceShipOUTDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.out.SpaceShipsOUTDTO;
import es.joseluisfm.pruebatecnica_w2m.entity.SpaceShipEntity;
import es.joseluisfm.pruebatecnica_w2m.entity.SpaceShipTypeEntity;
import es.joseluisfm.pruebatecnica_w2m.exception.LogicException;
import es.joseluisfm.pruebatecnica_w2m.repository.SpaceShipRepository;
import es.joseluisfm.pruebatecnica_w2m.repository.SpaceShipTypeRepository;
import es.joseluisfm.pruebatecnica_w2m.service.SpaceShipService;

@Service
public class SpaceShipServiceImpl implements SpaceShipService {

	private SpaceShipRepository spaceShipRepository;
	private SpaceShipTypeRepository spaceShipTypeRepository;

	public SpaceShipServiceImpl(SpaceShipRepository spaceShipRepository, SpaceShipTypeRepository spaceShipTypeRepository) {
		this.spaceShipRepository = spaceShipRepository;
		this.spaceShipTypeRepository = spaceShipTypeRepository;
	}

	@Override
	public SpaceShipsOUTDTO getAllSpaceShips(SpaceShipsINDTO spaceShipsINDTO) {

		Page<SpaceShipEntity> resultPage = spaceShipRepository.findAll(spaceShipsINDTO.getPageable().toPageable());

		SpaceShipsOUTDTO result = new SpaceShipsOUTDTO();
		result.setPageable(new PageableOUTDTO(resultPage));

		List<SpaceShipOUTDTO> spaceShips = new ArrayList<>();
		for (SpaceShipEntity spaceShipEntity : resultPage) {
			spaceShips.add(new SpaceShipOUTDTO(spaceShipEntity));
		}
		result.setSpaceShips(spaceShips);

		return result;
	}

	@Override
	public SpaceShipOUTDTO findById(long id) throws LogicException {
		Optional<SpaceShipEntity> spaceShipEntityOptional = this.spaceShipRepository.findById(id);
		if (!spaceShipEntityOptional.isPresent()) {
			throw new LogicException("SpaceShip not found", HttpStatus.NOT_FOUND);
		}
		SpaceShipEntity spaceShipEntity = spaceShipEntityOptional.get();

		return new SpaceShipOUTDTO(spaceShipEntity);
	}

	@Override
	public List<SpaceShipOUTDTO> findByName(String name) {
		Pageable pageable = PageRequest.of(0, 100);
		Page<SpaceShipEntity> pageResult = this.spaceShipRepository.findByName(name, pageable);
		List<SpaceShipEntity> entityList = pageResult.getContent();
		List<SpaceShipOUTDTO> result = new ArrayList<>();
		for (SpaceShipEntity entity : entityList) {
			result.add(new SpaceShipOUTDTO(entity));
		}
		return result;
	}

	@Override
	public SpaceShipOUTDTO newSpaceShip(NewSpaceShipINDTO newSpaceShipINDTO) throws LogicException {
		SpaceShipEntity spaceShipEntity = new SpaceShipEntity();
		spaceShipEntity.setName(newSpaceShipINDTO.getName());
		spaceShipEntity.setSpeed(newSpaceShipINDTO.getSpeed());
		spaceShipEntity.setType(this.getSpaceShiptTypeByIdType(newSpaceShipINDTO.getfIdType()));

		this.spaceShipRepository.save(spaceShipEntity);

		return new SpaceShipOUTDTO(spaceShipEntity);
	}

	private SpaceShipTypeEntity getSpaceShiptTypeByIdType(Long idType) throws LogicException {
		Optional<SpaceShipTypeEntity> spaceShipTypeEntityOptional = this.spaceShipTypeRepository.findById(idType);
		if (!spaceShipTypeEntityOptional.isPresent()) {
			throw new LogicException("SpaceShip type is not valid", HttpStatus.BAD_REQUEST);
		}
		return spaceShipTypeEntityOptional.get();
	}

	@Override
	public SpaceShipOUTDTO editSpaceShip(EditSpaceShipINDTO editSpaceShipINDTO) throws LogicException {
		Optional<SpaceShipEntity> spaceShipEntityOptional = this.spaceShipRepository.findById(editSpaceShipINDTO.getId());
		if (!spaceShipEntityOptional.isPresent()) {
			throw new LogicException("SpaceShip not found", HttpStatus.NOT_FOUND);
		}

		SpaceShipEntity spaceShipEntity = spaceShipEntityOptional.get();
		spaceShipEntity.setName(editSpaceShipINDTO.getName());
		spaceShipEntity.setSpeed(editSpaceShipINDTO.getSpeed());
		spaceShipEntity.setType(this.getSpaceShiptTypeByIdType(editSpaceShipINDTO.getfIdType()));

		this.spaceShipRepository.save(spaceShipEntity);

		return new SpaceShipOUTDTO(spaceShipEntity);
	}

	@Override
	public void deleteSpaceShip(Long id) throws LogicException {
		boolean exist = this.spaceShipRepository.existsById(id);
		if (!exist) {
			throw new LogicException("SpaceShip not found", HttpStatus.NOT_FOUND);
		}

		this.spaceShipRepository.deleteById(id);
	}

}
