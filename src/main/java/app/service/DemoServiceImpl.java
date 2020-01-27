package app.service;


import app.domain.entity.User;
import app.repository.TestRepo;
import app.utill.DemoUtill;
import core.annotations.dependency.Autowire;


public class DemoServiceImpl implements DemoService{

    private final DemoUtill demoUtill;
    private final TestRepo testRepo;

    @Autowire
    public DemoServiceImpl(DemoUtill demoUtill, TestRepo testRepo) {
        this.demoUtill = demoUtill;
        this.testRepo = testRepo;
    }

    public void saveUser(User user){
         this.testRepo.save(user);
        System.out.println("User saved!");
    }

    public void findUser(String username, String value){
        User user = this.testRepo.getSingle(username, value);
        System.out.println(user.getId());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
    }

}
