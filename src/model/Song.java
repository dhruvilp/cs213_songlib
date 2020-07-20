/**
 * @description CS 213 Project 1
 * @author Dhruvil Patel <dhp68>
 * @author Nicholas Bonura <njb127>
 */

package model;


public class Song {
	
	private String title;
	private String artist;
	private String album;
	private String year;
	
	/**@param title, artist, album, year*/
	public Song(String title, String artist, String album, String year)
	{
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.year = year;
	
	}
	
	/**@param title*/
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**@param artist*/
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	/**@param album*/
	public void setAlbum(String album) {
		this.album = album;
	}
	
	/**@param year*/
	public void setYear(String year) {
		this.year = year;
	}
	
	/**@return songTitle*/
	public String getTitle() {
		return this.title;
	}
	
	/**@return songArtist*/
	public String getArtist() {
		return this.artist;
	}
	
	/**@return songAlbum*/
	public String getAlbum() {
		return this.album;
	}
	
	/**@return songyear*/
	public String getYear() {
		return this.year;
	}
	
}



