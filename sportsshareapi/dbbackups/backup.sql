--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.3
-- Dumped by pg_dump version 9.1.3
-- Started on 2012-05-19 19:29:38 CDT

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 170 (class 3079 OID 11681)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1963 (class 0 OID 0)
-- Dependencies: 170
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 161 (class 1259 OID 34762)
-- Dependencies: 5
-- Name: answer; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE answer (
    id bigint NOT NULL,
    answer character varying(255),
    answernum double precision NOT NULL,
    answertype character varying(255),
    confidence integer NOT NULL,
    create_date timestamp without time zone,
    gamesessionid bigint NOT NULL,
    update_date timestamp without time zone,
    questionid bigint NOT NULL,
    userid bigint NOT NULL
);


--
-- TOC entry 162 (class 1259 OID 34770)
-- Dependencies: 5
-- Name: game_session; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE game_session (
    id bigint NOT NULL,
    category character varying(255),
    create_date timestamp without time zone,
    description character varying(255),
    end_date timestamp without time zone,
    update_date timestamp without time zone,
    maxusers bigint NOT NULL,
    performerid bigint NOT NULL,
    signup_expiration_date timestamp without time zone,
    start_date timestamp without time zone
);


--
-- TOC entry 163 (class 1259 OID 34778)
-- Dependencies: 5
-- Name: game_session_question; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE game_session_question (
    id bigint NOT NULL,
    create_date timestamp without time zone,
    update_date timestamp without time zone,
    game_session_id bigint NOT NULL,
    question_id bigint NOT NULL
);


--
-- TOC entry 164 (class 1259 OID 34783)
-- Dependencies: 5
-- Name: game_session_user; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE game_session_user (
    id bigint NOT NULL,
    create_date timestamp without time zone,
    update_date timestamp without time zone,
    game_session_id bigint NOT NULL,
    user_id bigint NOT NULL
);


--
-- TOC entry 165 (class 1259 OID 34788)
-- Dependencies: 5
-- Name: performer; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE performer (
    id bigint NOT NULL,
    category character varying(255),
    create_date timestamp without time zone,
    description character varying(255),
    update_date timestamp without time zone,
    maxshares bigint NOT NULL,
    name character varying(255),
    shareprice numeric(19,2)
);


--
-- TOC entry 166 (class 1259 OID 34796)
-- Dependencies: 5
-- Name: question; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE question (
    id bigint NOT NULL,
    answer character varying(255),
    answernum double precision NOT NULL,
    answertype character varying(255),
    category character varying(255),
    create_date timestamp without time zone,
    defaultanswer character varying(255),
    update_date timestamp without time zone,
    performerid bigint NOT NULL,
    question character varying(255)
);


--
-- TOC entry 167 (class 1259 OID 34804)
-- Dependencies: 5
-- Name: share_transaction; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE share_transaction (
    id bigint NOT NULL,
    create_date timestamp without time zone,
    gamesessionid bigint NOT NULL,
    update_date timestamp without time zone,
    performerid bigint NOT NULL,
    pointsper integer NOT NULL,
    priceper numeric(19,2),
    quantity integer NOT NULL,
    sessionid character varying(255),
    status character varying(255),
    totalpoints bigint NOT NULL,
    totalprice numeric(19,2),
    user_id bigint NOT NULL
);


--
-- TOC entry 169 (class 1259 OID 34822)
-- Dependencies: 5
-- Name: user_session; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE user_session (
    id bigint NOT NULL,
    create_date timestamp without time zone,
    update_date timestamp without time zone,
    sessionid character varying(255),
    userid bigint
);


--
-- TOC entry 168 (class 1259 OID 34812)
-- Dependencies: 5
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE users (
    id bigint NOT NULL,
    billingaddress1 character varying(255),
    billingaddress2 character varying(255),
    billingaddress3 character varying(255),
    billingaddress4 character varying(255),
    billingcity character varying(255),
    billingstate character varying(255),
    billingzip character varying(255),
    create_date timestamp without time zone,
    email character varying(255) NOT NULL,
    firstname character varying(255),
    login_date timestamp without time zone,
    lastname character varying(255),
    update_date timestamp without time zone,
    password character varying(255),
    shippingaddress1 character varying(255),
    shippingaddress2 character varying(255),
    shippingaddress3 character varying(255),
    shippingaddress4 character varying(255),
    shippingcity character varying(255),
    shippingstate character varying(255),
    shippingzip character varying(255)
);


--
-- TOC entry 1950 (class 0 OID 34762)
-- Dependencies: 161
-- Data for Name: answer; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1951 (class 0 OID 34770)
-- Dependencies: 162
-- Data for Name: game_session; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO game_session VALUES (1, 'NFL Football', '2012-05-19 18:39:02.802', 'NFL Fantasy Weekly League', NULL, '2012-05-19 18:39:02.92', 1000, 1, NULL, NULL);


--
-- TOC entry 1952 (class 0 OID 34778)
-- Dependencies: 163
-- Data for Name: game_session_question; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO game_session_question VALUES (1, '2012-05-19 18:39:02.87', '2012-05-19 18:39:02.87', 1, 1);


--
-- TOC entry 1953 (class 0 OID 34783)
-- Dependencies: 164
-- Data for Name: game_session_user; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1954 (class 0 OID 34788)
-- Dependencies: 165
-- Data for Name: performer; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO performer VALUES (1, 'NFL Football Team', '2012-05-19 18:39:03.311', 'The best team in the NFL....in 1985', '2012-05-19 18:39:03.311', 100000, 'Chicago Bears', 1.00);


--
-- TOC entry 1955 (class 0 OID 34796)
-- Dependencies: 166
-- Data for Name: question; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO question VALUES (1, '', 500, 'NUMERIC', 'RUSHING-YARDS', '2012-05-19 18:39:02.842', '100', '2012-05-19 18:39:02.843', 1, 'How many rushing yards will Matt Forte have in week 1?');
INSERT INTO question VALUES (2, '', 123, 'NUMERIC', 'RUSHING-YARDS', '2012-05-19 18:39:02.935', '100', '2012-05-19 18:39:02.958', 1, 'How many rushing yards will Matt Forte have in week 1?');


--
-- TOC entry 1956 (class 0 OID 34804)
-- Dependencies: 167
-- Data for Name: share_transaction; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO share_transaction VALUES (1, '2012-05-19 18:39:02.356', 1, '2012-05-19 18:39:02.469', 1, 90, 5.00, 2, NULL, 'Denied', 180, 10.00, 1);
INSERT INTO share_transaction VALUES (2, '2012-05-19 18:39:30.347', 0, '2012-05-19 18:39:30.347', 0, 10, 5.00, 10, NULL, 'Pending', 100, 50.00, 1);
INSERT INTO share_transaction VALUES (3, '2012-05-19 18:40:34.482', 0, '2012-05-19 18:40:34.482', 0, 10, 5.00, 10, NULL, 'Pending', 100, 50.00, 1);
INSERT INTO share_transaction VALUES (4, '2012-05-19 18:41:00.825', 0, '2012-05-19 18:41:00.826', 0, 10, 5.00, 10, NULL, 'Pending', 100, 50.00, 1);
INSERT INTO share_transaction VALUES (5, '2012-05-19 18:42:30.353', 0, '2012-05-19 18:42:30.353', 0, 10, 5.00, 10, NULL, 'Pending', 100, 50.00, 1);
INSERT INTO share_transaction VALUES (6, '2012-05-19 18:43:26.198', 0, '2012-05-19 18:43:26.198', 0, 10, 5.00, 10, NULL, 'Pending', 100, 50.00, 1);
INSERT INTO share_transaction VALUES (7, '2012-05-19 18:46:59.572', 0, '2012-05-19 18:46:59.572', 0, 10, 5.00, 10, NULL, 'Pending', 100, 50.00, 1);
INSERT INTO share_transaction VALUES (8, '2012-05-19 18:50:19.166', 0, '2012-05-19 18:50:19.166', 0, 10, 5.00, 10, NULL, 'Pending', 100, 50.00, 1);
INSERT INTO share_transaction VALUES (9, '2012-05-19 18:50:50.07', 0, '2012-05-19 18:50:50.07', 0, 10, 3.00, 50, NULL, 'Pending', 500, 150.00, 1);
INSERT INTO share_transaction VALUES (10, '2012-05-19 18:50:59.681', 0, '2012-05-19 18:50:59.681', 0, 10, 3.00, 50, NULL, 'Pending', 500, 150.00, 1);
INSERT INTO share_transaction VALUES (11, '2012-05-19 18:51:26.736', 0, '2012-05-19 18:51:26.736', 0, 10, 3.00, 50, NULL, 'Pending', 500, 150.00, 1);
INSERT INTO share_transaction VALUES (12, '2012-05-19 18:56:37.54', 0, '2012-05-19 18:56:37.54', 0, 356, 5.00, 10, NULL, 'Pending', 3560, 50.00, 1);


--
-- TOC entry 1958 (class 0 OID 34822)
-- Dependencies: 169
-- Data for Name: user_session; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO user_session VALUES (3, '2012-05-19 18:39:03.185', '2012-05-19 18:39:03.185', 'invalid', NULL);


--
-- TOC entry 1957 (class 0 OID 34812)
-- Dependencies: 168
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO users VALUES (1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2012-05-19 18:39:02.242', 'test@tesy.com', NULL, NULL, NULL, '2012-05-19 18:39:02.242', 'password', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO users VALUES (3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2012-05-19 18:39:03.024', 'simple@somple.com', NULL, NULL, NULL, '2012-05-19 18:39:03.024', 'pass', NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 1925 (class 2606 OID 34769)
-- Dependencies: 161 161
-- Name: answer_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY answer
    ADD CONSTRAINT answer_pkey PRIMARY KEY (id);


--
-- TOC entry 1927 (class 2606 OID 34777)
-- Dependencies: 162 162
-- Name: game_session_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY game_session
    ADD CONSTRAINT game_session_pkey PRIMARY KEY (id);


--
-- TOC entry 1929 (class 2606 OID 34782)
-- Dependencies: 163 163
-- Name: game_session_question_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY game_session_question
    ADD CONSTRAINT game_session_question_pkey PRIMARY KEY (id);


--
-- TOC entry 1931 (class 2606 OID 34787)
-- Dependencies: 164 164
-- Name: game_session_user_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY game_session_user
    ADD CONSTRAINT game_session_user_pkey PRIMARY KEY (id);


--
-- TOC entry 1933 (class 2606 OID 34795)
-- Dependencies: 165 165
-- Name: performer_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY performer
    ADD CONSTRAINT performer_pkey PRIMARY KEY (id);


--
-- TOC entry 1935 (class 2606 OID 34803)
-- Dependencies: 166 166
-- Name: question_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY question
    ADD CONSTRAINT question_pkey PRIMARY KEY (id);


--
-- TOC entry 1937 (class 2606 OID 34811)
-- Dependencies: 167 167
-- Name: share_transaction_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY share_transaction
    ADD CONSTRAINT share_transaction_pkey PRIMARY KEY (id);


--
-- TOC entry 1943 (class 2606 OID 34826)
-- Dependencies: 169 169
-- Name: user_session_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_session
    ADD CONSTRAINT user_session_pkey PRIMARY KEY (id);


--
-- TOC entry 1939 (class 2606 OID 34821)
-- Dependencies: 168 168
-- Name: users_email_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_email_key UNIQUE (email);


--
-- TOC entry 1941 (class 2606 OID 34819)
-- Dependencies: 168 168
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 1947 (class 2606 OID 34842)
-- Dependencies: 164 1940 168
-- Name: fk7286c721960ce266; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY game_session_user
    ADD CONSTRAINT fk7286c721960ce266 FOREIGN KEY (user_id) REFERENCES users(id);


--
-- TOC entry 1946 (class 2606 OID 34837)
-- Dependencies: 162 164 1926
-- Name: fk7286c721b6724; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY game_session_user
    ADD CONSTRAINT fk7286c721b6724 FOREIGN KEY (game_session_id) REFERENCES game_session(id);


--
-- TOC entry 1945 (class 2606 OID 34832)
-- Dependencies: 1934 166 163
-- Name: fk8019743c1672e34b; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY game_session_question
    ADD CONSTRAINT fk8019743c1672e34b FOREIGN KEY (question_id) REFERENCES question(id);


--
-- TOC entry 1944 (class 2606 OID 34827)
-- Dependencies: 1926 162 163
-- Name: fk8019743cb6724; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY game_session_question
    ADD CONSTRAINT fk8019743cb6724 FOREIGN KEY (game_session_id) REFERENCES game_session(id);


--
-- TOC entry 1949 (class 2606 OID 34852)
-- Dependencies: 169 1940 168
-- Name: fk_users_usersession; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_session
    ADD CONSTRAINT fk_users_usersession FOREIGN KEY (userid) REFERENCES users(id);


--
-- TOC entry 1948 (class 2606 OID 34847)
-- Dependencies: 1940 168 167
-- Name: fke60b763e960ce266; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY share_transaction
    ADD CONSTRAINT fke60b763e960ce266 FOREIGN KEY (user_id) REFERENCES users(id);


-- Completed on 2012-05-19 19:29:38 CDT

--
-- PostgreSQL database dump complete
--

