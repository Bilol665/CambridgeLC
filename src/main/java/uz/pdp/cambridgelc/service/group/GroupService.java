package uz.pdp.cambridgelc.service.group;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.cambridgelc.entity.dto.GroupCreateDto;
import uz.pdp.cambridgelc.entity.group.GroupEntity;
import uz.pdp.cambridgelc.repository.GroupRepository;

@Service
@AllArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    public GroupEntity save(GroupCreateDto groupCreateDto){
        GroupEntity group = modelMapper.map(groupCreateDto, GroupEntity.class);
        return groupRepository.save(group);
    }
}
