package com.motomarket.controlller;

import com.motomarket.repository.IPostRepository;
import com.motomarket.service.dto.DetailMotorDTO;
import com.motomarket.service.dto.ImageDTO;
import com.motomarket.service.dto.PostDTO;
import com.motomarket.service.dto.UserDTO;
import com.motomarket.service.motor.IDetailMotorService;
import com.motomarket.service.motor.IModelYearService;
import com.motomarket.service.post.IImageService;
import com.motomarket.service.post.IPostService;
import com.motomarket.service.user.IUserService;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private IPostService postService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IDetailMotorService detailMotorService;

    @Autowired
    private IImageService imageService;

    @Autowired
    private IModelYearService modelYearService;

    @Autowired
    private IPostRepository postRepository;

    @GetMapping("/newpost")
    public ModelAndView toPostPage() {
        UserDTO userLogin = userService.getById(1L);
        ModelAndView modelAndView = new ModelAndView("new-post");
        modelAndView.addObject("post", new PostDTO());
        modelAndView.addObject("userLogin", userLogin);
        return modelAndView;
    }

    @PostMapping("/newpost")
    public String handlePost(@ModelAttribute PostDTO post, @RequestParam("ip-upload-multi") MultipartFile[] files, @RequestParam("moder-year-id") Long moderYearId, @RequestParam("color-id") Long colorId) throws IOException {
        UserDTO user = userService.getById(1L);
        DetailMotorDTO detailMotor = detailMotorService.getByModelYearAndColorMotor(moderYearId, colorId);
        Long newPostId = postService.savePost(post, user, detailMotor, files);
        return "redirect:/post/detailpost/" + newPostId;
    }


    @GetMapping("/detailpost/{postId}")
    public ModelAndView viewDetailPost(@PathVariable Long postId) {
        PostDTO postDTO = postService.getById(postId);
        List<ImageDTO> imageList = imageService.findAllByPostDTO(postDTO);
        DetailMotorDTO detailMotorDTO = postDTO.getDetailMotorDTO();
        ModelAndView modelAndView = new ModelAndView("moto-detail");
        UserDTO userDTO = postDTO.getUserDTO();
        PrettyTime p = new PrettyTime(new Locale("vi"));
        String time = p.format(postDTO.getPostDate());
        String timeFormat = time.substring(0, 1).toUpperCase() + time.substring(1);
        String seriesName = detailMotorDTO.getBrandMotor().getBrandName() + " " + detailMotorDTO.getSeriesMotor().getSeriesName();
        List<PostDTO> relatedPostDTO = postService.findTopBySeriesMotor(8, seriesName);
        List<PostDTO> newPostList = postService.findListOfLatestPosts(15);
        modelAndView.addObject("post", postDTO);
        modelAndView.addObject("images", imageList);
        modelAndView.addObject("detail", detailMotorDTO);
//        modelAndView.addObject("timePost", timeFormat);
        modelAndView.addObject("postsRelated", relatedPostDTO);
        modelAndView.addObject("newPosts", newPostList);
        modelAndView.addObject("postOwner", userDTO);
        return modelAndView;
    }


    @GetMapping("/edit/{id}")
    public ModelAndView showEditPost(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("edit-post");
        PostDTO postDTO = postService.getById(id);
        UserDTO userLogin = userService.getById(1L);
        modelAndView.addObject("userLogin", userLogin);
        modelAndView.addObject("post", postDTO);
        return modelAndView;
    }

    @PostMapping("/edit")
    public String hanlderEditPost(@ModelAttribute PostDTO post,  @RequestParam("ip-upload-multi") MultipartFile[] files) {
        postService.update(post,files);
        return "redirect:/post/detailpost/" + post.getPostId();
    }

}
