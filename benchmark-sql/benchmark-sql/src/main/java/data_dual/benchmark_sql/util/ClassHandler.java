package data_dual.benchmark_sql.util;

import com.github.javafaker.Faker;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

@Service
@NoArgsConstructor
@Slf4j
public class ClassHandler<T> {
    public Class<?> getReturnTypeClassOfMethod(Method method) {
        return method.getReturnType();
    }

    /**
     * usage : getAllCLassMethod(Product.class)
     * @param classToWorkOn
     * @return
     */
    public List<Method> getAllCLassMethod(Class<?> classToWorkOn) {
        return List.of(classToWorkOn.getDeclaredMethods());
    }

    public List<String> extractMethodsName(List<Method> methods) {
        return methods.stream().map(Method::getName).toList();
    }

    public List<String> extractGetterMethodNames(List<Method> methods) {
        return methods.stream().map(Method::getName).filter(methodName -> methodName.startsWith("get")).toList();
    }
    public List<Method> extractGetterMethod(List<Method> methods) {
        return methods.stream().filter(methodName -> methodName.getName().startsWith("get")).toList();
    }

    public List<String> extractSetterMethodNames(List<Method> methods) {
        return methods.stream().map(Method::getName).filter(methodName -> methodName.startsWith("set")).toList();
    }
    public List<Method> extractSetterMethod(List<Method> methods) {
        return methods.stream().filter(methodName -> methodName.getName().startsWith("set")).toList();
    }
    public String generateRandomFieldFromFaker(String value) {
        Faker faker = new Faker();
        List<Method> filteredMethods;

        List<Method> allMethods = this.getAllCLassMethod(Faker.class);
        List<Method> excludedMethods = Stream
                .concat(this.extractGetterMethod(allMethods).stream(),
                        this.extractSetterMethod(allMethods).stream())
                .toList();
        filteredMethods = allMethods
                .stream()
                .filter(method -> excludedMethods
                        .stream().noneMatch(excludedMethod ->
                                excludedMethod.getName().equals(method.getName())))
                .toList();

        for (int i = 0; i < filteredMethods.size(); i++) {
            Object dynamicObjectInstance;
            try {
                Method currentMethod = this.getAllCLassMethod(Faker.class).get(i);
                dynamicObjectInstance = currentMethod.invoke(faker);
                List<Method> subMethods = this
                        .getAllCLassMethod(dynamicObjectInstance.getClass()).stream().filter(method -> method.getReturnType().getSimpleName().equals("String")).toList();

                for (Method submethod : subMethods) {
                    if(value.equalsIgnoreCase(submethod.getName())){
                        log.info("done ...");
                        log.info(value +" was retrieved");
                        return submethod.invoke(dynamicObjectInstance)==null ? "":
                                String.valueOf(submethod.invoke(dynamicObjectInstance));
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
                //
            }
        }
        return "";

    }

}
