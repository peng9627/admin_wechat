package game.infrastructure.persistence.hibernate.user;

import game.domain.model.user.IUserExternalConsumptionRepository;
import game.domain.model.user.UserExternalConsumption;
import game.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pengyi on 2016/4/15.
 */
@Repository("userExternalConsumptionRepository")
public class UserExternalConsumptionRepository extends AbstractHibernateGenericRepository<UserExternalConsumption, String>
        implements IUserExternalConsumptionRepository<UserExternalConsumption, String> {

}
