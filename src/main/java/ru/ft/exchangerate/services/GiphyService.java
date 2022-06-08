package ru.ft.exchangerate.services;

import org.springframework.stereotype.Service;
import ru.ft.exchangerate.giphy.GifsGiphyClient;
import ru.ft.exchangerate.giphy.GiphyException;
import ru.ft.exchangerate.giphy.MediaGiphyClient;
import ru.ft.exchangerate.giphy.SingleResponse;

@Service
public class GiphyService {

    private final GifsGiphyClient gifsGiphyClient;
    private final MediaGiphyClient mediaGiphyClient;

    public GiphyService(GifsGiphyClient gifsGiphyClient, MediaGiphyClient mediaGiphyClient) {
        this.gifsGiphyClient = gifsGiphyClient;
        this.mediaGiphyClient = mediaGiphyClient;
    }

    public byte[] getRandom(String tag) {
        SingleResponse response = gifsGiphyClient.getRandom(tag);
        if (response.meta.status != 200) throw new GiphyException(response.meta.msg);
        if (response.data == null) throw GiphyException.NOT_FOUND;

        return mediaGiphyClient.gif(response.data.id);
    }
}
