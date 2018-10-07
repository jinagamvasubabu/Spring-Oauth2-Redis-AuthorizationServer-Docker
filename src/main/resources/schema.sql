
create database if not exists secure;

drop table if exists secure.oauth_client_details;
drop table if exists secure.authority;
drop table if exists secure.credentials;
drop table if exists secure.credentials_authorities;
drop table if exists secure.oauth_approvals;


CREATE TABLE secure.oauth_client_details (
  client_id varchar(255) NOT NULL PRIMARY KEY,
  resource_ids varchar(255) DEFAULT NULL,
  client_secret varchar(255) DEFAULT NULL,
  scope varchar(255) DEFAULT NULL,
  authorized_grant_types varchar(255) DEFAULT NULL,
  web_server_redirect_uri varchar(255) DEFAULT NULL,
  authorities varchar(255) DEFAULT NULL,
  access_token_validity integer(11) DEFAULT NULL,
  refresh_token_validity integer(11) DEFAULT NULL,
  additional_information varchar(255) DEFAULT NULL,
  autoapprove varchar(255) DEFAULT NULL
);

CREATE TABLE secure.authority (
  id  integer,
  authority varchar(255),
  primary key (id)
);


CREATE TABLE secure.credentials (
  id  integer PRIMARY KEY,
  enabled boolean not null,
  name varchar(255) not null UNIQUE,
  password varchar(255) not null,
  version integer
);

CREATE TABLE secure.credentials_authorities (
  credentials_id bigint not null,
  authorities_id bigint not null
);

create table secure.oauth_approvals (
    userId VARCHAR(255),
    clientId VARCHAR(255),
    scope VARCHAR(255),
    status VARCHAR(10),
    expiresAt DATETIME,
    lastModifiedAt DATETIME
);


CREATE INDEX idx_credentials_name ON credentials (name);


