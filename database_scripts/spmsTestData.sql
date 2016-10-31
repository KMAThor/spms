/*user*/
INSERT INTO "user" VALUES (0,'admin@admin.com',
							'Thor','Ivanovych', 'Ivanov', /*Password is adminpassword (crypted with B-crypt)*/
							'$2a$06$6my9F/YPq8K/NWMPtbeWO.P8xjEPwJj04bRSkUbG0EIeSBKkkjlym',
							TRUE);

INSERT INTO "user" VALUES (1,'mentor@mentor.com',
							'Odin', 'Semenovych', 'Semenov', /*Password is mentorpassword (crypted with B-crypt)*/
							'$2a$06$hdjnRjWPY1gdK6JMBHOeQOemT0TAp2kCblLndi6oGWwbULaWnGslq',
							TRUE);

INSERT INTO "user" VALUES (2,'mentor2@mentor.com',
							'Loki', 'Petrovych', 'Petrov', /*Password is mentorpassword (crypted with B-crypt)*/
							'$2a$06$hdjnRjWPY1gdK6JMBHOeQOemT0TAp2kCblLndi6oGWwbULaWnGslq',
							TRUE);

INSERT INTO "user" VALUES (3,'hr@hr.com',
							'Erik', 'Андрійович', 'Selvig', /*Password is hrpassword (crypted with B-crypt)*/
							'$2a$06$eNw8LxKYHeAGRL2FJRjG2eOnbH9iOs3HUI0Rhwf39huLrP7GZyFuW',
							TRUE);

INSERT INTO "user" VALUES (4,'student@student.com',
							'Андрій', 'Андрійович', 'Андрюхов', /*Password is sudentpassword (crypted with B-crypt)*/
							'$2a$06$egLA1gY3hbXDRQd0rq/ERuXvq/fvVcx/mLGxqOViMRMD.p1LL1Uo2',
							TRUE);

/*role*/
INSERT INTO role VALUES (0,'admin');
INSERT INTO role VALUES (1,'mentor');
INSERT INTO role VALUES (2,'hr');
INSERT INTO role VALUES (3,'student');

/*user_role*/
INSERT INTO user_role VALUES(0,0);
INSERT INTO user_role VALUES(1,1);
INSERT INTO user_role VALUES(2,1);
INSERT INTO user_role VALUES(3,2);
INSERT INTO user_role VALUES(4,3);
/*prject*/
INSERT INTO project VALUES (4, 'KMA 2016',
								DEFAULT, DEFAULT,
								'2016-12-1 00:00:00', DEFAULT, 1);

INSERT INTO team VALUES (5, 'Thor', 4);
INSERT INTO user_team VALUES (4, 5);
INSERT INTO user_team VALUES (1, 5);
INSERT INTO user_team VALUES (2, 5);

INSERT INTO meeting VALUES (6, 'Docs Review', '2016-11-1 15:00:00', 5);
INSERT INTO presence VALUES (4, 6);

INSERT INTO meeting_feedback VALUES (7, 'Well Done', 4, 6, 2);

INSERT INTO trait_category VALUES (8, 'Soft skills');
INSERT INTO trait VALUES (9, 'Communication skills', 8);
