package com.example.watermelon.service;

import com.example.watermelon.dto.ChartResponseDto;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ChartService {

    public List<ChartResponseDto> getChartList() {
        List<ChartResponseDto> result = new ArrayList<>();
        String url = "https://www.melon.com/chart/index.htm";
        String likeUrl = "https://www.melon.com/commonlike/getSongLike.json?contsIds=";
        HttpClient client = HttpClient.newHttpClient();

        try {
            Document doc = Jsoup.connect(url).get(); // 전체 문서
            Element table = doc.select("table").get(0); // 테이블
            Elements rows = table.select("tr"); // 테이블 행

            for (int i=1; i<rows.size(); i++) {
                HashMap<String, String> data = new HashMap<>();
                Element row = rows.get(i);
                Elements cols = row.select("td");

                String dataSongNo = row.attr("data-song-no");
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(likeUrl + dataSongNo))
                        .build();

                int songLike = 0;
                try {
                    HttpResponse<String> response = client.send(request,
                            HttpResponse.BodyHandlers.ofString());
                    JSONObject songLikeObj = new JSONObject(response.body());
                    songLike = songLikeObj.getJSONArray("contsLike").getJSONObject(0).getInt("SUMMCNT"); // 좋아요 수
                } catch (InterruptedException e2) {
                    System.out.println("GET 요청에 실패했습니다.");
                    return result;
                }

                Element info = cols.get(5).select("div.wrap_song_info").get(0);
                String rank = cols.get(1).text();
                String title = info.select("div.rank01").get(0).text();
                String singer = info.select("span.checkEllipsis").get(0).text();
                String album = cols.get(6).text();

                ChartResponseDto song = ChartResponseDto.builder().rank(rank).title(title).singer(singer).album(album).like(songLike).build();

                result.add(song);
            }
        } catch (IOException e) {
            System.out.println("페이지를 가져 올 수 없습니다.");
            return result;
        }

        return result;
    }
}
