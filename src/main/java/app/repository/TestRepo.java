package app.repository;

import app.domain.entity.User;
import core.annotations.dependency.Repository;
import core.repository.GenericRepository;

@Repository
public interface TestRepo extends GenericRepository<User> {

}
