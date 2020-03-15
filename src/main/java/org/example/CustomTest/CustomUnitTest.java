package org.example.CustomTest;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CustomUnitTest {
    private static final char PKG_SEPARATOR = '.';
    private static final char DIR_SEPARATOR = '/';
    private static final String CLASS_FILE_SUFFIX = ".class";
    private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

    public static void runTest(String pathName) throws IllegalAccessException, InstantiationException {
        List<Class<?>> classList = getClassesFromPath(pathName);
        for (Class<?> clazz : classList) {
            Object o = clazz.newInstance();
            runAllMethodsWithAnnotations(getMethodsWithBeforeAnnotation(clazz),
                    getMethodsWithTestAnnotation(clazz),
                    getMethodsWithAfterAnnotation(clazz),
                    o);
        }
    }

    public static List<Class<?>> getClassesFromPath(String pathName) {
        String scannedPath = pathName.replace(PKG_SEPARATOR, DIR_SEPARATOR);
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, pathName));
        }
        File scannedDir = new File(scannedUrl.getFile());
        List<Class<?>> classes = new ArrayList<>();
        for (File file : Objects.requireNonNull(scannedDir.listFiles())) {
            classes.addAll(find(file, pathName));
        }
        return classes;
    }

    private static List<Class<?>> find(File file, String scannedPackage) {
        List<Class<?>> classes = new ArrayList<>();
        String resource = scannedPackage + PKG_SEPARATOR + file.getName();
        if (file.isDirectory()) {
            for (File child : Objects.requireNonNull(file.listFiles())) {
                classes.addAll(find(child, resource));
            }
        } else if (resource.endsWith(CLASS_FILE_SUFFIX)) {
            int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
            String className = resource.substring(0, endIndex);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }

    public static List<Method> getMethodsWithBeforeAnnotation(Class<?> clazz) {
        return getAllMethods(clazz).stream().filter(method -> method.isAnnotationPresent(Before.class))
                .collect(Collectors.toList());
    }

    public static List<Method> getMethodsWithTestAnnotation(Class<?> clazz) {
       return getAllMethods(clazz).stream().filter(method -> method.isAnnotationPresent(Test.class))
               .collect(Collectors.toList());
    }

    public static List<Method> getMethodsWithAfterAnnotation(Class<?> clazz) {
        return getAllMethods(clazz).stream().filter(method -> method.isAnnotationPresent(After.class))
                .collect(Collectors.toList());
    }

    private static List<Method> getAllMethods(Class<?> clazz) {
        return Arrays.asList(clazz.getMethods());
    }

    private static void runMethods(List<Method> methods, Object obj) {
        methods.forEach(method -> {
            try {
                method.invoke(obj);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    private static void runAllMethodsWithAnnotations(List<Method> before, List<Method> test, List<Method> after, Object o) {
        test.forEach(method -> {
                if (before.size() > 0) {
                    runMethods(before, o);
                }
                if (test.size() > 0) {
                    try {
                        System.out.println(method.getName());
                        method.invoke(o);
                    }catch (CustomTestException e) {
                        System.out.println(method.getName() + ": is Failed " + e.getMessage());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        System.out.println(e.getCause().getMessage());
                    }

                }
                if(after.size() > 0) {
                    runMethods(after,o);
                }


        });
    }
}
