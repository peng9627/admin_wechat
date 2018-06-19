package game.infrastructure.persistence.hibernate.user;

import game.domain.model.user.IUserParentRepository;
import game.domain.model.user.UserParent;
import game.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

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

    @Override
    public void updateLastDayRebate() {
        String hqlUpdate = "update UserParent u set u.lastDayCommission = u.todayCommission, u.todayCommission = 0, u.lastDayConsumption = todayConsumption, u.todayConsumption = 0";
        int updatedEntities = getSession().createQuery(hqlUpdate)
                .executeUpdate();
    }

    @Override
    public List<Integer> daqu() {
        Criteria criteria = getSession().createCriteria(this.getPersistentClass());
        criteria.add(Restrictions.isNull("parent"));
        criteria.add(Restrictions.eq("area", 1));
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.property("userId"));
        criteria.setProjection(projectionList);
        return criteria.list();
    }

    @Override
    public void addDaquCommission(String id, BigDecimal commission) {
        String hqlUpdate = "update UserParent u set u.daquCommission = u.daquCommission + " + commission.doubleValue() + ", " +
                "u.daquTotalCommission = u.daquTotalCommission + " + commission.doubleValue() + " where u.id = '" + id + "'";
        int updatedEntities = getSession().createQuery(hqlUpdate)
                .executeUpdate();
    }

}
