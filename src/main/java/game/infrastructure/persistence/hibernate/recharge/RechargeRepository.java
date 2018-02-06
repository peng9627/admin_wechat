package game.infrastructure.persistence.hibernate.recharge;

import game.core.enums.YesOrNoStatus;
import game.core.util.CoreDateUtils;
import game.domain.model.recharge.IRechargeRepository;
import game.domain.model.recharge.Recharge;
import game.infrastructure.persistence.hibernate.generic.AbstractHibernateGenericRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by pengyi on 16-7-9.
 */
@Repository("rechargeRepository")
public class RechargeRepository extends AbstractHibernateGenericRepository<Recharge, String>
        implements IRechargeRepository<Recharge, String> {

    @Override
    public BigDecimal total(List<Criterion> criterionList, Map<String, String> aliasMap) {

        Criteria criteriaCount = getSession().createCriteria(getPersistentClass());
        criteriaCount.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        if (null != criterionList) {
            criterionList.forEach(criteriaCount::add);
        }

        if (null != aliasMap) {
            for (Map.Entry<String, String> entry : aliasMap.entrySet()) {
                criteriaCount.createAlias(entry.getKey(), entry.getValue());
            }
        }
        criteriaCount.setProjection(Projections.sum("money"));

        return (BigDecimal) criteriaCount.uniqueResult();
    }

    @Override
    public Recharge byRechargeNo(String rechargeNo) {

        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("rechargeNo", rechargeNo));
        return (Recharge) criteria.uniqueResult();
    }

    @Override
    public Recharge searchByNo(String rechargeNo) {
        Criteria criteria = getSession().createCriteria(this.getPersistentClass());
        criteria.add(Restrictions.eq("rechargeNo", rechargeNo));
        return (Recharge) criteria.uniqueResult();
    }

    @Override
    public BigDecimal todayTotal(int userId) {
        Criteria criteriaCount = getSession().createCriteria(getPersistentClass());
        criteriaCount.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteriaCount.add(Restrictions.eq("userId", userId));
        criteriaCount.add(Restrictions.eq("isSuccess", YesOrNoStatus.YES));
        criteriaCount.add(Restrictions.ge("createDate", CoreDateUtils.parseDate(CoreDateUtils.formatDate(new Date(), "yyyy/MM/dd") + " 00:00", "yyyy/MM/dd HH:mm")));
        criteriaCount.setProjection(Projections.sum("money"));
        return (BigDecimal) criteriaCount.uniqueResult();
    }

}
