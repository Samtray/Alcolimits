USE master;  
GO  
CREATE DATABASE Alcolimits  
ON   
( NAME = Alcolimits_dat,  
    FILENAME = 'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\Alcolimits_dat.mdf',  
    SIZE = 10,  
    MAXSIZE = 50,  
    FILEGROWTH = 5 )  
LOG ON  
( NAME = Alcolimits_log,  
    FILENAME = 'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\Alcolimits_log.ldf',  
    SIZE = 5MB,  
    MAXSIZE = 25MB,  
    FILEGROWTH = 5MB ) ;  
GO  

USE Alcolimits;

create table logs(
	id_log varchar (10) primary key,
	date_time datetime,
	content varchar (50)
)


create table alcoholSensor(
	id_alcohol varchar (10) primary key,
	val int,
	result varchar (100)
)

create table temperatureSensor(
	id_temperature varchar (10) primary key,
	val float (6)
)


create table vehicles(
	plate_id varchar (7) primary key,
	model varchar (50),
	anio char (4),
	gps varchar(200),
	color varchar (10),
	isDriving varchar (50),
	isOn varchar (10),
	alcohol_inf varchar (10),
	temperature_inf varchar (10),
	logs_inf varchar (10)	
	FOREIGN KEY (alcohol_inf) REFERENCES alcoholSensor(id_alcohol),
	FOREIGN KEY (temperature_inf) REFERENCES temperatureSensor(id_temperature),
	FOREIGN KEY (logs_inf) REFERENCES logs(id_log)


)

create table drivers(
	id varchar(6) primary key,
	name varchar (50),
	middle_name varchar (25),
	last_name varchar (45),
	photo varchar(100),
	VehiclePlate varchar (7)
	FOREIGN KEY (VehiclePlate) REFERENCES Vehicles(plate_id)
)


insert into logs values('LOG1', CURRENT_TIMESTAMP, 'Alcohol test passed');
select * from logs;

insert into temperatureSensor values('TMP1', 12.22)
select * from temperatureSensor;

insert into alcoholSensor values('ALH1', 100, 'Test passed, alcohol detected.')
select * from alcoholSensor;

insert into vehicles values('A3827KH', 'Nissan Sentra', '2020', 'Zona Río, Blvd. Zamurano 3000', 'Red', 'Not Driving', 'Off', 'ALH1', 'TMP1', 'LOG1')

insert into drivers values ('D12021','Gustavo', 'Torrecillas', 'Beltrán', 'tphoto.png', 'A3827KH')
select * from drivers;


select * from drivers
inner join vehicles on VehiclePlate = plate_id
inner join temperatureSensor on id_temperature = temperature_inf
inner join alcoholSensor on id_alcohol = alcohol_inf
inner join logs on id_log = logs_inf
where id = 'D12021'


