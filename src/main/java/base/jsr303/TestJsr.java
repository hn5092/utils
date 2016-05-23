package base.jsr303;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by hadoop on 5/5/2016.
 */
public class TestJsr {
    @Test
    public void testJsr() {
        Model model = new Model();
        model.setAge(12);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Model>> validate = validator.validate(model);
        System.out.println(validate.size());
        for (ConstraintViolation<Model> c : validate) {
            System.out.println(c.getMessage());
        }
    }
}
