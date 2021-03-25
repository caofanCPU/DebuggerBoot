package com.xyz.caofancpu.trackingtime.studywaitingutils.singleinstance;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 禁止继承
 */
public final class LazySafeAndForbiddenReflectSingleton {

    /**
     * 禁止指令重排
     */
    private static volatile LazySafeAndForbiddenReflectSingleton instance = null;

    /**
     * 禁止外部访问
     */
    private LazySafeAndForbiddenReflectSingleton() {
        if (instance != null) {
            // 屏蔽反射
            throw new RuntimeException("妖精, 休得胡来!");
        }
    }

    public static LazySafeAndForbiddenReflectSingleton getInstance() {
        if (instance == null) {
            synchronized (LazySafeAndForbiddenReflectSingleton.class) {
                if (instance == null) {
                    instance = new LazySafeAndForbiddenReflectSingleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        try {
            Class<LazySafeAndForbiddenReflectSingleton> clazz = (Class<LazySafeAndForbiddenReflectSingleton>) Class.forName("com.xyz.caofancpu.trackingtime.studywaitingutils.singleinstance.LazySafeAndForbiddenReflectSingleton");
            Constructor<LazySafeAndForbiddenReflectSingleton> constructor = clazz.getDeclaredConstructor(null);
            constructor.setAccessible(true);
            LazySafeAndForbiddenReflectSingleton instance2 = constructor.newInstance();
            System.out.println(instance2);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        LazySafeAndForbiddenReflectSingleton instance1 = LazySafeAndForbiddenReflectSingleton.getInstance();
        System.out.println(instance1);
    }
}
