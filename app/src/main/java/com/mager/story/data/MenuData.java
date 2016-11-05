package com.mager.story.data;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class MenuData {
    public int version;
    public int versionMenu;
    public int versionPhoto;
    public int versionStory;
    public int versionAudio;
    public int versionVideo;
    public List<Photo> photo = new ArrayList<>();
    public List<Story> story = new ArrayList<>();
    public List<Audio> audio = new ArrayList<>();
    public List<Video> video = new ArrayList<>();

    @Parcel
    public static class Photo {
        public String code;
        public int count;
        public String name;
    }

    @Parcel
    public static class Story {
        public String code;
        public String chapter;
        public String title;
    }

    @Parcel
    public static class Audio {
        public String code;
        public String name;
    }

    @Parcel
    public static class Video {
        public String code;
        public String name;
        public boolean protect;
    }
}
