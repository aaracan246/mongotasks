Nombre del proyecto: 

mongotasks (Gestor de tareas en MongoDB).


Documentos que intervendrán en el proyecto:

Usuario. Un documento que almacene los datos del usuario. Su nombre, contraseña, el rol que tiene para controlar el acceso y su dirección.

Campos de Usuario: 
- username
- password (debe ir hasheada)
- roles
- direccion
- lista<Tarea>

Dirección. Un documento que almacena la dirección de un usuario. Usada para verificar vía GeoAPI si la información es correcta.

Campos de Dirección:
- municipio
- provincia

Tarea. Un documento que almacena la información sobre la tarea a resolver, ya sea su título o la descripción de esta. 

Campos de Tarea:
- title
- desc
- user: Usuario


Posibles endpoints del proyecto:

User. Gestiona la información relacionada con el usuario. Puedes registrar un usuario para después poder acceder a tu zona de usuario vía login. Además de poder actualizar tu información vía update o eliminarla vía delete. Solo los admin pueden acceder a todos los usuarios.

/usuarios/register 

/usuarios/login

/usuarios/

/usuarios/update_user/{id}

/usuarios/delete_user/{id}

Task. Gestiona la información relacionada con las tareas. Puedes registrar una tarea, acceder a todas las tareas y actualizar o eliminar una tarea concreta.

/tasks/insert_task

/tasks/

/tasks/update_task/{id}

/tasks/delete_task/{id}

La lógica de negocio:

En esta aplicación la lógica de negocio está muy relacionada a los permisos. No se pueden manejar los datos de un usuario a menos que estés logueado como él mismo (a menos que seas Administrador). En caso de ser Administrador sí tienes acceso total a la información. 

Sin necesidad de permiso se puede realizar un registro o un logueo.

Excepciones posibles:

- UnauthorizedException: Ocurre si se intenta acceder a una funcionalidad no permitida por el rol que se posee.
- BadRequestException: Ocurre si se intenta acceder a una funcionalidad sin cumplir con los requisitos. Ex: Introducir username vacío, etc.
- NotFoundException: Ocurre si se intenta acceder a una información y por algún motivo no se puede acceder a ella (no relacionada con permisos).

Permisos:
Existen dos roles, user o admin (explicados en lógica de negocio).

- USER: Acceso limitado. 

- ADMIN: Acceso total a la funcionalidad.


PRUEBAS GESTIÓN USUARIOS: !!!!!!!!!!! PRUEBAS AQUÍ

Base de datos pre register:
![CapMongo](https://github.com/user-attachments/assets/96024f1c-35b7-47c7-9e9d-52e8acd62690)


Register:

![CapMongo2](https://github.com/user-attachments/assets/d6b9a2c6-e023-4840-bbc3-d922d52d25c8)


Registro en LogCat:
![CapMongo3](https://github.com/user-attachments/assets/8a99977a-83e7-469b-aaf1-e9d3829f2bf7)

Registro erróneo:

![CapMongo5](https://github.com/user-attachments/assets/361e9ca2-4d2f-4ff9-aecf-58e210d89554)

Registro erróneo (municipio incorrecto):
![CapMongo4](https://github.com/user-attachments/assets/a4dbec8d-1886-42ac-b81b-0ab0ec9d8969)


Login (no funcional en interfaz aún):
![CapMongo6](https://github.com/user-attachments/assets/2525e572-5838-4574-bd21-5c16106a4ac0)


Municipio / Provincia erróneos (404 not found). San Fernando no está en Almería:
![CapMongo7](https://github.com/user-attachments/assets/89180d38-6ed3-4204-abd8-e405c4e8c22f)

Login (interfaz):

![CapMongo9](https://github.com/user-attachments/assets/1655ec8b-ce11-4c24-a50b-c76b096f0de5)


LogCat exitoso:


![CapMongo8](https://github.com/user-attachments/assets/d97097ae-6fb1-4faa-a6c7-d9af95caef14)


Usuario en MongoDB para el login:

![CapMongo99](https://github.com/user-attachments/assets/e076904d-0008-4c61-a987-5b0fa45aefbc)


