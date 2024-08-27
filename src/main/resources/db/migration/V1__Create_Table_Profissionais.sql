BEGIN;

CREATE TABLE IF NOT EXISTS public.profissionais
(
    id bigserial NOT NULL,
    nome character varying(255) COLLATE pg_catalog."default" NOT NULL,
    cargo character varying(50) NOT NULL CHECK (cargo IN ('Desenvolvedor', 'Designer', 'Suporte', 'Tester')),
    nascimento date NOT NULL,
    created_date timestamp(6) without time zone DEFAULT CURRENT_DATE,
    deleted boolean,
    deleted_date timestamp(6) without time zone,
    CONSTRAINT profissionais_pkey PRIMARY KEY (id)
);

END;