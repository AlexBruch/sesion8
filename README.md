# sesion8

Mostrar JSON en lista y guardar los datos en sqlLite, si no hay conexión hay que cargar los datos de la base de datos

MILLORAR: 
Una estructura millor seria agafar sempre les dades SQLite i fer updates de la base de dades. 
Ara estem agafant sempre l'informació per mostrarla i només fem servir SQLite quan no tenim internet.
També, en comptes de borrar cada cop la base de dades i guardar-la de nou, podriem fer un:
INSERT taula (...) values (...) ON DUPLICATE KEY UPDATE C=C+1;
