insert into flight_booking.user (dtype,id, username, password, email) values ('SystemAdmin', 1, 'admin', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'admin@admin.com');
insert into flight_booking.authorities (id, role) values ('1', 'ROLE_SYSTEM_ADMIN');
insert into flight_booking.user_authority (user_id, role_id) values (1, 1);

