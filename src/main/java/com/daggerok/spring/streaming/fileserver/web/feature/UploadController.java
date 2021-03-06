package com.daggerok.spring.streaming.fileserver.web.feature;

import com.daggerok.spring.streaming.fileserver.service.UploadService;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.daggerok.spring.streaming.fileserver.web.IndexPage.REDIRECT_INDEX;

@Controller
@RequiredArgsConstructor
public class UploadController {

  final UploadService uploadService;
  final ConnectionRepository connectionRepository;

  @PostMapping("/upload")
  public String post(@RequestParam("file") final MultipartFile file, final RedirectAttributes redirectAttributes) {

    val connection = connectionRepository.findPrimaryConnection(Facebook.class);
    val displayName = Try.of(() -> connection.getDisplayName())
                         .getOrElse(() -> "anonymous");

    uploadService.upload(file, redirectAttributes, displayName);
    return REDIRECT_INDEX;
  }
}
