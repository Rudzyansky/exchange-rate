package ru.ft.exchangerate.services;

import org.springframework.stereotype.Service;
import ru.ft.exchangerate.giphy.GifsGiphyClient;
import ru.ft.exchangerate.giphy.MediaGiphyClient;

@Service
public class GiphyService {

    private final GifsGiphyClient gifsGiphyClient;
    private final MediaGiphyClient mediaGiphyClient;

    public GiphyService(GifsGiphyClient gifsGiphyClient, MediaGiphyClient mediaGiphyClient) {
        this.gifsGiphyClient = gifsGiphyClient;
        this.mediaGiphyClient = mediaGiphyClient;
    }

    public byte[] getRandom(String tag) {
        String id = gifsGiphyClient.getRandom(tag).data.id;
        return mediaGiphyClient.gif(id);
    }
}
