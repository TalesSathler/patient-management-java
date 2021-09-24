--
-- PostgreSQL database dump
--

-- Dumped from database version 12.8 (Ubuntu 12.8-0ubuntu0.20.04.1)
-- Dumped by pg_dump version 12.8 (Ubuntu 12.8-0ubuntu0.20.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: doctor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.doctor (
    doctor_id bigint NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    doctor_crm character varying(255) NOT NULL,
    updated_at timestamp without time zone,
    person_id bigint
);


ALTER TABLE public.doctor OWNER TO postgres;

--
-- Name: nurse; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.nurse (
    nurse_id bigint NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    nurse_coren character varying(255) NOT NULL,
    updated_at timestamp without time zone,
    person_id bigint
);


ALTER TABLE public.nurse OWNER TO postgres;

--
-- Name: patient; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.patient (
    patient_id bigint NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    patient_birthday date,
    patient_height double precision,
    patient_uf character varying(255) NOT NULL,
    patient_weight double precision,
    updated_at timestamp without time zone,
    person_id bigint
);


ALTER TABLE public.patient OWNER TO postgres;

--
-- Name: person; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.person (
    person_id bigint NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    person_cpf character varying(14) NOT NULL,
    person_name character varying(255) NOT NULL,
    person_type character varying(10) DEFAULT 'patient'::character varying NOT NULL,
    updated_at timestamp without time zone
);


ALTER TABLE public.person OWNER TO postgres;

--
-- Name: role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.role (
    role_id bigint NOT NULL,
    role_name character varying(255) NOT NULL
);


ALTER TABLE public.role OWNER TO postgres;

--
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    user_id bigint NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamp without time zone,
    user_login character varying(50) NOT NULL,
    user_password character varying(150) NOT NULL,
    person_id bigint
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- Name: user_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.user_role (
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);


ALTER TABLE public.user_role OWNER TO postgres;

--
-- Data for Name: doctor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.doctor (doctor_id, created_at, doctor_crm, updated_at, person_id) FROM stdin;
1	2021-09-24 03:40:04.638	asd3151	2021-09-24 03:40:07.179	1
\.


--
-- Data for Name: nurse; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.nurse (nurse_id, created_at, nurse_coren, updated_at, person_id) FROM stdin;
1	2021-09-24 03:40:09.204	1234	2021-09-24 03:40:11.17	2
\.


--
-- Data for Name: patient; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.patient (patient_id, created_at, patient_birthday, patient_height, patient_uf, patient_weight, updated_at, person_id) FROM stdin;
1	2021-09-24 03:41:01.082	1992-12-19	1.6	SP	56.2	2021-09-24 03:41:13.341	3
\.


--
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.person (person_id, created_at, person_cpf, person_name, person_type, updated_at) FROM stdin;
1	2021-09-24 03:40:04.625	72548242811	João da Silva	doctor	2021-09-24 03:40:07.182
2	2021-09-24 03:40:09.199	48757766401	Maria da Silva	nurse	2021-09-24 03:40:11.17
3	2021-09-24 03:41:01.071	43482547530	Carla da Silva	patient	2021-09-24 03:41:13.343
\.


--
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.role (role_id, role_name) FROM stdin;
1	doctor
2	patient
3	nurse
\.


--
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."user" (user_id, created_at, updated_at, user_login, user_password, person_id) FROM stdin;
1	2021-09-24 03:40:04.637	2021-09-24 03:40:07.299	joao_silva	$2a$10$0r.Rdq61hWW0KwnewPcyWuJ4OwhBiQzIh5pLKKfr7WrR82SvYe.re	1
2	2021-09-24 03:40:09.203	2021-09-24 03:40:11.278	maria	$2a$10$eX.QX6DrbW5fe7gNqQKp1ODNOd/GPYsD9OWu2ZSrIVsQOG7OaZRmG	2
3	2021-09-24 03:41:01.081	2021-09-24 03:41:13.457	carla	$2a$10$u.TokINiVFyZIyW11Dn/PeEa06bAUrmA60d60Ac3mjMJ.IgL7YseS	3
\.


--
-- Data for Name: user_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.user_role (user_id, role_id) FROM stdin;
1	1
2	3
3	2
\.


--
-- Name: doctor doctor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT doctor_pkey PRIMARY KEY (doctor_id);


--
-- Name: nurse nurse_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nurse
    ADD CONSTRAINT nurse_pkey PRIMARY KEY (nurse_id);


--
-- Name: patient patient_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient
    ADD CONSTRAINT patient_pkey PRIMARY KEY (patient_id);


--
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (person_id);


--
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (role_id);


--
-- Name: person uk_idadc20ji2msochsjmnycvekx; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT uk_idadc20ji2msochsjmnycvekx UNIQUE (person_cpf);


--
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (user_id);


--
-- Name: user_role user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id);


--
-- Name: user_role fka68196081fvovjhkek5m97n3y; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fka68196081fvovjhkek5m97n3y FOREIGN KEY (role_id) REFERENCES public.role(role_id);


--
-- Name: patient fkdxxw8hyl1nca8o4x5r5eap9kf; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.patient
    ADD CONSTRAINT fkdxxw8hyl1nca8o4x5r5eap9kf FOREIGN KEY (person_id) REFERENCES public.person(person_id);


--
-- Name: user_role fkfgsgxvihks805qcq8sq26ab7c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fkfgsgxvihks805qcq8sq26ab7c FOREIGN KEY (user_id) REFERENCES public."user"(user_id);


--
-- Name: user fkgasl2a4gktuoa6l21exq28wtp; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT fkgasl2a4gktuoa6l21exq28wtp FOREIGN KEY (person_id) REFERENCES public.person(person_id);


--
-- Name: doctor fkjac3wynvf4efdxytrn6p8fkai; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.doctor
    ADD CONSTRAINT fkjac3wynvf4efdxytrn6p8fkai FOREIGN KEY (person_id) REFERENCES public.person(person_id);


--
-- Name: nurse fktr7vecm7lgy0f9vcgiameo8ar; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.nurse
    ADD CONSTRAINT fktr7vecm7lgy0f9vcgiameo8ar FOREIGN KEY (person_id) REFERENCES public.person(person_id);


--
-- PostgreSQL database dump complete
--

