CREATE TABLE public.tbl_authority (
	id bigserial NOT NULL,
	role_name varchar(255) NOT NULL,
	CONSTRAINT tbl_authority_pkey PRIMARY KEY (id)
);