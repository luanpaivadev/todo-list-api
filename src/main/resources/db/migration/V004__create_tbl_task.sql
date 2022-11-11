CREATE TABLE public.tbl_task (
	id bigserial NOT NULL,
	alarm varchar(255) NULL,
	completed bool NULL,
	description varchar(9999) NOT NULL,
	user_id int8 NULL,
	CONSTRAINT tbl_task_pkey PRIMARY KEY (id)
);

ALTER TABLE public.tbl_task ADD CONSTRAINT fk2hqotyiywteujxlq0phemtlmf FOREIGN KEY (user_id) REFERENCES public.tbl_user(id);