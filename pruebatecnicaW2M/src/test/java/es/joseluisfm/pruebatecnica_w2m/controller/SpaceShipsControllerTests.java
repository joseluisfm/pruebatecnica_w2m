package es.joseluisfm.pruebatecnica_w2m.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import es.joseluisfm.pruebatecnica_w2m.entity.SpaceShipEntity;
import es.joseluisfm.pruebatecnica_w2m.repository.SpaceShipRepository;

@SpringBootTest
@AutoConfigureMockMvc
class SpaceShipsControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private SpaceShipRepository spaceShipRepository;
	
	private static final String URL_CONTROLLER = "/spaceShips";
	
	private SpaceShipEntity getFirstElement() {
		Pageable pageable = PageRequest.of(0, 1);
		return spaceShipRepository.findAll(pageable).getContent().get(0);
	}
	
	@Test
	void getAllSpaceShips_whenPageIsGiven_thenAllSpaceShipsIsReturned() throws Exception {
		Long total = spaceShipRepository.count();
		SpaceShipEntity firstEntity = this.getFirstElement();
		
		String json = ""
				+ "{"
				+ "   \"num\": 0,"
				+ "   \"size\": 1"
				+ "}";

	    mockMvc.perform(post(URL_CONTROLLER + "/getAll")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(json))
	    		.andExpect(status().isOk())
	    		.andExpect(jsonPath("$.spaceShips").isArray()) 
	    		.andExpect(jsonPath("$.spaceShips[0].id").value(firstEntity.getId()))
	            .andExpect(jsonPath("$.spaceShips[0].name").value(firstEntity.getName()))
	            .andExpect(jsonPath("$.spaceShips[0].speed").value(firstEntity.getSpeed()))
	            .andExpect(jsonPath("$.spaceShips[0].type").value(firstEntity.getType().getName()))
	            .andExpect(jsonPath("$.pageable.totalPages").value(total))  
	            .andExpect(jsonPath("$.pageable.totalElements").value(total)); 
	}
	
	@Test
	void getAllSpaceShips_whenPageIsNotGiven_then400ErrorIsReturned() throws Exception {
		String json = ""
				+ "{"
				+ "}";

	    mockMvc.perform(post(URL_CONTROLLER + "/getAll")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(json))
	    		.andExpect(status().isBadRequest());
	}
	
	@Test
	void getAllSpaceShips_whenPageNumberIsSurpassed_thenEmptyListIsReturned() throws Exception {
		String json = ""
				+ "{"
				+ "   \"num\": 99999,"
				+ "   \"size\": 999,"
				+ "   \"sorts\" : []"
				+ "}";

	    mockMvc.perform(post(URL_CONTROLLER + "/getAll")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(json))
			    .andExpect(status().isOk())
				.andExpect(jsonPath("$.spaceShips").isArray()) 
				.andExpect(jsonPath("$.spaceShips").value(new ArrayList<>())); 

	}
	
	@Test
	void findById_whenExistingIdIsGiven_thenEntityIsReturned() throws Exception {
		 SpaceShipEntity firstElement = this.getFirstElement();

		 mockMvc.perform(get(URL_CONTROLLER + "/findById?id=" + firstElement.getId())
		            .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$.id").value(firstElement.getId())) 
		            .andExpect(jsonPath("$.name").value(firstElement.getName()))  
		            .andExpect(jsonPath("$.speed").value(firstElement.getSpeed())) 
		            .andExpect(jsonPath("$.type").value(firstElement.getType().getName())); 
	}
	
	
	@Test
	void findById_whenNotExistingIdIsGiven_then404IsReturned() throws Exception {
		 Long id = 99999999999L;

		 mockMvc.perform(get(URL_CONTROLLER + "/findById?id=" + id)
		            .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isNotFound()); 
	}
	
	@Test
	void findById_whenIdIsNotGiven_then400IsReturned() throws Exception {
		 mockMvc.perform(get(URL_CONTROLLER + "/findById")
		            .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isBadRequest()); 
	}
	
	
	
	@Test
	void findByName_whenExistingNameIsGiven_thenResultListContainsEntity() throws Exception {
		 SpaceShipEntity firstElement = this.getFirstElement();

		 mockMvc.perform(get(URL_CONTROLLER + "/findByName?name=" + firstElement.getName())
		            .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$").isArray())
		            .andExpect(jsonPath("$[?(@.id == " + firstElement.getId() + " && @.name == '" + firstElement.getName() + "' && @.speed == " + firstElement.getSpeed() + " && @.type == '" + firstElement.getType().getName() + "')]").exists());  
	}
		    
	
	@Test
	void findByName_whenNotExistingNameIsGiven_thenEmptyListIsReturned() throws Exception {
		SpaceShipEntity firstElement = this.getFirstElement();
		String notExistingName = firstElement.getName() + "XXXXXXXXXXXXXXXXXXXXXXXXXX";

		 mockMvc.perform(get(URL_CONTROLLER + "/findByName?name=" + notExistingName)
		            .contentType(MediaType.APPLICATION_JSON))
		 			.andExpect(status().isOk())
					.andExpect(jsonPath("$").isArray()) 
					.andExpect(jsonPath("$").value(new ArrayList<>())); 
	}
	
	@Test
	void findByName_whenNameIsNotGiven_then400IsReturned() throws Exception {
		 mockMvc.perform(get(URL_CONTROLLER + "/findByName")
		            .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isBadRequest()); 
	}
	
	
	
	@Test
	void newSpaceShip_whenNewEntityIsCreated_thenIsInDB() throws Exception {
		String name = "TESTXXXXXXXXXXXXX";
		Double speed = 999.99;
		Long fIdType = 1L;
		String json = ""
				+ "{"
				+ "        \"name\": \"" + name + "\","
				+ "        \"speed\": " + speed + ","
				+ "        \"fIdType\": " + fIdType
				+ "}";
		
		 mockMvc.perform(post(URL_CONTROLLER + "/new")
				 	.content(json)
		            .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$.name").value(name)); 
		 
		 SpaceShipEntity shipEntity = this.spaceShipRepository.findByName(name, PageRequest.of(0, 1)).toList().get(0);
		 assertEquals(name, shipEntity.getName());
		 assertEquals(speed, shipEntity.getSpeed());
		 assertEquals(fIdType, shipEntity.getType().getId());
		
	}
	
	
	@Test
	void editSpaceShip_whenEditEntity_thenModificationIsInDB() throws Exception {
		SpaceShipEntity spaceShipEntity = this.getFirstElement();
		
		Long id = spaceShipEntity.getId();
		String name = "TESTXXXXXXXXXXXXX";
		Double speed = 999.99;
		Long fIdType = 1L;
		String json = ""
				+ "{"
				+ "        \"id\": \"" + id + "\","
				+ "        \"name\": \"" + name + "\","
				+ "        \"speed\": " + speed + ","
				+ "        \"fIdType\": " + fIdType
				+ "}";
		
		 mockMvc.perform(put(URL_CONTROLLER + "/edit")
				 	.content(json)
		            .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isOk())
		            .andExpect(jsonPath("$.name").value(name)); 
		 
		 SpaceShipEntity shipEntity = this.spaceShipRepository.findById(id).get();
		 assertEquals(id, shipEntity.getId());
		 assertEquals(name, shipEntity.getName());
		 assertEquals(speed, shipEntity.getSpeed());
		 assertEquals(fIdType, shipEntity.getType().getId());
	}
	
	
	@Test
	void editSpaceShip_whenIdIsNotExisting_then404IsReturned() throws Exception {
		Long id = 99999999999L;
		String name = "TESTXXXXXXXXXXXXX";
		Double speed = 999.99;
		Long fIdType = 1L;
		String json = ""
				+ "{"
				+ "        \"id\": \"" + id + "\","
				+ "        \"name\": \"" + name + "\","
				+ "        \"speed\": " + speed + ","
				+ "        \"fIdType\": " + fIdType
				+ "}";
		
		 mockMvc.perform(put(URL_CONTROLLER + "/edit")
				 	.content(json)
		            .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isNotFound());
	}
	
	
	@Test
	void deleteSpaceShip_whenDeleteEntity_thenNotExistIsInDB() throws Exception {
		SpaceShipEntity spaceShipEntity = this.getFirstElement();

		Long id = spaceShipEntity.getId();

		mockMvc.perform(delete(URL_CONTROLLER + "/delete?id=" + id)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		assertFalse(this.spaceShipRepository.existsById(id));
	}
	
	@Test
	void deleteSpaceShip_whenIdNotExist_then404IsReturned() throws Exception {
		Long id = 9999999999999999L;

		mockMvc.perform(delete(URL_CONTROLLER + "/delete?id=" + id)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void deleteSpaceShip_whenIdNull_then400IsReturned() throws Exception {
		mockMvc.perform(delete(URL_CONTROLLER + "/delete")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
}
