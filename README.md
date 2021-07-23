# SGTM
Sistema de Gestión de Transporte Multimodal

Usen estos comandos:

-Vayan a su workspace (donde van a crear el proyecto)

-Click derecho dentro de la carpeta -> git bash here

-En la ventana ponen

git init

git remote add remSGTM https://github.com/MatyDiste/SGTM.git

git pull remSGTM su-branch

git merge remSGTM/main

git push

-Y ahora pueden abrir el Eclipse y abrir el proyecto

No deberian tener ningun problema. Capaz con el tema del usuario y eso deben configurarlo bien. La primera vez que pullean o pushean capaz les pide que se logueen y que tengan git config --global user.name="Su nombre" y git config --global user.email="Su email". Hablen al grupo o a mi directamente si tienen algun problema.

Cada vez que terminen un dia de programar, deberian hacer desde Eclipse o desde el git bash como recien : git commit -m "Mensaje descriptivo de sus cambios"
Seguido de: git push (esto lo va a subir a su branch en este repositorio).

Si ven que sus cambios pueden servirles a los demas, hagan pull request desde la pagina de este repositorio -> Pull requests -> New pull request -> Seleccionan main y su branch para mergear (son dos listas que estan arriba a la izquierda, las dos dicen main al principio, cambian la de la derecha por la suya, y despues ponen un mensaje o algo descriptivo de que es lo que meten al main) y si necesitan urgente que acepte el pull me mandan un msj.

