# a06-webfriends22-jdbc

Projecte web amb JavaEE: JSTL + BD MySQL/MariaDB. 

Què fa la aplicació web. 

Operacions CRUd amb 3 taules : Friends, Categories i Usuaris.

Entre Friends i Categories hi ha una relació 
``` N Friends  --  1 Categories ```

### Requisits previs.

-JDK 8/11 i serv.App. Java Web com Tomcat(9.0.20) o Glassfish(5.0)

-Base de dades MYQSL o MariaDB instal.lada

-Llibreries driver JDBC oficial i JSTL 1.2.

-Executar scrits per a fer els CREATE i INSERT a les taules SQL.

### Localització fitxers de configuració.

``` <carpeta_projectes_netbeans>/pt16-webfriends-jdbc-ab20/web/WEB-INF/resources ```

També contenen els scripts:
-friendship.sql
-users.sql

#### Configuració BBDD:

Crida al fitxer amb la cadena de connexió

``` <carpeta_projectes_netbeans>/pt16-webfriends-jdbc-ab20/src/java/model/persist/DBConnect.java ```

Fitxer invocat:

``` <carpeta_projectes_netbeans>/pt16-webfriends-jdbc-ab20/web/WEB-INF/resources/database_config_lab.properties ```
