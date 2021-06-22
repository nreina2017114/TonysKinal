-- Nestor Alejandro Reina Mendez 2017114 ----------

# Borrar la base de datos
drop database if exists DBTonysKinal2017114;

# Creamos la base de datos
create database DBTonysKinal2017114;

#Seleccionamos la base de datos
use DBTonysKinal2017114;

ALTER USER 'root'@'localhost' identified WITH mysql_native_password BY '1234';


####### ENTIDAD EMPRESAS #######################################################################################################
create table Empresas (
	codigoEmpresa INT AUTO_INCREMENT NOT NULL,
	nombreEmpresa VARCHAR(150) NOT NULL,
	direccion VARCHAR(150) NOT NULL,
	telefono VARCHAR(10) NOT NULL,
	primary key PK_codigoEmpresa(codigoEmpresa)
);



-- --------------------------------------------------------PROCEDIMIENTOS ALMACENADOS-------------------------------------------------------------------------



-- PROCEDIMIENTO AGREGAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_AgregarEmpresas (in nombreEmpresa VARCHAR(150), in direccion VARCHAR(150), in telefono VARCHAR(10))
	BEGIN
		insert into Empresas(nombreEmpresa, direccion, telefono)
			values (nombreEmpresa, direccion, telefono);
    END$$
DELIMITER ;

call sp_AgregarEmpresas('Pollo Campero','Zona 2 De Mixco','23456765');
call sp_AgregarEmpresas('Pizza Hut','Villa Canales','93878322');
call sp_AgregarEmpresas('Senator Video Loteria','Ciudad De Guatemala','97347812');
call sp_AgregarEmpresas('Taco Bell','Zona 5 De Mixco','45433454');
call sp_AgregarEmpresas('Burger King','San Cristobal','42342332');
call sp_AgregarEmpresas('Subway','Zona 4 De Mixco','93823467');
call sp_AgregarEmpresas('Dominos','Villa Canales','91257434');
call sp_AgregarEmpresas('Wendys','Zona 10 Ciudad De Guatemala','78934567');
call sp_AgregarEmpresas('La Media Cancha','Zona 9 Ciudad De Guatemala','29845637');
call sp_AgregarEmpresas('McDonalds','Villa Nueva','83465734');



-- PROCEDIMIENTO LISTAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ListarEmpresas()
	BEGIN
		select
			Empresas.codigoEmpresa, 
			Empresas.nombreEmpresa, 
			Empresas.direccion, 
			Empresas.telefono
			from Empresas;
    END$$
DELIMITER ;

call sp_ListarEmpresas();



-- PROCEDIMIENTO ACTUALIZAR---------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ActualizarEmpresas(in codEmp INT, in nomEmp VARCHAR(150), in direc VARCHAR(150), in tel VARCHAR(10)) 
	BEGIN
		update Empresas 
			set 
				nombreEmpresa = nomEmp, 
				direccion = direc, 
				telefono = tel
				where codigoEmpresa = codEmp;
    END$$
DELIMITER ;


-- PROCEDIMIENTO ELIMINAR-----------------------------------------------------------
-- --------------------------------------------------------------------------------- 
DELIMITER $$
create procedure sp_EliminarEmpresas(in codEmp INT)
	BEGIN
		delete from Empresas
        where codigoEmpresa = codEmp;
    END$$
DELIMITER ;  


-- PROCEDIMIENTO BUSCAR-------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_BuscarEmpresas(in codEmp INT) 
	BEGIN
		select
			Empresas.codigoEmpresa,
			Empresas.nombreEmpresa, 
			Empresas.direccion, 
			Empresas.telefono
			from Empresas
			where codigoEmpresa = codEmp;
    END$$
DELIMITER ;


####### ENTIDAD PRODUCTOS ###############################################################################################################
create table Productos(
	codigoProducto INT AUTO_INCREMENT NOT NULL,
	nombreProducto VARCHAR(150) NOT NULL,
	cantidad INT NOT NULL,
	primary key PK_codigoProducto(codigoProducto)
);



-- --------------------------------------------------------PROCEDIMIENTOS ALMACENADOS-------------------------------------------------------------------------



-- PROCEDIMIENTO AGREGAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_AgregarProductos(in nombreProducto VARCHAR(150), in cantidad INT)
	BEGIN
		insert into Productos(nombreProducto, cantidad)
			values (nombreProducto, cantidad);
    END$$
DELIMITER ;

call sp_AgregarProductos('Pizza',3);
call sp_AgregarProductos('Hamburguesa',5);
call sp_AgregarProductos('Lasaña',2);
call sp_AgregarProductos('Churrasco',4);
call sp_AgregarProductos('Ceviche',7);
call sp_AgregarProductos('Pollo',3);
call sp_AgregarProductos('Lomito',1);
call sp_AgregarProductos('Carne Asada',8);
call sp_AgregarProductos('Caldo De Pollo',4);
call sp_AgregarProductos('Sopa De Frijoles',6);


-- PROCEDIMIENTO LISTAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ListarProductos()
	BEGIN
		select
		Productos.codigoProducto, 
        Productos.nombreProducto, 
        Productos.cantidad
		from Productos;
    END$$
DELIMITER ;

call sp_ListarProductos();


-- PROCEDIMIENTO ACTUALIZAR---------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ActualizarProductos(in codProd INT, in nombProd VARCHAR(150), in cant INT) 
	BEGIN
		update Productos 
			set 
				nombreProducto = nombProd, 
				cantidad = cant
				where codigoProducto = codProd;
    END$$
DELIMITER ;


-- PROCEDIMIENTO ELIMINAR-----------------------------------------------------------
-- --------------------------------------------------------------------------------- 
DELIMITER $$
create procedure sp_EliminarProductos(in codProd INT)
	BEGIN
		delete from Productos
        where codigoProducto = codProd;
    END$$
DELIMITER ;  


-- PROCEDIMIENTO BUSCAR-------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_BuscarProductos(in codProd INT) 
	BEGIN
		select
			Productos.codigoProducto,
			Productos.nombreProducto, 
			Productos.cantidad
			from Productos
			where codigoProducto = codProd;
    END$$
DELIMITER ;


####### ENTIDAD TIPOPLATO #################################################################################################################
create table TipoPlato(
	codigoTipoPlato INT AUTO_INCREMENT NOT NULL,
	descripcionTipo VARCHAR(100) NOT NULL,
	primary key PK_codigoTipoPlato(codigoTipoPlato)
);



-- --------------------------------------------------------PROCEDIMIENTOS ALMACENADOS-------------------------------------------------------------------------



-- PROCEDIMIENTO AGREGAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_AgregarTipoPlato(in descripcionTipo VARCHAR(100))
	BEGIN
		insert into TipoPlato(descripcionTipo)
			values (descripcionTipo);
    END$$
DELIMITER ;

call sp_AgregarTipoPlato('Pizza con Hongos');
call sp_AgregarTipoPlato('Hamburguesa a la ranchera');
call sp_AgregarTipoPlato('Lasaña a la Boloñesa');
call sp_AgregarTipoPlato('Churrasco a la parrilla');
call sp_AgregarTipoPlato('Ceviche de Pollo');
call sp_AgregarTipoPlato('Pollo con Salsa');
call sp_AgregarTipoPlato('Hamburguesa Ranchera');
call sp_AgregarTipoPlato('Frijoles Negros');
call sp_AgregarTipoPlato('Carne Asada con Ensalada');
call sp_AgregarTipoPlato('Caldo de Pollo con Verduras');


-- PROCEDIMIENTO LISTAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ListarTipoPlato()
	BEGIN
		select
			TipoPlato.codigoTipoPlato, 
			TipoPlato.descripcionTipo
			from TipoPlato;
    END$$
DELIMITER ;

call sp_ListarTipoPlato();



-- PROCEDIMIENTO ACTUALIZAR---------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ActualizarTipoPlato(in codTipoPlato INT, in descTipo VARCHAR(100)) 
	BEGIN
		update TipoPlato 
			set 
				descripcionTipo = descTipo
				where codigoTipoPlato = codTipoPlato;
    END$$
DELIMITER ;


-- PROCEDIMIENTO ELIMINAR-----------------------------------------------------------
-- --------------------------------------------------------------------------------- 
DELIMITER $$
create procedure sp_EliminarTipoPlato(in codTipoPlato INT)
	BEGIN
		delete from TipoPlato
        where codigoTipoPlato = codTipoPlato;
    END$$
DELIMITER ;  


-- PROCEDIMIENTO BUSCAR-------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_BuscarTipoPlato(in codTipoPlato INT) 
	BEGIN
		select
			TipoPlato.codigoTipoPlato,
			TipoPlato.descripcionTipo
			from TipoPlato
			where codigoTipoPlato = codTipoPlato;
    END$$
DELIMITER ;


####### ENTIDAD TIPOEMPLEADO ##################################################################################################################
create table TipoEmpleado(
	codigoTipoEmpleado INT AUTO_INCREMENT NOT NULL,
	descripcion VARCHAR(100) NOT NULL,
	primary key PK_codigoTipoEmpleado(codigoTipoEmpleado)
);


-- --------------------------------------------------------PROCEDIMIENTOS ALMACENADOS-------------------------------------------------------------------------



-- PROCEDIMIENTO AGREGAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_AgregarTipoEmpleado(in descripcion VARCHAR(100))
	BEGIN
		insert into TipoEmpleado(descripcion)
			values (descripcion);
    END$$
DELIMITER ;

call sp_AgregarTipoEmpleado('Mesero');
call sp_AgregarTipoEmpleado('Cocinero');
call sp_AgregarTipoEmpleado('Administrador');
call sp_AgregarTipoEmpleado('Maitre');
call sp_AgregarTipoEmpleado('Hostess');
call sp_AgregarTipoEmpleado('Recepcionista');
call sp_AgregarTipoEmpleado('Chef');
call sp_AgregarTipoEmpleado('Ayudante de camarero');
call sp_AgregarTipoEmpleado('Gourmet ');
call sp_AgregarTipoEmpleado('Catador');


-- PROCEDIMIENTO LISTAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ListarTipoEmpleado()
	BEGIN
		select
			TipoEmpleado.codigoTipoEmpleado, 
			TipoEmpleado.descripcion
			from TipoEmpleado;
    END$$
DELIMITER ;

call sp_ListarTipoEmpleado();



-- PROCEDIMIENTO ACTUALIZAR---------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ActualizarTipoEmpleado(in codTipoEmpl INT, in descr VARCHAR(100)) 
	BEGIN
		update TipoEmpleado 
			set 
				descripcion = descr
				where codigoTipoEmpleado = codTipoEmpl;
    END$$
DELIMITER ;


-- PROCEDIMIENTO ELIMINAR-----------------------------------------------------------
-- --------------------------------------------------------------------------------- 
DELIMITER $$
create procedure sp_EliminarTipoEmpleado(in codTipoEmpl INT)
	BEGIN
		delete from TipoEmpleado
        where codigoTipoEmpleado = codTipoEmpl;
    END$$
DELIMITER ;  


-- PROCEDIMIENTO BUSCAR-------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_BuscarTipoEmpleado(in codTipoEmpl INT) 
	BEGIN
		select
			TipoEmpleado.codigoTipoEmpleado,
			TipoEmpleado.descripcion
			from TipoEmpleado
			where codigoTipoEmpleado = codTipoEmpl;
    END$$
DELIMITER ;



####### ENTIDAD PRESUPUESTO ###################################################################################################################
create table Presupuesto(
	codigoPresupuesto INT AUTO_INCREMENT NOT NULL,
	fechaSolicitud DATE NOT NULL,
	cantidadPresupuesto DECIMAL(10,2) NOT NULL,
	codigoEmpresa INT NOT NULL,
	primary key PK_codigoPresupuesto(codigoPresupuesto),
	constraint FK_Presupuesto_Empresas foreign key (codigoEmpresa) references Empresas (codigoEmpresa) on delete cascade
);



-- --------------------------------------------------------PROCEDIMIENTOS ALMACENADOS-------------------------------------------------------------------------



-- PROCEDIMIENTO AGREGAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_AgregarPresupuesto(in fechaSolicitud DATE, in cantidadPresupuesto DECIMAL(10,2), in codigoEmpresa INT)
	BEGIN
		insert into Presupuesto(fechaSolicitud, cantidadPresupuesto, codigoEmpresa)
			values (fechaSolicitud, cantidadPresupuesto, codigoEmpresa);
    END$$
DELIMITER ;

call sp_AgregarPresupuesto('2020-01-01',400, 1);
call sp_AgregarPresupuesto('2019-11-10',800, 2);
call sp_AgregarPresupuesto('2020-06-12',350.50, 3);
call sp_AgregarPresupuesto('2020-08-19',650.60, 4);
call sp_AgregarPresupuesto('2018-12-29',450.50, 5);
call sp_AgregarPresupuesto('2019-02-13',675.55, 6);
call sp_AgregarPresupuesto('2018-10-17',700, 7);
call sp_AgregarPresupuesto('2019-11-15',355.55, 8);
call sp_AgregarPresupuesto('2020-04-23',900, 9);
call sp_AgregarPresupuesto('2020-12-26',825.90, 10);


-- PROCEDIMIENTO LISTAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ListarPresupuesto()
	BEGIN
		select
			Presupuesto.codigoPresupuesto, 
			Presupuesto.fechaSolicitud,
			Presupuesto.cantidadPresupuesto,
			Presupuesto.codigoEmpresa
			from Presupuesto;
    END$$
DELIMITER ;

call sp_ListarPresupuesto();

-- PROCEDIMIENTO ACTUALIZAR---------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ActualizarPresupuesto(in codPresu INT, in fechaSoli DATE, in cantPresu DECIMAL(10,2)) 
	BEGIN
		update Presupuesto 
			set 
				fechaSolicitud = fechaSoli,
				cantidadPresupuesto = cantPresu
				where codigoPresupuesto = codPresu;
    END$$
DELIMITER ;


-- PROCEDIMIENTO ELIMINAR-----------------------------------------------------------
-- --------------------------------------------------------------------------------- 
DELIMITER $$
create procedure sp_EliminarPresupuesto(in codPresu INT)
	BEGIN
		delete from Presupuesto
        where codigoPresupuesto = codPresu;
    END$$
DELIMITER ;  


-- PROCEDIMIENTO BUSCAR-------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_BuscarPresupuesto(in codPresu INT) 
	BEGIN
		select
			Presupuesto.codigoPresupuesto,
			Presupuesto.fechaSolicitud,
			Presupuesto.cantidadPresupuesto,
			Presupuesto.codigoEmpresa
			from Presupuesto
			where codigoPresupuesto = codPresu;
    END$$
DELIMITER ;


####### ENTIDAD SERVICIOS ##################################################################################################################
create table Servicios(
	codigoServicio INT AUTO_INCREMENT NOT NULL,
	fechaServicio DATE NOT NULL,
	tipoServicio VARCHAR(100) NOT NULL,
	horaServicio TIME NOT NULL,
	lugarServicio VARCHAR(100) NOT NULL,
	telefonoContacto VARCHAR(10) NOT NULL,
	codigoEmpresa INT NOT NULL,
	primary key PK_codigoServicio(codigoServicio),
	constraint FK_Servicios_Empresas foreign key (codigoEmpresa) references Empresas (codigoEmpresa) on delete cascade
);



-- --------------------------------------------------------PROCEDIMIENTOS ALMACENADOS-------------------------------------------------------------------------



-- PROCEDIMIENTO AGREGAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_AgregarServicios(in fechaServicio DATE, in tipoServicio VARCHAR(100), in horaServicio TIME, 
									in lugarServicio VARCHAR(100), in telefonoContacto VARCHAR(10), in codigoEmpresa INT)
	BEGIN
		insert into Servicios(fechaServicio, tipoServicio, horaServicio, lugarServicio, telefonoContacto, codigoEmpresa)
			values (fechaServicio, tipoServicio, horaServicio, lugarServicio, telefonoContacto, codigoEmpresa);
    END$$
DELIMITER ;

call sp_AgregarServicios('20-01-01','Servicio Americano', now(),'Zona 4 De Mixco','23456789',1);
call sp_AgregarServicios('19-11-27','Servicio Francés', '7:00:00','Zona 3 De Villa Nueva','19563478',2);
call sp_AgregarServicios('20-08-28','Servicio a la Rusa', '12:00:00','Zona 6 Villa Canales','98342367',3);
call sp_AgregarServicios('20-09-17','Servicio Inglesa', '8:00:50','Zona 15 De Ciudad De Guatemala','98562345',4);
call sp_AgregarServicios('18-10-22','Servicio Gueridón', '8:30:50','Zona 12 De Ciudad De Guatemala','23492356',5);
call sp_AgregarServicios('20-04-19','Servicio Buffet', '1:00:00','Zona 4 De Mixco','91249834',2);
call sp_AgregarServicios('19-12-22','Servicio Buffet', '3:49:00','Colinas 2 Linda vista','98632745',3);
call sp_AgregarServicios('20-03-21','Servicio Francés', '2:10:00','Ciudad Quetzal','39269657',5);
call sp_AgregarServicios('20-02-12','Servicio Americano', '9:25:00','Colonia El Naranjo','49423486',2);
call sp_AgregarServicios('20-05-16','Servicio Buffet', '6:30:00','Zona 5 De Mixco','87357294',1);


-- PROCEDIMIENTO LISTAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ListarServicios()
	BEGIN
		select
			Servicios.codigoServicio,
			Servicios.fechaServicio,
			Servicios.tipoServicio,
			Servicios.horaServicio,
			Servicios.lugarServicio,
			Servicios.telefonoContacto,
			Servicios.codigoEmpresa
			from Servicios;
    END$$
DELIMITER ;

call sp_ListarServicios();


-- PROCEDIMIENTO ACTUALIZAR---------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ActualizarServicios(in codServi INT, in fechaServi DATE, in tipoServi VARCHAR(100), in horaServi TIME, 
									    in lugarServi VARCHAR(100), in telContac VARCHAR(10))
	BEGIN
		update Servicios 
			set 
				fechaServicio = fechaServi,
				tipoServicio = tipoServi,
				horaServicio = horaServi,
				lugarServicio = lugarServi,
				telefonoContacto = telContac
				where codigoServicio = codServi;
    END$$
DELIMITER ;


-- PROCEDIMIENTO ELIMINAR-----------------------------------------------------------
-- --------------------------------------------------------------------------------- 
DELIMITER $$
create procedure sp_EliminarServicios(in codServi INT)
	BEGIN
		delete from Servicios
        where codigoServicio = codServi;
    END$$
DELIMITER ;  


-- PROCEDIMIENTO BUSCAR-------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_BuscarServicios(in codServi INT) 
	BEGIN
		select
			Servicios.codigoServicio,
			Servicios.fechaServicio,
			Servicios.tipoServicio,
			Servicios.horaServicio,
			Servicios.lugarServicio,
			Servicios.telefonoContacto,
			Servicios.codigoEmpresa
			from Servicios
			where codigoServicio = codServi;
    END$$
DELIMITER ;



####### ENTIDAD PLATOS ########################################################################################################################
create table Platos(
	codigoPlato INT AUTO_INCREMENT NOT NULL,
	cantidad INT NOT NULL,
	nombrePlato VARCHAR(50) NOT NULL,
	descripcionPlato VARCHAR(150) NOT NULL,
	precioPlato DECIMAL(10,2) NOT NULL,
	codigoTipoPlato INT NOT NULL,
	primary key PK_codigoPlato(codigoPlato),
	constraint FK_Platos_TipoPlato foreign key (codigoTipoPlato) references TipoPlato (codigoTipoPlato) on delete cascade
);



-- --------------------------------------------------------PROCEDIMIENTOS ALMACENADOS-------------------------------------------------------------------------



-- PROCEDIMIENTO AGREGAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_AgregarPlatos(in cantidad INT, in nombrePlato VARCHAR(50), in descripcionPlato VARCHAR(150), 
								  in precioPlato DECIMAL(10,2), in codigoTipoPlato INT)
	BEGIN
		insert into Platos(cantidad, nombrePlato, descripcionPlato, precioPlato, codigoTipoPlato)
			values (cantidad, nombrePlato, descripcionPlato, precioPlato, codigoTipoPlato);
    END$$
DELIMITER ;

call sp_AgregarPlatos(4,'Pizza','Grande',45.4,1);
call sp_AgregarPlatos(3,'Hamburguesa','Torito',22,5);
call sp_AgregarPlatos(7,'Lasaña','Lasaña de pollo y Hongos',55.90,4);
call sp_AgregarPlatos(4,'Lomito','1 Libra',55,9);
call sp_AgregarPlatos(10,'Caldo De Pollo','Con Verduras',40,8);
call sp_AgregarPlatos(7,'Pizza','Mediana',55.50,3);
call sp_AgregarPlatos(9,'Caldo De Frijoles','Frijoles Rancheros',35,7);
call sp_AgregarPlatos(15,'Carne Asada','Con Ensalada',75,3);
call sp_AgregarPlatos(12,'Hamburguesa','Ranchera',35,6);
call sp_AgregarPlatos(11,'Pizza','Extra Grande',45.4,2);



-- PROCEDIMIENTO LISTAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ListarPlatos()
	BEGIN
		select
			Platos.codigoPlato,
			Platos.cantidad,
			Platos.nombrePlato,
			Platos.descripcionPlato,
			Platos.precioPlato,
			Platos.codigoTipoPlato
			from Platos;
    END$$
DELIMITER ;

call sp_ListarPlatos();



-- PROCEDIMIENTO ACTUALIZAR---------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ActualizarPlatos(in codPlato INT, in cant INT, in nombPlato VARCHAR(50), in descrPlato VARCHAR(150), 
								      in precPlato DECIMAL(10,2))
	BEGIN
		update Platos 
			set 
				cantidad = cant,
				nombrePlato = nombPlato,
				descripcionPlato = descrPlato,
				precioPlato = precPlato
				where codigoPlato = codPlato;
    END$$
DELIMITER ;


-- PROCEDIMIENTO ELIMINAR-----------------------------------------------------------
-- --------------------------------------------------------------------------------- 
DELIMITER $$
create procedure sp_EliminarPlatos(in codPlato INT)
	BEGIN
		delete from Platos
        where codigoPlato = codPlato;
    END$$
DELIMITER ;  


-- PROCEDIMIENTO BUSCAR-------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_BuscarPlatos(in codPlato INT) 
	BEGIN
		select
			Platos.codigoPlato,
			Platos.cantidad,
			Platos.nombrePlato,
			Platos.descripcionPlato,
			Platos.precioPlato,
			Platos.codigoTipoPlato
			from Platos
			where codigoPlato = codPlato;
    END$$
DELIMITER ;



####### ENTIDAD EMPLEADOS ##############################################################################################################
create table Empleados(
	codigoEmpleado INT AUTO_INCREMENT NOT NULL,
	numeroEmpleado INT NOT NULL,
	apellidoEmpleado VARCHAR(150) NOT NULL,
	nombreEmpleado VARCHAR(150) NOT NULL,
	direccionEmpleado VARCHAR(150) NOT NULL,
	telefonoContacto VARCHAR(10) NOT NULL,
	gradoCocinero VARCHAR(50),
	codigoTipoEmpleado INT NOT NULL,
	primary key PK_codigoEmpleado(codigoEmpleado),
	constraint FK_Empleados_TipoEmpleado foreign key (codigoTipoEmpleado) references TipoEmpleado (codigoTipoEmpleado) on delete cascade
);



-- --------------------------------------------------------PROCEDIMIENTOS ALMACENADOS-------------------------------------------------------------------------



-- PROCEDIMIENTO AGREGAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_AgregarEmpleados(in numeroEmpleado INT, in apellidoEmpleado VARCHAR(150), in nombreEmpleado VARCHAR(150), 
			in direccionEmpleado VARCHAR(150), in telefonoContacto VARCHAR(10), in gradoCocinero VARCHAR(50), in codigoTipoEmpleado INT)
									
	BEGIN
		insert into Empleados(numeroEmpleado, apellidoEmpleado, nombreEmpleado, direccionEmpleado, telefonoContacto, gradoCocinero, codigoTipoEmpleado)
			values (numeroEmpleado, apellidoEmpleado, nombreEmpleado, direccionEmpleado, telefonoContacto, gradoCocinero, codigoTipoEmpleado);
    END$$
DELIMITER ;


call sp_AgregarEmpleados(22,'Reina','Alejandro','Encinos De Cayalá','45456789','Chef',1);
call sp_AgregarEmpleados(12,'Hernandez','Jesus','Zona 5 Ciudad De guatemala','98456754','Cocinero',2);
call sp_AgregarEmpleados(5,'Palencia','Daniela','Zona 4 De Mixco','12983456','SubChef',3);
call sp_AgregarEmpleados(25,'Limatu','Angeles','Zona 5 De Mixco','34893423','Stewar',4);
call sp_AgregarEmpleados(8,'Gil','Melany','Zona 8 De Villa Nueva','72983467','Chef',5);
call sp_AgregarEmpleados(10,'Sanchez','William','Zona 15 De Ciudad De Guatemala','92742389','Stewar',6);
call sp_AgregarEmpleados(15,'Mendez','Victor',' Colonia Ciudad de Plata','49029845','Chef',7);
call sp_AgregarEmpleados(5,'Ramirez','Cristian','Villa Canales','70235634','SubChef',8);
call sp_AgregarEmpleados(14,'Muller','Alejandra','Antigua Guatemala','20508734','Cocinero',9);
call sp_AgregarEmpleados(18,'Reina','Teresa','Carretera Al Salvador','87067434','Chef',10);



-- PROCEDIMIENTO LISTAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ListarEmpleados()
	BEGIN
		select
			Empleados.codigoEmpleado,
			Empleados.numeroEmpleado,
			Empleados.apellidoEmpleado,
			Empleados.nombreEmpleado,
			Empleados.direccionEmpleado,
			Empleados.telefonoContacto,
			Empleados.gradoCocinero,
			Empleados.codigoTipoEmpleado
			from Empleados;
    END$$
DELIMITER ;

call sp_ListarEmpleados();

-- PROCEDIMIENTO ACTUALIZAR---------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ActualizarEmpleados(in codEmpl INT, in numeEmp INT, in apellEmp VARCHAR(150), in nombEmp VARCHAR(150), 
						                in direcEmpl VARCHAR(150), in telContac VARCHAR(10), in gradoCoci VARCHAR(50))
	BEGIN
		update Empleados 
			set 
				numeroEmpleado = numeEmp,
				apellidoEmpleado = apellEmp,
				nombreEmpleado = nombEmp,
				direccionEmpleado = direcEmpl,
				telefonoContacto = telContac,
				gradoCocinero = gradoCoci
				where codigoEmpleado = codEmpl;
    END$$
DELIMITER ;


-- PROCEDIMIENTO ELIMINAR-----------------------------------------------------------
-- --------------------------------------------------------------------------------- 
DELIMITER $$
create procedure sp_EliminarEmpleados(in codEmpl INT)
	BEGIN
		delete from Empleados
        where codigoEmpleado = codEmpl;
    END$$
DELIMITER ;  


-- PROCEDIMIENTO BUSCAR-------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_BuscarEmpleados(in codEmpl INT) 
	BEGIN
		select
			Empleados.codigoEmpleado,
			Empleados.numeroEmpleado,
			Empleados.apellidoEmpleado,
			Empleados.nombreEmpleado,
			Empleados.direccionEmpleado,
			Empleados.telefonoContacto,
			Empleados.gradoCocinero,
			Empleados.codigoTipoEmpleado
			from Empleados
			where codigoEmpleado = codEmpl;
    END$$
DELIMITER ;



####### ENTIDAD SERVICIOS_HAS_PLATOS #########################################################################################################
create table Servicios_has_Platos(
	Servicios_codigoServicio INT AUTO_INCREMENT NOT NULL,
    codigoPlato INT NOT NULL,
    codigoServicio INT NOT NULL,
	primary key PK_Servicios_codigoServicio(Servicios_codigoServicio),
	constraint FK_Servicios_has_Platos_Servicios1 foreign key (codigoServicio) references Servicios (codigoServicio) on delete cascade,
	constraint FK_Servicios_has_Platos_Platos1 foreign key (codigoPlato) references Platos (codigoPlato) on delete cascade
);



-- --------------------------------------------------------PROCEDIMIENTOS ALMACENADOS-------------------------------------------------------------------------



-- PROCEDIMIENTO AGREGAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_AgregarServicios_has_Platos(in codigoPlato INT, in codigoServicio INT)
	BEGIN
		insert into Servicios_has_Platos(codigoPlato, codigoServicio)
			values (codigoPlato, codigoServicio);
    END$$
DELIMITER ;

call sp_AgregarServicios_has_Platos(1,1);
call sp_AgregarServicios_has_Platos(2,2);
call sp_AgregarServicios_has_Platos(3,3);
call sp_AgregarServicios_has_Platos(4,4);
call sp_AgregarServicios_has_Platos(5,5);
call sp_AgregarServicios_has_Platos(6,10);
call sp_AgregarServicios_has_Platos(7,9);
call sp_AgregarServicios_has_Platos(8,8);
call sp_AgregarServicios_has_Platos(9,7);
call sp_AgregarServicios_has_Platos(10,6);



-- PROCEDIMIENTO LISTAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ListarServicios_has_Platos()
	BEGIN
		select
			Servicios_has_Platos.Servicios_codigoServicio,
			Servicios_has_Platos.codigoServicio, 
			Servicios_has_Platos.codigoPlato
			from Servicios_has_Platos;
    END$$
DELIMITER ;

call sp_ListarServicios_has_Platos();

-- PROCEDIMIENTO ACTUALIZAR---------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ActualizarServicios_has_Platos(in Servi_codServi INT, in codPlat INT, in codServi INT) 
	BEGIN
		update Servicios_has_Platos 
			set 
				codigoPlato = codPlat,
                codigoServicio = codServi
				where Servicios_codigoServicio = Servi_codServi;
    END$$
DELIMITER ;


-- PROCEDIMIENTO ELIMINAR-----------------------------------------------------------
-- --------------------------------------------------------------------------------- 
DELIMITER $$
create procedure sp_EliminarServicios_has_Platos(in Servi_codServi INT)
	BEGIN
		delete from Servicios_has_Platos
		where Servicios_codigoServicio = Servi_codServi;
    END$$
DELIMITER ;  


-- PROCEDIMIENTO BUSCAR-------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_BuscarServicios_has_Platos(in Servi_codServi INT) 
	BEGIN
		select
			Servicios_has_Platos.Servicios_codigoServicio,
			Servicios_has_Platos.codigoPlato,
            Servicios_has_Platos.codigoServicio
			from Servicios_has_Platos
		    where Servicios_codigoServicio = Servi_codServi;
    END$$
DELIMITER ;



####### ENTIDAD SERVICIOS_HAS_EMPLEADOS ###################################################################################################
create table Servicios_has_Empleados(
	Servicios_codigoServicio INT AUTO_INCREMENT NOT NULL,
    codigoServicio INT NOT NULL,
    codigoEmpleado INT NOT NULL,	
	fechaEvento DATE NOT NULL,
	horaEvento TIME NOT NULL,
	lugarEvento VARCHAR(150) NOT NULL,
	primary key PK_Servicios_codigoServicio(Servicios_codigoServicio),
	constraint FK_Servicios_has_Empleados_Servicios1 foreign key (codigoServicio) references Servicios (codigoServicio) on delete cascade,
	constraint FK_Servicios_has_Empleados_Empleados1 foreign key (codigoEmpleado) references Empleados (codigoEmpleado) on delete cascade
);


-- --------------------------------------------------------PROCEDIMIENTOS ALMACENADOS-------------------------------------------------------------------------



-- PROCEDIMIENTO AGREGAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_AgregarServicios_has_Empleados(in codigoServicio INT,in codigoEmpleado INT, in fechaEvento DATE,
												   in horaEvento TIME, in lugarEvento VARCHAR(150))
	BEGIN
		insert into Servicios_has_Empleados(codigoServicio, codigoEmpleado, fechaEvento, horaEvento, lugarEvento)
			values (codigoServicio, codigoEmpleado, fechaEvento, horaEvento, lugarEvento);
    END$$
DELIMITER ;

call sp_AgregarServicios_has_Empleados(1,10,'2020-06-12',now(),'Zona 4 De Mixco');
call sp_AgregarServicios_has_Empleados(2,9,'20-05-16','6:30:00','Zona 3 De Villa Nueva');
call sp_AgregarServicios_has_Empleados(3,8,'20-02-12','9:25:00','Zona 6 Villa Canales');
call sp_AgregarServicios_has_Empleados(4,7,'20-03-21','2:10:00','Zona 15 De Ciudad De Guatemala');
call sp_AgregarServicios_has_Empleados(5,6,'19-12-22','3:49:00','Zona 12 De Ciudad De Guatemala');
call sp_AgregarServicios_has_Empleados(6,5,'20-04-19','1:00:00','Zona 4 De Mixco');
call sp_AgregarServicios_has_Empleados(7,4,'18-10-22','8:30:50','Colinas 2 Linda vista');
call sp_AgregarServicios_has_Empleados(8,3,'20-09-17','8:00:50','Ciudad Quetzal');
call sp_AgregarServicios_has_Empleados(9,2,'20-08-28','12:00:00','Colonia El Naranjo');
call sp_AgregarServicios_has_Empleados(10,1,'19-11-27','7:00:00','Zona 5 De Mixco');



-- PROCEDIMIENTO LISTAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ListarServicios_has_Empleados()
	BEGIN
		select
			Servicios_has_Empleados.Servicios_codigoServicio, 
			Servicios_has_Empleados.codigoServicio,
			Servicios_has_Empleados.codigoEmpleado,
			Servicios_has_Empleados.fechaEvento,
			Servicios_has_Empleados.horaEvento,
			Servicios_has_Empleados.lugarEvento
			from Servicios_has_Empleados;
    END$$
DELIMITER ;

call sp_ListarServicios_has_Empleados();

-- PROCEDIMIENTO ACTUALIZAR---------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ActualizarServicios_has_Empleados(in Servi_codServi INT, in fechEven DATE,
												      in horaEven TIME, in lugarEven VARCHAR(150))
	BEGIN
		update Servicios_has_Empleados 
			set
				fechaEvento = fechEven,
				horaEvento = horaEven,
				lugarEvento = lugarEven
				where Servicios_codigoServicio = Servi_codServi;
	END$$
DELIMITER ;


-- PROCEDIMIENTO ELIMINAR-----------------------------------------------------------
-- --------------------------------------------------------------------------------- 
DELIMITER $$
create procedure sp_EliminarServicios_has_Empleados(in Servi_codServi INT)
	BEGIN
		delete from Servicios_has_Empleados
        where Servicios_codigoServicio = Servi_codServi;
    END$$
DELIMITER ;  


-- PROCEDIMIENTO BUSCAR-------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_BuscarServicios_has_Empleados(in Servi_codServi INT) 
	BEGIN
		select
			Servicios_has_Empleados.Servicios_codigoServicio,
			Servicios_has_Empleados.codigoServicio,
            Servicios_has_Empleados.codigoEmpleado,
			Servicios_has_Empleados.fechaEvento,
			Servicios_has_Empleados.horaEvento,
			Servicios_has_Empleados.lugarEvento
			from Servicios_has_Empleados
			where Servicios_codigoServicio = Servi_codServi;
    END$$
DELIMITER ;



####### ENTIDAD PRODUCTOS_HAS_PLATOS ########################################################################################################
create table Productos_has_Platos(
	Productos_codigoProducto INT AUTO_INCREMENT NOT NULL,
    codigoPlato INT NOT NULL,
    codigoProducto INT NOT NULL,
	primary key PK_Productos_codigoProducto(Productos_codigoProducto),
	constraint FK_Productos_has_Platos_Platos foreign key (codigoPlato) references Platos (codigoPlato) on delete cascade,
	constraint FK_Productos_has_Platos_Productos foreign key (codigoProducto) references Productos (codigoProducto) on delete cascade
);


-- --------------------------------------------------------PROCEDIMIENTOS ALMACENADOS-------------------------------------------------------------------------



-- PROCEDIMIENTO AGREGAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_AgregarProductos_has_Platos(in codigoPlato INT, in codigoProducto INT)
	BEGIN
		insert into Productos_has_Platos(codigoPlato, codigoProducto)
			values (codigoPlato, codigoProducto);
    END$$
DELIMITER ;

call sp_AgregarProductos_has_Platos(1,1);
call sp_AgregarProductos_has_Platos(2,2);
call sp_AgregarProductos_has_Platos(3,3);
call sp_AgregarProductos_has_Platos(4,4);
call sp_AgregarProductos_has_Platos(5,5);
call sp_AgregarProductos_has_Platos(6,10);
call sp_AgregarProductos_has_Platos(7,9);
call sp_AgregarProductos_has_Platos(8,8);
call sp_AgregarProductos_has_Platos(9,7);
call sp_AgregarProductos_has_Platos(10,6);



-- PROCEDIMIENTO LISTAR------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ListarProductos_has_Platos()
	BEGIN
		select
			Productos_has_Platos.Productos_codigoProducto, 
			Productos_has_Platos.codigoPlato,
            Productos_has_Platos.codigoProducto
			from Productos_has_Platos;
    END$$
DELIMITER ;

call sp_ListarProductos_has_Platos();

-- PROCEDIMIENTO ACTUALIZAR---------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_ActualizarProductos_has_Platos(in Produ_codProdu INT, in codPlat INT, in codProdu INT) 
	BEGIN
		update Productos_has_Platos 
			set 
				codigoPlato = codPlat,
                codigoProducto = codProdu
				where Productos_codigoProducto = Produ_codProdu;
    END$$
DELIMITER ;


-- PROCEDIMIENTO ELIMINAR-----------------------------------------------------------
-- --------------------------------------------------------------------------------- 
DELIMITER $$
create procedure sp_EliminarProductos_has_Platos(in Produ_codProdu INT)
	BEGIN
		delete from Productos_has_Platos
			where Productos_codigoProducto = Produ_codProdu;
    END$$
DELIMITER ;  


-- PROCEDIMIENTO BUSCAR-------------------------------------------------------------
-- ---------------------------------------------------------------------------------   
DELIMITER $$
create procedure sp_BuscarProductos_has_Platos(in Produ_codProdu INT) 
	BEGIN
		select
			Productos_has_Platos.Productos_codigoProducto, 
			Productos_has_Platos.codigoPlato,
            Productos_has_Platos.codigoProducto
			from Productos_has_Platos
			where Productos_codigoProducto = Produ_codProdu;
    END$$
DELIMITER ;



select * from Empresas E inner join Servicios S on
	E.codigoEmpresa = S.codigoEmpresa where E.codigoEmpresa = 1;
    
    
    
-- ----------------------------------- INNER JOIN PARA REPORTES -----------------------------------------------------------------
-- -----------------------                                                   ----------------------------------------------------
-- ------------------------------------------------------------------------------------------------------------------------------


################### INNER JOIN PARA PRESUPUESTOS ##############################################

drop procedure if exists sp_ListarReporte;
DELIMITER $$
create procedure sp_ListarReporte(in codEmp int)
	BEGIN
		select * from Empresas E inner join Presupuesto P on
			E.codigoEmpresa = P.codigoEmpresa
			inner join Servicios S on
				P.codigoEmpresa = S.codigoEmpresa
			    where E.codigoEmpresa = codEmp order by P.cantidadPresupuesto;

	END$$
DELIMITER ;

call sp_ListarReporte(1);


################### INNER JOIN PARA SERVICIOS #################################################

-- drop procedure sp_ListarReporte2;
 DELIMITER $$
create procedure sp_ListarReporte2(in codTipoPlato int)
	BEGIN
		select * from TipoPlato T inner join Platos P on
			T.codigoTipoPlato = P.codigoTipoPlato
				inner join Productos O on  T.codigoTipoPlato = O.codigoProducto
                inner join Servicios S on T.codigoTipoPlato = S.codigoServicio
                where T.codigoTipoPlato = codTipoPlato order by P.cantidad;
            
    END$$
 DELIMITER ;
 
 call sp_ListarReporte2(1);
