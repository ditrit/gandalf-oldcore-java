package com.orness.gandalf.core.module.databasemodule.manager;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.List;

public abstract class DatabaseCommonManager {

    private HashMap<Class, JpaRepository> classJpaRepositoryHashMap;

    public HashMap<Class, JpaRepository> getClassJpaRepositoryHashMap() {
        return classJpaRepositoryHashMap;
    }

    public void setClassJpaRepositoryHashMap(HashMap<Class, JpaRepository> classJpaRepositoryHashMap) {
        this.classJpaRepositoryHashMap = classJpaRepositoryHashMap;
    }

    public abstract List list(Class classObject);

    public abstract Object select(Class classObject, Long id);

    public abstract void insert(Class classObject, Object object);

    public abstract void update(Class classObject, Long id, Object object);

    public abstract void delete(Class classObject, Long id);
}
