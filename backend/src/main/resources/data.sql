
insert into flight_booking.user (dtype,id, username, password, email) values ('SystemAdmin', 1, 'admin', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'admin@admin.com');
insert into flight_booking.authorities (id, role) values ('1', 'ROLE_SYSTEM_ADMIN');
insert into flight_booking.user_authority (user_id, role_id) values (1, 1);

INSERT INTO flight_booking.user(dtype, id, email, first_name, last_name, password, username, bonus_points, is_confirmed) VALUES 
('RegisteredUser', 2, 'filipeng97@gmail.com', 'Filip', 'Mladenovic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'coxisd', 0, true);
insert into flight_booking.authorities (id, role) values ('2', 'ROLE_REGISTERED_USER');
insert into flight_booking.user_authority (user_id, role_id) values (2, 2);

INSERT INTO flight_booking.user(dtype, id, username, first_name, last_name, password, email) VALUES ('RentACarAdmin', 3, 'rcadmin', 'Filip', 'Mladenovic', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'rentACarAdmin@gmail.com');
insert into flight_booking.authorities (id, role) values ('3', 'ROLE_RENT_A_CAR_ADMIN');
insert into flight_booking.user_authority (user_id, role_id) values (3, 3);



INSERT INTO flight_booking.price_lists (id) VALUES (1);
INSERT INTO flight_booking.rentacar (id, description, name, additional_services_price_list_id) VALUES  (1,'Neki opis koji opisuje ovaj rent-a-car service, a da je dovoljno dugacakNeki opis koji opisuje ovaj rent-a-car service, a da je dovoljno dugacak.', 'Moj Rent A CAR', 1);

INSERT INTO flight_booking.price_list_items(id, description, discount, name, price) VALUES (1, "If you have low driver experience this is recommended.", 10, "Driver", 50);
INSERT INTO flight_booking.price_list_items(id, description, discount, name, price) VALUES (2, "We can tank vehicle after return.", 5, "Fuel", 30);
INSERT INTO flight_booking.price_list_items(id, description, discount, name, price) VALUES (3, "Insurance for you and passangers.", 5, "Insurance", 10);
INSERT INTO flight_booking.price_list_items(id, description, discount, name, price) VALUES (4, "Not needed but you can choose.", 5, "Something elese", 30);
INSERT INTO flight_booking.price_list_items(id, description, discount, name, price) VALUES (5, "Not needed but you can choose.", 5, "Something elese", 30);

INSERT INTO flight_booking.price_lists_price_list_items (price_list_id, price_list_items_id) VALUES (1,1);
INSERT INTO flight_booking.price_lists_price_list_items (price_list_id, price_list_items_id) VALUES (1,2);
INSERT INTO flight_booking.price_lists_price_list_items (price_list_id, price_list_items_id) VALUES (1,3);
INSERT INTO flight_booking.price_lists_price_list_items (price_list_id, price_list_items_id) VALUES (1,4);
INSERT INTO flight_booking.price_lists_price_list_items (price_list_id, price_list_items_id) VALUES (1,5);

INSERT INTO flight_booking.rentacar_administrators (rentacar_id, administrators_id) VALUE (1,3);

INSERT INTO flight_booking.vehicles (id, air_condition, average_feedback, daily_rental_price, engine, fuel, image, manufacturer, model, seats_count, transmission, year, branch_office_id)
VALUES (1, 1, 4.3, 250, 3.2, 'Gasoline', 'bmw.jpg', 'BMW', '520', 5, 1, 1990, 1);

INSERT INTO flight_booking.vehicles (id, air_condition, average_feedback, daily_rental_price, engine, fuel, image, manufacturer, model, seats_count, transmission, year, branch_office_id)
VALUES (2, 1, 10, 375, 1.9, 'Diesel', 'laguna.jpg', 'RENAULT', 'LAGUNA', 5, 0, 2003, 1 );

INSERT INTO flight_booking.vehicles (id, air_condition, average_feedback, daily_rental_price, engine, fuel, image, manufacturer, model, seats_count, transmission, year, branch_office_id)
VALUES (3, 1, 4.3, 280, 3.2, 'Gasoline', 'mercedes.jpg', 'Mercedes benz', 'GLC', 5, 1, 2011, 1 );

INSERT INTO flight_booking.vehicles (id, air_condition, average_feedback, daily_rental_price, engine, fuel, image, manufacturer, model, seats_count, transmission, year, branch_office_id)
VALUES (4, 0, 4.3, 500, 2.0, 'Gasoline', 'bmw.jpg', 'BMW', '320', 5, 0, 1990, 1);

INSERT INTO flight_booking.vehicles (id, air_condition, average_feedback, daily_rental_price, engine, fuel, image, manufacturer, model, seats_count, transmission, year, branch_office_id)
VALUES (5, 1, 4.3, 420, 3.2, 'Gasoline', 'bmw.jpg', 'BMW', '520', 5, 1, 1990, 1);

INSERT INTO flight_booking.vehicles (id, air_condition, average_feedback, daily_rental_price, engine, fuel, image, manufacturer, model, seats_count, transmission, year, branch_office_id)
VALUES (6, 1, 10, 340, 1.9, 'Diesel', 'laguna.jpg', 'RENAULT', 'LAGUNA', 5, 0, 2003, 1);

INSERT INTO flight_booking.vehicles (id, air_condition, average_feedback, daily_rental_price, engine, fuel, image, manufacturer, model, seats_count, transmission, year, branch_office_id)
VALUES (7, 1, 4.3, 720, 3.2, 'Gasoline', 'bmw.jpg', 'BMW', '520', 5, 1, 1990, 2);

INSERT INTO flight_booking.vehicles (id, air_condition, average_feedback, daily_rental_price, engine, fuel, image, manufacturer, model, seats_count, transmission, year, branch_office_id)
VALUES (8, 1, 4.3, 900, 3.2, 'Gasoline', 'bmw.jpg', 'BMW', '520', 5, 1, 1990, 2);

INSERT INTO flight_booking.vehicles (id, air_condition, average_feedback, daily_rental_price, engine, fuel, image, manufacturer, model, seats_count, transmission, year, branch_office_id)
VALUES (9, 1, 10, 110, 1.9, 'Diesel', 'laguna.jpg', 'RENAULT', 'LAGUNA', 5, 0, 2003, 1);

INSERT INTO flight_booking.vehicles (id, air_condition, average_feedback, daily_rental_price, engine, fuel, image, manufacturer, model, seats_count, transmission, year, branch_office_id)
VALUES (10, 1, 4.3, 850, 3.2, 'Gasoline', 'bmw.jpg', 'BMW', '520', 5, 1, 1990, 2);

INSERT INTO flight_booking.vehicles (id, air_condition, average_feedback, daily_rental_price, engine, fuel, image, manufacturer, model, seats_count, transmission, year, branch_office_id)
VALUES (11, 1, 4.3, 390, 3.2, 'Gasoline', 'bmw.jpg', 'BMW', '520', 5, 1, 1990, 3 );

INSERT INTO flight_booking.vehicles (id, air_condition, average_feedback, daily_rental_price, engine, fuel, image, manufacturer, model, seats_count, transmission, year, branch_office_id)
VALUES (12, 1, 10, 250, 1.9, 'Diesel', 'laguna.jpg', 'RENAULT', 'LAGUNA', 5, 0, 2003, 1);

INSERT INTO flight_booking.vehicles (id, air_condition, average_feedback, daily_rental_price, engine, fuel, image, manufacturer, model, seats_count, transmission, year, branch_office_id)
VALUES (13, 1, 4.3, 250, 3.2, 'Gasoline', 'bmw.jpg', 'BMW', '520', 5, 1, 1990, 1);

INSERT INTO flight_booking.vehicles (id, air_condition, average_feedback, daily_rental_price, engine, fuel, image, manufacturer, model, seats_count, transmission, year, branch_office_id)
VALUES (14, 1, 10, 375, 1.9, 'Diesel', 'laguna.jpg', 'RENAULT', 'LAGUNA', 5, 0, 2003, 1 );

INSERT INTO flight_booking.vehicles (id, air_condition, average_feedback, daily_rental_price, engine, fuel, image, manufacturer, model, seats_count, transmission, year, branch_office_id)
VALUES (15, 1, 4.3, 280, 3.2, 'Gasoline', 'mercedes.jpg', 'Mercedes benz', 'GLC', 5, 1, 2011, 1 );

INSERT INTO flight_booking.vehicles (id, air_condition, average_feedback, daily_rental_price, engine, fuel, image, manufacturer, model, seats_count, transmission, year, branch_office_id)
VALUES (16, 0, 4.3, 500, 2.0, 'Gasoline', 'bmw.jpg', 'BMW', '320', 5, 0, 1990, 1);

INSERT INTO flight_booking.vehicles (id, air_condition, average_feedback, daily_rental_price, engine, fuel, image, manufacturer, model, seats_count, transmission, year, branch_office_id)
VALUES (17, 1, 4.3, 420, 3.2, 'Gasoline', 'bmw.jpg', 'BMW', '520', 5, 1, 1990, 1);

INSERT INTO flight_booking.vehicles (id, air_condition, average_feedback, daily_rental_price, engine, fuel, image, manufacturer, model, seats_count, transmission, year, branch_office_id)
VALUES (18, 1, 10, 340, 1.9, 'Diesel', 'laguna.jpg', 'RENAULT', 'LAGUNA', 5, 0, 2003, 1);


INSERT INTO flight_booking.locations (id, city, country, street) VALUES (1, 'Smederevo', 'Serbia','Slovenska 1/a');
INSERT INTO flight_booking.locations (id, city, country, street) VALUES (2, 'Beograd', 'Serbia','Slovenska 1/a');
INSERT INTO flight_booking.locations (id, city, country, street) VALUES (3, 'Stara pazova', 'Serbia','Slovenska 1/a');
INSERT INTO flight_booking.locations (id, city, country, street) VALUES (4, 'Subotica', 'Serbia','Slovenska 1/a');
INSERT INTO flight_booking.locations (id, city, country, street) VALUES (5, 'Mladenovac', 'Serbia','Slovenska 1/a');


INSERT INTO flight_booking.branch_office (id, location_id, main_office_id) VALUES (1,1,1);
INSERT INTO flight_booking.branch_office (id, location_id, main_office_id) VALUES (2,2,1);
INSERT INTO flight_booking.branch_office (id, location_id, main_office_id) VALUES (3,3,1);
INSERT INTO flight_booking.branch_office (id, location_id, main_office_id) VALUES (4,4,1);
INSERT INTO flight_booking.branch_office (id, location_id, main_office_id) VALUES (5,5,1);


INSERT INTO flight_booking.vehicle_reservations (dtype, id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 1, '2019-06-30', '2019-07-07', 1000, 1);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 2, '2019-07-07', '2019-07-14', 1000, 2);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 3, '2019-07-21', '2019-07-28', 1000, 3);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 4, '2019-06-30', '2019-07-07', 1000, 4);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 5, '2019-07-07', '2019-07-14', 1000, 5);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 6, '2019-07-21', '2019-07-28', 1000, 6);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 7, '2019-06-30', '2019-07-07', 1000, 7);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 8, '2019-07-07', '2019-07-14', 1000, 8);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 9, '2019-07-21', '2019-07-28', 1000, 9);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 10, '2019-06-30', '2019-07-07', 1000, 10);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 11, '2019-07-07', '2019-07-14', 1000, 11);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 12, '2019-07-21', '2019-07-28', 1000, 12);

/*QUICK RESERVATIONS*/
INSERT INTO flight_booking.vehicle_reservations (dtype, id, date_from, date_to, total_price, reserved_vehicle_id, version) 
VALUES ("QuickVehicleReservation", 13, '2019-07-30', '2019-08-07', 1000, 1, 1);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id, version) 
VALUES ("QuickVehicleReservation", 14, '2019-08-07', '2019-08-14', 1000, 2, 1);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id, version) 
VALUES ("QuickVehicleReservation", 15, '2019-08-21', '2019-08-28', 1000, 3, 1);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id, version) 
VALUES ("QuickVehicleReservation", 16, '2019-07-30', '2019-08-07', 1000, 4, 1);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id, version) 
VALUES ("QuickVehicleReservation", 17, '2019-08-07', '2019-08-14', 1000, 5, 1);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id, version) 
VALUES ("QuickVehicleReservation", 18, '2019-08-21', '2019-08-28', 1000, 6, 1);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id, version) 
VALUES ("QuickVehicleReservation", 19, '2019-07-30', '2019-08-07', 1000, 7, 1);


INSERT INTO flight_booking.vehicle_reservations (dtype, id, date_from, date_to, total_price, reserved_vehicle_id, version) 
VALUES ("QuickVehicleReservation", 20, '2019-06-30', '2019-07-07', 1000, 13, 1);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id, version) 
VALUES ("QuickVehicleReservation", 21, '2019-07-07', '2019-07-14', 1000, 14, 1);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id, version) 
VALUES ("QuickVehicleReservation", 22, '2019-07-21', '2019-07-28', 1000, 15, 1);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id, version) 
VALUES ("QuickVehicleReservation", 23, '2019-06-30', '2019-07-07', 1000, 16, 1);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id, version) 
VALUES ("QuickVehicleReservation", 24, '2019-07-07', '2019-07-14', 1000, 17, 1);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id, version) 
VALUES ("QuickVehicleReservation", 25, '2019-07-21', '2019-07-28', 1000, 18, 1);

/*Prosle rezervacije*/
INSERT INTO flight_booking.vehicle_reservations (dtype, id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 26, '2019-01-02', '2019-01-05', 1000, 1);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 27, '2019-02-02', '2019-02-05', 1000, 2);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 28, '2019-02-01', '2019-02-08', 1000, 3);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 29, '2019-03-03', '2019-03-07', 1000, 4);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 30, '2019-03-01', '2019-03-03', 1000, 5);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 31, '2019-04-01', '2019-04-08', 1000, 6);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 32, '2019-04-11', '2019-04-27', 1000, 7);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 33, '2019-01-07', '2019-01-14', 1000, 8);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 34, '2019-01-02', '2019-01-08', 1000, 9);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 35, '2019-05-04', '2019-05-12', 1000, 10);

INSERT INTO flight_booking.vehicle_reservations (dtype,id, date_from, date_to, total_price, reserved_vehicle_id) 
VALUES ("VehicleReservation", 36, '2019-05-12', '2019-05-14', 1000, 11);



INSERT INTO  flight_booking.rentacar_quick_vehicle_reservations (rentacar_id, quick_vehicle_reservations_id) VALUES (1, 13);
INSERT INTO  flight_booking.rentacar_quick_vehicle_reservations (rentacar_id, quick_vehicle_reservations_id) VALUES (1, 14);
INSERT INTO  flight_booking.rentacar_quick_vehicle_reservations (rentacar_id, quick_vehicle_reservations_id) VALUES (1, 15);
INSERT INTO  flight_booking.rentacar_quick_vehicle_reservations (rentacar_id, quick_vehicle_reservations_id) VALUES (1, 16);
INSERT INTO  flight_booking.rentacar_quick_vehicle_reservations (rentacar_id, quick_vehicle_reservations_id) VALUES (1, 17);
INSERT INTO  flight_booking.rentacar_quick_vehicle_reservations (rentacar_id, quick_vehicle_reservations_id) VALUES (1, 18);
INSERT INTO  flight_booking.rentacar_quick_vehicle_reservations (rentacar_id, quick_vehicle_reservations_id) VALUES (1, 19);
INSERT INTO  flight_booking.rentacar_quick_vehicle_reservations (rentacar_id, quick_vehicle_reservations_id) VALUES (1, 20);
INSERT INTO  flight_booking.rentacar_quick_vehicle_reservations (rentacar_id, quick_vehicle_reservations_id) VALUES (1, 21);
INSERT INTO  flight_booking.rentacar_quick_vehicle_reservations (rentacar_id, quick_vehicle_reservations_id) VALUES (1, 22);
INSERT INTO  flight_booking.rentacar_quick_vehicle_reservations (rentacar_id, quick_vehicle_reservations_id) VALUES (1, 23);
INSERT INTO  flight_booking.rentacar_quick_vehicle_reservations (rentacar_id, quick_vehicle_reservations_id) VALUES (1, 24);
INSERT INTO  flight_booking.rentacar_quick_vehicle_reservations (rentacar_id, quick_vehicle_reservations_id) VALUES (1, 25);



INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (13, 1);
INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (13, 2);

INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (14, 3);
INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (14, 4);

INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (15, 1);
INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (15, 2);

INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (16, 3);
INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (16, 4);

INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (17, 1);
INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (17, 2);

INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (18, 3);
INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (18, 4);

INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (19, 1);
INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (19, 2);

INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (20, 3);
INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (20, 4);

INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (21, 1);
INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (21, 2);

INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (22, 3);
INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (22, 4);

INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (23, 1);
INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (23, 2);

INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (24, 3);
INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (24, 4);

INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (25, 1);
INSERT INTO  flight_booking.vehicle_reservations_additional_services (vehicle_reservation_id, additional_services_id) VALUES (25, 2);




/*HOTEL*/
INSERT INTO flight_booking.price_lists (id) VALUES (2);

INSERT INTO flight_booking.locations (id, city, country, street) VALUES (6, 'Stara Pazova', 'Serbia','Neka ulica');

INSERT INTO flight_booking.hotels (id, description, name, location_id, additional_services_price_list_id) 
VALUES (1,'Neki opis koji opisuje ovaj hotel, a da je dovoljno dugacakNeki opis koji opisuje ovaj hotel, a da je dovoljno dugacak.','Stara Pazova rezidencija', 6, 2);


INSERT INTO flight_booking.price_list_items(id, description, discount, name, price) VALUES (6, "Not needed but you can choose.", 10, "Something elese", 50);
INSERT INTO flight_booking.price_list_items(id, description, discount, name, price) VALUES (7, "Not needed but you can choose.", 5, "Something elese", 30);
INSERT INTO flight_booking.price_list_items(id, description, discount, name, price) VALUES (8, "Not needed but you can choose.", 5, "Something elese", 10);
INSERT INTO flight_booking.price_list_items(id, description, discount, name, price) VALUES (9, "Not needed but you can choose.", 5, "Something elese", 30);
INSERT INTO flight_booking.price_list_items(id, description, discount, name, price) VALUES (10, "Not needed but you can choose.", 5, "Something elese", 30);

INSERT INTO flight_booking.price_lists_price_list_items (price_list_id, price_list_items_id) VALUES (2,6);
INSERT INTO flight_booking.price_lists_price_list_items (price_list_id, price_list_items_id) VALUES (2,7);
INSERT INTO flight_booking.price_lists_price_list_items (price_list_id, price_list_items_id) VALUES (2,8);
INSERT INTO flight_booking.price_lists_price_list_items (price_list_id, price_list_items_id) VALUES (2,9);
INSERT INTO flight_booking.price_lists_price_list_items (price_list_id, price_list_items_id) VALUES (2,10);








