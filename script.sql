CREATE TABLE public."user"
(
    id serial NOT NULL,
    name character varying(255) NOT NULL,
    login character varying(100) NOT NULL,
    password character varying(100) NOT NULL,
    created_date timestamp without time zone NOT NULL
)
WITH (
    OIDS = FALSE
);

ALTER TABLE public."user"
    OWNER to postgres;