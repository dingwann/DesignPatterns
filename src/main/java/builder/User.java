package builder;

/**
 * @author: wangcai
 * @date: 2025/10/22 20:33
 */
public class User {

    private final String name;

    private final Integer age;

    private User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private Integer age;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(Integer age) {
            this.age = age;
            return this;
        }

        public User build() {
            User user = new User(this);
            if (this.age < 10 && "Tom".equals(name)) {
                throw new IllegalArgumentException("姓名不合法");
            }
            if (this.age > 10 && "Jerry".equals(name)) {
                throw new IllegalArgumentException("姓名不合法");
            }
            return user;
        }

    }

}
