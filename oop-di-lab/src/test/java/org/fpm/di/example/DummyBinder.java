package org.fpm.di.example;

import org.fpm.di.Binder;

import javax.inject.Singleton;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyBinder implements Binder {
    private List<Class<?>> listbd = new ArrayList<>();
    @Override
    public <T> void bind(Class<T> clazz) {
        if(clazz.isAnnotationPresent(Singleton.class)){
            try {
                bind(clazz, clazz.getConstructor().newInstance());
                return;
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        listbd.add(clazz);
    }
    private Map<Class<?>,Class<?>> mapbd = new HashMap<>();
    @Override
    public <T> void bind(Class<T> clazz, Class<? extends T> implementation) {
        mapbd.put(clazz, implementation);
    }
    private Map<Class<?>,Object> mapbd1 = new HashMap<>();
    @Override
    public <T> void bind(Class<T> clazz, T instance) {
        mapbd1.put(clazz, instance);
    }

    public <T> Class<T> getListbd(Class<T> clazz){
        if (listbd.contains(clazz)) return clazz;
        return null;
    }
    public <T> Class<T> getMapbd(Class<T> clazz){
        if (mapbd.containsKey(clazz)) return (Class<T>) mapbd.get(clazz);
        return null;
    }
    public <T> T getMapbd1(Class<T> clazz){
        if (mapbd1.containsKey(clazz)) return (T) mapbd1.get(clazz);
        return null;
    }
}
