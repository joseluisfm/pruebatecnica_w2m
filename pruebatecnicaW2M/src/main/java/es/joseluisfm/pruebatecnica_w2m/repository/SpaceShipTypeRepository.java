package es.joseluisfm.pruebatecnica_w2m.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import es.joseluisfm.pruebatecnica_w2m.entity.SpaceShipTypeEntity;

@Repository
public interface SpaceShipTypeRepository extends CrudRepository<SpaceShipTypeEntity, Long> {

}
