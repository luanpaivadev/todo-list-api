CREATE TABLE public.tbl_user (
	id bigserial NOT NULL,
	enabled bool NULL,
	"password" varchar(255) NOT NULL,
	username varchar(255) NOT NULL UNIQUE,
	CONSTRAINT tbl_user_pkey PRIMARY KEY (id)
);