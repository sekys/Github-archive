/*
	Proces naplnenia tabulky pouzivatelmi (iba unikatnymi uctami) z 3 systemov.

	Zavislost: Proces je nezavisli
	Statistika: 21sec, 91 202 rows inserted.
*/
INSERT INTO STAR_Pouzivatelia(meno, avatar, url, povod_id) 
(
    SELECT 
			CAST(login as VARCHAR2(250)) as meno, 
			CAST(avatar_url as VARCHAR2(512)) as avatar,
			CAST(url as VARCHAR2(512)) as url,
			1 as "povod_id"			
        FROM PDT.git_members
	UNION
    SELECT 
			CAST(username as VARCHAR2(250)) as meno,
			CAST(avatar as VARCHAR2(512)) as avatar,
			NULL as url, -- bit_users nema url
			2 as "povod_id"
        FROM PDT.bit_users
	UNION
	SELECT 
			CAST(displayname as VARCHAR2(250)) as meno,
			NULL as avatar,
			CAST(websiteurl as VARCHAR2(512)) as url,
			3 as "povod_id"
		FROM PDT.users	
);