CREATE SEQUENCE seq_ids MINVALUE 1000; /*1 till 1000 is reserved for test data*/

CREATE TABLE "user"(
	id BIGSERIAL PRIMARY KEY,
	email VARCHAR(254) UNIQUE NOT NULL,
	first_name VARCHAR(255) NOT NULL,
	second_name VARCHAR(255) NOT NULL,
	last_name VARCHAR(255) NOT NULL,
	password VARCHAR(60) NOT NULL,
	is_active BOOLEAN NOT NULL DEFAULT TRUE  
);

CREATE TABLE application_form(
	user_id BIGINT PRIMARY KEY REFERENCES "user"(id) ON DELETE CASCADE,
	photo_scope TEXT NOT NULL 
);

CREATE TABLE status(
	id BIGINT PRIMARY KEY DEFAULT nextval('seq_ids'),
	name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE role(
	id BIGSERIAL PRIMARY KEY,
	role VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE user_role(
	user_id BIGINT NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
	role_id BIGINT NOT NULL REFERENCES role(id) ON DELETE RESTRICT,
	PRIMARY KEY(user_id, role_id)
);

CREATE TABLE project(
	id BIGINT PRIMARY KEY DEFAULT nextval('seq_ids'),
	name VARCHAR(255) NOT NULL,
	description TEXT DEFAULT NULL,
	start_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	end_date TIMESTAMP NOT NULL,
	is_completed BOOLEAN NOT NULL DEFAULT FALSE,
	chief_mentor_id BIGINT NOT NULL REFERENCES "user"(id) ON DELETE RESTRICT
);

CREATE TABLE team(
	id BIGINT PRIMARY KEY DEFAULT nextval('seq_ids'),
	name VARCHAR(255) NOT NULL,
	project_id BIGINT NOT NULL REFERENCES project(id) ON DELETE CASCADE
);

CREATE TABLE user_team(
	user_id BIGINT NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
	team_id BIGINT NOT NULL REFERENCES team(id) ON DELETE CASCADE,
	status_id BIGINT DEFAULT NULL REFERENCES status(id) ON DELETE RESTRICT,
	comment VARCHAR(255) DEFAULT NULL,
	PRIMARY KEY(user_id, team_id)
);

CREATE TABLE meeting(
	id BIGINT PRIMARY KEY DEFAULT nextval('seq_ids'),
	topic VARCHAR(255) NOT NULL,
	start_date TIMESTAMP NOT NULL,
	team_id BIGINT NOT NULL REFERENCES team(id) ON DELETE CASCADE
);

CREATE TABLE presence(
	user_id BIGINT NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
	meeting_id BIGINT NOT NULL REFERENCES meeting(id) ON DELETE CASCADE,
	PRIMARY KEY(user_id, meeting_id)
);

CREATE TABLE meeting_feedback(
	id BIGINT PRIMARY KEY DEFAULT nextval('seq_ids'),
	summary VARCHAR(255) DEFAULT NULL,
	student_id BIGINT NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
	meeting_id BIGINT NOT NULL REFERENCES meeting(id) ON DELETE CASCADE,
	author_id BIGINT NOT NULL REFERENCES "user"(id) ON DELETE RESTRICT
);

CREATE TABLE hr_feedback(
	id BIGINT PRIMARY KEY DEFAULT nextval('seq_ids'),
	topic VARCHAR(255) NOT NULL,
	summary TEXT DEFAULT NULL,
	student_id BIGINT NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
	added_by_id BIGINT NOT NULL REFERENCES "user"(id) ON DELETE RESTRICT,
	author_id BIGINT NOT NULL REFERENCES "user"(id) ON DELETE RESTRICT
);

CREATE TABLE trait_category(
	id BIGINT PRIMARY KEY DEFAULT nextval('seq_ids'),
	name VARCHAR(255) NOT NULL
);

CREATE TABLE trait(
	id BIGINT PRIMARY KEY DEFAULT nextval('seq_ids'),
	name VARCHAR(255) NOT NULL,
	category_id BIGINT NOT NULL REFERENCES trait_category(id) ON DELETE CASCADE
);


CREATE TABLE trait_feedback(
	id BIGINT PRIMARY KEY DEFAULT nextval('seq_ids'),
	score SMALLINT NOT NULL,
	comment VARCHAR(255) DEFAULT NULL,
	trait_id BIGINT NOT NULL REFERENCES trait(id) ON DELETE RESTRICT,
	feedback_id BIGINT NOT NULL /*REFERENCES to meeting_feedback(id) or hr_feedback(id)*/
);

CREATE TABLE trait_project(
	trait_id BIGINT NOT NULL REFERENCES trait(id) ON DELETE RESTRICT,
	project_id BIGINT NOT NULL REFERENCES project(id) ON DELETE CASCADE,
	PRIMARY KEY(trait_id, project_id)
);

CREATE TABLE file(
	id BIGINT PRIMARY KEY DEFAULT nextval('seq_ids'),
	path TEXT NOT NULL,
	appended_to_id BIGINT NOT NULL /*REFERENCES to project(id) or team(id)*/
);

 /*select 'DROP TABLE "' || tablename || '" CASCADE;' 
  from pg_tables
 where schemaname = 'public'; */
 /*DROP SCRIPT

 DROP TABLE "project" CASCADE;
 DROP TABLE "role" CASCADE;
 DROP TABLE "user_role" CASCADE;
 DROP TABLE "status" CASCADE;
 DROP TABLE "meeting" CASCADE;
 DROP TABLE "user" CASCADE;
 DROP TABLE "meeting_feedback" CASCADE;
 DROP TABLE "user_team" CASCADE;
 DROP TABLE "team" CASCADE;
 DROP TABLE "hr_feedback" CASCADE;
 DROP TABLE "presentce" CASCADE;
 DROP TABLE "trait_category" CASCADE;
 DROP TABLE "trait_feedback" CASCADE;
 DROP TABLE "trait" CASCADE;
 DROP TABLE "trait_project" CASCADE;
 DROP TABLE "file" CASCADE;
 DROP TABLE "application_form" CASCADE;
 DROP SEQUENCE "seq_ids";

  */
