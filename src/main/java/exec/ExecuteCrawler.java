package exec;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.motozone.article.model.ArticleBean;
import com.motozone.article.model.service.ArticleService;
import com.motozone.article.model.util.Crawler;
import com.motozone.article.model.util.UrlParser;

public class ExecuteCrawler {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(com.motozone.config.SpringJavaConfig.class);
		SessionFactory factory = (SessionFactory) context.getBean("sessionFactory");
		
		
		String forumListUrl = "https://www.mobile01.com/forumlist.php?f=29";

		// get url parser instance
		UrlParser urlParser = new UrlParser();
		
		// get html parser instance
		Crawler crawler = new Crawler();
		
		// get Service
		ArticleService dao = (ArticleService) context.getBean("articleService");
		
		
		// get article url
		List<String> forumList = urlParser.getM01ForumList(forumListUrl);
		
		
		// finish counter
		int iCount = 0;
		
		
		for(String forumUrl : forumList) {
			List<String> topicList = urlParser.getM01TopicList(forumUrl);
			
			for(String topicUrl : topicList) {
				List<String> articleList = urlParser.getM01ArticleList(topicUrl);
				
				
				for(String articleUrl : articleList) {
					
					
					// get each post in bean list
					List<ArticleBean> beanList = crawler.parseFromM01(articleUrl);
					
					// get Hibernate transaction
					factory.getCurrentSession().beginTransaction();
					
					// insert into database
					dao.addPosts(beanList);
					
					// commit transaction after insert posts
					factory.getCurrentSession().getTransaction().commit();
					
				}
				
				iCount++;
				
				System.out.println("Already finished " + iCount + " posts");
				
				// get 200 post at each forum
				if((iCount % 200) == 0) {
					System.out.println("-------------------finish-------------------");
					break;
				}
			}
			
			// get 25 forum(5000 = 25 * 200) , then stop crawler, 
			if(iCount == 5000) {
				System.out.println("----------------All finish------------------");
				break;
			}
		}
		
		
		// close resource
		factory.close();
		((ConfigurableApplicationContext) context).close();
	}

}
