begin
     for trec in ( select table_name
                   from user_tables
                   where table_name like 'STAR_%' )
     loop
         execute immediate 'drop table '||trec.table_name||' cascade constraints';
     end loop;
end;
