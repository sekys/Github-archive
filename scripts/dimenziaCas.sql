/*
	Proces naplnenia dimenzie star_datum.
	Tzv, mnohe procesy spracuju cas, tu tento cas predspracujeme.
	Teda ulozime len unikatne hodnoty casov zo vsetkych systemov.
	V ostatnych procesoch potom ukladame len kluce.
	
	Zdroj dat : repozitare, eventy a issues - cas predstavuje datum vytvorenia

	Zavislost na procesoch: ziadna
	Statistiky: 0,8sec 
				832 rows inserted.
				264 rows inserted.
				706 rows inserted.
				84 rows inserted.
				0 rows inserted.
				4 rows inserted.
	Po pridani indexu 3,8sec.
*/
INSERT INTO STAR_Datum(rok, mesiac, den) 
(

	SELECT DISTINCT
		EXTRACT(year FROM datum.created_at) as rok, 
		EXTRACT(month FROM datum.created_at) as mesiac, 
		EXTRACT(day FROM datum.created_at) as den 
	FROM PDT.git_issues datum
	WHERE NOT EXISTS
	(SELECT ulozene.rok, ulozene.mesiac, ulozene.den  FROM STAR_Datum ulozene
		WHERE 
			EXTRACT(year FROM datum.created_at) = ulozene.rok
		AND 	EXTRACT(month FROM datum.created_at) = ulozene.mesiac
		AND EXTRACT(day FROM datum.created_at) = ulozene.den
	)
);

INSERT INTO STAR_Datum(rok, mesiac, den) 
(

	SELECT DISTINCT
		EXTRACT(year FROM to_date(substr(datum.created_on,1,10), 'yyyy-mm-dd') )   as rok, 
		EXTRACT(month FROM to_date(substr(datum.created_on,1,10), 'yyyy-mm-dd' ) ) as mesiac, 
	EXTRACT(day FROM to_date(substr(datum.created_on,1,10), 'yyyy-mm-dd' ) )  as den 
	FROM PDT.BIT_issues datum
	WHERE NOT EXISTS
	(SELECT ulozene.rok, ulozene.mesiac, ulozene.den  FROM STAR_Datum ulozene
		WHERE 
			EXTRACT(year FROM to_date(substr(datum.created_on,1,10), 'yyyy-mm-dd') ) = ulozene.rok
		AND 	EXTRACT(month FROM to_date(substr(datum.created_on,1,10), 'yyyy-mm-dd' ) ) = ulozene.mesiac
		AND EXTRACT(day FROM to_date(substr(datum.created_on,1,10), 'yyyy-mm-dd' ) )  = ulozene.den
	)
  
);

INSERT INTO STAR_Datum(rok, mesiac, den) 
(

	SELECT DISTINCT
		EXTRACT(year FROM datum.created_at) as rok, 
		EXTRACT(month FROM datum.created_at) as mesiac, 
		EXTRACT(day FROM datum.created_at) as den 
	FROM PDT.git_repository datum
	WHERE NOT EXISTS
	(SELECT ulozene.rok, ulozene.mesiac, ulozene.den  FROM STAR_Datum ulozene
		WHERE 
			EXTRACT(year FROM datum.created_at) = ulozene.rok
		AND 	EXTRACT(month FROM datum.created_at) = ulozene.mesiac
		AND EXTRACT(day FROM datum.created_at) = ulozene.den
	)
);

INSERT INTO STAR_Datum(rok, mesiac, den) 
(

	SELECT DISTINCT
		EXTRACT(year FROM to_date(substr(datum.created_on,1,10), 'yyyy-mm-dd') )   as rok, 
		EXTRACT(month FROM to_date(substr(datum.created_on,1,10), 'yyyy-mm-dd' ) ) as mesiac, 
	EXTRACT(day FROM to_date(substr(datum.created_on,1,10), 'yyyy-mm-dd' ) )  as den 
	FROM PDT.BIT_repositories datum
	WHERE NOT EXISTS
	(SELECT ulozene.rok, ulozene.mesiac, ulozene.den  FROM STAR_Datum ulozene
		WHERE 
			EXTRACT(year FROM to_date(substr(datum.created_on,1,10), 'yyyy-mm-dd') ) = ulozene.rok
		AND 	EXTRACT(month FROM to_date(substr(datum.created_on,1,10), 'yyyy-mm-dd' ) ) = ulozene.mesiac
		AND EXTRACT(day FROM to_date(substr(datum.created_on,1,10), 'yyyy-mm-dd' ) )  = ulozene.den
	)
  
  
);

INSERT INTO STAR_Datum(rok, mesiac, den) 
(

	SELECT DISTINCT
		EXTRACT(year FROM datum.created_at) as rok, 
		EXTRACT(month FROM datum.created_at) as mesiac, 
		EXTRACT(day FROM datum.created_at) as den 
	FROM PDT.git_events datum
	WHERE NOT EXISTS
	(SELECT ulozene.rok, ulozene.mesiac, ulozene.den  FROM STAR_Datum ulozene
		WHERE 
			EXTRACT(year FROM datum.created_at) = ulozene.rok
		AND 	EXTRACT(month FROM datum.created_at) = ulozene.mesiac
		AND EXTRACT(day FROM datum.created_at) = ulozene.den
	)
);

INSERT INTO STAR_Datum(rok, mesiac, den) 
(

		SELECT DISTINCT
		EXTRACT(year FROM to_date(substr(datum.created_on,1,10), 'yyyy-mm-dd') )   as rok, 
		EXTRACT(month FROM to_date(substr(datum.created_on,1,10), 'yyyy-mm-dd' ) ) as mesiac, 
	EXTRACT(day FROM to_date(substr(datum.created_on,1,10), 'yyyy-mm-dd' ) )  as den 
	FROM PDT.BIT_events datum
	WHERE NOT EXISTS
	(SELECT ulozene.rok, ulozene.mesiac, ulozene.den  FROM STAR_Datum ulozene
		WHERE 
			EXTRACT(year FROM to_date(substr(datum.created_on,1,10), 'yyyy-mm-dd') ) = ulozene.rok
		AND 	EXTRACT(month FROM to_date(substr(datum.created_on,1,10), 'yyyy-mm-dd' ) ) = ulozene.mesiac
		AND EXTRACT(day FROM to_date(substr(datum.created_on,1,10), 'yyyy-mm-dd' ) )  = ulozene.den
	)
);



