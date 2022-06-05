package ru.ft.exchangerate.giphy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "giphy", url = "api.giphy.com", path = "/v1/gifs")
public interface GifsGiphyClient {
    @GetMapping("/random?api_key=${giphy.api-key}&rating=${giphy.rating}&tag={tag}")
    SingleResponse getRandom(@PathVariable String tag);
}
