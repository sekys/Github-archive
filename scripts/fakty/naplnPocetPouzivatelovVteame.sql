/*
	Proces naplnenia poctu pouzivatelov pre dimenziu tim.

	Zavislost: Pred procesom musia byt naplnene data v STAR_Tim
	Cas: 189sec,  9057 inserted
*/
CREATE OR REPLACE PROCEDURE naplnPocetPouzivatelovVteame
IS
    local_pocet NUMBER;
BEGIN
      -- Pre kazdu dimenziu napis FOR
    FOR tim IN (
		SELECT * FROM STAR_Tim
	) LOOP
		SELECT COUNT(*) INTO local_pocet 
			FROM PDT.GIT_OrgsMembers git 
		WHERE
			git.orgs_id IN (
			SELECT id FROM PDT.git_orgs WHERE CAST(login as VARCHAR2(250)) LIKE tim.name
		);
		INSERT INTO star_pocet_clenov_timu(num_users_in_team, povod_id, team_id) VALUES (local_pocet, 1, tim.id);

		
		SELECT COUNT(*) INTO local_pocet 
			FROM PDT.BIT_followers bit 
		WHERE
			bit.user_id IN (
			SELECT id FROM PDT.bit_users WHERE CAST(username as VARCHAR2(250)) LIKE tim.name
		);
		INSERT INTO star_pocet_clenov_timu(num_users_in_team, povod_id, team_id) VALUES (local_pocet, 2, tim.id);

		
		-- Viaceri ludia mozu mat rovnaky odznak, teda akokeby patrili do rovnakeho timu
		SELECT COUNT(*) INTO local_pocet FROM PDT.badges WHERE CAST(name as VARCHAR2(250)) LIKE tim.name;
		INSERT INTO star_pocet_clenov_timu(num_users_in_team, povod_id, team_id) VALUES (local_pocet, 2, tim.id);

	
	END LOOP;
END;
/
