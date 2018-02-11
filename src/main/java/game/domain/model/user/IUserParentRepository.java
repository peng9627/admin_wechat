package game.domain.model.user;

import game.infrastructure.persistence.hibernate.generic.IHibernateGenericRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pengyi on 2016/4/15.
 */
public interface IUserParentRepository<T, ID extends Serializable> extends IHibernateGenericRepository<T, ID> {

    UserParent searchByUserId(int userId);

    int spreadCount(int userId);

    void updateLastDayRebate();

    List<Integer> userIds();

    List<T> byParent(Integer parent);
}
