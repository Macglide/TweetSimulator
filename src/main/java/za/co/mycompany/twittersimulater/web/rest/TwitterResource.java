package za.co.mycompany.twittersimulater.web.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import za.co.mycompany.twittersimulater.service.FileProcessingService;

@Service
public class TwitterResource implements za.co.mycompany.twittersimulater.web.api.TwitterFeedApiDelegate {

    @Autowired
    private FileProcessingService fileProcessingService;

    Logger logger = LoggerFactory.getLogger(TwitterResource.class);
    Map<String, ArrayList<String>> userMap = new HashMap<>();

    LinkedHashMap<String, ArrayList<String>> userTweetsMap = new LinkedHashMap<>();

    @Override
    public ResponseEntity<String> updateTwitterFeed(MultipartFile userfile, MultipartFile tweetfile) {
        try {
            //reading and processing user file
            InputStream userfileInputStream = userfile.getInputStream();
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(userfileInputStream, StandardCharsets.UTF_8));

            fileReader.lines().forEach(s -> fileProcessingService.handleUserFileLines(s));

            fileReader.close();
            userfileInputStream.close();

            //reading and processing tweet file
            InputStream tweetFileInputStream = tweetfile.getInputStream();
            BufferedReader tweetFileReader = new BufferedReader(new InputStreamReader(tweetFileInputStream, StandardCharsets.UTF_8));

            tweetFileReader.lines().forEach(s -> fileProcessingService.handleTweetFileLines(s));

            tweetFileReader.close();
            tweetFileInputStream.close();

            //Printing the userTweets Map
            fileProcessingService.printUsersAndTweetsToConsole();
            /*
            for (Map.Entry<String, ArrayList<String>> entry : userTweetsMap.entrySet())
            {
                logger.info(entry.getKey());
                for (int i = 0; i < entry.getValue().size(); i++)
                {
                    logger.info(entry.getValue().get(i));
                }
            }
*/

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok("Successfully uploaded the files. Please have a look at the console");
    }

    private void handleUserFileLines(String string) {
        String user;

        String[] arrayOfUsers = string.split(" follows |, ");

        user = arrayOfUsers[0];

        //Add the user to the map with an empty list of followers
        userMap.put(user, new ArrayList<>());

        ArrayList<String> followers = new ArrayList<>(Arrays.asList(arrayOfUsers).subList(1, arrayOfUsers.length));

        //add the individuals followed by user
        //assuming the file keeps record of who each user follows on every line that they occur
        userMap.put(user, followers);
    }

    private void handleTweetFileLines(String string) {
        String[] arrayOfTweets = string.split(">");
        String tweetUser = arrayOfTweets[0];
        String tweet = arrayOfTweets[1];

        ArrayList<String> tweets = new ArrayList<>();

        if (userTweetsMap.get(tweetUser) != null) {
            ArrayList<String> userTweets = userTweetsMap.get(tweetUser);
            userTweets.add("@" + tweetUser + ":" + tweet);
            Collections.sort(userTweets);
            userTweetsMap.put(tweetUser, userTweets);
        } else {
            tweets.add("@" + tweetUser + ":" + tweet);
            Collections.sort(tweets);
            userTweetsMap.put(tweetUser, tweets);
        }

        //add tweet to the user's list of individuals he follows
        if (userMap.get(tweetUser) != null) {
            //get the users the user is following
            ArrayList<String> usersTheUserIsFollowing = userMap.get(tweetUser);

            ArrayList<String> userTweets;

            for (String userTheUserIsFollowing : usersTheUserIsFollowing) {
                if (userTweetsMap.get(userTheUserIsFollowing) != null) {
                    //get the tweets of each user the user is following
                    userTweets = userTweetsMap.get(userTheUserIsFollowing); //add this tweets to current tweetUser

                    //get tweets for the current tweetUser
                    ArrayList<String> tweetsForCurrentUser = userTweetsMap.get(tweetUser);

                    //add the tweets of the users the user is following to its list of tweets
                    tweetsForCurrentUser.addAll(userTweets);

                    //update the map of the current tweet user with the updated list of tweets that includes the ones of those he follows
                    userTweetsMap.put(tweetUser, tweetsForCurrentUser);
                }
            }
        }
    }
}
