package game.application.commissiondetails;

import game.application.commissiondetails.command.CreateCommand;

import java.util.List; /**
 * Created by pengyi
 * Date : 18-5-14.
 * desc:
 */
public interface ICommissionDetailsAppService {
    void create(CreateCommand createCommand);

    void createAll(List<CreateCommand> createCommands);
}
