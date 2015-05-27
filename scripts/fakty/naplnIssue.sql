/*
	Proces naplnenia issues z 3 systemov.

	Zavislost: Pred procesom musia byt naplnene data v star_respository, star_datum, star_jazyk
	Statistika:
	cas 10,6
	15 547 rows inserted.
	1 520 rows inserted.
	Po pridani indexu PK STAR_Issue 14sec
*/
-- Pre git
INSERT INTO STAR_Issue(title, body, datum_id, povod_id, repository_id, jazyk_id) (
	SELECT 	
			CAST(issue.title as VARCHAR2(250)) as title,
			issue.body as body, -- NCLOB
			datum.id as datum_id,
			1 as "povod_id",
			repn.id as repository_id,
			jazyk.id as jazyk_id
		FROM pdt.GIT_ISSUES issue
	JOIN pdt.git_repository rep ON issue.repository_id = rep.id
	LEFT JOIN star_repository repn ON repn.name LIKE CAST(rep.name as VARCHAR2(250))
	LEFT JOIN star_jazyk jazyk ON jazyk.meno LIKE CAST(rep.language as VARCHAR2(250))
	LEFT JOIN star_datum datum ON 
		EXTRACT(year FROM issue.created_at) = datum.rok 
		AND EXTRACT(month FROM issue.created_at) = datum.mesiac
		AND EXTRACT(day FROM issue.created_at) = datum.den		
);

-- Pre bit buket
INSERT INTO STAR_Issue(title, body, datum_id, povod_id, repository_id, jazyk_id) (
	SELECT 	
			CAST(issue.title as VARCHAR2(250)) as title,
			TO_CLOB(issue.content) as body,
			datum.id as datum_id,
			2 as "povod_id",
			repn.id as repository_id,
			jazyk.id as jazyk_id
		FROM pdt.BIT_ISSUES issue
	JOIN pdt.bit_repositories rep ON issue.repository_id = rep.id
	LEFT JOIN star_repository repn ON repn.name LIKE CAST(rep.name as VARCHAR2(250))
	LEFT JOIN star_jazyk jazyk ON jazyk.meno LIKE CAST(rep.language as VARCHAR2(250))
	LEFT JOIN star_datum datum ON 
	-- Bitbucket ma ako datum VARCHAR ... popici ...
		EXTRACT(year FROM to_date(substr(issue.created_on,1,10), 'yyyy-mm-dd') ) = datum.rok 
		AND EXTRACT(month FROM to_date(substr(issue.created_on,1,10), 'yyyy-mm-dd' ) ) = datum.mesiac
		AND EXTRACT(day FROM to_date(substr(issue.created_on,1,10), 'yyyy-mm-dd' ) ) = datum.den
);
	

-- Stack overflow nema repository ani issue takze tuto cast ignorujeme

