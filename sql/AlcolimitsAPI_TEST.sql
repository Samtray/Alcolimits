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

create table sensorsMinMax(
	id varchar (5) primary key,
	[name] varchar (50),
	minimum int,
	maximum int,
	color varchar (10),
	icon varchar (MAX)
);

insert into sensorsMinMax values ('TMP1','Low', 0, 90, '#4BDD5E', 'http://localhost:59853/Photos/tmpGreen.svg')
insert into sensorsMinMax values ('TMP2','Medium', 91, 110, '#FEF84F', 'http://localhost:59853/Photos/tmpYellow.svg');
insert into sensorsMinMax values ('TMP3','High', 111, 150, '#E14B4B', 'http://localhost:59853/Photos/tmpRed.svg');
insert into sensorsMinMax values ('ALH1','No alcohol', 0, 300, '#FFFFFF', 'http://localhost:59853/Photos/alhWhite.svg')
insert into sensorsMinMax values ('ALH2','Some alcohol', 301, 500, '#FEF84F', 'http://localhost:59853/Photos/alhYellow.svg');
insert into sensorsMinMax values ('ALH3','High alcohol', 501, 900, '#E14B4B', 'http://localhost:59853/Photos/alhRed.svg');


create table vehicleMinMax(
	id varchar (5) primary key,
	isOn varchar (20),
	isDriving varchar (20),
	isOnColor varchar (10),
	isDrivingColor varchar (10),
	iconOn varchar (MAX),
	iconDriving varchar (MAX)
);

insert into vehicleMinMax values ('VHC1', 'Vehicle Off', 'Not Driving', '#E14B4B','#E14B4B', 'http://localhost:59853/Photos/offRed.svg', 'http://localhost:59853/Photos/swRed.svg');
insert into vehicleMinMax values ('VHC2', 'Vehicle Off', 'Driving', '#E14B4B','#4BDD5E', 'http://localhost:59853/Photos/offRed.svg', 'http://localhost:59853/Photos/swGreen.svg');
insert into vehicleMinMax values ('VHC3', 'Vehicle On ', 'Not Driving', '#4BDD5E','#E14B4B', 'http://localhost:59853/Photos/onGreen.svg', 'http://localhost:59853/Photos/swRed.svg');
insert into vehicleMinMax values ('VHC4', 'Vehicle On', 'Driving', '#4BDD5E','#4BDD5E', 'http://localhost:59853/Photos/onGreen.svg', 'http://localhost:59853/Photos/swGreen.svg');

select * from vehicleMinMax;

create table alcoholSensor(
	id int identity(1000,1) primary key,
	val int,
	[status] varchar (5),
	FOREIGN KEY ([status]) REFERENCES sensorsMinMax(id)
);

create table temperatureSensor(
	id int identity(1000,1) primary key,
	val float,
	[status] varchar (5),
	FOREIGN KEY ([status]) REFERENCES sensorsMinMax(id)
);

create table vehicleStatus(
	id int identity(1000,1) primary key,
	isOn bit,
	isDriving bit,
	[status] varchar (5),
	FOREIGN KEY ([status]) REFERENCES vehicleMinMax(id)
);


create table [location](
	id int primary key identity(1000,1),
	[address] varchar (MAX), 
	latitude float, 
	longitude float,
	icon varchar (MAX)
)

create table vehicles(
	id int identity(1000,1) primary key,
	plate varchar (7) unique not null,
	model varchar (50),
	[year] char (4),
	color varchar (10),
	photo varchar (MAX),
	[location] int,
	status_inf int,
	alcohol_inf int,
	temperature_inf int,
	FOREIGN KEY ([location]) REFERENCES location(id),
	FOREIGN KEY (status_inf) REFERENCES vehicleStatus(id),
	FOREIGN KEY (alcohol_inf) REFERENCES alcoholSensor(id),
	FOREIGN KEY (temperature_inf) REFERENCES temperatureSensor(id)
)


create table logs(
	id int identity(1000,1) primary key,
	[dateTime] datetime,
	content varchar (100),
	vehiclePlate varchar (7), 
	FOREIGN KEY (vehiclePlate) REFERENCES vehicles(plate)
)


create table drivers(
	id int identity(1000,1) primary key,
	firstName varchar (50) not null,
	middleName varchar (25),
	lastName varchar (45),
	profilePhoto varchar (MAX),
	licensePhoto varchar (MAX),
	vehiclePlate varchar (7), 
	FOREIGN KEY (vehiclePlate) REFERENCES vehicles(plate)
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

create procedure GetDriversId
@id int
as 
select * from drivers where id = @id;

--exec GetDrivers;

--NewDriver procedure--------------------------------------------------------------------
create procedure AddNewDriver 
@firstName varchar(50), 
@middleName varchar (25), 
@lastName varchar (45), 
@profilePhoto varchar (MAX),
@licensePhoto varchar (MAX),
@vehiclePlate varchar(7)
as
insert into drivers values (@firstName, @middleName, @lastName, @profilePhoto, @licensePhoto, @vehiclePlate);


exec AddNewDriver 'Gustavo', 'Torrecillas', 'Beltran', 'http://localhost:59853/Photos/u1.jpg', 'http://localhost:59853/Photos/placeholder.jpg',  '6X4BR87';
exec AddNewDriver 'Pepe','Marela','Varela','http://localhost:59853/Photos/u2.jpg', 'test.png', 'UWIAN22';
exec AddNewDriver 'Juan','Carlos','Barrera','http://localhost:59853/Photos/u3.jpg', 'test.png', 'UWIAN23'; 
exec AddNewDriver 'Jose','Antonio','Alfredo','http://localhost:59853/Photos/u4.jpg', 'test.png', 'UWIAN24';
exec AddNewDriver 'Ruben','Peralta','Guzmán','http://localhost:59853/Photos/u5.jpg', 'test.png', 'UWIAN25'; 
--select * from drivers

--UpdateDriver procedure------------------------------------------------------------------

create procedure UpdateDriver 
@id int, 
@firstName varchar(50), 
@middleName varchar (25), 
@lastName varchar (45), 
@profilePhoto varchar (MAX),
@licensePhoto varchar (MAX),
@vehiclePlate varchar(7)
as
update drivers set 
firstName = @firstName,  
middleName = @middleName, 
lastName = @lastName, 
profilePhoto = @profilePhoto, 
licensePhoto = @licensePhoto, 
VehiclePlate = @VehiclePlate
where id = @id

--exec UpdateDriver 1004, 'Pepe','Marela','Varela','http://localhost:59853/Photos/u2.jpg', 'test.png', 'UWIAN22'; 
--select  * from drivers


--DeleteDriver procedure------------------------------------------------------------------

create procedure DeleteDriver
@id int
as
delete from drivers where id =  @id;

--exec DeleteDriver 1002
--select * from drivers

----------------------------------Vehicle Procedures-------------------------------------


--GetVehicles procedure------------------------------------------------------------------
create procedure GetVehicles as
select * from vehicles;
--exec GetVehicles

--GetVehicles procedure------------------------------------------------------------------
create procedure GetVehiclesId 
@id int
as
select * from vehicles where id = @id;

create procedure GetVehiclesByPlate
@plate varchar (7)
as
select * from vehicles where plate = @plate;




--vw_test view------------------------------------------------------------------
CREATE VIEW vw_test AS
(SELECT id FROM vehicles WHERE id = (SELECT max(id) FROM vehicles));


--AddNewVehicle procedure------------------------------------------------------------------
create procedure AddNewVehicle 
@plate varchar(7),
@model varchar(50), 
@year char (4), 
@color varchar (10),
@photo varchar (MAX)
as
insert into vehicles values (@plate, @model, @year, @color, @photo, null, null, null, null)
insert into temperatureSensor values(0, 'TMP1');
insert into alcoholSensor values(0, 'ALH1');
insert into vehicleStatus values('false', 'false', 'VHC1');
insert into logs values(CURRENT_TIMESTAMP, 'First log message (Test)', @plate)
insert into [location] values ('Location placeholder', 0, 0, 'http://localhost:59853/Photos/location.svg')
update vehicles set  [location] = (select * from vw_test), alcohol_inf = (select * from vw_test), temperature_inf = (select * from vw_test), status_inf = (select * from vw_test)
where id = (select * from vw_test);

select * from vehicleStatus
select * from vehicleMinMax

select * from location
select * from vehicles

--exec AddNewVehicle 'UWIAN26', 'Chevrolet Vento', '2020', 'Red', 'http://localhost:59853/Photos/carRed.svg'

--SelectVehiclePate-------------------------------------------------------

create procedure SelectVehiclePlate
@plate varchar (7)
as
select * from vehicles where plate  = @plate;

--UpdateVehicle procedure------------------------------------------------------------------

create procedure UpdateVehicle 
@id int,
@plate varchar(7), 
@model varchar(50), 
@year char (4), 
@color varchar (10),
@photo varchar(MAX)
as
update vehicles set 
plate = @plate,
model = @model, 
[year] = @year,
color = @color,
photo = @photo
where id = @id

--exec UpdateVehicle 1000, 'D12023A', 'Chevrolet Aveo', '2019','Avenida Revolución 42069', 'Blue'
--update drivers set VehiclePlate = null, where VehiclePlate = @plate

--Delete Vehicle procedure------------------------------------------------------------------
create procedure DeleteVehicle
@id int
as
update drivers set vehiclePlate = null where vehiclePlate = (select plate from vehicles where id  = @id);
--update logs set VehiclePlate = null where VehiclePlate = (select plate from vehicles where id_vehicle  = @id_vehicle);
update vehicles set [location] = null, alcohol_inf = null, temperature_inf = null, status_inf = null where id = @id;
delete from logs where vehiclePlate = (select plate from vehicles where id  = @id);
delete from temperatureSensor where id = @id;
delete from alcoholSensor where id = @id;
delete from vehicleStatus where id = @id;
delete from location where id = @id;
delete from vehicles where id =  @id;

-- exec DeleteVehicle 

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
@id int
as
select * from alcoholSensor where id = @id;


--UpdateAlcoholProcedure------------------------------------------------------------------
create procedure UpdateAlcohol
@id int,
@val int
as
if @val <= (select maximum from sensorsMinMax where id = 'ALH1')
update alcoholSensor set val = @val, [status] = 'ALH1' where id = @id;
if @val >= (select minimum from sensorsMinMax where id = 'ALH2') and @val <= (select maximum from sensorsMinMax where id = 'ALH2') 
update alcoholSensor set val = @val, [status] = 'ALH2' where id = @id;
if @val >= (select minimum from sensorsMinMax where id = 'ALH3')
update alcoholSensor set val = @val, [status] = 'ALH3' where id = @id;

select * from alcoholSensor 
select * from sensorsMinMax

exec UpdateAlcohol 1005, 600
select * from alcoholSensor

--GetTemperature procedure------------------------------------------------------------------
create procedure GetTemperature
as
select * from temperatureSensor

create procedure GetTemperatureId
@id int
as
select * from temperatureSensor where @id = @id;

--UpdateTemperatureProcedure------------------------------------------------------------------
create procedure UpdateTemperature
@id int,
@val int
as
if @val <= (select maximum from sensorsMinMax where id = 'TMP1')
update temperatureSensor set val = @val, [status] = 'TMP1' where id = @id;
if @val >= (select minimum from sensorsMinMax where id = 'TMP2') and @val <= (select maximum from sensorsMinMax where id = 'TMP2') 
update temperatureSensor set val = @val, [status] = 'TMP2' where id = @id;
if @val >= (select minimum from sensorsMinMax where id = 'TMP3')
update temperatureSensor set val = @val, [status] = 'TMP3' where id = @id;


select * from sensorsMinMax
exec UpdateTemperature 1000, 110
select * from alcoholSensor


--GetStatus procedure------------------------------------------------------------------
create procedure GetStatus
as
select * from vehicleStatus

create procedure GetStatusId
@id int
as
select * from vehicleStatus where id = @id


--UpdateStatusProcedure------------------------------------------------------------------
create procedure UpdateStatus
@id int,
@isOn bit,
@isDriving bit
as
update vehicleStatus set isOn = @isOn, isDriving = @isDriving where id = @id;
if (select isOn from vehicleStatus where id = @id)  = 0 and (select isDriving from vehicleStatus where id = @id)  = 0 
update vehicleStatus set [status] = 'VHC1' where id = @id;
if (select isOn from vehicleStatus where id = @id)  = 0 and (select isDriving from vehicleStatus where id = @id)  = 1 
update vehicleStatus set [status] = 'VHC2' where id = @id;
if (select isOn from vehicleStatus where id = @id)  = 1 and (select isDriving from vehicleStatus where id = @id)  = 0 
update vehicleStatus set [status] = 'VHC3' where id = @id;
if (select isOn from vehicleStatus where id = @id)  = 1 and (select isDriving from vehicleStatus where id = @id)  = 1 
update vehicleStatus set [status] = 'VHC4' where id = @id;



select * from vehicleMinMax

exec UpdateStatus 1000, 'false', 'false' 
select * from vehicleStatus
--GetLogs procedure------------------------------------------------------------------
create procedure GetLogs
as
select * from logs

create procedure GetLogsId
@plate varchar (7)
as
select * from logs where vehiclePlate = @plate

--AddLogProcedure------------------------------------------------------------------
create procedure AddLog
@dateTime datetime,
@content varchar (100),
@plate varchar (7)
as
insert into logs values (@dateTime, @content, @plate)

exec AddLog '2021-07-28 22:20', 'Third test log', 'MJHY6TR'

select * from logs
select * from vehicles

--UpdateLogProcedure------------------------------------------------------------------
create procedure UpdateLogs
@id int,
@dateTime datetime,
@content varchar (100)
as
update logs set [dateTime] = @dateTime, content = @content where id = @id;

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

--GetLocaction----------------------------------------------------------------------------
create procedure GetLocation 
@id int
as
select * from location where id = @id;

create procedure GetAllLocations 
as
select * from location

exec GetLocation 1000

--GetAlcoholRanges-------------------------------
create procedure GetAlcoholRanges
as
select * from sensorsMinMax where id = 'ALH1' or  id = 'ALH2' or id = 'ALH3'

--GetRangesId-------------------------------
create procedure GetRangesId
@id varchar (10)
as
select * from sensorsMinMax where id = @id

--GetTemperaturesRanges-------------------------------
create procedure GetTemperatureRanges
as
select * from sensorsMinMax where id = 'TMP1' or  id = 'TMP2' or id = 'TMP3'

--GetVehiclesRanges-------------------------------
create procedure GetVehicleRanges
as
select * from vehicleMinMax

--GetVehiclesRangesId-------------------------------
create procedure GetVehicleRangesId
@id varchar (10)
as
select * from vehicleMinMax where id  =@id;

--GetLocation---------------------------------------------------

create procedure GetLocationId
@id int
as
select * from location where id = @id

create procedure UpdateLocation
@id int,
@address varchar(MAX),
@latitude float,
@longitude float
as
update location set [address] = 
@address, latitude = @latitude, 
longitude = @longitude 
where id = @id;

exec UpdateLocation 1000, [Carretera Libre Tijuana-Tecate Km 10 Fracc. El Refugio, Quintas Campestre, 22253 Redondo, B.C.], 32.460949, -116.825415

select * from drivers
select * from vehicleStatus  
select *  from vehicles
select * from temperatureSensor
select * from logs

select * from alcoholSensor

create procedure GetALH1
as
SELECT COUNT(status) as value
FROM alcoholSensor
WHERE status = 'ALH1';

create procedure GetALH2
as
SELECT COUNT(status) as value
FROM alcoholSensor
WHERE status = 'ALH2';

create procedure GetALH3
as
SELECT COUNT(status) as value
FROM alcoholSensor
WHERE status = 'ALH3';

create procedure GetVHC1
as
SELECT COUNT(status) as value
FROM vehicleStatus
where status = 'VHC1' or status = 'VHC3'

create procedure GetVHC2
as
SELECT COUNT(status) as value
FROM vehicleStatus
where status = 'VHC2' or status = 'VHC4'

exec UpdateAlcohol 1000, 0

create procedure getAllUnusedPlates
as
select plate from vehicles where plate not in (select vehiclePlate from drivers)

create procedure getAllUnassignedVehicles
as
select * from vehicles where plate not in (select vehiclePlate from drivers)



EXEC sp_MSForEachTable 'DISABLE TRIGGER ALL ON ?'
GO
EXEC sp_MSForEachTable 'ALTER TABLE ? NOCHECK CONSTRAINT ALL'
GO
EXEC sp_MSForEachTable 'DELETE FROM ?'
GO
EXEC sp_MSForEachTable 'ALTER TABLE ? CHECK CONSTRAINT ALL'
GO
EXEC sp_MSForEachTable 'ENABLE TRIGGER ALL ON ?'
GO
EXEC sp_msforeachtable 'ALTER TABLE ? NOCHECK CONSTRAINT all'
EXEC sp_MSforeachtable 'DROP TABLE ?'