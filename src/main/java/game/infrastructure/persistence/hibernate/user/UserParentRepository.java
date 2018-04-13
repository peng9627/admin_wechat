package game.infrastructure.persistence.hibernate.user;

import game.core.util.CoreDateUtils;
import game.domain.model.user.IUserParentRepository;
import game.domain.model.user.UserParent;
import game.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
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
        String hqlUpdate = "update UserParent u set u.lastdayRebate = u.todayRebate, u.lastdayTotalRebate = u.totalRebate, u.todayRebate = 0, u.lastdaySelfRebate = u.todaySelfRebate, u.todaySelfRebate = 0, u.lastDayConsumption = u.todayConsumption, u.todayConsumption = 0";
        int updatedEntities = getSession().createQuery(hqlUpdate)
                .executeUpdate();
    }

    @Override
    public List<Integer> userIds() {
        Criteria criteria = getSession().createCriteria(this.getPersistentClass());
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.property("userId"));
        criteria.setProjection(projectionList);
        return criteria.list();
    }

    @Override
    public List<UserParent> byParent(Integer parent) {
        Criteria criteria = getSession().createCriteria(this.getPersistentClass());
        criteria.add(Restrictions.eq("parent", parent));
        return criteria.list();
    }

}
