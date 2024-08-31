package data_dual.benchmark_sql.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassHandlerTest {

    private ClassHandler<Object> classHandler;

    @BeforeEach
    void setUp() {
        classHandler = new ClassHandler<>();
    }

    @Test
    void testGetReturnTypeClassOfMethod() throws NoSuchMethodException {
        Method method = SampleClass.class.getMethod("getName");
        Class<?> returnType = classHandler.getReturnTypeClassOfMethod(method);
        assertEquals(String.class, returnType);
    }

    @Test
    void testGetAllClassMethod() {
        List<Method> methods = classHandler.getAllCLassMethod(SampleClass.class);
        assertEquals(2, methods.size());
    }

    @Test
    void testExtractMethodsName() {
        List<Method> methods = classHandler.getAllCLassMethod(SampleClass.class);
        List<String> methodNames = classHandler.extractMethodsName(methods);
        assertTrue(methodNames.contains("getName"));
        assertTrue(methodNames.contains("setName"));
    }

    @Test
    void testExtractGetterMethodNames() {
        List<Method> methods = classHandler.getAllCLassMethod(SampleClass.class);
        List<String> getterNames = classHandler.extractGetterMethodNames(methods);
        assertEquals(1, getterNames.size());
        assertEquals("getName", getterNames.get(0));
    }

    @Test
    void testExtractSetterMethodNames() {
        List<Method> methods = classHandler.getAllCLassMethod(SampleClass.class);
        List<String> setterNames = classHandler.extractSetterMethodNames(methods);
        assertEquals(1, setterNames.size());
        assertEquals("setName", setterNames.get(0));
    }

    @Test
    void testCreateAnInstanceWithClassName() {
        Class<?> clazz = classHandler.createAnInstanceWithCLassName("data_dual.benchmark_sql.util.ClassHandlerTest$SampleClass");
        assertNotNull(clazz);
        assertEquals(SampleClass.class, clazz);
    }

    @Test
    void testCreateAnInstanceWithClassName_ClassNotFound() {
        Class<?> clazz = classHandler.createAnInstanceWithCLassName("non.existent.ClassName");
        assertNull(clazz);
    }

    static class SampleClass {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
