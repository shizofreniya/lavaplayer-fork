package com.sedmelluq.discord.lavaplayer.source.vk;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.Units;
import com.sedmelluq.discord.lavaplayer.tools.io.HttpClientTools;
import com.sedmelluq.discord.lavaplayer.tools.io.HttpConfigurable;
import com.sedmelluq.discord.lavaplayer.tools.io.HttpInterface;
import com.sedmelluq.discord.lavaplayer.tools.io.HttpInterfaceManager;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioReference;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.URI;
import java.util.function.Consumer;
import java.util.function.Function;

public class VKAudioSourceManager implements AudioSourceManager, HttpConfigurable {
    private final HttpInterfaceManager httpInterfaceManager;

    public VKAudioSourceManager() {
        httpInterfaceManager = HttpClientTools.createDefaultThreadLocalManager();
    }

    @Override
    public String getSourceName() {
        return "vk";
    }

    @Override
    public AudioItem loadItem(AudioPlayerManager manager, AudioReference reference) {
        System.out.println(reference.identifier);
        if (!reference.identifier.endsWith("index.m3u8")) return null;
        System.out.println("прости если трахнул");

        return new VKAudioTrack(new AudioTrackInfo(
                "VK-TITLE",
                "VK-AUTHOR",
                Units.DURATION_MS_UNKNOWN,
                reference.identifier,
                true,
                reference.identifier
        ), this);
    }

    @Override
    public boolean isTrackEncodable(AudioTrack track) {
        return true;
    }

    @Override
    public void encodeTrack(AudioTrack track, DataOutput output) throws IOException {

    }

    @Override
    public AudioTrack decodeTrack(AudioTrackInfo trackInfo, DataInput input) throws IOException {
        return null;
    }

    @Override
    public void shutdown() {

    }

    /**
     * @param url Request URL
     * @return Request with necessary headers attached.
     */
    public HttpUriRequest createGetRequest(String url) {
        return addClientHeaders(new HttpGet(url));
    }

    /**
     * @param url Request URL
     * @return Request with necessary headers attached.
     */
    public HttpUriRequest createGetRequest(URI url) {
        return addClientHeaders(new HttpGet(url));
    }

    /**
     * @return Get an HTTP interface for a playing track.
     */
    public HttpInterface getHttpInterface() {
        return httpInterfaceManager.getInterface();
    }

    @Override
    public void configureRequests(Function<RequestConfig, RequestConfig> configurator) {
        httpInterfaceManager.configureRequests(configurator);
    }

    @Override
    public void configureBuilder(Consumer<HttpClientBuilder> configurator) {
        httpInterfaceManager.configureBuilder(configurator);
    }

    private static HttpUriRequest addClientHeaders(HttpUriRequest request) {
        //request.setHeader("Accept", "application/vnd.twitchtv.v5+json; charset=UTF-8");
        return request;
    }
}
