/*
	Pocet issue vytvorenych v danom dni, v danom zdroji pre repozitar
	
	Zavislost: Pred procesom musi byt spusteny proces naplnenia star_issue
	Cas: 3sec, 12 552 rows inserted.
*/
INSERT INTO STAR_Pocet_Issue(time_id, repository_id, povod_id, num_issues) (
	SELECT 
		issue.datum_id as time_id, 
		issue.repository_id as repository_id,
		issue.povod_id as povod_id,
		COUNT(*) as num_issues
		FROM STAR_issue issue 		
	GROUP BY issue.povod_id, issue.repository_id, issue.datum_id
);
