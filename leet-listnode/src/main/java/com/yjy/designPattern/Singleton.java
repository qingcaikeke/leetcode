package com.yjy.designPattern;
//不放在一个类里就不报错了
//单例，创建日志管理类之类的
public class Singleton {
    //0.使用静态类
    //1.懒汉模式，实例在第一次被使用时才被创建
    public class LazySingleton{
        //声明但不初始化实例
        //static修饰类级别的成员，在类加载时直接运行，分配内存并初始化
        private static LazySingleton instance;
        //私有构造方法
        private LazySingleton(){
        }
        public static LazySingleton getInstance(){
            if(instance==null){
                //双重锁定,保证线程安全
                synchronized (LazySingleton.class){
                    if(instance==null){ //.this' cannot be referenced from a static context静态上下文中不能引用this关键字
                        instance = new LazySingleton();
                    }
                }
            }
            return instance;
        }
    }
    //2.饿汉模式，类加载时就创建实例
    public class EagerSingleton{
        //final类不可被继承，方法不可被重写，值不可以被修改
        private static final EagerSingleton instance = new EagerSingleton();
        private EagerSingleton(){
        }
        //提供全局访问点
        public static EagerSingleton getInstance(){
            return instance;
        }

    }



}
