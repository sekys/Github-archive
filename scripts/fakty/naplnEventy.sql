/*
	Proces naplnenia events z 3 systemov.

	Zavislost: Pred procesom musia byt naplnene data v star_respository, star_datum
	
	Najprv to bezalo nad 15min ... cena v explain plan 12 286 441.
	Po pridani indexu na datum.rok / mesiac / den a nazov na repozitar 786 000
	
	Bez indexu STAR_Events__IDXv1 1,3 sec
	Po pridani indexu STAR_Events__IDXv1 58sec
	Prikaz na vypnutie indexu nepracuje spravne.

	103 223 rows inserted.
	4 536 rows inserted.
*/

INSERT INTO STAR_Events(creator, description, datum_id, povod_id, repository_id) (
	SELECT 	
			CAST(event.actor as VARCHAR2(250)) as creator,
			event.type as description, -- NCLOB
			datum.id as datum_id,
			1 as "povod_id",
			repn.id as repository_id
		FROM pdt.GIT_EVENTS event
	JOIN pdt.git_repository rep ON event.repository_id = rep.id
	LEFT JOIN star_repository repn ON repn.name LIKE CAST(rep.name as VARCHAR2(250))
	LEFT JOIN star_datum datum ON 
		EXTRACT(year FROM event.created_at) = datum.rok 
		AND EXTRACT(month FROM event.created_at) = datum.mesiac
		AND EXTRACT(day FROM event.created_at) = datum.den			
	WHERE -- inkrementalny zapis
		event.created_at >= TO_DATE('20/JAN/2010','dd/mon/yyyy') 
	-- vsetky datum_id maju id 291 lebo sme stahovali eventy len z toho dna
);


-- Pre bit buket
INSERT INTO STAR_Events(creator, description, datum_id, povod_id, repository_id) (
	SELECT 	
			CAST(usr.username as VARCHAR2(250)) as creator,
			TO_CLOB(event.description) as description,
			datum.id as datum_id,
			2 as "povod_id",
			repn.id as repository_id

		FROM pdt.BIT_EVENTS event
	JOIN pdt.bit_repositories rep ON event.repository_id = rep.id
JOIN PDT.bit_users usr ON event.user_id = usr.id
	LEFT JOIN star_repository repn ON repn.name LIKE CAST(rep.name as VARCHAR2(250))
	LEFT JOIN star_datum datum ON 
	-- Bitbucket ma ako datum VARCHAR ... popici ...
		EXTRACT(year FROM to_date(substr(event.created_on,1,10), 'yyyy-mm-dd') ) = datum.rok 
		AND EXTRACT(month FROM to_date(substr(event.created_on,1,10), 'yyyy-mm-dd' ) ) = datum.mesiac
		AND EXTRACT(day FROM to_date(substr(event.created_on,1,10), 'yyyy-mm-dd' ) ) = datum.den
);


-- Stack overflow nema events ani issue takze tuto cast ignorujeme
