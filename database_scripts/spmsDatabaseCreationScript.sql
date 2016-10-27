CREATE SEQUENCE seq_ids MINVALUE 1000; /*1 till 1000 is reserved for test data*/

CREATE TABLE "user"(
	id BIGSERIAL PRIMARY KEY,
	email VARCHAR(254) UNIQUE NOT NULL,
	first_name VARCHAR(255),
	second_name VARCHAR(255),
	last_name VARCHAR(255),
	password VARCHAR(60) NOT NULL,
	is_active BOOLEAN NOT NULL DEFAULT FALSE  
);

CREATE TABLE application_form(
	user_id BIGINT PRIMARY KEY REFERENCES "user"(id) ON DELETE CASCADE,
	photo_scope TEXT NOT NULL
);

CREATE TABLE status(
	id BIGINT PRIMARY KEY DEFAULT nextval('seq_ids'),
	name VARCHAR(255) NOT NULL
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
	is_compleated BOOLEAN NOT NULL DEFAULT FALSE,
	chief_user_id BIGINT NOT NULL REFERENCES "user"(id) ON DELETE RESTRICT
);

CREATE TABLE team(
	id BIGINT PRIMARY KEY DEFAULT nextval('seq_ids'),
	name VARCHAR(255) NOT NULL,
	project_id BIGINT NOT NULL REFERENCES project(id) ON DELETE CASCADE
);

CREATE TABLE user_team(
	user_id BIGINT NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
	team_id BIGINT NOT NULL REFERENCES team(id) ON DELETE CASCADE,
	status_id BIGINT NOT NULL REFERENCES status(id) ON DELETE RESTRICT,
	comment TEXT DEFAULT NULL,
	PRIMARY KEY(user_id, team_id)
);

CREATE TABLE meeting(
	id BIGINT PRIMARY KEY DEFAULT nextval('seq_ids'),
	topic VARCHAR(255) NOT NULL,
	start_date TIMESTAMP NOT NULL,
	team_id BIGINT NOT NULL REFERENCES team(id) ON DELETE CASCADE
);

CREATE TABLE presentce(
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
	topic VARCHAR(255) DEFAULT NULL,
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
	project_id BIGINT REFERENCES project(id) ON DELETE CASCADE,
	PRIMARY KEY(trait_id, project_id)
);

CREATE TABLE file(
	id BIGINT PRIMARY KEY DEFAULT nextval('seq_ids'),
	appended_to_id BIGINT NOT NULL /*REFERENCES to project(id) or team(id)*/
);

 /*select 'DROP TABLE "' || tablename || '" CASCADE;' 
  from pg_tables
 where schemaname = 'public'; */