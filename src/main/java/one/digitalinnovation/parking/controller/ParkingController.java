package one.digitalinnovation.parking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import one.digitalinnovation.parking.controller.mapper.ParkingMapper;
import one.digitalinnovation.parking.dto.ParkingCreateDTO;
import one.digitalinnovation.parking.dto.ParkingDTO;
import one.digitalinnovation.parking.model.Parking;
import one.digitalinnovation.parking.service.ParkingService;

@RestController
@RequestMapping("/parking")
public class ParkingController {
	
	private final ParkingService parkingService;
	private final ParkingMapper parkingMapper;

	public ParkingController(ParkingService parkingService, ParkingMapper parkingMapper) {
		this.parkingService = parkingService;
		this.parkingMapper = parkingMapper;
	}

	@GetMapping
	public ResponseEntity<List<ParkingDTO>> findAll() {
		
		List<Parking> parkingList =  parkingService.findAll();
		List<ParkingDTO> result = parkingMapper.toParkingDTOList(parkingList);
		return ResponseEntity.ok(result);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ParkingDTO> findById(@PathVariable String id) {
		
		Parking parking =  parkingService.findById(id);
		ParkingDTO result = parkingMapper.toParkingDTO(parking);
		return ResponseEntity.ok(result);
		
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("/{id}")
	public ResponseEntity remove(@PathVariable String id) {
		
		parkingService.remove(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@PostMapping
	public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDTO dto) {
		var parkingCreate = parkingMapper.toParkingCreate(dto);
		var parking =  parkingService.create(parkingCreate);
		var result = parkingMapper.toParkingDTO(parking);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ParkingDTO> update(@PathVariable String id, @RequestBody ParkingCreateDTO dto) {
		var parkingCreate = parkingMapper.toParkingCreate(dto);
		var parking =  parkingService.update(id, parkingCreate);
		var result = parkingMapper.toParkingDTO(parking);
		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<ParkingDTO> checkOut(@PathVariable String id){
		Parking parking = parkingService.checkOut(id);
		return ResponseEntity.ok(parkingMapper.toParkingDTO(parking));
	}
}
