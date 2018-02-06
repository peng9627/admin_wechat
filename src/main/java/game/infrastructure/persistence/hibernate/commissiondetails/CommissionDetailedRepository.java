package game.infrastructure.persistence.hibernate.commissiondetails;

import game.domain.model.commissiondetailed.CommissionDetailed;
import game.domain.model.commissiondetailed.ICommissionDetailedRepository;
import game.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pengyi on 16-7-9.
 */
@Repository("commissionDetailedRepository")
public class CommissionDetailedRepository extends AbstractHibernateGenericRepository<CommissionDetailed, String>
        implements ICommissionDetailedRepository<CommissionDetailed, String> {
}
