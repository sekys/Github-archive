SET DEFINE OFF;
PROMPT Creating User Emulation ...
CREATE USER Emulation IDENTIFIED BY Emulation DEFAULT TABLESPACE USERS TEMPORARY TABLESPACE TEMP;
GRANT CREATE SESSION, RESOURCE, CREATE VIEW, CREATE MATERIALIZED VIEW,  CREATE SYNONYM,CREATE PUBLIC SYNONYM  TO Emulation;
SET SCAN OFF;
PROMPT Creating User pdt ...
CREATE USER pdt IDENTIFIED BY pdt DEFAULT TABLESPACE USERS TEMPORARY TABLESPACE TEMP;
GRANT CREATE SESSION, RESOURCE, CREATE VIEW, CREATE MATERIALIZED VIEW, CREATE SYNONYM TO pdt;
connect pdt/pdt;

-- DROP TABLE comments CASCADE CONSTRAINTS;


PROMPT Creating Table comments ...
CREATE TABLE comments (
  id NUMBER(10,0) NOT NULL,
  url CLOB,
  html_url CLOB,
  body CLOB,
  created_at DATE,
  updated_at DATE,
  User_ NUMBER(10,0) NOT NULL
);


COMMENT ON COLUMN comments.User_ IS 'ORIGINAL NAME:User'
;

PROMPT Creating Primary Key Constraint PRIMARY on table comments ... 
ALTER TABLE comments
ADD CONSTRAINT PRIMARY PRIMARY KEY
(
  id
)
ENABLE
;
PROMPT Creating Index fk_Comments_Members1_idx on comments ...
CREATE INDEX fk_Comments_Members1_idx ON comments
(
  User_
) 
;

-- DROP TABLE events CASCADE CONSTRAINTS;


PROMPT Creating Table events ...
CREATE TABLE events (
  id NUMBER(10,0) NOT NULL,
  url CLOB,
  event CLOB,
  commit_id CLOB,
  created_at DATE,
  actor NUMBER(10,0) NOT NULL,
  Issue NUMBER(10,0) NOT NULL
);


PROMPT Creating Primary Key Constraint PRIMARY_1 on table events ... 
ALTER TABLE events
ADD CONSTRAINT PRIMARY_1 PRIMARY KEY
(
  id
)
ENABLE
;
PROMPT Creating Index fk_Events_Members1_idx on events ...
CREATE INDEX fk_Events_Members1_idx ON events
(
  actor
) 
;
PROMPT Creating Index fk_Events_Issue1_idx on events ...
CREATE INDEX fk_Events_Issue1_idx ON events
(
  Issue
) 
;

-- DROP TABLE issue_has_labels CASCADE CONSTRAINTS;


PROMPT Creating Table issue_has_labels ...
CREATE TABLE issue_has_labels (
  Issue_id NUMBER(10,0) NOT NULL,
  abels_id NUMBER(10,0) NOT NULL
);


COMMENT ON COLUMN issue_has_labels.abels_id IS 'ORIGINAL NAME:Labels_id'
;

PROMPT Creating Primary Key Constraint PRIMARY_2 on table issue_has_labels ... 
ALTER TABLE issue_has_labels
ADD CONSTRAINT PRIMARY_2 PRIMARY KEY
(
  Issue_id,
  abels_id
)
ENABLE
;
PROMPT Creating Index fk_Issue_has_Labels_Labels1_id on issue_has_labels ...
CREATE INDEX fk_Issue_has_Labels_Labels1_id ON issue_has_labels
(
  abels_id
) 
;
PROMPT Creating Index fk_Issue_has_Labels_Issue1_idx on issue_has_labels ...
CREATE INDEX fk_Issue_has_Labels_Issue1_idx ON issue_has_labels
(
  Issue_id
) 
;

-- DROP TABLE issues CASCADE CONSTRAINTS;


PROMPT Creating Table issues ...
CREATE TABLE issues (
  id NUMBER(10,0) NOT NULL,
  html_url CLOB,
  number_ CLOB,
  state CLOB,
  title CLOB,
  body CLOB,
  comments NUMBER(10,0),
  closed_at DATE,
  created_at DATE,
  updated_at DATE,
  User_ NUMBER(10,0) NOT NULL,
  Assignee NUMBER(10,0) NOT NULL,
  Milestone NUMBER(10,0) NOT NULL
);


COMMENT ON COLUMN issues.number_ IS 'ORIGINAL NAME:number'
;

COMMENT ON COLUMN issues.User_ IS 'ORIGINAL NAME:User'
;

PROMPT Creating Primary Key Constraint PRIMARY_7 on table issues ... 
ALTER TABLE issues
ADD CONSTRAINT PRIMARY_7 PRIMARY KEY
(
  id
)
ENABLE
;
PROMPT Creating Index fk_Issue_Members1_idx on issues ...
CREATE INDEX fk_Issue_Members1_idx ON issues
(
  User_
) 
;
PROMPT Creating Index fk_Issue_Members2_idx on issues ...
CREATE INDEX fk_Issue_Members2_idx ON issues
(
  Assignee
) 
;
PROMPT Creating Index fk_Issue_Milestones1_idx on issues ...
CREATE INDEX fk_Issue_Milestones1_idx ON issues
(
  Milestone
) 
;

-- DROP TABLE labels CASCADE CONSTRAINTS;


PROMPT Creating Table labels ...
CREATE TABLE labels (
  id NUMBER(10,0) NOT NULL,
  url CLOB,
  name CLOB,
  color CLOB
);


PROMPT Creating Primary Key Constraint PRIMARY_4 on table labels ... 
ALTER TABLE labels
ADD CONSTRAINT PRIMARY_4 PRIMARY KEY
(
  id
)
ENABLE
;

-- DROP TABLE members CASCADE CONSTRAINTS;


PROMPT Creating Table members ...
CREATE TABLE members (
  id NUMBER(10,0) NOT NULL,
  login CLOB,
  avatar_url CLOB,
  gravatar_id CLOB,
  url CLOB
);


PROMPT Creating Primary Key Constraint PRIMARY_5 on table members ... 
ALTER TABLE members
ADD CONSTRAINT PRIMARY_5 PRIMARY KEY
(
  id
)
ENABLE
;

-- DROP TABLE milestones CASCADE CONSTRAINTS;


PROMPT Creating Table milestones ...
CREATE TABLE milestones (
  id NUMBER(10,0) NOT NULL,
  url CLOB,
  number_ NUMBER(10,0),
  state CLOB,
  title CLOB,
  description CLOB,
  open_issues NUMBER(10,0),
  closed_issues NUMBER(10,0),
  created_at DATE,
  due_on DATE,
  creator NUMBER(10,0) NOT NULL
);


COMMENT ON COLUMN milestones.number_ IS 'ORIGINAL NAME:number'
;

PROMPT Creating Primary Key Constraint PRIMARY_6 on table milestones ... 
ALTER TABLE milestones
ADD CONSTRAINT PRIMARY_6 PRIMARY KEY
(
  id
)
ENABLE
;
PROMPT Creating Index fk_Milestones_Members1_idx on milestones ...
CREATE INDEX fk_Milestones_Members1_idx ON milestones
(
  creator
) 
;

-- DROP TABLE users CASCADE CONSTRAINTS;


PROMPT Creating Table users ...
CREATE TABLE users (
  id NUMBER(10,0) NOT NULL,
  login CLOB NOT NULL,
  avatar_url CLOB NOT NULL,
  gravatar_id NUMBER(10,0),
  url CLOB,
  name CLOB,
  company CLOB,
  blog CLOB,
  location CLOB,
  email CLOB,
  hireable NUMBER(3,0),
  bio CLOB,
  public_repos NUMBER(10,0),
  public_gists NUMBER(10,0),
  followers NUMBER(10,0),
  following NUMBER(10,0),
  html_url CLOB,
  created_at DATE,
  type CLOB
);


PROMPT Creating Primary Key Constraint PRIMARY_3 on table users ... 
ALTER TABLE users
ADD CONSTRAINT PRIMARY_3 PRIMARY KEY
(
  id
)
ENABLE
;

connect pdt/pdt;

PROMPT Creating Foreign Key Constraint fk_Comments_Members1 on table members...
ALTER TABLE comments
ADD CONSTRAINT fk_Comments_Members1 FOREIGN KEY
(
  User_
)
REFERENCES members
(
  id
)
ENABLE
;

PROMPT Creating Foreign Key Constraint fk_Events_Members1 on table members...
ALTER TABLE events
ADD CONSTRAINT fk_Events_Members1 FOREIGN KEY
(
  actor
)
REFERENCES members
(
  id
)
ENABLE
;

PROMPT Creating Foreign Key Constraint fk_Events_Issue1 on table issues...
ALTER TABLE events
ADD CONSTRAINT fk_Events_Issue1 FOREIGN KEY
(
  Issue
)
REFERENCES issues
(
  id
)
ENABLE
;

PROMPT Creating Foreign Key Constraint fk_Issue_has_Labels_Issue1 on table issues...
ALTER TABLE issue_has_labels
ADD CONSTRAINT fk_Issue_has_Labels_Issue1 FOREIGN KEY
(
  Issue_id
)
REFERENCES issues
(
  id
)
ENABLE
;

PROMPT Creating Foreign Key Constraint fk_Issue_has_Labels_Labels1 on table labels...
ALTER TABLE issue_has_labels
ADD CONSTRAINT fk_Issue_has_Labels_Labels1 FOREIGN KEY
(
  abels_id
)
REFERENCES labels
(
  id
)
ENABLE
;

PROMPT Creating Foreign Key Constraint fk_Milestones_Members1 on table members...
ALTER TABLE milestones
ADD CONSTRAINT fk_Milestones_Members1 FOREIGN KEY
(
  creator
)
REFERENCES members
(
  id
)
ENABLE
;

PROMPT Creating Foreign Key Constraint fk_Issue_Members1 on table members...
ALTER TABLE issues
ADD CONSTRAINT fk_Issue_Members1 FOREIGN KEY
(
  User_
)
REFERENCES members
(
  id
)
ENABLE
;

PROMPT Creating Foreign Key Constraint fk_Issue_Members2 on table members...
ALTER TABLE issues
ADD CONSTRAINT fk_Issue_Members2 FOREIGN KEY
(
  Assignee
)
REFERENCES members
(
  id
)
ENABLE
;

PROMPT Creating Foreign Key Constraint fk_Issue_Milestones1 on table milestones...
ALTER TABLE issues
ADD CONSTRAINT fk_Issue_Milestones1 FOREIGN KEY
(
  Milestone
)
REFERENCES milestones
(
  id
)
ENABLE
;

DISCONNECT;
