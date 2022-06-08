package ru.ft.exchangerate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ft.exchangerate.giphy.GifsGiphyClient;
import ru.ft.exchangerate.giphy.GiphyException;
import ru.ft.exchangerate.giphy.MediaGiphyClient;
import ru.ft.exchangerate.giphy.SingleResponse;
import ru.ft.exchangerate.services.GiphyService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GiphyTests {

    @Autowired
    private GifsGiphyClient gifsGiphyClient;

    @Autowired
    private MediaGiphyClient mediaGiphyClient;

    @Autowired
    private GiphyService giphyService;

    @Test
    public void unknownTagTest() {
        Exception exception = assertThrows(GiphyException.class, () ->
                giphyService.getRandom("unknown_tag")
        );
        String expected = GiphyException.NOT_FOUND.getMessage();
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected), actual);
    }

    @Test
    public void randomTest() {
        SingleResponse response = gifsGiphyClient.getRandom("random");
        assertNotNull(response.data, response.meta.msg);
        assertFalse(response.data.id.isEmpty(), response.meta.msg);
    }

    @Test
    public void serviceRandomTest() {
        assertTrue(giphyService.getRandom("random").length > 0);
    }
}
