package com.example.chsterio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.Animatable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView txtTimeSong, txtTimeTotal, txtTitle;
    SeekBar seekBarSong;
    ImageButton btnPre, btnPlay, btnStop, btnNext;
    ArrayList<Song> arraySong;
    int position=0;
    MediaPlayer mediaPlayer;
    ImageView imgDisk;
    Animation animationRotate;
    ConstraintLayout screenPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();
        addSong();
        initMediaPlayer();
        animationRotate = AnimationUtils.loadAnimation(this, R.anim.rotate);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    //neu dang phat -> pause -> doi hinh play
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.icons8_play_50px);
//                    btnPlay.clearAnimation();
                }else{
                    //dang ngung -> phat -> doi hinh pause
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.icons8_pause_50px);
                }
                setTimeTotal();
                setTimeSong();
                imgDisk.startAnimation(animationRotate);

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                btnPlay.setImageResource(R.drawable.icons8_play_50px);
                initMediaPlayer();
                imgDisk.clearAnimation();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if(position > arraySong.size()-1){ //neu bai hat lon hon vi tri trong mang -> quay lai bai dau tien
                    position=0;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                initMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.icons8_pause_50px);
                setTimeTotal();
                setTimeSong();
                imgDisk.clearAnimation();
                imgDisk.startAnimation(animationRotate);
            }
        });

        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if(position < 0){
                    position=arraySong.size()-1;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                initMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.icons8_pause_50px);
                setTimeTotal();
                setTimeSong();
                imgDisk.clearAnimation();
                imgDisk.startAnimation(animationRotate);
            }
        });

        seekBarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBarSong.getProgress());
            }
        });
    }

    private void initMediaPlayer(){
        mediaPlayer = MediaPlayer.create(MainActivity.this, arraySong.get(position).getFile());
        txtTitle.setText(arraySong.get(position).getTitle());
        screenPhone.setBackgroundResource(arraySong.get(position).getPicture());
    }

    //Lay va dinh dang thoi gian theo bai hat
    private void setTimeTotal(){
        SimpleDateFormat formatTime = new SimpleDateFormat("mm:ss");
        txtTimeTotal.setText(formatTime.format(mediaPlayer.getDuration()));

        //gan max cua seekbarSong = mediaPlayer.getDuration()
        seekBarSong.setMax(mediaPlayer.getDuration());
    }

    //Lay va dinh dang thoi gian chay theo thoi luong seekbar
    private void setTimeSong(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat formatTime = new SimpleDateFormat("mm:ss");
                txtTimeSong.setText(formatTime.format(mediaPlayer.getCurrentPosition()));
                handler.postDelayed(this, 500);

                //update seekbar
                seekBarSong.setProgress(mediaPlayer.getCurrentPosition());

                //Kiem tra thoi gian bai hat -> neu ket thuc -> next
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if(position > arraySong.size()-1){ //neu bai hat lon hon vi tri trong mang -> quay lai bai dau tien
                            position=0;
                        }
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        initMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.icons8_pause_50px);
                        setTimeTotal();
                        setTimeSong();
                    }
                });
            }
        }, 100);
    }

    private void addSong() {
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Trong Trí Nhớ Của Anh", R.raw.trong_tri_tho_cua_anh, R.drawable.trong_tri_nho_cua_anh));
        arraySong.add(new Song("Tình Đắng Như Ly Cafe", R.raw.tinh_dang_nhu_ly_cafe, R.drawable.tinh_dang_nhu_ly_cafe));
        arraySong.add(new Song("Memories", R.raw.memories, R.drawable.momories));
    }

    private void anhXa() {
        txtTimeSong=(TextView)findViewById(R.id.txtTimeSong);
        txtTimeTotal=(TextView)findViewById(R.id.txtTimeTotal);
        txtTitle=(TextView)findViewById(R.id.txtTitleSong);
        seekBarSong=(SeekBar) findViewById(R.id.seekBarSong);
        btnPre=(ImageButton) findViewById(R.id.btnPre);
        btnPlay=(ImageButton) findViewById(R.id.btnPlay);
        btnStop=(ImageButton) findViewById(R.id.btnStop);
        btnNext=(ImageButton) findViewById(R.id.btnNext);
        imgDisk=(ImageView)findViewById(R.id.imgDisk);
        screenPhone=(ConstraintLayout)findViewById(R.id.screenPhone);
    }
}