package com.TwitterClone.ProjectBackend.service;

import com.TwitterClone.ProjectBackend.model.Hashtag;
import com.TwitterClone.ProjectBackend.model.Notification;
import com.TwitterClone.ProjectBackend.model.Tweet;
import com.TwitterClone.ProjectBackend.repository.HashtagRepository;
import com.TwitterClone.ProjectBackend.repository.NotificationRepository;
import com.TwitterClone.ProjectBackend.repository.TweetRepository;
import com.TwitterClone.ProjectBackend.repository.UserRepository;
import com.TwitterClone.ProjectBackend.userManagement.User;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Initialize the database with some examples data
 */
@Service
public class DBInitializer {
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashtagRepository trendRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NotificationRepository notificationRepository;

    @Value("${admin.pass}")
    String adminPass;

    /**
     * Init the database
     *
     * @throws IOException
     */
    @PostConstruct
    public void init() throws IOException {

        //Sample Users
        //User user1 = new User("Rubiu5", "Elon Mú", "I will buy this project", "rubius@gmail.com", passwordEncoder.encode("rubius"), LocalDate.of(2013, 10, 13), "BANNED");
        User user1 = new User("elon", "Elon Mú", "I will buy this project", "elonmusk@gmail.com", passwordEncoder.encode("elon"), LocalDate.of(2013, 10, 13), "BANNED");

        user1.setEnabled(false);

        User user2 = new User("Keyland71", "KOI KEYLAND71", "19 y/o\nRocket League proplayer for @KOI", "example2@gmail.com", passwordEncoder.encode("examplePassword2"), LocalDate.of(2018, 4, 21), "PUBLIC");

        User user3 = new User("antonioalanxs", "Alanís", "", "example3@gmail.com", passwordEncoder.encode("examplePassword3"), LocalDate.of(2019, 8, 7), "PUBLIC");

        User user4 = new User("ibai", "Ibai", "Sigue a nuestros equipos @KOI y @PorcinosFC, http://twitch.tv/ibai", "ibai@gmail.com", passwordEncoder.encode("ibai"), LocalDate.of(2014, 8, 5), "VERIFIED");

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        User testUser = new User("user", passwordEncoder.encode("pass"), "twittercloneuser@gmail.com", "USER");
        testUser.setNickname("user");
        userRepository.save(testUser);

        User admin = new User("admin", adminPass, "admin@mail.com", "ADMIN");
        admin.setNickname("admin");
        userRepository.save(admin);

        testUser.addFollower(user3);
        user3.addFollowed(testUser);

        user3.addFollower(testUser);
        testUser.addFollowed(user3);

        user4.addFollower(user1);
        user1.addFollowed(user4);

        user3.addFollower(user2);
        user2.addFollowed(user3);

        user2.addFollower(user4);
        user4.addFollowed(user2);

        user1.addFollower(user3);
        user3.addFollowed(user1);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);


        //Sample Tweets
        //@ibai
        Blob[] images = new Blob[]{
                null,
                null,
                null,
                null
        };
        Tweet tweet5 = new Tweet("Test tweet", user4, LocalDateTime.of(2023, 02, 14, 20, 00, 00), images);

        Resource image = new ClassPathResource("example_data/bkavgpt.png");
        images = new Blob[]{
                BlobProxy.generateProxy(image.getInputStream(), image.contentLength()),
                null,
                null,
                null
        };
        Tweet tweet6 = new Tweet("BKAV phát triển trợ lý ảo tương tự ChatGPT #BKAV", user4, LocalDateTime.of(2023, 02, 14, 21, 00, 00), images);

        images = new Blob[]{
                null,
                null,
                null,
                null
        };
        Tweet tweet10 = new Tweet("Creéis que lo de Shakira iba por Piqué?", user4, LocalDateTime.of(2023, 01, 12, 1, 55, 05), images);
        tweetRepository.save(tweet6);
        tweetRepository.save(tweet10);
        tweetRepository.save(tweet5);

        //@Rubiu5
        Resource image1 = new ClassPathResource("example_data/elon_tweet1.jpg");
//        Resource image2 = new ClassPathResource("example_data/tweet3_2.jpg");
//        Resource image3 = new ClassPathResource("example_data/tweet3_3.jpg");
        images = new Blob[]{
                BlobProxy.generateProxy(image1.getInputStream(), image1.contentLength()),
                null,
                null,
                null
        };
        Tweet tweet4 = new Tweet("Next I'm buying Coca-Cola to put the cocaine back in", user1, LocalDateTime.of(2024, 06, 26, 20, 00, 00), images);

        images = new Blob[]{
                null,
                null,
                null,
                null
        };
        Tweet tweet3 = new Tweet("People often think they’re breathing oxygen, but are actually breathing nitrogen (78%) with a side serving of oxygen (21%) in argon sauce (1%), spiced up with CO2, H2O & a dash of neon & krypton (etc.)", user1, LocalDateTime.of(2023, 02, 14, 20, 30, 05), images);
        tweetRepository.save(tweet4);
        tweetRepository.save(tweet3);
        //@Keyland71
        image1 = new ClassPathResource("example_data/tweet1.jpg");
        images = new Blob[]{
                BlobProxy.generateProxy(image1.getInputStream(), image1.contentLength()),
                null,
                null,
                null
        };
        //Tweet tweet1 = new Tweet("Fotito con mi fan \uD83D\uDCAA\uD83C\uDFFD\uD83D\uDCAA\uD83C\uDFFD\uD83D\uDD25\uD83D\uDD25", user2, LocalDateTime.of(2022, 06, 14, 16, 03, 00), images);

        image1 = new ClassPathResource("example_data/tweet2_1.jpg");
        Resource image2 = new ClassPathResource("example_data/tweet2_2.jpg");
        Resource image3 = new ClassPathResource("example_data/tweet2_3.jpg");
        Resource image4 = new ClassPathResource("example_data/tweet2_4.jpg");
        images = new Blob[]{
                BlobProxy.generateProxy(image1.getInputStream(), image1.contentLength()),
                BlobProxy.generateProxy(image2.getInputStream(), image2.contentLength()),
                BlobProxy.generateProxy(image3.getInputStream(), image3.contentLength()),
                BlobProxy.generateProxy(image4.getInputStream(), image4.contentLength())
        };
        Tweet tweet2 = new Tweet("#MarvelsSpiderManMilesMorales", user2, LocalDateTime.of(2021, 8, 16, 20, 00, 00), images);

        images = new Blob[]{
                null,
                null,
                null,
                null
        };
        Tweet tweet9 = new Tweet("biết đâu backend đang dùng api chatgpt \uD83D\uDE0F", user2, LocalDateTime.of(2023, 02, 20, 14, 9, 00), images);
       // tweetRepository.save(tweet1);
        tweetRepository.save(tweet2);
        tweetRepository.save(tweet9);

        //@antonioalanxs
        image1 = new ClassPathResource("example_data/tweet7.jpg");
        images = new Blob[]{
                BlobProxy.generateProxy(image1.getInputStream(), image1.contentLength()),
                null,
                null,
                null
        };
        Tweet tweet7 = new Tweet("En primaria deberían poner una asignatura de coger aceitunas @Keyland71", user3, LocalDateTime.of(2023, 02, 20, 14, 03, 00), images);

        //@user
        images = new Blob[]{
                null,
                null,
                null,
                null
        };
        Tweet tweet12 = new Tweet("I am using Twitter; the best app", testUser, LocalDateTime.of(2023, 3, 1, 0, 00, 00), images);
        tweetRepository.save(tweet12);
        //Comments
        images = new Blob[]{
                null,
                null,
                null,
                null
        };
       // Tweet tweet8 = new Tweet("Tienes razón \uD83E\uDD75\uD83E\uDD75", user3, LocalDateTime.of(2023, 02, 20, 14, 04, 00), images);
        Tweet tweet8 = new Tweet("Mạnh dạn xuống 2 tỷ để cùng anh Quảng ra biển lớn  \uD83C\uDF0A \uD83C\uDF0A", user3, LocalDateTime.of(2023, 02, 20, 14, 04, 00), images);
        Tweet tweet100 = new Tweet("AI mới sẽ có tên: Biết bố mày là AI không", user3, LocalDateTime.of(2023, 02, 20, 14, 12, 00), images);

        tweetRepository.save(tweet7);
        tweetRepository.save(tweet8);
        tweetRepository.save(tweet100);

        tweet6 = tweetRepository.findById(tweet6.getId()).get();
        //Comments
        tweet6.addComment(tweet8);
        tweet6.addComment(tweet9);
        tweet6.addComment(tweet100);
        tweetRepository.save(tweet6);

        //Hashtags
        Set<Tweet> tweetsSet = new HashSet<>();
        tweetsSet.add(tweet6);
        Hashtag trend3 = new Hashtag("BKAV", tweetsSet);
        tweetsSet = new HashSet<>();
        Hashtag trend1 = new Hashtag("RCLS", tweetsSet);
        tweetsSet = new HashSet<>();
        tweetsSet.add(tweet2);
        Hashtag trend2 = new Hashtag("MarvelSpiderManMilesMorales", tweetsSet);
        tweetsSet = new HashSet<>();
        Hashtag trend4 = new Hashtag("Tailwind", tweetsSet);
        tweetsSet = new HashSet<>();
        Hashtag trend5 = new Hashtag("Pokemon", tweetsSet);

        trendRepository.save(trend1);
        trendRepository.save(trend2);
        trendRepository.save(trend3);
        trendRepository.save(trend4);
        trendRepository.save(trend5);

        //Interactions
        tweet5.addLike(user3);
        tweet8.addRetweet(user3);
        tweet8.addRetweet(user4);
        tweetRepository.save(tweet5);
        tweetRepository.save(tweet8);

        //Sample notifications
        Tweet tweet = tweet5;
        User user = user3;
        Notification notification = new Notification(tweet, tweet.getUser(), user, LocalDateTime.of(2022, 06, 14, 17, 0, 0), "LIKE");
        notificationRepository.save(notification);

        tweet = tweet8;
        user = user4;
        notification = new Notification(tweet, tweet.getUser(), user, LocalDateTime.of(2023, 02, 20, 14, 3, 0), "RETWEET");
        notificationRepository.save(notification);

        tweet = tweet8;
        user = user3;
        notification = new Notification(tweet, tweet.getUser(), user, LocalDateTime.of(2023, 02, 20, 14, 9, 0), "MENTION");
        notificationRepository.save(notification);

        //Follows
        User user_notificated = user4;
        user = user1;
        notification = new Notification(null, user_notificated, user, LocalDateTime.of(2020, 04, 11, 15, 3, 0), "FOLLOW");
        notificationRepository.save(notification);

        user_notificated = user3;
        user = user2;
        notification = new Notification(null, user_notificated, user, LocalDateTime.of(2020, 04, 11, 15, 3, 0), "FOLLOW");
        notificationRepository.save(notification);

        user_notificated = user2;
        user = user4;
        notification = new Notification(null, user_notificated, user, LocalDateTime.of(2020, 04, 11, 15, 3, 0), "FOLLOW");
        notificationRepository.save(notification);

        user_notificated = user1;
        user = user3;
        notification = new Notification(null, user_notificated, user, LocalDateTime.of(2020, 04, 11, 15, 3, 0), "FOLLOW");
        notificationRepository.save(notification);

        user_notificated = user3;
        user = testUser;
        notification = new Notification(null, user_notificated, user, LocalDateTime.now(), "FOLLOW");
        notificationRepository.save(notification);
        user_notificated = testUser;
        user = user3;
        notification = new Notification(null, user_notificated, user, LocalDateTime.now(), "FOLLOW");
        notificationRepository.save(notification);

        //Bookmarks
        //testUser.getBookmarks().add(tweet1);
        userRepository.save(testUser);

        List<User> users = this.userRepository.findAll();
        user1 = users.get(0);
        user2 = users.get(1);
        user3 = users.get(2);
        user4 = users.get(3);
        testUser = users.get(4);
        admin = users.get(5);

        String[] files = {"example_data/elon_profilepic.jpg", "example_data/elon_profilebanner.jpg"};
        user1.setImages(files);
        files = new String[]{"example_data/KOI_KEYLAND_profilepic.jpg", "example_data/KOI_KEYLAND_profilebanner.jpg"};
        user2.setImages(files);
        files = new String[]{"example_data/Alanis_profilepic.jpg", "example_data/Alanis_profilebanner.jpg"};
        user3.setImages(files);
        files = new String[]{"example_data/Ibai_profilepic.jpg", "example_data/Ibai_profilebanner.jpg"};
        user4.setImages(files);
        files = new String[]{"example_data/Default_profilepic.jpg", "example_data/Default_profilebanner.jpg"};
        testUser.setImages(files);
        admin.setImages(files);

        this.userRepository.save(user1);
        this.userRepository.save(user2);
        this.userRepository.save(user3);
        this.userRepository.save(user4);
        this.userRepository.save(testUser);
        this.userRepository.save(admin);
    }
}
