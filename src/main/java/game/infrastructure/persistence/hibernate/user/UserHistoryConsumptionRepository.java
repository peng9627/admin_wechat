package game.infrastructure.persistence.hibernate.user;

import game.domain.model.user.IUserHistoryConsumptionRepository;
import game.domain.model.user.UserHistoryConsumption;
import game.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pengyi on 2016/4/15.
 */
@Repository("userHistoryConsumptionRepository")
public class UserHistoryConsumptionRepository extends AbstractHibernateGenericRepository<UserHistoryConsumption, String>
        implements IUserHistoryConsumptionRepository<UserHistoryConsumption, String> {

}
