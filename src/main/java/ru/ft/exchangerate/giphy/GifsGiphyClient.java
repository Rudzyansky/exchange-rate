package ru.ft.exchangerate.giphy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "giphy", url = "${giphy.url}", path = "/v1/gifs", configuration = GiphyConfiguration.class)
public interface GifsGiphyClient {

    @GetMapping("/random?api_key=${giphy.api-key}&rating=${giphy.rating}&tag={tag}")
    SingleResponse getRandom(@PathVariable String tag);
}
