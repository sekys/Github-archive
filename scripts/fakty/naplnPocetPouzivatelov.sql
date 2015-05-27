/*
	Proces naplnenia poctu pouzivatelov pre kazdy system.
	
	Zavislost procesu od inych procesov: ziadna
	Statistika: 0,02 sec
*/
CREATE OR REPLACE PROCEDURE naplnPocetPouzivatelov
IS
   pocet_temp NUMBER;
BEGIN
    SELECT COUNT(*) INTO pocet_temp FROM PDT.git_members;
    INSERT INTO star_pocet_pouzivatelov (pocet_pouzivatelov, povod_id) VALUES (pocet_temp, 1);

    SELECT COUNT(*) INTO pocet_temp FROM PDT.bit_users;
    INSERT INTO star_pocet_pouzivatelov (pocet_pouzivatelov, povod_id) VALUES (pocet_temp, 2);

    SELECT COUNT(*) INTO pocet_temp FROM PDT.users;
    INSERT INTO star_pocet_pouzivatelov (pocet_pouzivatelov, povod_id) VALUES (pocet_temp, 3);
END;
/