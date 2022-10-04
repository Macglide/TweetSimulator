package za.co.mycompany.twittersimulater.service;

public interface FileProcessingService {
    void handleUserFileLines(String string);

    void handleTweetFileLines(String string);

    void printUsersAndTweetsToConsole();
}
