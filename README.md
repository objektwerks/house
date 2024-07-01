House
-----
>House model using Jsoniter, ScalikeJdbc, Scaffeine, HikariCP, Helidon, Postgresql and Scala 3.

Todo
----
1. Enhance model.

Install
-------
1. brew install postgresql@14

Build
-----
1. sbt clean compile

Test
----
1. sbt clean test

Model
-----
* House 1 --> 1 Account
* House 1 --> * Structure | Integral | External
* Entity * <--> * Types

Structure
---------
>Foundation, Frame, Attic, Insulation, Ductwork, Ventilation, Roof, Chimney, Balcony, Drywall, Room, Driveway, Garage

Integral
--------
>Siding, Gutter, Soffit, Window, Door, Plumbing, Electrical, Fusebox, Alarm, Heater, AirConditioner, Floor, Lighting

External
--------
>Sewage, Well, Water, WaterHeater, Lawn, Garden, Sprinkler, Shed, SolarPanel, Porch, Patio, Pool, Dock, Gazebo, Mailbox

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

Environment
-----------
>The following environment variables must be defined:
```
export HOUSE_HOST="127.0.0.1"
export HOUSE_PORT=7070
export HOUSE_ENDPOINT="/command"

export HOUSE_CACHE_POOL_INITIAL_SIZE=4
export HOUSE_CACHE_POOL_MAX_SIZE=10
export HOUSE_CACHE_POOL_EXPIRE_AFTER=24

export HOUSE_POSTGRESQL_DRIVER="org.postgresql.ds.PGSimpleDataSource"
export HOUSE_POSTGRESQL_URL="jdbc:postgresql://localhost:5432/house"
export HOUSE_POSTGRESQL_USER="your.computer.name"
export HOUSE_POSTGRESQL_PASSWORD="your.password"

export HOUSE_EMAIL_HOST="your.email.host"
export HOUSE_EMAIL_ADDRESS="your.email.address@email.com"
export HOUSE_EMAIL_PASSWORD="your.email.password"
```

License
-------
>Copyright (c) [2024] [Objektwerks]

>Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    * http://www.apache.org/licenses/LICENSE-2.0

>Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
