package game.infrastructure.persistence.hibernate.recharge;

import game.domain.model.recharge.IRechargeSelectRepository;
import game.domain.model.recharge.RechargeSelect;
import game.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Created by pengyi on 16-7-9.
 */
@Repository("rechargeSelectRepository")
public class RechargeSelectRepository extends AbstractHibernateGenericRepository<RechargeSelect, String>
        implements IRechargeSelectRepository<RechargeSelect, String> {
}
