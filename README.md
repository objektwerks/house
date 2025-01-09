House
-----
>House http service using Jsoniter, ScalikeJdbc, Scaffeine, HikariCP, Tapir, Ox, Postgresql and Scala 3.

Tapir
-----
>I initially failed to derive endpoint schemas for **Command** and **Event** due to auto/semi-auto derivation and inline
>errors. Consequently, the initial **Endpoint** handles in/out, text/plain values via just-in-time json conversions.
>Think, **old school**.

>The [Sttp-Tapir](https://github.com/objektwerks/sttp.tapir) project successfully implements a **Tapir** Json endpoint.
>It's a very simple endpoint, though. No derived schema issues.

>The **House** model is more complex. And the **Tapir** derive schema feature *trips up* on **Type** enums. I suspect if I
>derive all enum types, it might work. Even so, it's too much work for too little reward.

>I'd like to **opt out** of schemas all together. I don't need them **in all** cases. I just need fundamental entity<->json binding.

>**Finally**, I succeeded in deriving schemas for all required classes and enums. See **Schemas** and **Endpoint**.

>To work around inline errors, I had to edited scalacOptions as follows:
```
scalacOptions ++= Seq(
  "-Wunused:all",
  "-Xmax-inlines",
  "128"
)
```
>The key lines are ```-Xmax-inlines``` and ```128```.

Ox
--
>I opened this Ox Github issue ( https://github.com/softwaremill/ox/issues/199 ) to get clarity on my usage of Ox.

>Given Ox's edge over Gears ( https://github.com/lampepfl/gears ), I'm betting Ox will continue moving forward. Consequently,
>I've implemented Ox in 6 projects.

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

Run
---
1. sbt run

Model
-----
* House 1 --> 1 Account
* House 1 --> * Meta | Structure | Integral | External
* Entity * --> 1 Type

Meta
----
>Account, House, Drawing, Issue

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
export HOUSE_PATH="command"

export HOUSE_CACHE_INITIAL_SIZE=4
export HOUSE_CACHE_MAX_SIZE=10
export HOUSE_CACHE_EXPIRE_AFTER=24

export HOUSE_POSTGRESQL_DRIVER="org.postgresql.ds.PGSimpleDataSource"
export HOUSE_POSTGRESQL_URL="jdbc:postgresql://localhost:5432/house"
export HOUSE_POSTGRESQL_USER="your.computer.name"
export HOUSE_POSTGRESQL_PASSWORD="your.password"

export HOUSE_EMAIL_HOST="your.email.host"
export HOUSE_EMAIL_ADDRESS="your.email.address@email.com"
export HOUSE_EMAIL_PASSWORD="your.email.password"
```

Resources
---------
* [Ox](https://ox.softwaremill.com/latest/)
* [Tapir](https://tapir.softwaremill.com/en/latest/)

License
-------
>Copyright (c) [2024, 2025] Objektwerks

>Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    * http://www.apache.org/licenses/LICENSE-2.0

>Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
