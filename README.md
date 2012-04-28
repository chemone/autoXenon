autoXenon
=========

GUI installer for libxenon

**AutoXenon 0.2**

En ésta versión sólo está incluída la instalación de la toolchain y de libxenon, de momento la aplicación sólo es compatible con Ubuntu o Debian. 


-Modo de empleo:

Debemos tener instalado java en nuestro equipo, recomiendo instalar open jdk. 

Para utilizar la aplicacón primero debemos darle permisos de ejecución pulsando en clic derecho y propiedades, marcamos la casilla de permitir ejecutar.

Para ejecutarla deberemos hacer clic derecho y seleccionar ejecutar, si hacemos doble clic se abrirá con el gestor da archivadores.

La aplicación pedirá en una terminal nuestro password, para instalar los paquetes previos, luego, muy posiblemente os pida vuestro nombre de usuario para daros permisos sobre la carpeta opt. (Si la carpeta no existe no pedirá el nombre de usuario).

Si estáis actualizando os pedirá permiso para borrar los ficheros de los anteriores repositorios.

Para poder compilar nuestros proyectos debemos de editar el fichero .bashrc, tanto el del usuario root como nuestro usuario normal, añadiendo lo siguiente:

export DEVKITXENON="/usr/local/xenon"
export PATH="$PATH:$DEVKITXENON/bin:$DEVKITXENON/usr/bin"

-Cambios en la versión 0.2:

  -Corregidos problemas con permisos de las carpetas.

  -Añadido soporte para actualizar libxenon, descargando los últimos repositorios.

  -Añadido mensaje de fin de proceso.

-Cambios en la versión 0.2_b:

  -Corregido bug menor en ubuntu 11.04

-Agradecimientos:

Gracias a GliGli, tmbinc (por decubrir los hacks rgh y jtag respectivamente), a todos los usuarios de libxenon.org (por todas sus inestimables aportaciones), 
a los usuarios de elotrolado.net (gracias por todo) y a toda la gente que hace posible que ésto siga creciendo. Un saludo. 