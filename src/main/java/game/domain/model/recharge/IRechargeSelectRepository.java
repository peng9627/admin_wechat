package game.domain.model.recharge;

import game.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;
import org.hibernate.criterion.Criterion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by pengyi on 16-7-9.
 */
public interface IRechargeSelectRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
