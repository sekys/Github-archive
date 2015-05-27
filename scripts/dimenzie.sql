/*
	Proces naplnenia dimenzie STAR_POVOD.
	Toto je dimenzia, ktora sa nemeni v case.
	
	Zavislost na procesoch: ziadna
	Statistiky: 0,1 sec
*/
INSERT INTO STAR_POVOD (NAME) VALUES ('GithubArchive');
INSERT INTO STAR_POVOD (NAME) VALUES ('Bitbucket');
INSERT INTO STAR_POVOD (NAME) VALUES ('StackOverflow');


/*
	Proces naplnenia dimenzie repozitarov.
	V urcitej analyze by sme toto mohli chapat ako fakt, mys sme vsak repozitare brali ako dimenziu.
	Preto obsahuje unikatne hodnty podla nazvu.
	Je to dimenzia meniaca sa v case.
	
	Zavislost na procesoch: ziadna
	Poznamka: niekedy je owner prazdny
	Statistiky: 103sec, 364 821 rows inserted.
	Po pridani indexu 155sec.
	Po vypnuti indexu 126sec.
*/
alter index STAR_Repository__IDXv1 unusable;
INSERT INTO STAR_Repository(name, owner) 
(
	-- Spoj vsetky tabulky
	SELECT DISTINCT CAST(name as VARCHAR2(250)) as name, CAST(owner as VARCHAR2(250)) as owner 
        FROM PDT.git_repository
	UNION
		SELECT DISTINCT CAST(name as VARCHAR2(250)) as name, CAST(u.username as VARCHAR2(250)) as owner 
            FROM PDT.bit_repositories rep
		    JOIN PDT.bit_users u ON rep.owner = u.id
	UNION
	SELECT DISTINCT CAST(stack.title as VARCHAR2(250)) as name, CAST(u.displayname as VARCHAR2(250))  
        FROM PDT.posts stack
		JOIN PDT.users u ON stack.owneruserid = u.id
);
alter index STAR_Repository__IDXv1 rebuild;

/*
	Proces naplnenia dimenzie jazykov.
	
	Zavislost na procesoch: ziadna
	Statistiky: 0,052sec, 125 rows inserted.
*/
INSERT INTO STAR_jazyk(meno) 
(
    SELECT DISTINCT CAST(b.language as VARCHAR2(250)) as meno
        FROM PDT.git_repository b
    UNION
    SELECT DISTINCT CAST(g.language as VARCHAR2(250)) as meno 
        FROM PDT.bit_repositories g    
	-- Stackoverflow nema language
);

/*
	Proces naplnenia dimenzie timu ( beru sa len unikatne nazvy teamov).
	
	Zavislost na procesoch: ziadna
	Statistiky: 1,3sec, 3 019 rows inserted.
*/
INSERT INTO STAR_Tim(name) 
(
    SELECT DISTINCT CAST(b.username as VARCHAR2(250)) as name
        FROM PDT.bit_users b
        WHERE b.is_team = 1
    UNION
    SELECT DISTINCT CAST(g.login as VARCHAR2(250)) as name 
        FROM PDT.git_orgs g    
     UNION
    SELECT DISTINCT CAST(s.name as VARCHAR2(250)) as name 
        FROM PDT.badges s 
);