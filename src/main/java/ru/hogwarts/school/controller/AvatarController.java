package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.AvatarDto;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(AvatarController.BASE_URI)
@Validated
public class AvatarController {
    public static final String BASE_URI = "/avatars";
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Avatar> uploadAvatar(
            @PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException {

        return ResponseEntity.ok(avatarService.uploadAvatar(studentId, avatar));
    }

    @GetMapping(value = "/{id}/avatar-from-db")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }


    @GetMapping(value = "/{id}/avatar-from-file")
    public void downloadAvatarFromFile(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);
        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength(avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping
    public List<AvatarDto> getAllAvatars(
            @RequestParam @Min(value = 1, message = "Минимальный номер страницы=1") int pageNumber,
            @RequestParam @Min(value = 1, message = "Минимальный размер страницы=1") int pageSize) {
        return avatarService.getAllAvatars(pageNumber, pageSize);
    }
}
