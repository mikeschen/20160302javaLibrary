--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.0
-- Dumped by pg_dump version 9.5.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: authors; Type: TABLE; Schema: public; Owner: michael
--

CREATE TABLE authors (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE authors OWNER TO michael;

--
-- Name: authors_books; Type: TABLE; Schema: public; Owner: michael
--

CREATE TABLE authors_books (
    id integer NOT NULL,
    author_id integer,
    book_id integer
);


ALTER TABLE authors_books OWNER TO michael;

--
-- Name: authors_books_id_seq; Type: SEQUENCE; Schema: public; Owner: michael
--

CREATE SEQUENCE authors_books_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE authors_books_id_seq OWNER TO michael;

--
-- Name: authors_books_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: michael
--

ALTER SEQUENCE authors_books_id_seq OWNED BY authors_books.id;


--
-- Name: authors_id_seq; Type: SEQUENCE; Schema: public; Owner: michael
--

CREATE SEQUENCE authors_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE authors_id_seq OWNER TO michael;

--
-- Name: authors_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: michael
--

ALTER SEQUENCE authors_id_seq OWNED BY authors.id;


--
-- Name: books; Type: TABLE; Schema: public; Owner: michael
--

CREATE TABLE books (
    id integer NOT NULL,
    title character varying,
    ischeckedout boolean
);


ALTER TABLE books OWNER TO michael;

--
-- Name: books_id_seq; Type: SEQUENCE; Schema: public; Owner: michael
--

CREATE SEQUENCE books_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE books_id_seq OWNER TO michael;

--
-- Name: books_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: michael
--

ALTER SEQUENCE books_id_seq OWNED BY books.id;


--
-- Name: copies; Type: TABLE; Schema: public; Owner: michael
--

CREATE TABLE copies (
    id integer NOT NULL,
    count integer,
    book_id integer
);


ALTER TABLE copies OWNER TO michael;

--
-- Name: copies_id_seq; Type: SEQUENCE; Schema: public; Owner: michael
--

CREATE SEQUENCE copies_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE copies_id_seq OWNER TO michael;

--
-- Name: copies_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: michael
--

ALTER SEQUENCE copies_id_seq OWNED BY copies.id;


--
-- Name: patrons; Type: TABLE; Schema: public; Owner: michael
--

CREATE TABLE patrons (
    id integer NOT NULL,
    first_name character varying,
    last_name character varying
);


ALTER TABLE patrons OWNER TO michael;

--
-- Name: patrons_id_seq; Type: SEQUENCE; Schema: public; Owner: michael
--

CREATE SEQUENCE patrons_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patrons_id_seq OWNER TO michael;

--
-- Name: patrons_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: michael
--

ALTER SEQUENCE patrons_id_seq OWNED BY patrons.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: michael
--

ALTER TABLE ONLY authors ALTER COLUMN id SET DEFAULT nextval('authors_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: michael
--

ALTER TABLE ONLY authors_books ALTER COLUMN id SET DEFAULT nextval('authors_books_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: michael
--

ALTER TABLE ONLY books ALTER COLUMN id SET DEFAULT nextval('books_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: michael
--

ALTER TABLE ONLY copies ALTER COLUMN id SET DEFAULT nextval('copies_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: michael
--

ALTER TABLE ONLY patrons ALTER COLUMN id SET DEFAULT nextval('patrons_id_seq'::regclass);


--
-- Data for Name: authors; Type: TABLE DATA; Schema: public; Owner: michael
--

COPY authors (id, name) FROM stdin;
7	JK Rowling
8	JRR Tolkien
\.


--
-- Data for Name: authors_books; Type: TABLE DATA; Schema: public; Owner: michael
--

COPY authors_books (id, author_id, book_id) FROM stdin;
7	7	16
8	8	17
\.


--
-- Name: authors_books_id_seq; Type: SEQUENCE SET; Schema: public; Owner: michael
--

SELECT pg_catalog.setval('authors_books_id_seq', 8, true);


--
-- Name: authors_id_seq; Type: SEQUENCE SET; Schema: public; Owner: michael
--

SELECT pg_catalog.setval('authors_id_seq', 8, true);


--
-- Data for Name: books; Type: TABLE DATA; Schema: public; Owner: michael
--

COPY books (id, title, ischeckedout) FROM stdin;
16	Harry Potter	f
17	Lord of the rings	f
\.


--
-- Name: books_id_seq; Type: SEQUENCE SET; Schema: public; Owner: michael
--

SELECT pg_catalog.setval('books_id_seq', 17, true);


--
-- Data for Name: copies; Type: TABLE DATA; Schema: public; Owner: michael
--

COPY copies (id, count, book_id) FROM stdin;
2	31	16
\.


--
-- Name: copies_id_seq; Type: SEQUENCE SET; Schema: public; Owner: michael
--

SELECT pg_catalog.setval('copies_id_seq', 2, true);


--
-- Data for Name: patrons; Type: TABLE DATA; Schema: public; Owner: michael
--

COPY patrons (id, first_name, last_name) FROM stdin;
\.


--
-- Name: patrons_id_seq; Type: SEQUENCE SET; Schema: public; Owner: michael
--

SELECT pg_catalog.setval('patrons_id_seq', 1, false);


--
-- Name: authors_books_pkey; Type: CONSTRAINT; Schema: public; Owner: michael
--

ALTER TABLE ONLY authors_books
    ADD CONSTRAINT authors_books_pkey PRIMARY KEY (id);


--
-- Name: authors_pkey; Type: CONSTRAINT; Schema: public; Owner: michael
--

ALTER TABLE ONLY authors
    ADD CONSTRAINT authors_pkey PRIMARY KEY (id);


--
-- Name: books_pkey; Type: CONSTRAINT; Schema: public; Owner: michael
--

ALTER TABLE ONLY books
    ADD CONSTRAINT books_pkey PRIMARY KEY (id);


--
-- Name: copies_pkey; Type: CONSTRAINT; Schema: public; Owner: michael
--

ALTER TABLE ONLY copies
    ADD CONSTRAINT copies_pkey PRIMARY KEY (id);


--
-- Name: patrons_pkey; Type: CONSTRAINT; Schema: public; Owner: michael
--

ALTER TABLE ONLY patrons
    ADD CONSTRAINT patrons_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: michael
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM michael;
GRANT ALL ON SCHEMA public TO michael;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

