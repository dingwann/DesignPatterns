package builder;


import java.util.List;

/**
 * @author: wangcai
 * @date: 2025/10/22 21:01
 */
public class Useage {
    public static void main(String[] args) {
/*        User.Builder builder = User.builder().name("wangcai");
        User.Builder builder1 = builder.age(18);
        System.out.println(builder == builder1);
        User user = builder1.build();
        System.out.printf("name: %s, age: %d", user.getName(), user.getAge());
        System.out.println();
        CompletableFuture.supplyAsync(() -> "a").thenAccept(System.out::println);*/

/*        String sql = SQL.builder()
                .sqlType(SQL.SQLType.SELECT)
                .table("user")
                .columns("name", "age")
                .where("id = 1")
                .build();
        System.out.println(sql);
        String sql1 = SQL.builder()
                .sqlType(SQL.SQLType.UPDATE)
                .table("user")
                .set("name", "wangcai2")
                .set("age", 99)
                .where("id = 1")
                .build();
        System.out.println(sql1);*/

        String selectSQL = SQL.select("name", "age")
                .from("user")
                .where("id = 1")
                .build();
        System.out.println(selectSQL);

        String updateSQL = SQL.update()
                .table("user")
                .where("id = 1")
                .set("name", "wangcai2")
                .set("age", 99)
                .build();
        System.out.println(updateSQL);

        String built = SQL.update()
                .table("order")
                .where("order_id = 2")
                .set("status", "success")
                .set("pay_time", System.currentTimeMillis())
                .build();
        System.out.println(built);

        String built1 = SQL.select("name", "age")
                .from("user1")
                .where("id = 111")
                .build();
        System.out.println(built1);

        String built2 = SQL.delete()
                .from("user")
                .where("id = 1")
                .build();
        System.out.println(built2);

        String built3 = SQL.insert()
                .table("user")
                .columns("name", "age")
                .value(List.of("wangcai", 18))
                .build();
        System.out.println(built3);

        String built4 = SQL.insert()
                .table("user")
                .columns("name", "age")
                .valueList(List.of(List.of("wangcai", 18), List.of("lisi", 19)))
                .build();
        System.out.println(built4);
    }
}
