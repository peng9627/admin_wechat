package game.domain.model.user;

import game.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;

/**
 * Created by pengyi on 2016/4/15.
 */
public interface IUserConsumptionRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {

}
