package game.application.recharge.representation.mapping;

import game.application.recharge.representation.RechargeSelectRepresentation;
import game.domain.model.recharge.RechargeSelect;
import ma.glasnost.orika.CustomMapper;
import org.springframework.stereotype.Component;

/**
 * Created by pengyi
 * Date : 16-7-19.
 */
@Component
public class RechargeSelectRepresentationMapper extends CustomMapper<RechargeSelect, RechargeSelectRepresentation> {
}
