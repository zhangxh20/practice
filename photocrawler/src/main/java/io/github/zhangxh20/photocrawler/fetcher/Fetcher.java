package io.github.zhangxh20.photocrawler.fetcher;

import java.util.List;

public interface Fetcher {

    List<String> fetchPhotoUrl(String url);
}
