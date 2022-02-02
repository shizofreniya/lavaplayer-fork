package com.sedmelluq.discord.lavaplayer.source.vk;

import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.stream.M3uStreamSegmentUrlProvider;
import com.sedmelluq.discord.lavaplayer.source.stream.MpegTsM3uStreamAudioTrack;
import com.sedmelluq.discord.lavaplayer.tools.io.HttpInterface;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.sedmelluq.discord.lavaplayer.track.playback.LocalAudioTrackExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.sedmelluq.discord.lavaplayer.source.twitch.TwitchStreamAudioSourceManager.getChannelIdentifierFromUrl;

public class VKAudioTrack extends MpegTsM3uStreamAudioTrack {
    private static final Logger log = LoggerFactory.getLogger(VKAudioTrack.class);

    private final VKAudioSourceManager sourceManager;
    private final M3uStreamSegmentUrlProvider segmentUrlProvider;

    /**
     * @param trackInfo Track info
     * @param sourceManager Source manager which was used to find this track
     */
    public VKAudioTrack(AudioTrackInfo trackInfo, VKAudioSourceManager sourceManager) {
        super(trackInfo);

        this.sourceManager = sourceManager;
        this.segmentUrlProvider = new VKSegmentUrlProvider(trackInfo.identifier, sourceManager);
    }

    @Override
    protected M3uStreamSegmentUrlProvider getSegmentUrlProvider() {
        return segmentUrlProvider;
    }

    @Override
    protected HttpInterface getHttpInterface() {
        return sourceManager.getHttpInterface();
    }

    @Override
    public void process(LocalAudioTrackExecutor localExecutor) throws Exception {
        log.debug("Starting to play VK m3u8 playlist {}.", trackInfo.identifier);

        super.process(localExecutor);
    }

    @Override
    protected AudioTrack makeShallowClone() {
        return new VKAudioTrack(trackInfo, sourceManager);
    }

    @Override
    public AudioSourceManager getSourceManager() {
        return sourceManager;
    }
}
