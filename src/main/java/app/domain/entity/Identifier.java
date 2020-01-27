package app.domain.entity;

import javax.persistence.*;

@MappedSuperclass
class Identifier {

    private Long id;

    Identifier() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
