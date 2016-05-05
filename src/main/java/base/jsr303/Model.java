package base.jsr303;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by hadoop on 5/5/2016.
 * JSR 303 验证属性
 */
public class Model {
    @NotNull(message = "不能为空")
    @Size(max = 10, message = "超过最大限制")
    public String name;
    @Max(value = 10, message = "最大值10 ")
    public int age;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
