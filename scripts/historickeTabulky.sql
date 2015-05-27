/*
	Proces naplnenia historickej tabulky.
	Pri zmene nazvu issue dojde k skopirovaniu nazvu do tabulky issue_history.
	Tzv dojde k ulozeniu starych dat.
	Tento proces ukazuje trivialne riesenie za pomoci trigger.
	
	Zavislost proceosu od inych procesov: ziadna
*/
CREATE OR REPLACE TRIGGER STAR_ISSUES_CLOSED_TRIGGER
AFTER UPDATE
   ON PDT.git_issues
   FOR EACH ROW
DECLARE
 
BEGIN
   -- Meno prihlaseneho pouzivatela je PDT
   INSERT INTO issue_history (issue_id, nazov, username) VALUES(:old.id, :old.title, 'PDT' );     
END;


/*
	Pri spravovani dat v data warehouse, moze dojst k potrebe ulozit vsetky data.
	Teda v danom case spravit zalohu tabulku, mozme to nazvat aj historiu dat.
	Cely proces moze byt takto trivialne vyrieseny.
*/
create table STAR_HISTORY_ISSUES NOLOGGING as select * from PDT.git_issues g WHERE g.closed_at IS NOT NULL;
