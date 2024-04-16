package com.example.mission.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> getList () {

        List<Article> articleList = this.articleRepository.findAll();

        if (articleList.isEmpty()) {
            Article article = new Article();
            article.setTitle("newTitle...");
            article.setContent("");
            article.setCreateDate(LocalDateTime.now());
            this.articleRepository.save(article);
        }
        return articleList ;
    }
    public Article create () {

        Article article = new Article();
        article.setTitle("newTitle...");
        article.setContent("");
        article.setCreateDate(LocalDateTime.now());
        return this.articleRepository.save(article);
    }
    public Article getArticle (Integer id) {
        Optional<Article> article = this.articleRepository.findById(id);
        if (article.isPresent()) {
            return article.get();
        }
        else {
            throw new DataNotFoundException("article not found");
        }
    }
    public void updateArticle(Article article) {
        this.articleRepository.save(article);
    }
    public void delete(Integer id) {
        this.articleRepository.deleteById(id);
    }
}
