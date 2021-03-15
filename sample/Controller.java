package sample;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;



public class Controller  implements Initializable{


    @FXML
    private Pane pane ;
    @FXML
    private Label l1  ;
    @FXML
    private Button play , Pause , Rest , Piriviose  ,Next ;
    @FXML
    private ComboBox<String> Speed ;
    @FXML
    private Slider spedd  ;
    @FXML
    private ProgressBar pro ;




    private File dir ;
    private File[] files ;

    private Media media ;
    private MediaPlayer mediaPlayer  ;

    private ArrayList<File> songs ;
    private  int song_nuber  ;

    private int[] speeds = {2 , 10 ,15 , 25 , 50 , 75 , 100 , 125 , 150 , 175 , 200} ;


    private Timer timer ;
    private TimerTask task ;
    private   boolean run  ;






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        songs = new ArrayList<File>() ;

        dir = new File("music") ;

        files = dir.listFiles() ;

        if (files != null){
            for (File file : files){
                songs.add(file) ;

            }
        }




    media =new Media((songs.get(song_nuber).toURI().toString())) ;
    mediaPlayer = new MediaPlayer(media) ;

        l1.setText(songs.get(song_nuber).getName());



        for (int i = 0; i <speeds.length; i++) {

            Speed.getItems().add(Integer.toString(speeds[i]) + "%") ;

        }

        Speed.setOnAction(this::change);

        spedd.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

                mediaPlayer.setVolume(spedd.getValue() * 0.01);
            }
        });

        pro.setStyle("-fx-accent:#00FF00");

    }


    public void play() {

        begintimer();
        change(null);
        mediaPlayer.setVolume(spedd.getValue() * 0.01);
        mediaPlayer.play() ;

    }

    public void Pause(ActionEvent actionEvent) {

        canceltime();
        mediaPlayer.pause();
    }



    public void Rest(ActionEvent actionEvent) {

        pro.setProgress(0);
        mediaPlayer.seek(Duration.seconds(0));
    }



    public void Piriviose(ActionEvent actionEvent) {

        if (song_nuber  > 0 ){
            song_nuber-- ;

            mediaPlayer.stop();

            if (run){
                canceltime();
            }


            media =new Media((songs.get(song_nuber).toURI().toString())) ;
            mediaPlayer = new MediaPlayer(media) ;

            l1.setText(songs.get(song_nuber).getName());
            play();
        }

        else {

            song_nuber = songs.size() -1  ;

            mediaPlayer.stop();


            if (run){
                canceltime();
            }


            media =new Media((songs.get(song_nuber).toURI().toString())) ;
            mediaPlayer = new MediaPlayer(media) ;

            l1.setText(songs.get(song_nuber).getName());
            play();

        }


    }




    public void Next(ActionEvent actionEvent) {


        if (song_nuber  <  songs.size() -1){
            song_nuber++ ;

            mediaPlayer.stop();


            media =new Media((songs.get(song_nuber).toURI().toString())) ;
            mediaPlayer = new MediaPlayer(media) ;

            l1.setText(songs.get(song_nuber).getName());
            play();
        }
        else {

            song_nuber = 0 ;

            mediaPlayer.stop();


            media =new Media((songs.get(song_nuber).toURI().toString())) ;
            mediaPlayer = new MediaPlayer(media) ;

            l1.setText(songs.get(song_nuber).getName());
            play();

        }


    }

    public void change(ActionEvent actionEvent) {

        if (Speed.getValue() == null){
            mediaPlayer.setRate(1);
        }
        else {

            mediaPlayer.setRate(Integer.parseInt(Speed.getValue().substring(0  , Speed.getValue().length() -1)) * 0.01);
        }


    }


    public  void begintimer(){

        timer = new Timer() ;

        task = new TimerTask() {

            @Override
            public void run() {

                run = true ;
                double current = mediaPlayer.getCurrentTime().toSeconds() ;
                double end = media.getDuration().toSeconds() ;
                pro.setProgress(current/end);


                if (current/end == 1 ){
                    canceltime();

                }


            }
        }  ;

        timer.scheduleAtFixedRate(task , 0 , 1000);

    }



    public  void canceltime(){

        run = false  ;
        timer.cancel();

    }
}


















