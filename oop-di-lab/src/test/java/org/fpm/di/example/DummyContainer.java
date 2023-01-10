package org.fpm.di.example;

import org.fpm.di.Container;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class DummyContainer implements Container {
    private final DummyBinder dummyBinder;

    public DummyContainer(DummyBinder dummyBinder) {
        this.dummyBinder = dummyBinder;
    }

    @Override
    public <T> T getComponent(Class<T> clazz) {
        if (dummyBinder.getListbd(clazz)!=null) {
            for (Constructor<?> constructor: clazz.getConstructors()){
                if (constructor.isAnnotationPresent(Inject.class)){
                    List<Object> list = new ArrayList<>();
                    for (Class<?> clas: constructor.getParameterTypes())
                        list.add(getComponent(clas));
                    try {
                        return (T) constructor.newInstance(list.toArray());
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            try {
                return clazz.getConstructor().newInstance();
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
        if (dummyBinder.getMapbd(clazz)!=null) {
            return getComponent(dummyBinder.getMapbd(clazz));
        }
        if (dummyBinder.getMapbd1(clazz)!=null) {
            return dummyBinder.getMapbd1(clazz);
        }
        return null;
    }
}
