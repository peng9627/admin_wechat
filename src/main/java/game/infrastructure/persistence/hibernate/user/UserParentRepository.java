package game.infrastructure.persistence.hibernate.user;

import game.domain.model.user.IUserParentRepository;
import game.domain.model.user.UserParent;
import game.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by pengyi on 2016/4/15.
 */
@Repository("userParentRepository")
public class UserParentRepository extends AbstractHibernateGenericRepository<UserParent, String>
        implements IUserParentRepository<UserParent, String> {

    @Override
    public UserParent searchByUserId(int userId) {
        Criteria criteria = getSession().createCriteria(this.getPersistentClass());
        criteria.add(Restrictions.eq("userId", userId));
        return (UserParent) criteria.uniqueResult();
    }

    @Override
    public int spreadCount(int userId) {
        Criteria criteria = getSession().createCriteria(this.getPersistentClass());
        criteria.add(Restrictions.eq("parent", userId));
        return criteria.list().size();
    }

}
