package dpt.misc;

import org.apache.log4j.Logger;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class QueryBuilder {
    private static final Logger logger = Logger.getLogger(QueryBuilder.class);
    private String nativeQuery;
    private List<String> parameterList;

    public QueryBuilder() {
        nativeQuery = null;
        parameterList = new ArrayList<>();
    }

    public QueryBuilder select(String query) {
        nativeQuery = query;
        return this;
    }

    public QueryBuilder addMany(Collection<? extends Object> valueList, boolean withUpper) {
        if (valueList == null || valueList.isEmpty()) {
            return this;
        }

        for (Object value : valueList) {
            if (withUpper) {
                parameterList.add(value.toString().toUpperCase());
            } else {
                parameterList.add(value.toString());
            }
        }

        return this;
    }

    public interface IItem2String<T> {
        public String toString(T object);
    }

    public <T> QueryBuilder addMany(String name, Collection<T> valueList, IItem2String<T> method) {
        if (valueList == null || valueList.isEmpty()) {
            return this;
        }
        for (T value : valueList) {
            parameterList.add(method.toString(value));
        }
        return this;
    }

    public QueryBuilder add(String value) {
        parameterList.add(value);
        return this;
    }

    public void setQueryParameters(Query query) {
        setQueryParameters(query, 1);
    }

    public void setQueryParameters(Query query, int prefix) {
        List<String> parameters = getParameterList();
        for (int i = 0; i < parameters.size(); i++) {
            query.setParameter(prefix + i, parameters.get(i));
        }
    }

    public String getNativeQuery() {
        return nativeQuery;
    }

    public List<String> getParameterList() {
        return this.parameterList;
    }
}

