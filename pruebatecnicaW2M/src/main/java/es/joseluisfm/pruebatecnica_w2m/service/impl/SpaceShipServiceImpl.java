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
import es.joseluisfm.pruebatecnica_w2m.dto.in.PageableINDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.out.PageableOUTDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.out.SpaceShipOUTDTO;
import es.joseluisfm.pruebatecnica_w2m.dto.out.SpaceShipsOUTDTO;
import es.joseluisfm.pruebatecnica_w2m.entity.SpaceShipEntity;
import es.joseluisfm.pruebatecnica_w2m.entity.SpaceShipTypeEntity;
import es.joseluisfm.pruebatecnica_w2m.exception.LogicException;
import es.joseluisfm.pruebatecnica_w2m.kafka.KafkaCache;
import es.joseluisfm.pruebatecnica_w2m.repository.SpaceShipRepository;
import es.joseluisfm.pruebatecnica_w2m.repository.SpaceShipTypeRepository;
import es.joseluisfm.pruebatecnica_w2m.service.SpaceShipService;
import es.joseluisfm.pruebatecnica_w2m.utils.JsonUtils;

@Service
public class SpaceShipServiceImpl implements SpaceShipService {

	private SpaceShipRepository spaceShipRepository;
	private SpaceShipTypeRepository spaceShipTypeRepository;
	
	private KafkaCache kafkaCache;
	
	private static final String CACHE_PREFFIX = "SPACESHIP-";
	
	private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SpaceShipServiceImpl.class);

	public SpaceShipServiceImpl(SpaceShipRepository spaceShipRepository, SpaceShipTypeRepository spaceShipTypeRepository, KafkaCache kafkaCache) {
		this.spaceShipRepository = spaceShipRepository;
		this.spaceShipTypeRepository = spaceShipTypeRepository;
		this.kafkaCache = kafkaCache;
	}

	@Override
	public SpaceShipsOUTDTO getAllSpaceShips(PageableINDTO pageableINDTO) {

		Page<SpaceShipEntity> resultPage = spaceShipRepository.findAll(pageableINDTO.toPageable());

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
		try {
			SpaceShipOUTDTO cacheValue = (SpaceShipOUTDTO) kafkaCache.getValue(CACHE_PREFFIX + id, SpaceShipOUTDTO.class);
			if (cacheValue != null) { // in cache
				LOGGER.info("Id {} in cache", id);
				return cacheValue;
			} else { //not in cache
				LOGGER.info("Id {} not in cache", id);
				Optional<SpaceShipEntity> spaceShipEntityOptional = this.spaceShipRepository.findById(id);
				if (!spaceShipEntityOptional.isPresent()) {
					throw new LogicException("SpaceShip not found", HttpStatus.NOT_FOUND);
				}
				SpaceShipEntity spaceShipEntity = spaceShipEntityOptional.get();
				SpaceShipOUTDTO spaceShipOUTDTO = new SpaceShipOUTDTO(spaceShipEntity);
				kafkaCache.setValue(CACHE_PREFFIX + id, JsonUtils.toJson(spaceShipOUTDTO));
				return spaceShipOUTDTO;
			}
		} catch (Exception e) {
			throw new LogicException("Error getting space ship by id", e);
		}		
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
		spaceShipEntity.setType(this.getSpaceShipTypeByIdType(newSpaceShipINDTO.getfIdType()));

		this.spaceShipRepository.save(spaceShipEntity);

		return new SpaceShipOUTDTO(spaceShipEntity);
	}

	/**
	 * Gets the space ship type by id type.
	 *
	 * @param idType the id type
	 * @return the space ship  type entity by id type
	 * @throws LogicException the logic exception, when a type is not found
	 */
	private SpaceShipTypeEntity getSpaceShipTypeByIdType(Long idType) throws LogicException {
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
		spaceShipEntity.setType(this.getSpaceShipTypeByIdType(editSpaceShipINDTO.getfIdType()));

		this.spaceShipRepository.save(spaceShipEntity);
		
		this.kafkaCache.remove(CACHE_PREFFIX + spaceShipEntity.getId()); // borrar cache

		return new SpaceShipOUTDTO(spaceShipEntity);
	}

	@Override
	public void deleteSpaceShip(Long id) throws LogicException {
		boolean exist = this.spaceShipRepository.existsById(id);
		if (!exist) {
			throw new LogicException("SpaceShip not found", HttpStatus.NOT_FOUND);
		}

		this.spaceShipRepository.deleteById(id);
		this.kafkaCache.remove(CACHE_PREFFIX + id); // borrar de cache
	}

}
