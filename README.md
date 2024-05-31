House
----
>Model of a house using Jsoniter, ScalikeJdbc, Scaffeine, HikariCP, Postgresql and Scala 3.

Model
-----
* House 1 --> 1 Account
* House 1 --> * Structure | Integral | External
* Entity * <--> * Types

Structure
---------
>Foundation, Frame, Attic, Insulation, Ventilation, Roof, Chimney, Balcony, Drywall,
>Room, Driveway, Garage

Integral
--------
>Siding, Gutter, Soffit, Window, Door, Plumbing, Electrical, Fusebox, Alarm, Heater,
>AirConditioner, Floor, Lighting

External
--------
>Sewage, Well, Water, WaterHeater, Lawn, Garden, Sprinkler, Shed, SolarPanel, Dock,
>Porch, Patio, Pool, Mailbox

Postgresql
----------
1. config:
    1. on osx intel: /usr/local/var/postgres/postgresql.config : listen_addresses = ‘localhost’, port = 5432
    2. on osx m1: /opt/homebrew/var/postgres/postgresql.config : listen_addresses = ‘localhost’, port = 5432
2. run:
    1. brew services start postgresql@14
3. logs:
    1. on osx intel: /usr/local/var/log/postgres.log
    2. on m1: /opt/homebrew/var/log/postgres.log

Database
--------
>Example database url: postgresql://localhost:5432/house?user=mycomputername&password=house"
1. psql postgres
2. CREATE DATABASE house OWNER [your computer name];
3. GRANT ALL PRIVILEGES ON DATABASE house TO [your computer name];
4. \l
5. \q
6. psql house
7. \i ddl.sql
8. \q

DDL
---
>Alternatively run: psql -d house -f ddl.sql
1. psql house
2. \i ddl.sql
3. \q

Drop
----
1. psql postgres
2. drop database house;
3. \q

Resources
---------
* [House Parts](https://www.hippo.com/learn-center/parts-of-a-house)