package com.sedmelluq.discord.lavaplayer.source.vk;

import com.sedmelluq.discord.lavaplayer.container.playlists.ExtendedM3uParser;
import com.sedmelluq.discord.lavaplayer.source.stream.M3uStreamSegmentUrlProvider;
import com.sedmelluq.discord.lavaplayer.tools.io.HttpClientTools;
import com.sedmelluq.discord.lavaplayer.tools.io.HttpInterface;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sedmelluq.discord.lavaplayer.tools.io.HttpClientTools.fetchResponseLines;

public class VKSegmentUrlProvider extends M3uStreamSegmentUrlProvider {
    private static final Logger log = LoggerFactory.getLogger(VKSegmentUrlProvider.class);

    private final String streamURL;
    private final VKAudioSourceManager manager;

    private String streamSegmentPlaylistUrl;
    private long tokenExpirationTime;

    /**
     * @param streamURL identifier.
     * @param manager Twitch source manager.
     */
    public VKSegmentUrlProvider(String streamURL, VKAudioSourceManager manager) {
        this.streamURL = streamURL;
        this.manager = manager;
        this.tokenExpirationTime = -1;
    }

    @Override
    protected String getQualityFromM3uDirective(ExtendedM3uParser.Line directiveLine) {
        return directiveLine.directiveArguments.get("VIDEO");
    }

    @Override
    protected String fetchSegmentPlaylistUrl(HttpInterface httpInterface) throws IOException {
        List<SegmentInfo> segments = loadStreamSegmentsList(httpInterface, streamURL);

        for (SegmentInfo segment : segments) {
            System.out.println(segment.url);
        }

        return streamSegmentPlaylistUrl;
    }

    @Override
    protected HttpUriRequest createSegmentGetRequest(String url) {
        return manager.createGetRequest(url);
    }
}
