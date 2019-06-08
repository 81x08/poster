create extension if not exists pgcrypto;

update usr set password = crypt(password, get_salt('bf', 8));