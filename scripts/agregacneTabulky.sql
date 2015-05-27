/*
	Proces naplnenia STAR_mesiace_roky unikatnymi datumami.
	Zavislost: Pred spustenim procesu musi byt naplnena tabulka star_pocet_issue.
	Cas: 0,057 sec, 63 rows inserted.
*/
INSERT INTO STAR_mesiace_roky(rok, mesiac) 
SELECT DISTINCT cas.rok, cas.mesiac FROM star_pocet_issue stat
	JOIN star_datum cas ON stat.time_id = cas.id;

/*
	Proces naplnenia star_pocet_issue_za_mesiac teda celkovy pocet issue za mesiac.
	Za dany mesiac sa urcuje aj lokalne minimum a maximum.
	Zavislost: Pred spustenim musi byt naplnena dimenzia STAR_mesiace_roky a fakt star_pocet_issue.
	Cas: 57sec,  107  rows inserted
*/
CREATE OR REPLACE PROCEDURE naplnPocetIsuseZaMesiac
IS
BEGIN
    FOR mesiace_roky IN (
		SELECT * FROM STAR_mesiace_roky
	) LOOP
		INSERT INTO star_pocet_issue_za_mesiac(mesiace_roky_id, minimum_v_mesiaci, maximum_v_mesiaci, pocet_issue, povod_id) (
			SELECT 	mesiace_roky.id as mesiace_roky_id, 
					MIN(num_issues) as minimum_v_mesiaci,
					MAX(num_issues) as maximum_v_mesiaci,
					SUM(num_issues) as pocet_issue, 
					povod_id 
			FROM star_pocet_issue stat
				JOIN star_datum cas ON stat.time_id = cas.id
			WHERE cas.mesiac = mesiace_roky.mesiac AND cas.rok = mesiace_roky.rok
			GROUP BY povod_id
		);
	END LOOP;
END;
/

/*
	Proces naplnenia STAR_roky teda vsetkych moznych rokov, pre ktore mame zaznam.
	Zavislost: Pred procesom musi byt spusteny proces pre naplnenie STAR_mesiace_roky.
	Cas: 0,02sec, 8 rows inserted.
*/
INSERT INTO STAR_roky(rok) 
SELECT DISTINCT rok FROM STAR_mesiace_roky;

/*
	Proces naplnenia poctu vsetkych issues za roky.
	Za dany mesiac sa urcuje aj lokalne minimum a maximum.
	Zavislost: Pred spustenim procesu musi byt spusteny proces pre naplnenie STAR_roky a star_pocet_issue_za_mesiac.
	Cas: 0,053sec, 13  rows inserted
*/
CREATE OR REPLACE PROCEDURE naplnPocetIsuseZaRoky
IS
BEGIN
    FOR roky IN (
		SELECT * FROM STAR_roky
	) LOOP
		INSERT INTO star_pocet_issue_za_rok(rok_id, pocet_issue, povod_id, minimum_v_roku, maximum_v_roku) (
			SELECT 
				roky.id as rok_id,
				SUM(pocet_issue) as pocet_issue,
				povod_id, 
				MIN(pocet_issue) as minimum_v_roku,
				MAX(pocet_issue) as maximum_v_roku
			FROM star_pocet_issue_za_mesiac stat
				JOIN STAR_mesiace_roky cas ON stat.mesiace_roky_id = cas.id
			WHERE cas.rok = roky.rok
			GROUP BY povod_id
		);
	END LOOP;
END;
/
