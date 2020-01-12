package app.domain.entity;

import javax.persistence.*;

@MappedSuperclass
class Identifier {

    private long id;

    Identifier() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
