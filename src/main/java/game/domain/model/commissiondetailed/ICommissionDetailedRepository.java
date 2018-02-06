package game.domain.model.commissiondetailed;

import game.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by pengyi
 * Date : 2016/3/9.
 */
public interface ICommissionDetailedRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {
}
