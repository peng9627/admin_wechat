package game.application.user;

import game.domain.model.user.UserHistoryConsumption;

import java.util.List; /**
 * Created by pengyi
 * Date : 18-5-14.
 * desc:
 */
public interface IUserHistoryConsumptionAppService {
    void createAll(List<UserHistoryConsumption> userHistoryConsumptions);
}
