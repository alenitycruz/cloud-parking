package one.digitalinnovation.parking.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import one.digitalinnovation.parking.exception.ParkingNotFoundException;
import one.digitalinnovation.parking.model.Parking;

@Service
public class ParkingService {

	private static Map<String, Parking> parkingMap = new HashMap();
	
	static {
		var id = getUUID();
		var id1 = getUUID();
		Parking parking = new Parking(id, "DMS 1111", "SC", "Celta", "Preto");
		Parking parking1 = new Parking(id, "ALE 1111", "PE", "Golf", "Dourado");
		parkingMap.put(id, parking);
		parkingMap.put(id1, parking1);
	}
	
	public List<Parking> findAll(){
		return parkingMap.values().stream().collect(Collectors.toList());
	}
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public Parking findById(String id) {
		
		Parking parking = parkingMap.get(id);
		if(parking == null) {
			throw new ParkingNotFoundException(id);
		}
		return parking;
	}

	public Parking create(Parking parkingCreate) {
		String uuid = getUUID();
		parkingCreate.setId(getUUID());
		parkingCreate.setEntryDate(LocalDateTime.now());
		parkingMap.put(uuid, parkingCreate);
		return parkingCreate;
	}

	public void remove(String id) {

		findById(id);
		parkingMap.remove(id);
	}

	public Parking update(String id, Parking parkingCreate) {
		Parking parking = findById(id);
		parking.setColor(parkingCreate.getColor());
		parkingMap.replace(id, parking);
		return parking;
	}
}
