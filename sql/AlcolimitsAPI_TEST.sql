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
--drop database Alcolimits;

create table alcoholSensor(
	id_alcohol int identity(1000,1) primary key,
	val int,
	result varchar (100)
)

create table temperatureSensor(
	id_temperature int identity(1000,1) primary key,
	val float (6)
)

create table vehicleStatus(
	id_status int identity(1000,1) primary key,
	isDriving varchar (50),
	isOn varchar (10)
);


create table vehicles(
	id_vehicle int identity(1000,1) primary key,
	plate varchar (7) unique,
	model varchar (50),
	vyear char (4),
	gps varchar(200),
	color varchar (10),
	status_inf int,
	alcohol_inf int,
	temperature_inf int,
	FOREIGN KEY (status_inf) REFERENCES vehicleStatus(id_status),
	FOREIGN KEY (alcohol_inf) REFERENCES alcoholSensor(id_alcohol),
	FOREIGN KEY (temperature_inf) REFERENCES temperatureSensor(id_temperature)
)


create table logs(
	id_log int identity(1000,1) primary key,
	date_time datetime,
	content varchar (100),
	VehiclePlate varchar (7), 
	FOREIGN KEY (VehiclePlate) REFERENCES vehicles(plate)
)


create table drivers(
	id_driver int identity(1000,1) primary key,
	first_name varchar (50),
	middle_name varchar (25),
	last_name varchar (45),
	photo varchar(100),
	VehiclePlate varchar (7), 
	FOREIGN KEY (VehiclePlate) REFERENCES vehicles(plate)
)

ALTER TABLE Drivers 
ADD CONSTRAINT FK_DriverUpdatePlate 
FOREIGN KEY (VehiclePlate) 
REFERENCES Vehicles (plate) ON UPDATE CASCADE; 

ALTER TABLE logs 
ADD CONSTRAINT FK_LogUpdatePlate 
FOREIGN KEY (VehiclePlate) 
REFERENCES Vehicles (plate) ON UPDATE CASCADE; 


--GetDriver procedure--------------------------------------------------------------------
create procedure GetDrivers 
as 
select * from drivers
go;
--exec GetDrivers;

--NewDriver procedure--------------------------------------------------------------------
create procedure AddNewDriver 
@first_name varchar(50), 
@middle_name varchar (25), 
@last_name varchar (45), 
@photo varchar (100),
@VehiclePlate varchar(7)
as
insert into drivers values (@first_name, @middle_name, @last_name, @photo, @VehiclePlate)

--exec AddNewDriver 'Gustavo', 'Torrecillas', 'Beltran', 'tphoto.png', 'D12023T';
--select * from drivers

--UpdateDriver procedure------------------------------------------------------------------

create procedure UpdateDriver 
@id_driver int, 
@first_name varchar(50), 
@middle_name varchar (25), 
@last_name varchar (45), 
@photo varchar (100),
@VehiclePlate varchar(7)
as
update drivers set 
first_name = @first_name,  
middle_name = @middle_name, 
last_name = @last_name, 
photo = @photo, 
VehiclePlate = @VehiclePlate
where id_driver = @id_driver

--exec UpdateDriver 1000, 'Pepe','Marela','Varela','fotazo.png', 'A3827KH' 
--select  * from drivers


--DeleteDriver procedure------------------------------------------------------------------

create procedure DeleteDriver
@id_driver int
as
delete from drivers where id_driver =  @id_driver;

--exec DeleteDriver 1001
--select * from drivers

----------------------------------Vehicle Procedures-------------------------------------


--GetVehicles procedure------------------------------------------------------------------
create procedure GetVehicles as
select * from vehicles;
--exec GetVehicles


--vw_test view------------------------------------------------------------------
CREATE VIEW vw_test AS
(SELECT id_vehicle FROM vehicles WHERE id_vehicle=(SELECT max(id_vehicle) FROM vehicles));


--AddNewVehicle procedure------------------------------------------------------------------
create procedure AddNewVehicle 
@plate varchar(7),
@model varchar(50), 
@vyear char (4), 
@gps varchar (200), 
@color varchar (10),
@q varchar(MAX) = "(SELECT id_vehicle FROM vehicles WHERE id_vehicle=(SELECT max(id_vehicle) FROM vehicles));" 
as
insert into vehicles values (@plate, @model, @vyear, @gps, @color, null, null, null)
insert into temperatureSensor values(0)
insert into alcoholSensor values(0, 'Not available')
insert into vehicleStatus values('Not Driving', 'Off');
insert into logs values(CURRENT_TIMESTAMP, 'First log message (Test)', @plate)
update vehicles set alcohol_inf = (select * from vw_test), temperature_inf = (select * from vw_test), status_inf = (select * from vw_test)
where id_vehicle = (select * from vw_test)


--exec AddNewVehicle 'UWIAN26', 'Chevrolet Test', '2020', 'Zona Río, Blvd. Zamurano 3000', 'Red'


--UpdateVehicle procedure------------------------------------------------------------------

create procedure UpdateVehicle 
@id_vehicle int,
@plate varchar(7), 
@model varchar(50), 
@vyear char (4), 
@gps varchar (200), 
@color varchar (10)
as
update vehicles set 
plate = @plate,
model = @model, 
vyear = @vyear, 
gps = @gps, 
color = @color
where id_vehicle = @id_vehicle 

--exec UpdateVehicle 1000, 'D12023A', 'Chevrolet Aveo', '2019','Avenida Revolución 42069', 'Blue'
--update drivers set VehiclePlate = null, where VehiclePlate = @plate

--Delete Vehicle procedure------------------------------------------------------------------
create procedure DeleteVehicle
@id_vehicle int
as
update drivers set VehiclePlate = null where VehiclePlate = (select plate from vehicles where id_vehicle  = @id_vehicle);
--update logs set VehiclePlate = null where VehiclePlate = (select plate from vehicles where id_vehicle  = @id_vehicle);
update vehicles set alcohol_inf = null, temperature_inf = null, status_inf = null where id_vehicle = @id_vehicle;
delete from logs where VehiclePlate = (select plate from vehicles where id_vehicle  = @id_vehicle);
delete from temperatureSensor where id_temperature = @id_vehicle;
delete from alcoholSensor where id_alcohol = @id_vehicle;
delete from vehicleStatus where id_status = @id_vehicle;
delete from vehicles where id_vehicle =  @id_vehicle;

-- exec DeleteVehicle 1000
--select * from vehicles
--select * from drivers;
--update drivers set VehiclePlate = null where VehiclePlate = (select plate from vehicles where id_vehicle  = 1001);

--GetVehiclePlates procedure------------------------------------------------------------------
create procedure GetAllVehiclePlates
as
select plate from vehicles

--GetAlcohol procedure------------------------------------------------------------------
create procedure GetAlcohol
as
select * from alcoholSensor

create procedure GetAlcoholId
@id_alcohol int
as
select * from alcoholSensor where id_alcohol = @id_alcohol;



--UpdateAlcoholProcedure------------------------------------------------------------------
create procedure UpdateAlcohol
@id_alcohol int,
@val int,
@result varchar(100)
as
update alcoholSensor set val = @val, result = @result where id_alcohol = @id_alcohol;

--GetTemperature procedure------------------------------------------------------------------
create procedure GetTemperature
as
select * from temperatureSensor

create procedure GetTemperatureId
@id_temperature int
as
select * from temperatureSensor where id_temperature = @id_temperature;

--UpdateTemperatureProcedure------------------------------------------------------------------
create procedure UpdateTemperature
@id_temperature int,
@val float (6)
as
update temperatureSensor set val = @val where id_temperature = @id_temperature;


--GetStatus procedure------------------------------------------------------------------
create procedure GetStatus
as
select * from vehicleStatus

create procedure GetStatusId
@id_status int
as
select * from vehicleStatus where id_status = @id_status


--UpdateStatusProcedure------------------------------------------------------------------
create procedure UpdateStatus
@id_status int,
@isDriving varchar (50),
@isON varchar (10)
as
update vehicleStatus set isDriving = @isDriving, isOn = @isON  where id_status = @id_status;

--GetLogs procedure------------------------------------------------------------------
create procedure GetLogs
as
select * from logs


create procedure GetLogsId
@plate varchar (7)
as
select * from logs where VehiclePlate = @plate

--AddLogProcedure------------------------------------------------------------------
create procedure AddLog
@date_time datetime,
@content varchar (100),
@plate varchar (7)
as
insert into logs values (@date_time, @content, @plate)


--UpdateLogProcedure------------------------------------------------------------------
create procedure UpdateLogs
@id_log int,
@date_time datetime,
@content varchar (100)
as
update logs set date_time = @date_time, content = @content where id_log = @id_log;

--exec UpdateLogs 1011, '2021-07-21 22:11:17.600', 'Test'

--select * from logs
--select * from alcoholSensor
--select * from temperatureSensor
--select * from vehicleStatus

--insert into logs values (CURRENT_TIMESTAMP, 'Second log message (Test2)', '73HD6FK')
--select * from logs

--select * from vehicles;
--select * from drivers;

--select * from vehicles where id_vehicle = 100;

--exec DeleteVehicle 1004