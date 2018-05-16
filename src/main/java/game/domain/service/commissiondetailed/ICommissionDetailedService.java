package game.domain.service.commissiondetailed;

import game.application.commissiondetails.command.CreateCommand;

import java.util.List;

/**
 * Created by pengyi
 * Date : 16-7-9.
 */
public interface ICommissionDetailedService {

    void create(CreateCommand command);

    void createAll(List<CreateCommand> createCommands);
}
