package com.mager.story.datamodel;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class MenuDataModel {
    public int version;
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
    }
}
