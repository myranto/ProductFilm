create database production;
\c production

create table plateau
(
    idplateau  serial primary key,
    nomplateau varchar(30) not null,
    dateDebutIndisponibilite timestamp,
    dateFinIndisponibile timestamp
);

create table RegleAction(
durree int
);

create table personnage
(
    idperso       serial primary key,
    nomperso      varchar(30) not null,
    DateNaissance date        not null check ( DateNaissance < current_date ),
    dateDebutIndisponibilite date,
    dateFinIndisponibile date
);

-- alter table personnage add column dateDebutIndisponibilite date;
-- alter table personnage add column dateFinIndisponibile date;

create table scene
(
    idscene   serial primary key,
    nom       varchar(40) not null,
    dateDebut timestamp default current_timestamp
);


create table action
(
    idaction    serial primary key,
    idscene     int not null references scene (idscene),
    dateAction  int default 1,--duree
    idplateau int not null references plateau (idplateau),
    description text,
    finished  int default 0 check ( finished in (0, 1) ),
    isValidate int not null check ( isValidate in (0,1) )
);
-- alter table action add  column     isValidate int not null check ( isValidate in (0,1) );


-- alter table action
--     add column finished int default 0 check ( finished in (0, 1) );

create table notification
(
    id        serial primary key,
    message   text not null,
    idaction  int references action (idaction) unique,
    Datenotif timestamp default current_timestamp
);
-- alter table notification
--     add column idaction int references action (idaction) unique;

create table photos
(
    idaction int not null references action (idaction),
    image    text
);


create table mis_en_action
(
    idaction  int not null references action (idaction),
    idperso   int not null references personnage (idperso)
);

-- create table suggestion(
--     id serial primary key ,
--     idaction int not null references action(idaction),
--     debut timestamp not null ,
--     fin timestamp not null check ( debut<fin ) ,
--     isValidate int not null check ( isValidate in (0,1) )
-- );



create table calendar(
    idCalendar serial primary key ,
    idAction int not null references action (idaction) unique ,
    dateCalendar timestamp not null default current_timestamp,
    datefin timestamp not null check ( datefin>dateCalendar )
);

-- alter table calendar add column datefin timestamp not null check ( datefin>dateCalendar );


-- create or replace function getDebutAction(idactions int) returns timestamp language plpgsql
-- as
-- $$
--     declare
--         idplat integer;
--         dated timestamp;
--     begin
-- --         select
--     end;
-- $$;

-- create function to calculate date
-- create or replace function suggestion() returns table(idact int,debut timestamp,fin timestamp) language plpgsql
-- as
-- $$
--     declare
--         debutAction timestamp;
--     begin
--         select * from action where isValidate=0;
--
--     end;
-- $$;

--change disponibilty of plateau

create or replace function update_Disponibility_action() returns trigger language plpgsql
as
$$
declare
    idplat integer;
BEGIN
    select a.idplateau into idplat from action a where a.idaction=new.idAction;
    update plateau  set dateDebutIndisponibilite=new.dateCalendar,dateFinIndisponibile=new.datefin where idplateau=idplat;
    return new;
end;
$$;
--declencheur
create or replace  trigger updateAction
    before insert on calendar
    for each row
execute procedure update_Disponibility_action();

INSERT INTO plateau(nomplateau)
VALUES ('Plateau1'),
       ('Plateau2'),
       ('Plateau3'),
       ('Plateau4');
-- add more rows here

insert into personnage (nomperso, DateNaissance)
values ('Myranto', '2002/12/11'),
       ('Ardy', '2003/12/11'),
       ('Leka', '2002/12/11'),
       ('Isaia', '2002/12/11'),
       ('Namana', '2002/12/11'),
       ('Antso', '2004/1/14'),
       ('Malala', '2003/04/15');


insert into scene(nom, dateDebut)
values ('scene1', '2023/02/26 08:00'),
       ('scene2', '2023/03/02 08:00'),
       ('scene3', '2023/02/11 08:00');

--
-- insert into action(idscene, dateAction, description)
-- VALUES (1, '2023/02/26 10:00', 'mis en oeuvre d art'),
--        (1, '2023/03/27 12:00', 'Romantisme avec sexe'),
--        (1, '2023/03/01 17:00', 'Presentation de parent'),
--        (2, '2023/03/04 12:00', 'Discussion du mariage'),
--        (2, '2023/03/08 10:00', 'Rompture entre les romanteur'),
--        (2, '2023/03/10 17:00', 'Nouveau copine du gars'),
--        (3, '2023/03/12 10:00', 'Deprecion du fille'),
--        (3, '2023/03/14 16:00', 'Retour en oeuvres des deux'),
--        (3, '2023/03/17 10:00', 'Discussion avec la famille'),
--        (3, '2023/03/19 17:00', 'Mariage des deux');
--
-- insert INTO mis_en_action (idaction, idperso, idplateau)
-- VALUES (1, 1, 1),
--        (1, 7, 1),
--        (2, 7, 2),
--        (2, 1, 2),
--        (3, 1, 3),
--        (3, 2, 3),
--        (3, 3, 3),
--        (3, 7, 3),
--        (4, 1, 4),
--        (4, 2, 4),
--        (4, 3, 4),
--        (4, 7, 4),
--        (4, 4, 4),
--        (4, 5, 4)
-- ;
--
-- create or replace view plateauMaction as
-- (
-- select m.*, p.nomplateau as plateau, pers.nomperso as personnage
-- from mis_en_action as m
--          join plateau as p
--               on p.idplateau = m.idplateau
--          join personnage as pers
--               on pers.idperso = m.idperso
--     );
-- create or replace view sceneaction as
-- (
-- select a.*, s.datedebut, s.datefin, s.nom
-- from action as a
--          join scene as s
--               on s.idscene = a.idscene
--     );
-- --  idaction | idperso | idplateau | plateau  | personnage
-- --  idaction | idscene |     dateaction      |         description          |      datedebut      |       datefin       |  nom
-- create or replace view view_scene as
-- (
-- select p.personnage, p.idperso,  p.plateau, s.*
-- from sceneaction as s
--          join plateaumaction as p
--               on p.idaction = s.idaction
--     );
-- select * from Action where idaction='0' and idscene='0' and 1=1 and upper(description) like upper('%myra%') and finished='0' and 1=1
-- -- select * from photos
-- -- select * from action