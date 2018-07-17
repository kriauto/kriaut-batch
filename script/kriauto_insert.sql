DELETE FROM positions;
DELETE FROM devices;
DELETE FROM car;
DELETE FROM profile;
DELETE FROM agency;
DELETE FROM contact;


INSERT INTO agency(id,name,code,city,address,phone,fax,carnumber) 
VALUES(212340,'A2 CAR',223564000,'Tanger','79 Hay Sanawbar Branes II',2126213547895,2125213547895,10);

INSERT INTO profile(id,agencyid,login,password,name,mail,phone,job,label,token,googlekey) 
VALUES (123540,212340,'admin','admin','abdelhaq el hassnaoui','abdelhaq.elhassnaoui@gmail.com','33617638348','1','Gérant','','');

INSERT INTO devices (id,name,uniqueid) VALUES (512460,'tk103b','358688800519092');
INSERT INTO devices (id,name,uniqueid) VALUES (512461,'tk103b','358688800519094');
INSERT INTO devices (id,name,uniqueid) VALUES (512462,'tk103b','864180035844682');

INSERT INTO car(id,agencyid,imei,simnumber,immatriculation,vin,mark,model,color,photo,status,deviceid,maxspeed,mileage,fuel,colorcode,isgeofence,isnotifgeofence,isnotifdefaultgeofence,devicetype)
VALUES (321450,212340,'358688800519092','0033617638348','12354-A-56','3215468756','Renault','Clio','Noir','toto.jpeg',0,512460,120,150000,'DIE','#125436',true,false,false,'TK103');
INSERT INTO car(id,agencyid,imei,simnumber,immatriculation,vin,mark,model,color,photo,status,deviceid,maxspeed,mileage,fuel,colorcode,isgeofence,isnotifgeofence,isnotifdefaultgeofence,devicetype)
VALUES (321451,212340,'358688800519094','0033617638348','12354-B-56','3215468756','Peugeot','Citroen','Gris','toto.jpeg',0,512461,120,150000,'DIE','#125436',true,false,false,'FMA110');
INSERT INTO car(id,agencyid,imei,simnumber,immatriculation,vin,mark,model,color,photo,status,deviceid,maxspeed,mileage,fuel,colorcode,isgeofence,isnotifgeofence,isnotifdefaultgeofence,devicetype)
VALUES (321452,212340,'358688800519096','0033617638348','12354-C-56','3215468756','Ford','Fiesta','Bleu','toto.jpeg',0,512462,120,150000,'DIE','#125436',true,false,false,'FMA110');

INSERT INTO contact (id,city,name,mail,phone,photo) 
VALUES (412560,'Tanger','Fouad','fouad@gmail.com','2123654789564','photo1.jpg');
INSERT INTO contact (id,city,name,mail,phone,photo) 
VALUES (412561,'Marrakech','Fouad','fouad@gmail.com','2123654789564','photo1.jpg');
INSERT INTO contact (id,city,name,mail,phone,photo) 
VALUES (412562,'Rabat','Fouad','fouad@gmail.com','2123654789564','photo1.jpg');

insert into positions(id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (1,'gt06',512460,'2018-02-10 08:51:00.00' ,'2017-11-29 08:51:00','2017-11-29 08:51:00',true,33.972861, -6.842999,0
,1,250,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (2,'gt06',512460,'2018-02-10 08:52:00.00' ,'2017-11-29 08:52:00','2017-11-29 08:52:00',true,33.972585, -6.843181,0
,14,120,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (3,'gt06',512460,'2018-02-10 08:53:00.00' ,'2017-11-29 08:53:00','2017-11-29 08:53:00',true,33.973297, -6.844125,0
,12,250,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (4,'gt06',512460,'2018-02-10 08:54:00.00' ,'2017-11-29 08:54:00','2017-11-29 08:54:00',true,33.974311, -6.845262,0
,5,14,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (5,'gt06',512460,'2018-02-10 08:55:00.00' ,'2017-11-29 08:55:00','2017-11-29 08:55:00',true,33.974311, -6.845262,0
,15,63,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (6,'gt06',512460,'2018-02-10 08:56:00.00' ,'2017-11-29 08:56:00','2017-11-29 08:56:00',true,33.974018, -6.847532,0
,40,5000,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (7,'gt06',512460,'2018-02-10 08:57:00.00' ,'2017-11-29 08:57:00','2017-11-29 08:57:00',true,33.973110, -6.848498,0
,30,2541,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (8,'gt06',512460,'2018-02-10 08:58:00.00' ,'2017-11-29 08:58:00','2017-11-29 08:58:00',true,33.970823, -6.851127,0
,25,321,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (9,'gt06',512460,'2018-02-10 08:59:00.00' ,'2017-11-29 08:59:00','2017-11-29 08:59:00',true,33.971811, -6.852264,0
,0,4021,'','',0,'');

insert into positions(id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (10,'gt06',512461,'2018-02-10 08:01:00.00' ,'2017-11-29 08:01:00','2017-11-29 08:01:00',true,33.955374, -6.867150,0
,12,125,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (11,'gt06',512461,'2018-02-10 08:02:00.00' ,'2017-11-29 08:02:00','2017-11-29 08:02:00',true,33.954733, -6.867869,0
,12,154,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (12,'gt06',512461,'2018-02-10 08:03:00.00' ,'2017-11-29 08:03:00','2017-11-29 08:03:00',true,33.954733, -6.867869,0
,15,125,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (13,'gt06',512461,'2018-02-10 08:04:00.00' ,'2017-11-29 08:04:00','2017-11-29 08:04:00',true,33.954680, -6.869092,12
,21,452,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (14,'gt06',512461,'2018-02-10 08:05:00.00' ,'2017-11-29 08:05:00','2017-11-29 08:05:00',true,33.954965, -6.869510,78
,25,154,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (15,'gt06',512461,'2018-02-10 08:06:00.00' ,'2017-11-29 08:06:00','2017-11-29 08:06:00',true,33.955704, -6.868770,0
,12,541,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (16,'gt06',512461,'2018-02-10 08:07:00.00' ,'2017-11-29 08:07:00','2017-11-29 08:07:00',true,33.955428, -6.868330,0
,10,215,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (17,'gt06',512461,'2018-02-10 08:08:00.00' ,'2017-11-29 08:08:00','2017-11-29 08:08:00',true,33.955846, -6.867965,0
,15,452,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (18,'gt06',512461,'2018-02-10 08:09:00.00' ,'2017-11-29 08:09:00','2017-11-29 08:09:00',true,33.956700, -6.869016,20
,12,542,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (28,'gt06',512461,'2018-02-10 08:04:00.00' ,'2017-11-29 08:04:00','2017-11-29 08:04:00',true,33.954680, -6.869092,12
,21,452,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (29,'gt06',512461,'2018-02-10 08:05:00.00' ,'2017-11-29 08:05:00','2017-11-29 08:05:00',true,33.954965, -6.869510,78
,25,154,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (30,'gt06',512461,'2018-02-10 08:06:00.00' ,'2017-11-29 08:06:00','2017-11-29 08:06:00',true,33.955704, -6.868770,0
,12,541,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (31,'gt06',512461,'2018-02-10 08:07:00.00' ,'2017-11-29 08:07:00','2017-11-29 08:07:00',true,33.955428, -6.868330,0
,10,215,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (32,'gt06',512461,'2018-02-10 08:08:00.00' ,'2017-11-29 08:08:00','2017-11-29 08:08:00',true,33.955846, -6.867965,0
,15,452,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (33,'gt06',512461,'2018-02-10 08:09:00.00' ,'2017-11-29 08:09:00','2017-11-29 08:09:00',true,33.956700, -6.869016,20
,0,542,'','',0,'');

insert into positions(id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (19,'gt06',512462,'2018-02-10 08:10:00.00' ,'2017-11-29 08:10:00','2017-11-29 08:10:00',true,33.998710, -6.845135,0
,15,524,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (20,'gt06',512462,'2018-02-10 08:11:00.00' ,'2017-11-29 08:11:00','2017-11-29 08:11:00',true,33.998372, -6.846058,0
,10,5412,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (21,'gt06',512462,'2018-02-10 08:12:00.00' ,'2017-11-29 08:12:00','2017-11-29 08:12:00',true,33.997794, -6.845768,0
,14,563,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (22,'gt06',512462,'2018-02-10 08:13:00.00' ,'2017-11-29 08:13:00','2017-11-29 08:13:00',true,33.997145, -6.845425,0
,45,5236,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (23,'gt06',512462,'2018-02-10 08:14:00.00' ,'2017-11-29 08:14:00','2017-11-29 08:14:00',true,33.996923, -6.845961,0
,12,5236,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (24,'gt06',512462,'2018-02-10 08:15:00.00' ,'2017-11-29 08:15:00','2017-11-29 08:15:00',true,33.996692, -6.846615,0
,15,154,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (25,'gt06',512462,'2018-02-10 08:16:00.00' ,'2017-11-29 08:16:00','2017-11-29 08:16:00',true,33.996229, -6.847999,0
,52,2541,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (26,'gt06',512462,'2018-02-10 08:17:00.00' ,'2017-11-29 08:17:00','2017-11-29 08:17:00',true,33.994708, -6.847259,0
,12,6325,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (27,'gt06',512462,'2018-02-10 08:18:00.00' ,'2017-11-29 08:18:00','2017-11-29 08:18:00',true,33.994325, -6.8485360,0
,30,452,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (34,'gt06',512462,'2018-01-25 08:13:00.00' ,'2017-11-29 08:13:00','2017-11-29 08:13:00',true,33.997145, -6.845425,0
,45,5236,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (35,'gt06',512462,'2018-01-25 08:14:00.00' ,'2017-11-29 08:14:00','2017-11-29 08:14:00',true,33.996923, -6.845961,0
,12,5236,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (36,'gt06',512462,'2018-01-25 08:15:00.00' ,'2017-11-29 08:15:00','2017-11-29 08:15:00',true,33.996692, -6.846615,0
,15,154,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (37,'gt06',512462,'2018-01-25 08:16:00.00' ,'2017-11-29 08:16:00','2017-11-29 08:16:00',true,33.996229, -6.847999,0
,52,2541,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (38,'gt06',512462,'2018-01-25 08:17:00.00' ,'2017-11-29 08:17:00','2017-11-29 08:17:00',true,33.994708, -6.847259,0
,12,6325,'','',0,'');
insert into positions (id,protocol,deviceid,servertime,devicetime,fixtime,valid,latitude,longitude,altitude,speed,course,address,attributes,accuracy,network) 
values (39,'gt06',512462,'2018-01-25 08:18:00.00' ,'2017-11-29 08:18:00','2017-11-29 08:18:00',true,33.994325, -6.8485360,0
,30,452,'','',0,'');