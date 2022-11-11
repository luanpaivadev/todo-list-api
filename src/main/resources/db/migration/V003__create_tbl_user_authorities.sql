CREATE TABLE public.tbl_user_authorities (
	user_id int8 NOT NULL,
	authorities_id int8 NOT NULL
);

ALTER TABLE public.tbl_user_authorities ADD CONSTRAINT fk3q2hlio81rgeix1smjptypi0w FOREIGN KEY (user_id) REFERENCES public.tbl_user(id);
ALTER TABLE public.tbl_user_authorities ADD CONSTRAINT fkdbxw3wnhgyj5kirxt7fgopjx4 FOREIGN KEY (authorities_id) REFERENCES public.tbl_authority(id);