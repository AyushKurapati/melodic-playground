package com.example.harmonic.ui.slideshow;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.harmonic.MainActivity;
import com.example.harmonic.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SlideshowFragment extends Fragment {
    ImageView imageView;
    public void setimg(int n){
        switch(n){
            case 0:
                imageView.setImageResource(R.drawable.p0);
                break;
            case 1:
                imageView.setImageResource(R.drawable.p1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.p2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.p3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.p4);
                break;
            case 5:
                imageView.setImageResource(R.drawable.p5);
                break;
            case 6:
                imageView.setImageResource(R.drawable.p6);
                break;
            case 7:
                imageView.setImageResource(R.drawable.p7);
                break;
            case 8:
                imageView.setImageResource(R.drawable.p8);
                break;
            case 9:
                imageView.setImageResource(R.drawable.p9);
                break;
        }
    }
    public String sname(int n){
        String s=null;
        switch(n){
            case 0:
                s="Namo Namo Shankara";
                break;
            case 1:
                s="Shivoham - Sounds Of Isha";
                break;
            case 2:
                s="Shankara Re Shankara";
                break;
            case 3:
                s="Teri Mitti";
                break;
            case 4:
                s="Breathless - Shankar Mahadevan";
                break;
            case 5:
                s="Maa Tujhe Salam";
                break;
            case 6:
                s="Maay Bhavani - Tanhaji";
                break;
            case 7:
                s="Kandhon Se Kandhe Milte Hai";
                break;
            case 8:
                s="Malhari - Bajirao Mastani";
                break;
            case 9:
                s="Ranjan Gavala";
                break;
        }
        return s;
    }
    MediaPlayer player;
    int sid;
    TextView t;
    AudioManager audioManager;
    int i=0;

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        ImageButton play=root.findViewById(R.id.play);
        imageView=(ImageView) root.findViewById(R.id.imageView11);
        imageView.setImageResource(0);
        ImageButton pause=root.findViewById(R.id.pause);
        ImageButton stop=root.findViewById(R.id.stop);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.start();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.pause();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
            }
        });

        ImageButton b1=root.findViewById(R.id.button);
        ImageButton b2=root.findViewById(R.id.button2);
        t=root.findViewById(R.id.tview);
        int sid1=getResources().getIdentifier("music0", "raw",getActivity().getPackageName() );
        String str1=sname(0);
        //Toast.makeText(SlideshowFragment.super.getContext(),"Now Playing "+str1,Toast.LENGTH_SHORT).show();
        //t.setText(str1);
        player = MediaPlayer.create(SlideshowFragment.super.getContext(), sid1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.pause();
                i++;
                int temp=i%10;

                String z = "music" + temp;
                String str=sname(temp);
                t.setText(str);
                setimg(temp);
                Toast.makeText(SlideshowFragment.super.getContext(),"Now Playing "+str,Toast.LENGTH_SHORT).show();
                int sid = getResources().getIdentifier(z, "raw", getActivity().getPackageName());
                player = MediaPlayer.create(SlideshowFragment.super.getContext(), sid);
                player.start();
                player.seekTo(0);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.pause();
                i--;
                if(i<0){
                    i=Math.abs(i);
                }
                int temp=i%10;
                String z = "music" + temp;
                String str=sname(temp);
                t.setText(str);
                setimg(temp);
                Toast.makeText(SlideshowFragment.super.getContext(),"Now Playing "+str,Toast.LENGTH_SHORT).show();
                int sid = getResources().getIdentifier(z, "raw", getActivity().getPackageName());
                player = MediaPlayer.create(SlideshowFragment.super.getContext(), sid);
                player.start();
                player.seekTo(0);
            }
        });

        audioManager=(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        int maxvol=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curvol=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC); //current volume level

        SeekBar seekvol=root.findViewById(R.id.seekvol);
        seekvol.setMax(maxvol);
        seekvol.setProgress(curvol);
        seekvol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar seekprog=root.findViewById(R.id.seekprog);
        seekprog.setMax(player.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekprog.setProgress(player.getCurrentPosition());
            }
        },0,2000);
        seekprog.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                player.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ArrayList<String> arrayList=new ArrayList<String>(12);
        for(int i=0;i<10;i++){
            String str=sname(i);
            arrayList.add((i+1)+" - "+str);
        }
        ListView lv=root.findViewById(R.id.lview);
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(SlideshowFragment.super.getContext(),android.R.layout.simple_list_item_1,arrayList);

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int temp=position;
                player.pause();
                String z = "music" + temp;
                int sid = getResources().getIdentifier(z, "raw", getActivity().getPackageName());
                player = MediaPlayer.create(SlideshowFragment.super.getContext(), sid);
                String str=sname(temp);
                setimg(temp);
                t.setText(str);
                Toast.makeText(SlideshowFragment.super.getContext(),"Now Playing "+str,Toast.LENGTH_SHORT).show();
                player.start();
                player.seekTo(0);

            }
        });


        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.stop();
    }
}