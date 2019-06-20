package com.tim10.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/rooms")
public class RoomController {
	
//	@Autowired
//	private RoomService roomService;
	
//	@RequestMapping(value="/getRooms/{dateFrom}/{dateTo}", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> getRooms(@RequestBody List<RoomTypesDTO> lista,
//										@PathVariable("dateFrom") String dateFrom,
//										@PathVariable("dateTo") String dateTo) throws ParseException{
//		List<Room> rooms;
//		List<RoomDTO> responseRooms = new ArrayList<>();
//		int numberOfRooms;
//		Date checkInDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateFrom);
//		Date checkOutDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateTo);
//		
//		for(RoomTypesDTO roomTypeDTO : lista) {
//			numberOfRooms = roomTypeDTO.getNumberOfRooms();
//			rooms = roomService.getRooms(roomTypeDTO.getRoomType().getId());
//			
//			/*Proverimo da li uopste postoji trazeni broj soba*/
//			if(rooms.size() < numberOfRooms) 
//				return new ResponseEntity<>("Unfortunately, " + roomTypeDTO.getNumberOfRooms() + " " + roomTypeDTO.getRoomType().getType().toUpperCase() + " rooms are not available",
//											HttpStatus.FORBIDDEN);
//			
//			/* Ako postoji trazeni broj soba, ponovo proveravamo rezervacije svake od soba*/
//			for(Room room : rooms) {
//				if(numberOfRooms == 0)
//					break;
//				
//				if(!room.isReserved(checkInDate, checkOutDate)) {
//					RoomDTO r = new RoomDTO(room);
//					responseRooms.add(r);
//					numberOfRooms--;
//				}
//			}
//			if(numberOfRooms != 0)
//				return new ResponseEntity<>("Unfortunately, " + roomTypeDTO.getNumberOfRooms() + " " + roomTypeDTO.getRoomType().getType().toUpperCase() + " rooms are currently not available",
//											HttpStatus.FORBIDDEN);
//		}
//		return new ResponseEntity<>(responseRooms, HttpStatus.OK);
//	}
}
