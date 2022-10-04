package za.co.mycompany.twittersimulater.web.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import za.co.mycompany.twittersimulater.service.FileProcessingService;
import za.co.mycompany.twittersimulater.service.FileProcessingServiceImpl;

@Service
public class TwitterResource implements za.co.mycompany.twittersimulater.web.api.TwitterFeedApiDelegate {

    @Autowired
    private final FileProcessingService fileProcessingService;

    public TwitterResource(FileProcessingService fileProcessingService) {
        this.fileProcessingService = fileProcessingService;
    }

    @Override
    public ResponseEntity<String> updateTwitterFeed(MultipartFile userfile, MultipartFile tweetfile) {
        try {
            //reading and processing user file
            InputStream userfileInputStream = userfile.getInputStream();
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(userfileInputStream, StandardCharsets.UTF_8));

            fileReader.lines().forEach(fileProcessingService::handleUserFileLines);

            fileReader.close();
            userfileInputStream.close();

            //reading and processing tweet file
            InputStream tweetFileInputStream = tweetfile.getInputStream();
            BufferedReader tweetFileReader = new BufferedReader(new InputStreamReader(tweetFileInputStream, StandardCharsets.UTF_8));

            tweetFileReader.lines().forEach(fileProcessingService::handleTweetFileLines);

            tweetFileReader.close();
            tweetFileInputStream.close();

            //Printing the userTweets Map
            fileProcessingService.printUsersAndTweetsToConsole();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok("Successfully uploaded the files. Please have a look at the console");
    }
}
