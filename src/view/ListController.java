/**
 * @description CS 213 Project 1
 * @author Dhruvil Patel <dhp68>
 * @author Nicholas Bonura <njb127>
 */

package view;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import model.SongComparator;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Song;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ListController {
	@FXML         
	   ListView<Song> listView;  
	@FXML
	   TextField songTitle, songArtist, songAlbum, songYear,
	   		enterTitle, enterArtist, enterAlbum, enterYear;
	   

	   private ObservableList<Song> obsrList;  
	  
	   public void start(Stage mainStage) {                
		  obsrList = FXCollections.observableArrayList(readFromFile("src/songs.txt")); 
	      listView.setItems(obsrList); 
	      listView.setCellFactory(new Callback<ListView<Song>, ListCell<Song>>(){
	    	  
	            @Override
	            public ListCell<Song> call(ListView<Song> p) {
	                ListCell<Song> cell = new ListCell<Song>(){
	 
                    @Override
                    protected void updateItem(Song s, boolean bool) {
                        super.updateItem(s, bool);
                        
                        if (s != null) {setText("'"+s.getTitle()+"' by "+s.getArtist());}
                        else if (s == null){setText(null);}
                    }
	 
	                };
	                return cell;
	            }
	        });
	      
	      if (!obsrList.isEmpty())
	    	listView.getSelectionModel().select(0);
	      	showSong();

	      listView
	      .getSelectionModel()
	      .selectedItemProperty()
	      .addListener((obs, oldVal, newVal) -> showSong());
	      
	      mainStage.setOnCloseRequest(event -> {
    	  PrintWriter writer;
    	  	try {
	  			File file = new File ("src/songs.txt");
	  			file.createNewFile();
	  			writer = new PrintWriter(file);
				for(Song s: obsrList){
	    		  writer.println(s.getTitle());
	    		  writer.println(s.getArtist());
	    		  writer.println(s.getAlbum());
	    		  writer.println(s.getYear());
		    	}
			writer.close(); 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    	 
	      });
	   }
	   
	   /**
	    * UPDATE Button
	    * @param event
	    */
	   @FXML
	   private void handleUpdateButton(ActionEvent event) {
		   if (obsrList.isEmpty()){showError("Error! There's nothing to update.");return;}
		   
		   int index = listView.getSelectionModel().getSelectedIndex();
		   TextField titleTextField = new TextField(songTitle.getText());
		   TextField artistTextField = new TextField(songArtist.getText());
		   TextField albumTextField = new TextField(songAlbum.getText());
		   TextField yearTextField = new TextField(songYear.getText());

		   Optional<ButtonType> result;
		   Alert alert = new Alert(AlertType.CONFIRMATION, "yes", ButtonType.YES);
		   alert.setTitle("Update Item");
		   String content = "Are you sure you want to update this song ?" ;
		   alert.setContentText(content);
		   result = alert.showAndWait();
		   
		   if(result.isPresent()){
			   if(albumTextField == null || yearTextField == null) {
				   Song temp = new Song(titleTextField.getText(),artistTextField.getText(),albumTextField.getText(),yearTextField.getText());
				   obsrList.set(index,temp);
				   listView.getSelectionModel().select(index);
				   FXCollections.sort(obsrList, new SongComparator());
				   showSong();
			   }
			   String error = checkFields(titleTextField.getText(), artistTextField.getText());
			   if(songYear.getText() == null && songAlbum.getText() == null && error!=null) {
				   showError("There is nothing to update!");
			   }
			   else if (songYear.getText() != null && songYear.getText().isEmpty() == false) {
				   if(!songYear.getText().trim().matches("[0-9]+")) {
				   showError("Only numbers are acceptable!");
				   }
			   }
			   else if (error != null) {
				   showError(error);
			   }
			   else {
				   Song temp = new Song(titleTextField.getText(),artistTextField.getText(),albumTextField.getText(),yearTextField.getText());
				   obsrList.set(index,temp);
				   listView.getSelectionModel().select(index);
				   FXCollections.sort(obsrList, new SongComparator());
				   showSong();
			   }
		   }
	   }

	/**
	    * ADD Button
	    * @param event
	    */
	   @FXML
	   private void handleAddButton(ActionEvent event) {
		   
		   if(enterTitle==null && enterArtist==null) {
			   if (obsrList.isEmpty()){showError("Error! Enter valid inputs.");return;}
		   }		   
		   int index = listView.getSelectionModel().getSelectedIndex();
		   TextField titleTextField = new TextField(enterTitle.getText());
		   TextField artistTextField = new TextField(enterArtist.getText());
		   TextField albumTextField = new TextField(enterAlbum.getText());
		   TextField yearTextField = new TextField(enterYear.getText());
		   
		   String error = checkFields(titleTextField.getText(), artistTextField.getText(),
				   albumTextField.getText(), yearTextField.getText());
		   
		   if(error == null) {
				  if(enterTitle != null && enterArtist != null) {
				   Optional<ButtonType> result;
				   Alert alert = new Alert(AlertType.CONFIRMATION, "yes", ButtonType.YES);
				   alert.setTitle("Add Item");
				   String content = "Are you sure you want to add '" + enterTitle.getText() + "' song by " + enterArtist.getText() + " ?" ;
				   alert.setContentText(content);
				   result = alert.showAndWait();
			   }
		   }
		   if (error != null) {
			   showError(error);
		   } 
		   
		   else {
			   Song tmp = new Song(titleTextField.getText(),artistTextField.getText(),albumTextField.getText(),yearTextField.getText());
			   obsrList.add(tmp);
			   FXCollections.sort(obsrList, new SongComparator());
			   if (obsrList.size() == 1) {
				   listView.getSelectionModel().select(0);
			   }
			   else{
				   index = 0;
				   for(Song s: obsrList){
					   if(s == tmp){
						  listView.getSelectionModel().select(index);
						  break;
					   }
					   index++;
				   }
			   }
		   }
		   enterTitle.clear();
		   enterArtist.clear();
		   enterAlbum.clear();
		   enterYear.clear();
	   }
	   
	   /**
	    * DELETE Button
	    * @param event
	    */
	   @FXML
	   private void handleDeleteButton(ActionEvent event) {
		   if (obsrList.isEmpty()) {
			   showError("There is nothing to delete.");
			   return;
		   }
		   
		   Song item = listView.getSelectionModel().getSelectedItem();
		   int index = listView.getSelectionModel().getSelectedIndex();
		   
		   Alert alert = new Alert(AlertType.WARNING);
		   alert.setTitle("Delete Item");
		   String content = "Are you sure you want to delete " + item.getTitle() + "?";
		   alert.setContentText(content);

		   Optional<ButtonType> result = alert.showAndWait();
		   if (result.isPresent()) {
			   obsrList.remove(item);
			   if (obsrList.isEmpty()) {
				   songTitle.setText("");
				   songArtist.setText("");
				   songAlbum.setText("");
				   songYear.setText("");
			   }
			   else if(index == obsrList.size()-1){
				   listView.getSelectionModel().select(index--);
			   }
			   else{
				   listView.getSelectionModel().select(index++);
			   }
			   showSong();
		   }	
	   }
	   
	   private void showSong() {
		   if (listView.getSelectionModel().getSelectedIndex() < 0)
			   return;
		   Song s = listView.getSelectionModel().getSelectedItem();
		   
		   songTitle.setText(s.getTitle());
		   songArtist.setText(s.getArtist());
		   songAlbum.setText(s.getAlbum());
		   songYear.setText(s.getYear());
	   }
	   
	  /**
	    * When clicked on a cell
	    * @param event
	    */
	  @FXML
	   private void listKeyPress(KeyEvent event) {
		   if(event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN)
			   showSong();
	   }
	   
	  /**
	    * @param fileName
	    * @return listOfSongs
	    */ 
	  private ArrayList<Song> readFromFile(String fileName)
	   {
		   ArrayList <Song> songs = new ArrayList<Song>();
		   BufferedReader br;
		   Path path = Paths.get(fileName);
		   try {
			if (!new File(fileName).exists()){
			   return songs;
			}
			br = Files.newBufferedReader(path);
			String line = br.readLine();
				
			while (line != null) { 
			   String title = line;
			    line = br.readLine();
			   String artist = line;
			    line = br.readLine();
			   String album = line;
			    line = br.readLine();
			   String year = line;
			   
			   Song tmp = new Song(title, artist, album, year);
			    songs.add(tmp);  
			   line = br.readLine();
			}  	
		   } catch (IOException e) { e.printStackTrace(); }
		      
		   Collections.sort(songs, new SongComparator());
		   return songs;
	   }
	   
	  /**
	    * @param error
	    */ 
	   private void showError(String error) {
		   Alert alert = new Alert(AlertType.INFORMATION);
		   alert.setTitle("Error");
		   alert.setHeaderText("Error");
		   String content = error;
		   alert.setContentText(content);
		   alert.showAndWait();		// As professor said in class :) 
	   }
	   
	   /**
	    * TextField Checker 
	    * @param title, artist, album, year 			
	    * @return error
	    */ 
	   private String checkFields(String title, String artist, String album, String year) {
		   if (title.trim().isEmpty())
			   return "Title textfield CAN'T be empty! Please enter Title Name.";
		   else if (artist.trim().isEmpty())
			   return "Artist textfield CAN'T be empty! Please enter Artist Name.";
		   else if (!isUniqueSong(title, artist))
			   return "Title & Artist already exist in library!";
		   else if (!year.trim().isEmpty()) {
			   if (!year.trim().matches("[0-9]+"))
				   return "Only numbers are acceptable!";
			   else if (year.trim().length() != 4)
				   return "Year must be 4 digits long!";
		   }
		   return null;
	   }
	   private String checkFields(String title, String artist) {
		   if (title.trim().isEmpty())
			   return "Title textfield CAN'T be empty! Please enter Title Name.";
		   else if (artist.trim().isEmpty())
			   return "Artist textfield CAN'T be empty! Please enter Artist Name.";
		   else if (!isUniqueSong(title, artist))
			   return "Title & Artist already exist in library!";
		   return null;
	   }
	   
	   /**
	    * @param title, artist
	    * @return true V false
	    */ 
	   private boolean isUniqueSong(String title, String artist) {
		   for (Song s : obsrList) {		   
			   if (s.getTitle().toLowerCase().equals(title.trim().toLowerCase()) &&
					   s.getArtist().toLowerCase().equals(artist.trim().toLowerCase())){
				   return false;
			   }   
		   }
		   return true;
	   }
}
