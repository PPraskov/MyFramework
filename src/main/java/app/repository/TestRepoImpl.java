package app.repository;

import app.domain.entity.User;
import core.annotations.dependency.Autowire;
import core.repository.Repository;

public class TestRepoImpl extends Repository<User> implements TestRepo {

    @Autowire
    public TestRepoImpl() {

    }
}
