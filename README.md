## Descripción

Esta app contiene seguridad con spring security y encriptación con JWT, los usuarios están asociados a un rol lo cual tienen restringido el consumo de algunas peticiones (API públicas y privadas),
Los usuarios pueden dar de alta a Post solo con sesión activa, la asociación de usuario y post se hacen por la sesión activa que contenga.

## Swagger
Integración con swagger, para poder acceder es desde la siguiente url:

URL: `/swagger-ui/index.html`

## Levantar proyecto

Primero importar como proyecto de maven en algunos de los siguientes IDE para ver el codigo fuente:

```bash
Eclipce IDE
# or
Intellij IDE
# or
Visual Studio Code
```

Segundo Importar el archivo `postman_collection` como colleccion  de Postman.

## Pruebas funcionales desde Postman

Dentro de la colleccion de postman encontraremos 3 folders `Auth-JWT`, `User` y `Post`.

 - Auth-JWT contiene las peticiones de autenticación.
 - User contine las peticiones para el mantenimiento de usuarios.
 - Post contine las peticiones para el mantenimiento de post.

 ### Auth-JWT

 En el folder Auth-JWT encontramos las siguientes peticiones:

 - `POST api/v1/auth/register` es la petición para el registro de usuarios, el cual requiere los llenar los siguientes atributos.
 ```json
{
    "name": "nombre de usuario",
    "cellphone": "númerico telefonico",
    "password": "contraseña",
    "lastname": "apellido",    
}
```
 - `POST api/v1/auth/login` es la petición para el inicio de sesión de usuario, el cual requiere los llenar los siguientes atributos.
 ```json
{
    "name": "nombre de usaurio",
    "password": "contraseña"
}
```

 ### User

 En el folder User encontramos las siguientes peticiones:

 - `GET api/v1/user` es la petición que devuelve todos los tipos de cambios disponible en el sistema.
  ```json
{
    "content": [
        {
            "id": id,
            "name": "name",
            "cellphone": "cellphone",
            "lastname": "lastname",
            "createAt": "create at",
            "lastModified": "last modified"
        }
    ]
}
```

 - `UPDATE api/v1/user` es la petición que devuelve todos los tipos de cambios disponible en el sistema.
  ```json
BODY
{
    "name": "name",
    "cellphone": "cellphone",
    "username": "username",
    "password": "password",
    "lastname": "lastname"
}
```

 - `DELETE api/v1/user/{id}` es la petición que devuelve todos los tipos de cambios disponible en el sistema.
  ```json
no content
```

 ### Post

 En el folder Post encontramos las siguientes peticiones:

 - `GET api/v1/post` es la petición para obtener todos los post:
 ```json
{
   "content": [
        {
            "id": id,
            "text": "text",
            "user": {
                "id": id,
                "name": "name",
                "cellphone": "cellphone",
                "lastname": "lastname",
                "createAt": "create at",
                "lastModified": "last modified"
            },
            "createAt": "create at",
            "lastModified": "last modified"
        }
    ]
}
```
 - `POST api/v1/post` es la petición para el registro de post, el cual requiere los llenar los siguientes atributos:
    - text: string
 ```json
{
    "id": int,
    "text": "string",
    "user": {
        "id": int,
        "name": "string",
        "cellphone": "string",
        "lastname": "string",
        "createAt": "string",
        "lastModified": "string"
    },
    "createAt": "string",
    "lastModified": "string"
}
```
 - `UPDATE api/v1/post/{id}` es la petición para buscar la transacción, el cual requiere los llenar los siguientes atributos:
    - text: string
 ```json
{
    "id": int,
    "text": "string",
    "user": {
        "id": int,
        "name": "string",
        "cellphone": "string",
        "lastname": "string",
        "createAt": "string",
        "lastModified": "string"
    },
    "createAt": "string",
    "lastModified": "string"
}