package com.yjy.singleton;

public class lazySingleton {

        private static final lazySingleton instance = new lazySingleton();

        private lazySingleton() {
            // 私有构造方法
        }

        public static lazySingleton getInstance() {
            return instance;
        }


}
