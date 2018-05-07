package soap.util;

import org.mapstruct.Mapper;
import soap.dto.GroupDTO;
import soap.entity.Group;

/**
 * Created by EGBoldyr on 07.05.18.
 */

@Mapper(componentModel = "spring")
public interface GroupsMapper {

    GroupDTO groupToGroupDTO(Group group);
}