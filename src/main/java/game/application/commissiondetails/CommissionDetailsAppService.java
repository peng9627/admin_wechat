package game.application.commissiondetails;

import game.application.commissiondetails.command.CreateCommand;
import game.domain.service.commissiondetailed.ICommissionDetailedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pengyi
 * Date : 18-5-14.
 * desc:
 */
@Service("commissionDetailsAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CommissionDetailsAppService implements ICommissionDetailsAppService {

    private final ICommissionDetailedService commissionDetailedService;

    @Autowired
    public CommissionDetailsAppService(ICommissionDetailedService commissionDetailedService) {
        this.commissionDetailedService = commissionDetailedService;
    }

    @Override
    public void create(CreateCommand createCommand) {
        commissionDetailedService.create(createCommand);
    }

    @Override
    public void createAll(List<CreateCommand> createCommands) {
        commissionDetailedService.createAll(createCommands);
    }
}
