package com.xyz.caofancpu.trackingtime.studywaitingutils.singleinstance;

import java.lang.reflect.Constructor;

/**
 *
 */
public final class HolderSafeAndForbiddenReflectSingleton {
    private HolderSafeAndForbiddenReflectSingleton() {
        if (getInstance() != null) {
            throw new RuntimeException("妖精, 哪里逃!");
        }
    }

    public static HolderSafeAndForbiddenReflectSingleton getInstance() {
        return SingletonHolder.instance;
    }

    public static void main(String[] args) {
        try {
            testReflectBefore();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            testReflectAfter();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testReflectBefore()
            throws Exception {
        Class<HolderSafeAndForbiddenReflectSingleton> aClass = (Class<HolderSafeAndForbiddenReflectSingleton>) Class.forName("com.xyz.caofancpu.trackingtime.studywaitingutils.singleinstance.HolderSafeAndForbiddenReflectSingleton");
        Constructor<HolderSafeAndForbiddenReflectSingleton> constructor = aClass.getDeclaredConstructor(null);
        constructor.setAccessible(true);
        HolderSafeAndForbiddenReflectSingleton instance2 = constructor.newInstance();
        System.out.println(instance2);
        HolderSafeAndForbiddenReflectSingleton instance1 = HolderSafeAndForbiddenReflectSingleton.getInstance();
        System.out.println(instance1);
    }

    public static void testReflectAfter()
            throws Exception {
        HolderSafeAndForbiddenReflectSingleton instance1 = HolderSafeAndForbiddenReflectSingleton.getInstance();
        System.out.println(instance1);
        Class<HolderSafeAndForbiddenReflectSingleton> aClass = (Class<HolderSafeAndForbiddenReflectSingleton>) Class.forName("com.xyz.caofancpu.trackingtime.studywaitingutils.singleinstance.HolderSafeAndForbiddenReflectSingleton");
        Constructor<HolderSafeAndForbiddenReflectSingleton> constructor = aClass.getDeclaredConstructor(null);
        constructor.setAccessible(true);
        HolderSafeAndForbiddenReflectSingleton instance2 = constructor.newInstance();
        System.out.println(instance2);
    }

    public static class SingletonHolder {
        public static final HolderSafeAndForbiddenReflectSingleton instance = new HolderSafeAndForbiddenReflectSingleton();
    }
}
