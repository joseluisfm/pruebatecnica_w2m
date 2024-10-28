package es.joseluisfm.pruebatecnica_w2m.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.joseluisfm.pruebatecnica_w2m.entity.SpaceShipEntity;

@Repository
public interface SpaceShipRepository extends CrudRepository<SpaceShipEntity, Long> {
	public Page<SpaceShipEntity> findAll(Pageable pageable);
	
	@Query(value = ""
			+ "select s "
			+ "from SpaceShipEntity s "
			+ "where UPPER(s.name) LIKE CONCAT('%', UPPER(:name), '%') "
			+ "order by id ASC "
			+ "")
	public Page<SpaceShipEntity> findByName(@Param("name") String name, Pageable pageable);
}
