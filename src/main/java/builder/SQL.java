package builder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: wangcai
 * @date: 2025/10/22 22:24
 */
public class SQL {

    private SQL() {
    }

    /* =====================SELECT======================== */
    public static FromStage select(String... columns) {
        return new SelectBuilder(columns);
    }

    private static class SelectBuilder implements FromStage, WhereStageBySelectAndDelete, BuildStage {

        private String[] columns;
        private String table;
        private String where;

        private SelectBuilder() {
        }

        private SelectBuilder(String[] columns) {
            this.columns = columns;
        }

        @Override
        public SelectBuilder from(String table) {
            this.table = table;
            return this;
        }

        @Override
        public SelectBuilder where(String where) {
            this.where = where;
            return this;
        }

        @Override
        public String build() {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ").append(String.join(", ", columns))
                    .append(" FROM ")
                    .append(table);
            if (where != null) {
                sql.append(" WHERE ")
                        .append(where);
            }
            return sql.toString();
        }

    }

    /* =====================UPDATE======================== */

    public static TableStage update() {
        return new UpdateBuilder();
    }

    private static class UpdateBuilder implements TableStage, SetStage, WhereStage {

        private String table;
        private String where;
        private final Map<String, Object> setMap = new LinkedHashMap<>();

        private UpdateBuilder() {
        }

        @Override
        public UpdateBuilder table(String table) {
            this.table = table;
            return this;
        }

        @Override
        public UpdateBuilder set(String key, Object value) {
            this.setMap.put(key, value);
            return this;
        }

        @Override
        public UpdateBuilder where(String where) {
            this.where = where;
            return this;
        }

        @Override
        public String build() {
            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE ")
                    .append(table)
                    .append(" SET ");
            String setSql = setMap.entrySet().stream()
                    .map(entry -> {
                        return entry.getKey() + " = " + entry.getValue();
                    })
                    .collect(Collectors.joining(", "));
            sql.append(setSql);
            if (where != null) {
                sql.append(" WHERE ")
                        .append(where);
            }
            return sql.toString();
        }

    }

    /* =====================DELETE======================== */

    public static FromStage delete() {
        return new DeleteBuilder();
    }

    private static class DeleteBuilder implements FromStage, WhereStageBySelectAndDelete, BuildStage {

        private String table;
        private String where;

        private DeleteBuilder() {
        }

        @Override
        public DeleteBuilder from(String table) {
            this.table = table;
            return this;
        }

        @Override
        public DeleteBuilder where(String where) {
            this.where = where;
            return this;
        }

        @Override
        public String build() {
            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM ")
                    .append(table);
            if (where != null) {
                sql.append(" WHERE ")
                        .append(where);
            }
            return sql.toString();
        }

    }


    /* =====================INSERT======================== */
    public static TableStageByInsert insert() {
        return new InsertBuilder();
    }

    private static class InsertBuilder implements TableStageByInsert, ColumnStage, SetStageByInsert, BuildStage{
        private String table;
        private String[] columns;
        private List<Object> value;
        private List<List<Object>> valueList;

        private InsertBuilder() {
        }

        @Override
        public InsertBuilder table(String table) {
            this.table = table;
            return this;
        }

        @Override
        public InsertBuilder columns(String ...columns) {
            this.columns = columns;
            return this;
        }

        @Override
        public InsertBuilder value(List<Object> value) {
            this.value = value;
            return this;
        }

        @Override
        public InsertBuilder valueList(List<List<Object>> valueList) {
            this.valueList = valueList;
            return this;
        }

        @Override
        public String build() {
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO ")
                    .append(table)
                    .append(" (")
                    .append(String.join(", ", columns))
                    .append(") VALUES ");
            if (value != null) {
                sql.append("(")
                        .append(value.stream()
                                .map(Object::toString)
                                .collect(Collectors.joining(", ")))
                        .append(")");
            }
            if (valueList != null) {
                sql.append(valueList.stream()
                        .map(value -> "(" + value.stream()
                                .map(Object::toString)
                                .collect(Collectors.joining(", ")) + ")")
                        .collect(Collectors.joining(", ")));
            }
            return sql.toString();
        }

    }

}

/* =====================UPDATE接口====================== */
interface TableStage {
    WhereStage table(String table);
}

interface WhereStage {
    SetStage where(String where);
}

interface SetStage {
    SetStage set(String key, Object value);

    String build();
}

/* =====================SELECT接口====================== */
interface FromStage {
    WhereStageBySelectAndDelete from(String table);
}

interface WhereStageBySelectAndDelete {
    BuildStage where(String where);
}

interface BuildStage {
    String build();
}

/* =====================INSERT接口====================== */
interface TableStageByInsert {
    ColumnStage table(String table);
}

interface ColumnStage {
    SetStageByInsert columns(String ...columns);
}

interface SetStageByInsert {
    BuildStage value(List<Object> value);

    BuildStage valueList(List<List<Object>> valueList);
}

/* =====================DELETE接口====================== */
