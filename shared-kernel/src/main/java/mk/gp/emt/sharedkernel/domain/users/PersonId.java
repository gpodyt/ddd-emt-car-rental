package mk.gp.emt.sharedkernel.domain.users;

import mk.gp.emt.sharedkernel.domain.base.DomainObjectId;

public class PersonId extends DomainObjectId {
    private PersonId() {
        super(PersonId.randomId(PersonId.class).getId());
    }

    public PersonId(String uuid) {
        super(uuid);
    }

    public static PersonId of(String uuid){
        return new PersonId(uuid);
    }
}
