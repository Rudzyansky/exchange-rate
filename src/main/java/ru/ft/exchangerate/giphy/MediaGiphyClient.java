package ru.ft.exchangerate.giphy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "giphy-media", url = "${giphy-media.domain}", path = "/media")
public interface MediaGiphyClient {

    @GetMapping("/{id}/giphy.gif")
    byte[] gif(@PathVariable String id);
}
