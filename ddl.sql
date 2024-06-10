DROP SCHEMA PUBLIC CASCADE;
CREATE SCHEMA PUBLIC;

CREATE TABLE account (
  id BIGSERIAL PRIMARY KEY,
  license CHAR(36) UNIQUE NOT NULL,
  email VARCHAR NOT NULL,
  pin CHAR(7) NOT NULL,
  activated VARCHAR(10) NOT NULL
);

CREATE TABLE house (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES account(id),
  typeof VARCHAR NOT NULL,
  location VARCHAR NOT NULL,
  built VARCHAR NOT NULL
);

CREATE TABLE foundation (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  built VARCHAR NOT NULL
);

CREATE TABLE frame (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  built VARCHAR NOT NULL
);

CREATE TABLE attic (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  built VARCHAR NOT NULL
);

CREATE TABLE insulation (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE ductwork (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE ventilation (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE roof (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  built VARCHAR NOT NULL
);

CREATE TABLE chimney (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  built VARCHAR NOT NULL
);

CREATE TABLE balcony (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  built VARCHAR NOT NULL
);

CREATE TABLE drywall (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  built VARCHAR NOT NULL
);

CREATE TABLE room (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  built VARCHAR NOT NULL
);

CREATE TABLE driveway (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  culvert_typeof VARCHAR NOT NULL,
  built VARCHAR NOT NULL
);

CREATE TABLE garage (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  built VARCHAR NOT NULL
);

CREATE TABLE siding (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE gutter (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE soffit (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE window (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE door (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE plumbing (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE electrical (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE fusebox (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE alarm (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE heater (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE ac (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE floor (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE lighting (
  id BIGSERIAL PRIMARY KEY,
  house_id BIGINT REFERENCES house(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE sewage (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES account(id),
  typeof VARCHAR NOT NULL,
  built VARCHAR NOT NULL
);

CREATE TABLE well (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES account(id),
  typeof VARCHAR NOT NULL,
  built VARCHAR NOT NULL
);

CREATE TABLE water (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES account(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE water_heater (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES account(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE lawn (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES account(id),
  typeof VARCHAR NOT NULL,
  planted VARCHAR NOT NULL
);

CREATE TABLE garden (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES account(id),
  typeof VARCHAR NOT NULL,
  planted VARCHAR NOT NULL
);

CREATE TABLE sprinkler (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES account(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE shed (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES account(id),
  typeof VARCHAR NOT NULL,
  built VARCHAR NOT NULL
);

CREATE TABLE solarpanel (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES account(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);

CREATE TABLE porch (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES account(id),
  typeof VARCHAR NOT NULL,
  built VARCHAR NOT NULL
);

CREATE TABLE patio (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES account(id),
  typeof VARCHAR NOT NULL,
  built VARCHAR NOT NULL
);

CREATE TABLE pool (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES account(id),
  typeof VARCHAR NOT NULL,
  built VARCHAR NOT NULL
);

CREATE TABLE dock (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES account(id),
  typeof VARCHAR NOT NULL,
  built VARCHAR NOT NULL
);

CREATE TABLE gazebo (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES account(id),
  typeof VARCHAR NOT NULL,
  built VARCHAR NOT NULL
);

CREATE TABLE mailbox (
  id BIGSERIAL PRIMARY KEY,
  account_id BIGINT REFERENCES account(id),
  typeof VARCHAR NOT NULL,
  installed VARCHAR NOT NULL
);