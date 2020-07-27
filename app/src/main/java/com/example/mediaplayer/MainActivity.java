package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekVolume;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste);
        inicializarSeekBar();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();

            //liberando recurso de memoria
            mediaPlayer.release();

            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    private void inicializarSeekBar() {

        seekVolume = findViewById(R.id.seekVolume);

        //config audio manager - saber o volume atual e o max
        //para exibir o progresso do volume na seekbar
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //recuperar volume max e volume atual
        int volumeMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //config volume max na seekBar
        seekVolume.setMax(volumeMax);

        //config volume progresso atual
        seekVolume.setProgress(volumeAtual);

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void executarSom(View view) {
        if(mediaPlayer != null ) {
            mediaPlayer.start();
        }
    }

    public void pausarSom(View view) {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void pararSom(View view) {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();

            //permite executar novamente, caso clique no play
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste);
        }
    }
}
