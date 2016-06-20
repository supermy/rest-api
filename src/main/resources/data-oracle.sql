INSERT INTO users(username,password,enabled)
VALUES ('jamesmo','$2a$10$04TVADrR6/SPLBjsK0N30.Jf5fNjBugSACeGv1S69dZALR7lSov0y', 1);

INSERT INTO users(username,password,enabled)
VALUES ('alex','$2a$10$04TVADrR6/SPLBjsK0N30.Jf5fNjBugSACeGv1S69dZALR7lSov0y', 1);

INSERT INTO user_roles (username, role,user_role_id)
VALUES ('jamesmo', 'ROLE_USER',1);
INSERT INTO user_roles (username, role,user_role_id)
VALUES ('jamesmo', 'ROLE_ADMIN',2);
INSERT INTO user_roles (username, role,user_role_id)
VALUES ('alex', 'ROLE_USER',3);



