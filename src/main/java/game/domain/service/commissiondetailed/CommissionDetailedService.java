package game.domain.service.commissiondetailed;

import game.application.commissiondetails.command.CreateCommand;
import game.core.enums.FlowType;
import game.domain.model.commissiondetailed.CommissionDetailed;
import game.domain.model.commissiondetailed.ICommissionDetailedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by pengyi
 * Date : 16-7-9.
 */
@Service("commissionDetailedService")
public class CommissionDetailedService implements ICommissionDetailedService {

    private final ICommissionDetailedRepository<CommissionDetailed, String> commissionDetailedRepository;

    @Autowired
    public CommissionDetailedService(ICommissionDetailedRepository<CommissionDetailed, String> commissionDetailedRepository) {
        this.commissionDetailedRepository = commissionDetailedRepository;
    }

    @Override
    public void create(CreateCommand command) {
        CommissionDetailed commissionDetailed;
        if (0 < command.getMoney().compareTo(BigDecimal.ZERO)) {
            commissionDetailed = new CommissionDetailed(command.getUserId(), FlowType.IN_FLOW, command.getMoney(), command.getDescription());
        } else {
            commissionDetailed = new CommissionDetailed(command.getUserId(), FlowType.OUT_FLOW, BigDecimal.ZERO.subtract(command.getMoney()), command.getDescription());
        }
        commissionDetailedRepository.save(commissionDetailed);
    }
}
