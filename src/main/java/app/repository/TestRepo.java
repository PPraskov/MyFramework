package app.repository;

import core.annotations.dependency.Repository;

@Repository
public interface TestRepo {

    public void findById(String id);
}
