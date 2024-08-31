package data_dual.benchmark_sql.util;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;

@Service
@NoArgsConstructor
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

    public List<String> extractSetterMethodNames(List<Method> methods) {
        return methods.stream().map(Method::getName).filter(methodName -> methodName.startsWith("set")).toList();
    }

    Class<?> createAnInstanceWithCLassName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
