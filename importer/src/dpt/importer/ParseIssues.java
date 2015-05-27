package dpt.importer;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import dpt.entities.events.Events;
import dpt.entities.issues.*;
import dpt.entities.users.Members;
import dpt.entities.users.Orgs;
import dpt.entities.users.OrgsMembers;
import dpt.misc.DBException;
import dpt.misc.DBService;
import dpt.misc.QueryBuilder;
import dpt.misc.Util;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Seky
 * Date: 19.10.2013
 * Time: 19:42
 */
public class ParseIssues {
    private static final Logger logger = Logger.getLogger(ParseIssues.class.getName());
    private static Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    public static void parseOrgs() {
        // Stiahni issues kde issue je null
        QueryBuilder sql = new QueryBuilder();
        sql.select("SELECT * FROM PDT.MEMBERS WHERE parser IS NULL AND url IS NOT NULL AND rownum < 1000");
        List<Members> members = DBService.nativeQuery(sql, Members.class);
        logger.info("Stiahol som " + members.size() + " members. Parsujem Orgs.");

        // Parsuj issues
        Integer orgsCount = 0;
        for (Members mem : members) {
            String url = getApiUrlFromUrl(mem.getUrl() + "/orgs");
            if (url == null) continue;
            try {
                String issueData = Util.download(url);
                Type listType = new TypeToken<List<Orgs>>() {
                }.getType();
                List<Orgs> orgs = gson.fromJson(issueData, listType);
                mem.setParser("A");
                for (Orgs org : orgs) {
                    Orgs existuje = DBService.get("Orgs", org.getUrl(), Orgs.class);
                    if (existuje == null) {
                        DBService.insertOne(org);
                        orgsCount++;
                    } else {
                        org = existuje;
                    }

                    OrgsMembers vztah = new OrgsMembers();
                    vztah.setMember(mem);
                    vztah.setOrg(org);
                    DBService.insertOne(vztah);
                }
            } catch (FileNotFoundException e) {
                mem.setParser("B");
                logger.info(url + " error 404");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }

            EntityManager em = DBService.getEntityManager();
            em.getTransaction().begin();
            em.merge(mem);
            em.getTransaction().commit();
        }
        logger.info("Ulozil som " + orgsCount + " orgs.");
    }

    public static void parseRepositoryMilestones() {
        // Stiahni issues kde issue je null
        QueryBuilder sql = new QueryBuilder();
        sql.select("SELECT * FROM PDT.REPOSITORY WHERE (parser = ? OR parser IS NULL ) AND url IS NOT NULL AND rownum < 1000");
        sql.add("A");
        List<Repository> repos = DBService.nativeQuery(sql, Repository.class);
        logger.info("Stiahol som " + repos.size() + " Repository. Parsujem milestones.");

        // Parsuj issues
        Integer mileStonesCount = 0;
        for (Repository rep : repos) {
            String parser = rep.getParser();
            parser = parser == null ? "" : parser;
            String url = getApiUrlFromUrl(rep.getUrl() + "/milestones");
            if (url == null) continue;
            try {
                String issueData = Util.download(url);
                Type listType = new TypeToken<List<Milestones>>() {
                }.getType();
                List<Milestones> milestones = gson.fromJson(issueData, listType);
                rep.setParser(parser + "C");
                for (Milestones mile : milestones) {
                    Milestones existuje = DBService.get("Milestones", mile.getUrl(), Milestones.class);
                    if (existuje == null) {
                        mile.setRepository(rep);
                        saveMilestones(mile);
                        mileStonesCount++;
                    }
                }
            } catch (FileNotFoundException e) {
                rep.setParser(parser + "B");
                logger.info(url + " error 404");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }

            EntityManager em = DBService.getEntityManager();
            em.getTransaction().begin();
            em.merge(rep);
            em.getTransaction().commit();
        }
        logger.info("Ulozil som " + mileStonesCount + " milestones.");
    }


    private static void saveIssue(Issues issue) {
        // Updatni pouzivatelov
        Members user = issue.getUser();
        if (user != null) {
            user = DBService.getOrCreate("Members", user.getUrl(), Members.class, user);
            issue.setUser(user);
        }
        Members assignee = issue.getAssignee();
        if (assignee != null) {
            assignee = DBService.getOrCreate("Members", assignee.getUrl(), Members.class, assignee);
            issue.setAssignee(assignee);
        }
        Members closedBy = issue.getClosed_by();
        if (closedBy != null) {
            closedBy = DBService.getOrCreate("Members", closedBy.getUrl(), Members.class, closedBy);
            issue.setClosed_by(closedBy);
        }
        Milestones milestone = issue.getMilestone();
        if (milestone != null) {
            Milestones existuje = DBService.get("Milestones", milestone.getUrl(), Milestones.class);
            if (existuje == null) {
                milestone = saveMilestones(milestone);
            } else {
                milestone = existuje;
            }
            issue.setMilestone(milestone);
        }

        // Updatni labels
        List<Labels> labels = issue.getLabels();
        for (int i = 0; i < labels.size(); i++) {
            Labels label = labels.get(i);
            label = DBService.getOrCreate("Labels", label.getUrl(), Labels.class, label);
            labels.set(i, label);
        }

        // Uloz issue a a event vztah
        // Uloz aj pouzivatelov
        EntityManager em = DBService.getEntityManager();
        em.getTransaction().begin();
        em.persist(issue);
        for (Labels label : labels) {
            label.setIssue(issue);
            em.merge(label);
        }
        em.getTransaction().commit();
    }

    public static void parseRepositoryIssues() {
        // Stiahni issues kde issue je null
        QueryBuilder sql = new QueryBuilder();
        sql.select("SELECT * FROM PDT.REPOSITORY WHERE parser IS NULL AND url IS NOT NULL AND rownum < 1000");
        List<Repository> repos = DBService.nativeQuery(sql, Repository.class);
        logger.info("Stiahol som " + repos.size() + " Repository. Parsujem issues.");

        // Parsuj issues
        Integer issuesCount = 0;
        for (Repository rep : repos) {
            String url = getApiUrlFromUrl(rep.getUrl() + "/issues");
            if (url == null) continue;
            try {
                String issueData = Util.download(url);
                Type listType = new TypeToken<List<Issues>>() {
                }.getType();
                List<Issues> issues = gson.fromJson(issueData, listType);
                rep.setParser("A");
                for (Issues issue : issues) {
                    Issues existuje = DBService.get("Issues", issue.getUrl(), Issues.class);
                    if (existuje == null) {
                        issue.setRepository(rep);
                        saveIssue(issue);
                        issuesCount++;
                    }
                }
            } catch (FileNotFoundException e) {
                rep.setParser("B");
                logger.info(url + " error 404");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }

            EntityManager em = DBService.getEntityManager();
            em.getTransaction().begin();
            em.merge(rep);
            em.getTransaction().commit();
        }
        logger.info("Ulozil som " + issuesCount + " issues");
    }

    public static void parseIssues() {
        // Stiahni issues kde issue je null
        QueryBuilder sql = new QueryBuilder();
        sql.select("SELECT * FROM PDT.EVENTS WHERE parser IS NULL AND url IS NOT NULL AND issue IS NULL AND type = ?");
        sql.add("IssuesEvent");
        List<Events> eventy = DBService.nativeQuery(sql, Events.class);
        logger.info("Stiahol som " + eventy.size() + " eventov. Parsujem issues.");

        // Parsuj issues
        Integer issuesCount = 0;
        for (Events event : eventy) {
            String url = getApiUrlFromUrl(event.getUrl());
            if (url == null) continue;
            try {
                String issueData = Util.download(url);
                Issues issue = gson.fromJson(issueData, Issues.class);
                Issues existuje = DBService.get("Issues", issue.getUrl(), Issues.class);
                if (existuje != null) {
                    event.setIssues(existuje);
                } else {
                    issue.setRepository(event.getRepository());
                    saveIssue(issue);
                    event.setIssues(issue);
                    event.setParser("A");
                    issuesCount++;
                }
            } catch (IOException e) {
                event.setParser("B");
                logger.info(url + " error 404");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }

            EntityManager em = DBService.getEntityManager();
            em.getTransaction().begin();
            em.merge(event);
            em.getTransaction().commit();
        }
        logger.info("Ulozil som " + issuesCount + " issues");
    }

    public static Milestones saveMilestones(Milestones milestone) {
        Members creator = milestone.getCreator();
        if (creator != null) {
            creator = DBService.getOrCreate("Members", creator.getUrl(), Members.class, creator);
            milestone.setCreator(creator);
        }
        try {
            DBService.insertOne(milestone);
            return milestone;
        } catch (DBException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getApiUrlFromUrl(String url) {
        if (url == null) return null;
        url = url.replace("https://github.com/", "https://api.github.com/repos/");
        url = url + "?access_token=" + Importer.ACCESS_TOKEN;
        return url;
    }

}
