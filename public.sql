/*
 Navicat Premium Data Transfer

 Source Server         : postgresql
 Source Server Type    : PostgreSQL
 Source Server Version : 150004 (150004)
 Source Host           : localhost:5432
 Source Catalog        : library
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 150004 (150004)
 File Encoding         : 65001

 Date: 07/10/2023 00:06:07
*/


-- ----------------------------
-- Sequence structure for books_bid_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."books_bid_seq";
CREATE SEQUENCE "public"."books_bid_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for users_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."users_id_seq";
CREATE SEQUENCE "public"."users_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Table structure for books
-- ----------------------------
DROP TABLE IF EXISTS "public"."books";
CREATE TABLE "public"."books" (
  "id" int4 NOT NULL DEFAULT nextval('books_bid_seq'::regclass),
  "book_name" varchar(50) COLLATE "pg_catalog"."default",
  "author" varchar(20) COLLATE "pg_catalog"."default",
  "year" int4
)
;

-- ----------------------------
-- Records of books
-- ----------------------------
INSERT INTO "public"."books" VALUES (7, 'Tutorial Database', 'ego', 2023);
INSERT INTO "public"."books" VALUES (8, 'Tutorial java', 'Faiz', 2023);
INSERT INTO "public"."books" VALUES (9, 'Tutorial C++', 'Choir', 2022);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS "public"."users";
CREATE TABLE "public"."users" (
  "id" int4 NOT NULL DEFAULT nextval('users_id_seq'::regclass),
  "username" text COLLATE "pg_catalog"."default" NOT NULL,
  "password" text COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO "public"."users" VALUES (1, 'admin', 'admin');

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."books_bid_seq"
OWNED BY "public"."books"."id";
SELECT setval('"public"."books_bid_seq"', 9, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."users_id_seq"
OWNED BY "public"."users"."id";
SELECT setval('"public"."users_id_seq"', 1, true);

-- ----------------------------
-- Primary Key structure for table books
-- ----------------------------
ALTER TABLE "public"."books" ADD CONSTRAINT "books_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table users
-- ----------------------------
ALTER TABLE "public"."users" ADD CONSTRAINT "users_pkey" PRIMARY KEY ("id");
