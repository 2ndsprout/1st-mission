package com.example.mission.article;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/list")
    public String list (Model model) {
        List<Article> articleList = this.articleService.getList();
        model.addAttribute("articleList", articleList);

        return "article_list";
    }
    @PostMapping("/create")
    public String create (Model model) {
        Article article = this.articleService.create();
        model.addAttribute("article", article);

        return "redirect:/article/list";
    }
    @GetMapping("/detail/{id}")
    public String detail (@PathVariable("id")Integer id,
                          Model model) {
        List<Article> articleList = this.articleService.getList();
        Article article = this.articleService.getArticle(id);
        model.addAttribute("articleList", articleList);
        model.addAttribute("article", article);

        return "article_detail";
    }
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable("id") Integer id,
                         @ModelAttribute("articleForm") @Valid ArticleForm articleForm,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            // 에러가 있을 경우 수정 페이지를 다시 보여줍니다.
            model.addAttribute("article", articleService.getArticle(id));
            model.addAttribute("articleList", articleService.getList());
            return "article_detail";
        }

        // 게시글을 수정합니다.
        Article article = articleService.getArticle(id);

        // 제목이 입력되지 않은 경우 '제목 없음'으로 설정
        article.setTitle(articleForm.getTitle() == null || articleForm.getTitle().isEmpty() ? "제목 없음" : articleForm.getTitle());
        article.setContent(articleForm.getContent());
        articleService.updateArticle(article);

        // 수정이 완료되면 수정된 상세 페이지로 이동합니다.
        return "redirect:/article/detail/" + id;
    }
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id") Integer id,
                         @ModelAttribute("articleForm") ArticleForm articleForm,
                         BindingResult bindingResult) {
        Article article = this.articleService.getArticle(id);

        if (bindingResult.hasErrors()) {
            return "article_detail";
        }

        if (articleForm.getTitle() == null || articleForm.getTitle().isEmpty()) {
            article.setTitle("제목 없음"); // 제목이 입력되지 않은 경우 '제목 없음'으로 설정
        } else {
            article.setTitle(articleForm.getTitle());
        }

        article.setContent(articleForm.getContent());

        this.articleService.updateArticle(article); // 수정된 게시글 저장

        return "redirect:/article/detail/" + id;
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        this.articleService.delete(id);
        return "redirect:/article/list";
    }

}
