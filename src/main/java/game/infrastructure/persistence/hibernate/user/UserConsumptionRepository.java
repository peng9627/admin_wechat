package game.infrastructure.persistence.hibernate.user;

import game.domain.model.user.IUserConsumptionRepository;
import game.domain.model.user.UserConsumption;
import game.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pengyi on 2016/4/15.
 */
@Repository("userConsumptionRepository")
public class UserConsumptionRepository extends AbstractHibernateGenericRepository<UserConsumption, String>
        implements IUserConsumptionRepository<UserConsumption, String> {

}
