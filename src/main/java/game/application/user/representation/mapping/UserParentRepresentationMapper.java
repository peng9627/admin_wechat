package game.application.user.representation.mapping;

import game.application.user.representation.UserRepresentation;
import game.domain.model.user.User;
import game.domain.model.user.UserParent;
import ma.glasnost.orika.CustomMapper;
import org.springframework.stereotype.Component;

/**
 * Created by pengyi on 2016/4/19.
 */
@Component
public class UserParentRepresentationMapper extends CustomMapper<UserParent, UserRepresentation> {
}
