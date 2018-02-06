package game.domain.service.commissiondetailed;

import game.application.commissiondetails.command.CreateCommand;

/**
 * Created by pengyi
 * Date : 16-7-9.
 */
public interface ICommissionDetailedService {

    void create(CreateCommand command);
}
