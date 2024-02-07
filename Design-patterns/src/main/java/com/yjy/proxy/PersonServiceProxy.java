package com.yjy.proxy;

import com.yjy.entity.PersonDTO;
import cn.hutool.json.JSONUtil;

/**
 * 代理模式，不改变原有代码的情况下增加功能
 * 用途aop，Mybtais
 * 先从二级缓存查，查不到就通过被代理的对象查找数据，减少耦合，因为正常二级缓存不开启（Mybatis）
 */
public class PersonServiceProxy implements PersonService {

    private final PersonService personService = new PersonServiceImpl();

    @Override
    public void savePerson(PersonDTO person) {
        System.out.println("savePerson接口入参:{}"+JSONUtil.toJsonStr(person));
        personService.savePerson(person);
    }
}
