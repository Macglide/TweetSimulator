package za.co.mycompany.twittersimulater.web.rest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import za.co.mycompany.twittersimulater.service.FileProcessingService;
import za.co.mycompany.twittersimulater.service.FileProcessingServiceImpl;

public class TwitterResourceTest {

    @Mock
    private FileProcessingService fileProcessingService;

    @InjectMocks
    private TwitterResource twitterResource;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test //success
    public void givenUserAndTweetFiles_whenUpdateTwitterFeed_thenPrintTweetsToConsole() {
        //given
        MockMultipartFile userfile = new MockMultipartFile(
            "userfile",
            "userfile.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "<<text/plain>>".getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile tweetfile = new MockMultipartFile(
            "userfile",
            "userfile.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "<<text/plain>>".getBytes(StandardCharsets.UTF_8)
        );

        //when
        twitterResource.updateTwitterFeed(userfile, tweetfile);

        //then
        verify(fileProcessingService, times(1)).printUsersAndTweetsToConsole();
    }
}
