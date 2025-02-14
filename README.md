Nombre del proyecto: 

mongotasks (Gestor de tareas en MongoDB).


Documentos que intervendrán en el proyecto:

Usuario. Un documento que almacene los datos del usuario. Su nombre, contraseña, el rol que tiene para controlar el acceso y su dirección.

Campos de Usuario: 
- username
- password
- roles
- direccion

Dirección. Un documento que almacena la dirección de un usuario. Usada para verificar vía GeoAPI si la información es correcta.

Campos de Dirección:
- municipio
- provincia

Tarea. Un documento que almacena la información sobre la tarea a resolver, ya sea su título o la descripción de esta. 

Campos de Tarea:
- title
- desc


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

