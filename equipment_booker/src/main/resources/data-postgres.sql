insert into addresses (id, street, number, city, country, longitude, latitude) values (2, 'Puskinova', '17', 'Novi Sad', 'Srbija', 45.4777777, 54.3444444);
insert into addresses (id, street, number, city, country, longitude, latitude) values (3, 'Cara Dusana', '17', 'Novi Sad', 'Srbija', 45.4555555, 54.3555555);
insert into addresses (id, street, number, city, country, longitude, latitude) values (4, 'Futoska', '17', 'Novi Sad', 'Srbija', 45.4333333, 54.333333);


insert into companies (id, name, description, average_score, start_time, end_time, address_id) values (2, 'Medicom', 'Kompanija za medicinsku opremu', 3, '2023-02-01 08:00', '2023-02-01 18:00', 2);
insert into companies (id, name, description, average_score, start_time, end_time, address_id) values (3, 'Medical equipment', 'Kompanija za medicinsku opremu', 5, '2023-02-01 08:00', '2023-02-01 16:00', 3);
insert into companies (id, name, description, average_score, start_time, end_time, address_id) values (4, 'Medical', 'Kompanija za medicinsku opremu', 2, '2023-02-01 08:00', '2023-02-01 17:00', 4);


insert into equipment (id, name, description, type, quantity, company_id) values (3, 'Pacijent monitor', 'ePM pruža odlično vizuelno iskustvo, inteligentan rad, precizna fiziološka merenja, lagan tok rada i sveobuhvatnu opciju povezivanja za zahtevna bolnička podešavanja.', 'Aparati', 5, 2);
insert into equipment (id, name, description, type, quantity, company_id) values (4, 'Kardioloska stolica', 'Kardiološka stolica SPENCER 420, namenjena za transport pacijenata u sedećem položaju. Koristi se kod kardioloških pacijenata.', 'Transport', 7, 2);
insert into equipment (id, name, description, type, quantity, company_id) values (5, 'Sterilna gaza', 'Sterilna gaza primenjuje se u medicini i pogodna je za aplikaciju na ranu direktno.', 'Potrosni materijal', 20, 3);
insert into equipment (id, name, description, type, quantity, company_id) values (6, 'Aspirator', 'Portabilni hirurški aspirator namenjen za aspiraciju telesnih tečnosti kod dece i odraslih.', 'Urgentna medicina', 3, 3);
insert into equipment (id, name, description, type, quantity, company_id) values (7, 'Otoskop', 'Otoskop sa halogenom sijalicom.', 'Dijagnosticki instrumenti', 10, 4);
insert into equipment (id, name, description, type, quantity, company_id) values (8, 'Humby noz', 'Humby nož namenjen za korišćenje u dermatologiji.', 'Hirurski instrumenti', 10, 4);




