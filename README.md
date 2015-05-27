# Github' Archive
Lukas Sekerak's project for Advanced Database Technologies subject.

## Description
We created program "importer" to download millions of GitHub's data (issues, events).
Then we imported these data to the Postgresql and Oracle database.
We learnt how to process several millions rows and specific features of databases.
Scripts folders contains scripts used to aggregate data and compute some statistics.
There is also diagram of our database proposal.
In the final we had to present our project to the teacher, so in the next block is comment, which describe, which features we used.
More information in a [specification](specification.pdf).

## Comment to the project

Export obsahuje:
- schemu s nazvom PDT.zip. Tato schema obsahuje tabulky vsetkych 3 systemov po migracii. Z tychto dat nasledne je naplnena star schema.
-  FINAL_STAR.sql - sql script na vytvorenie star schemy
- FINAL_STAR.png - fyzicky model databazy
- sql script reporty.sql, ktory ukazuje ake napriklad reporty mozme spravit
- sql scripty na naplnenie dimenzii, faktov, historickej tabulky, agregacnych tabuliek

Kazdy proces naplnenia dimenzie alebo faktu je popisany priamo komentary v SQL scripte. 
Kazdy komentar obsahuje popis, statistiky spustenia a zavislost na inych procesoch. 
Tato zavislost opisuje, ktore procesy musia byt spustene predtym.

Mnohe tabulky obsahuju primarny kluc a mnoho cudzich klucov a indexov.
Tieto cudzie kluce a indexi je pomorne lachko zistit z modelu udajov.

Co sme pouzili:
- pouzili sme vypnutie databazoveho logovania pri histoickej tabulky STAR_ISSUE_HISTORY
- pouzili sme bulk load pri vacsine insertov
- pouzili sme vypnutie indexov pri naplnenie dimenzie star_repository
- pouzili sme inkrementalny delta zapis pomocou WHERE event.created_at >= TO_DATE('20/JAN/2010','dd/mon/yyyy') 
- medzi dimenzie nemiace sa v case patri napriklad dimenzia star_povod
- medzi dimenzie ktore sa menia v case patri napriklad dimenzia star_repository
- filtrovali sme 
- mnohe repozitare nemaju vyplneny stlpec language, osetrujeme teda null hodnoty
- granularitu casov a datumov sme zmenili format (granularitu) zo sekund na minimalne dni

Dalsie bonusy:
- konvertujeme data pri tvorbe dimenzii
- museli sme migrovat data z mysql, postgre
- vyuzitie ? napriklad pocet teamov, pouzivatelov pre analytikov
- pouzili sme PL/SQL
- mnohe selekty sme optimalizovali za pomoci EXPLAIN FOR
- pouzili sme oracle hints /*+ FULL(e) */ a oracle parallelism /*+ parallel(git,2) */ 

Hierarchiu parent_id nemame pouzitu, pretoze systemy odkial sme cerpali, sami nemali taku hierachiu vytvorenu.

## License

This software is released under the [MIT License](LICENSE.md).

## Credits
- P. Hamar 
- G. Králik - only generating CRUD operation formulars
