package DBHandler;

import com.j256.ormlite.field.DatabaseField;

/**
 * Music object which holds table information from the Music table
 *
 * @author Thomas René Gabrielsen
 * @version 11.12.2016
 */
public class Music
{
    //region Fields
    public static final String ID_FIELD = "ID";
    public static final String ARTIST_FIELD = "Artist";
    public static final String SONG_FIELD = "Song";
    public static final String ALBUM_FIELD = "Album";
    public static final String COUNTRY_FIELD = "Country";
    public static final String YEAR_FIELD = "Year";

    @DatabaseField(columnName = ID_FIELD, generatedId = true)
    private int id;

    @DatabaseField(columnName = ARTIST_FIELD, canBeNull = false)
    private String artist;

    @DatabaseField(columnName = SONG_FIELD, canBeNull = false)
    private String song;

    @DatabaseField(columnName = ALBUM_FIELD, canBeNull = false)
    private String album;

    @DatabaseField(columnName = COUNTRY_FIELD, canBeNull = false)
    private String country;

    @DatabaseField(columnName = YEAR_FIELD, canBeNull = false)
    private String year;
    //endregion

    /**
     * Empty constructor for music
     */
    public Music()
    {
        this(0, "", "", "", "", "");
    }

    /**
     * Constructor for Music
     * @param id        column id
     * @param artist    the artistname
     * @param song      name of song
     * @param album     name of album
     * @param country   name of country
     * @param year      which year song was given out
     */
    public Music(int id, String artist, String song, String album, String country, String year)
    {
        this.id = id;
        this.artist = artist;
        this.song = song;
        this.album = album;
        this.country = country;
        this.year = year;
    }

    /**
     * Gets the id
     * @return int  return Id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Gets the Artistname
     * @return String  return artist' name
     */
    public String getArtist()
    {
        return artist;
    }

    /**
     * Gets the Artistname
     * @return String  return artist' name
     */
    public String getSong()
    {
        return song;
    }

    /**
     * Gets the name of the album
     * @return String  return albums name
     */
    public String getAlbum()
    {
        return album;
    }

    /**
     * Gets the name of the country where artist is from
     * @return String  return artists country of origin
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * Gets the year the song is given out. This is set to CHAR(4) instead of date/int to simplify the program.
     * @return String  return year
     */
    public String getYear()
    {
        return year;
    }
}