package game.domain.service.user;

import game.domain.model.user.UserHistoryConsumption;

import java.util.List;

/**
 * Created by pengyi
 * Date : 2016/4/19.
 */
public interface IUserHistoryConsumptionService {
    void createAll(List<UserHistoryConsumption> userHistoryConsumptions);
}
