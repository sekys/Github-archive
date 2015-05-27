-- Zobraz vysledok reportu pre repozitare STAR_Pocet_issue
-- Napriklad kolko je repozitarov s nazvom lolka a bolo ich vytvorenych v 12 mesiaci
SELECT * FROM star_pocet_issue stat
    JOIN star_repository rep ON stat.repository_id = rep.id
	JOIN star_datum cas ON stat.time_id = cas.id
	JOIN star_povod povod ON stat.povod_id = povod.id
WHERE cas.mesiac = 3;

-- Zobraz vysledok reportu pre eventy STAR_Events
-- Napriklad ktore eventy boli vytvorene pre repozitar "lol" 16.2.1888 na portali github
SELECT * FROM star_events events
    JOIN star_repository rep ON events.repository_id = rep.id
	JOIN star_datum cas ON events.datum_id = cas.id
	JOIN star_povod povod ON events.povod_id = povod.id
WHERE povod.id = 1;

-- Zobraz vysledok reportu pre eventy STAR_Issue 
-- Napriklad ktore issue boli vytvorene pre repozitar "lol" 16.2.1888 na portali github v jazyku c++
SELECT * FROM star_issue issue
    JOIN star_repository rep ON issue.repository_id = rep.id
	JOIN star_datum cas ON issue.datum_id = cas.id
	JOIN star_povod povod ON issue.povod_id = povod.id
	JOIN star_jazyk jazyk ON issue.jazyk_id = jazyk.id
WHERE povod.id = 1;

-- Zobraz vysledok reportu pre pocet clenov portalu
-- Napriklad kolko je pouzivatelov na bitbuckete
SELECT * FROM star_pocet_pouzivatelov stat
  JOIN star_povod p ON stat.povod_id = p.id
WHERE p.name = 'Bitbucket';

-- Zobraz vysledok reportu pre pouzivatelov
-- Napriklad aky sú pouzivatelia na portali bitbucket
SELECT * FROM star_pouzivatelia stat
  JOIN star_povod p ON stat.povod_id = p.id
WHERE p.name = 'Bitbucket';

-- Zobraz vysledok reportu pre pocet clenov timu
-- Napriklad kolko je clenov v timoch na bitbuckete
SELECT * FROM star_pocet_clenov_timu stat
  JOIN star_tim t ON stat.team_id = t.id
  JOIN star_povod p ON stat.povod_id = p.id
WHERE p.name = 'Bitbucket';

-- Zobraz vysledok reportu pocet issue za rok
-- Napriklad ake je minimalny pocet pridanych issue za mesiac maj v roku 2012
SELECT * FROM star_pocet_issue_za_rok stat
  JOIN star_roky r ON stat.rok_id = r.id
  JOIN star_povod p ON stat.povod_id = p.id
WHERE r.rok = 2012;


-- Zobraz vysledok reportu pocet issue za mesiac
-- Napriklad ake je minimalny pocet pridanych issue za den v mesiaci maj
SELECT * FROM star_pocet_issue_za_mesiac stat
  JOIN star_mesiace_roky r ON stat.mesiace_roky_id = r.id
  JOIN star_povod p ON stat.povod_id = p.id
WHERE r.mesiac = 5;

