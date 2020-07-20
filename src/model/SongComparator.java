/**
 * @description CS 213 Project 1
 * @author Dhruvil Patel <dhp68>
 * @author Nicholas Bonura <njb127>
 */

package model;

import java.util.Comparator;
import model.Song;

public class SongComparator implements Comparator<Song>{

	@Override
	public int compare(Song s1, Song s2) {
		
		if(s1.getTitle().toLowerCase().equals(s2.getTitle().toLowerCase())){
			return s1.getArtist().toLowerCase().compareTo(s2.getArtist().toLowerCase());
		}
		else
			return s1.getTitle().toLowerCase().compareTo(s2.getTitle().toLowerCase());
	}
}

