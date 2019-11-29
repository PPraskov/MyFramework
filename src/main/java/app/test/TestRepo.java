package app.test;

@Repo
public interface TestRepo {

    @TestAnnot(query = "Testing")
    TestClass testing();
}
