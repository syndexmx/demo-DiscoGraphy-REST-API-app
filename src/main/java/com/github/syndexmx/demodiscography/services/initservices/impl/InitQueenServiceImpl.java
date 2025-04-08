package com.github.syndexmx.demodiscography.services.initservices.impl;

import com.github.syndexmx.demodiscography.domain.*;
import com.github.syndexmx.demodiscography.domain.enums.Sex;
import com.github.syndexmx.demodiscography.services.*;
import com.github.syndexmx.demodiscography.services.initservices.InitQueenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InitQueenServiceImpl implements InitQueenService {

    private ArtistService artistService;
    private AlbumService albumService;
    private SongService songService;
    private GroupService groupService;
    private RecordingService recordingService;

    @Autowired
    public InitQueenServiceImpl(ArtistService artistService,
                                AlbumService albumService,
                                SongService songService,
                                GroupService groupService,
                                RecordingService recordingService) {
        this.artistService = artistService;
        this.albumService = albumService;
        this.songService = songService;
        this.groupService = groupService;
        this.recordingService = recordingService;
    }

    @Override
    public String createQueen() {
        StringBuilder responseBuilder = new StringBuilder();
        Artist freddie = artistService.create(getFreddieMercury());
        Artist taylor = artistService.create(getRogerTaylor());
        Artist brian = artistService.create(getBrianMay());
        Artist deacon = artistService.create(getJohnDeackon());
        Group queen = groupService.create(getQueen(List.of(freddie, taylor, brian, deacon)));
        Song bohemianRhapsody = songService.create(getBohemianRhapsodySong(List.of(freddie)));
        Recording bohemian = recordingService.create(
                getBohemianRhapsodyRecording(bohemianRhapsody,
                        List.of(freddie, taylor, brian, deacon),
                        List.of(queen)));
        Song loveOfMyLifeSong = songService.create(getLoveOfMyLiveSong(List.of(freddie)));
        Recording loveOfMyLife1975 = recordingService.create(
                getLoveOfMyLiveRecording(loveOfMyLifeSong,
                        List.of(freddie, taylor, brian, deacon),
                        List.of(queen)));
        Album aNightAtTheOpera = albumService.create(getNingAtTheOpera(
                List.of(bohemian, loveOfMyLife1975),List.of(queen), List.of()
        ));
        responseBuilder.append(aNightAtTheOpera.toString());
        return responseBuilder.toString();
    }

    private Artist getFreddieMercury() {
        return Artist.builder()
                .name("Freddie Mercury")
                .firstName("Farrokh")
                .secondName("")
                .lastName("Bulsara")
                .birthDate(LocalDate.parse("1946-09-05"))
                .birthPlace("Zanzibar")
                .sex(Sex.MALE)
                .build();
    }

    private Artist getRogerTaylor() {
        return Artist.builder()
                .name("Roger Taylor")
                .firstName("Roger")
                .secondName("Meddows")
                .lastName("Taylor")
                .birthDate(LocalDate.parse("1949-07-26"))
                .birthPlace("Kings-Linn, Norfolk, England (UK)")
                .sex(Sex.MALE)
                .build();
    }

    private Artist getBrianMay() {
        return Artist.builder()
                .name("Brian May")
                .firstName("Brian")
                .secondName("Harold")
                .lastName("May")
                .birthDate(LocalDate.parse("1947-07-19"))
                .birthPlace("London, England (UK)")
                .sex(Sex.MALE)
                .build();
    }

    private Artist getJohnDeackon() {
        return Artist.builder()
                .name("John Deacon")
                .firstName("John")
                .secondName("Richard")
                .lastName("Deacon")
                .birthDate(LocalDate.parse("1951-10-19"))
                .birthPlace("London, England (UK)")
                .sex(Sex.MALE)
                .build();
    }

    private Group getQueen(List<Artist> artistList) {
        return Group.builder()
                .name("Queen")
                .place("London")
                .synonym("The Queen")
                .year(1970)
                .artistsList(artistList)
                .build();
    }

    public Song getBohemianRhapsodySong(List<Artist> authoursList) {
        return Song.builder()
                .title("Bohemian Rhapsody")
                .year(1975)
                .synonym("The Bohemian Rhapsody")
                .authoursList(authoursList)
                .build();
    }

    public Recording getBohemianRhapsodyRecording(Song song,
                                               List<Artist> artistList,
                                               List<Group> groupList) {
        return Recording.builder()
                .song(song)
                .place("Monmouth, Wales")
                .studio("Rockfield 1")
                .year(1975)
                .length(4 * 5 + 55)
                .artistList(artistList)
                .groupList(groupList)
                .build();
    }

    public Song getLoveOfMyLiveSong(List<Artist> authoursList) {
        return Song.builder()
                .title("Love of My Live")
                .year(1975)
                .synonym("")
                .authoursList(authoursList)
                .build();
    }

    public Recording getLoveOfMyLiveRecording(Song song,
                                                  List<Artist> artistList,
                                                  List<Group> groupList) {
        return Recording.builder()
                .song(song)
                .place("Monmouth, Wales")
                .studio("Rockfield 1")
                .year(1975)
                .length(4 * 3 + 39)
                .artistList(artistList)
                .groupList(groupList)
                .build();
    }

    public static Album getNingAtTheOpera(List<Recording> recordingList,
                                          List<Group> groupList,
                                          List<Artist> artistList) {
        return Album.builder()
                .recordingList(recordingList)
                .groupList(groupList)
                .title("A Night at The Opera")
                .year(1975)
                .artistList(artistList)
                .build();
    }

}
