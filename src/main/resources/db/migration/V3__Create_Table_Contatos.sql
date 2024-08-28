BEGIN;

CREATE TABLE IF NOT EXISTS public.contatos
(
    id serial NOT NULL,
    nome varchar(50) COLLATE pg_catalog."default" NOT NULL,
    contato varchar(100) COLLATE pg_catalog."default" NOT NULL,
    created_date date DEFAULT CURRENT_DATE,
    profissional_id integer NOT NULL,
    deleted_profissional boolean,
    CONSTRAINT contatos_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.contatos
    ADD CONSTRAINT contatos_profissional_id_fkey FOREIGN KEY (profissional_id)
    REFERENCES public.profissionais (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

END;