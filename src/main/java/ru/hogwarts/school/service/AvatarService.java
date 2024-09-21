package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.AvatarDto;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService {
    Avatar uploadAvatar(Long studentId, MultipartFile avatar) throws IOException;

    Avatar findAvatar(Long avatarId);

    List<AvatarDto> getAllAvatars(int pageNumber, int pageSize);

}
