package com.example.harmonic.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.harmonic.R;
import com.example.harmonic.ui.slideshow.SlideshowFragment;

public class GalleryFragment extends Fragment {
    boolean gameActive = true;

    // Player representation
    // 0 - X
    // 1 - O
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    // State meanings:
    // 0 - X
    // 1 - O
    // 2 - Null
    // put all win positions in a 2D array
    int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    public static int counter = 0;
    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        ImageButton b1=root.findViewById(R.id.but);
        ImageView i0=root.findViewById(R.id.imageView0);
        ImageView i1=root.findViewById(R.id.imageView1);
        ImageView i2=root.findViewById(R.id.imageView2);
        ImageView i3=root.findViewById(R.id.imageView3);
        ImageView i4=root.findViewById(R.id.imageView4);
        ImageView i5=root.findViewById(R.id.imageView5);
        ImageView i6=root.findViewById(R.id.imageView6);
        ImageView i7=root.findViewById(R.id.imageView7);
        ImageView i8=root.findViewById(R.id.imageView8);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameActive = true;
                activePlayer = 0;
                counter=0;
                for (int i = 0; i < gameState.length; i++) {
                    gameState[i] = 2;
                }
                // remove all the images from the boxes inside the grid
                ((ImageView) root.findViewById(R.id.imageView0)).setImageResource(0);
                ((ImageView) root.findViewById(R.id.imageView1)).setImageResource(0);
                ((ImageView) root.findViewById(R.id.imageView2)).setImageResource(0);
                ((ImageView) root.findViewById(R.id.imageView3)).setImageResource(0);
                ((ImageView) root.findViewById(R.id.imageView4)).setImageResource(0);
                ((ImageView) root.findViewById(R.id.imageView5)).setImageResource(0);
                ((ImageView) root.findViewById(R.id.imageView6)).setImageResource(0);
                ((ImageView) root.findViewById(R.id.imageView7)).setImageResource(0);
                ((ImageView) root.findViewById(R.id.imageView8)).setImageResource(0);

                TextView status = root.findViewById(R.id.status);
                Toast.makeText(GalleryFragment.super.getContext(),"Restarting the Game....",Toast.LENGTH_SHORT).show();
                status.setText("X's Turn - Tap to play");
            }
        });

        i0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView img = (ImageView) v;
                int tappedImage = Integer.parseInt(img.getTag().toString());

                // game reset function will be called
                // if someone wins or the boxes are full
                if (!gameActive) {
                    gameActive = true;
                    activePlayer = 0;
                    counter=0;
                    for (int i = 0; i < gameState.length; i++) {
                        gameState[i] = 2;
                    }
                    // remove all the images from the boxes inside the grid
                    ((ImageView) root.findViewById(R.id.imageView0)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView1)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView2)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView3)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView4)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView5)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView6)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView7)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView8)).setImageResource(0);

                    TextView status = root.findViewById(R.id.status);
                    status.setText("X's Turn - Tap to play");
                }

                // if the tapped image is empty
                if (gameState[tappedImage] == 2) {
                    // increase the counter
                    // after every tap
                    counter++;

                    // check if its the last box
                    if (counter == 9) {
                        // reset the game
                        gameActive = false;
                    }

                    // mark this position
                    gameState[tappedImage] = activePlayer;

                    // this will give a motion
                    // effect to the image
                    img.setTranslationY(-1000f);

                    // change the active player
                    // from 0 to 1 or 1 to 0
                    if (activePlayer == 0) {
                        // set the image of x
                        img.setImageResource(R.drawable.x);
                        activePlayer = 1;
                        TextView status = root.findViewById(R.id.status);

                        // change the status
                        status.setText("O's Turn - Tap to play");
                    } else {
                        // set the image of o
                        img.setImageResource(R.drawable.o);
                        activePlayer = 0;
                        TextView status = root.findViewById(R.id.status);

                        // change the status
                        status.setText("X's Turn - Tap to play");
                    }
                    img.animate().translationYBy(1000f).setDuration(300);
                }
                int flag = 0;
                // Check if any player has won
                for (int[] winPosition : winPositions) {
                    if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                            gameState[winPosition[1]] == gameState[winPosition[2]] &&
                            gameState[winPosition[0]] != 2) {
                        flag = 1;

                        // Somebody has won! - Find out who!
                        String winnerStr;

                        // game reset function be called
                        gameActive = false;
                        if (gameState[winPosition[0]] == 0) {
                            winnerStr = "X has won";
                            Toast.makeText(GalleryFragment.super.getContext(),winnerStr,Toast.LENGTH_SHORT).show();
                        } else {
                            winnerStr = "O has won";
                            Toast.makeText(GalleryFragment.super.getContext(),winnerStr,Toast.LENGTH_SHORT).show();
                        }
                        // Update the status bar for winner announcement
                        TextView status = root.findViewById(R.id.status);
                        status.setText(winnerStr);
                    }
                }
                // set the status if the match draw
                if (counter == 9 && flag == 0) {
                    TextView status = root.findViewById(R.id.status);
                    status.setText("Match Draw");
                    Toast.makeText(GalleryFragment.super.getContext(),"Match Draw.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView img = (ImageView) v;
                int tappedImage = Integer.parseInt(img.getTag().toString());

                // game reset function will be called
                // if someone wins or the boxes are full
                if (!gameActive) {
                    gameActive = true;
                    activePlayer = 0;
                    counter=0;
                    for (int i = 0; i < gameState.length; i++) {
                        gameState[i] = 2;
                    }
                    // remove all the images from the boxes inside the grid
                    ((ImageView) root.findViewById(R.id.imageView0)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView1)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView2)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView3)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView4)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView5)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView6)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView7)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView8)).setImageResource(0);

                    TextView status = root.findViewById(R.id.status);
                    status.setText("X's Turn - Tap to play");
                }

                // if the tapped image is empty
                if (gameState[tappedImage] == 2) {
                    // increase the counter
                    // after every tap
                    counter++;

                    // check if its the last box
                    if (counter == 9) {
                        // reset the game
                        gameActive = false;
                    }

                    // mark this position
                    gameState[tappedImage] = activePlayer;

                    // this will give a motion
                    // effect to the image
                    img.setTranslationY(-1000f);

                    // change the active player
                    // from 0 to 1 or 1 to 0
                    if (activePlayer == 0) {
                        // set the image of x
                        img.setImageResource(R.drawable.x);
                        activePlayer = 1;
                        TextView status = root.findViewById(R.id.status);

                        // change the status
                        status.setText("O's Turn - Tap to play");
                    } else {
                        // set the image of o
                        img.setImageResource(R.drawable.o);
                        activePlayer = 0;
                        TextView status = root.findViewById(R.id.status);

                        // change the status
                        status.setText("X's Turn - Tap to play");
                    }
                    img.animate().translationYBy(1000f).setDuration(300);
                }
                int flag = 0;
                // Check if any player has won
                for (int[] winPosition : winPositions) {
                    if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                            gameState[winPosition[1]] == gameState[winPosition[2]] &&
                            gameState[winPosition[0]] != 2) {
                        flag = 1;

                        // Somebody has won! - Find out who!
                        String winnerStr;

                        // game reset function be called
                        gameActive = false;
                        if (gameState[winPosition[0]] == 0) {
                            winnerStr = "X has won";
                            Toast.makeText(GalleryFragment.super.getContext(),winnerStr,Toast.LENGTH_SHORT).show();
                        } else {
                            winnerStr = "O has won";
                            Toast.makeText(GalleryFragment.super.getContext(),winnerStr,Toast.LENGTH_SHORT).show();
                        }
                        // Update the status bar for winner announcement
                        TextView status = root.findViewById(R.id.status);
                        status.setText(winnerStr);
                    }
                }
                // set the status if the match draw
                if (counter == 9 && flag == 0) {
                    TextView status = root.findViewById(R.id.status);
                    Toast.makeText(GalleryFragment.super.getContext(),"Match Draw",Toast.LENGTH_SHORT).show();
                    status.setText("Match Draw");
                }
            }
        });
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView img = (ImageView) v;
                int tappedImage = Integer.parseInt(img.getTag().toString());

                // game reset function will be called
                // if someone wins or the boxes are full
                if (!gameActive) {
                    gameActive = true;
                    activePlayer = 0;
                    counter=0;
                    for (int i = 0; i < gameState.length; i++) {
                        gameState[i] = 2;
                    }
                    // remove all the images from the boxes inside the grid
                    ((ImageView) root.findViewById(R.id.imageView0)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView1)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView2)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView3)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView4)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView5)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView6)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView7)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView8)).setImageResource(0);

                    TextView status = root.findViewById(R.id.status);
                    status.setText("X's Turn - Tap to play");
                }

                // if the tapped image is empty
                if (gameState[tappedImage] == 2) {
                    // increase the counter
                    // after every tap
                    counter++;

                    // check if its the last box
                    if (counter == 9) {
                        // reset the game
                        gameActive = false;
                    }

                    // mark this position
                    gameState[tappedImage] = activePlayer;

                    // this will give a motion
                    // effect to the image
                    img.setTranslationY(-1000f);

                    // change the active player
                    // from 0 to 1 or 1 to 0
                    if (activePlayer == 0) {
                        // set the image of x
                        img.setImageResource(R.drawable.x);
                        activePlayer = 1;
                        TextView status = root.findViewById(R.id.status);

                        // change the status
                        status.setText("O's Turn - Tap to play");
                    } else {
                        // set the image of o
                        img.setImageResource(R.drawable.o);
                        activePlayer = 0;
                        TextView status = root.findViewById(R.id.status);

                        // change the status
                        status.setText("X's Turn - Tap to play");
                    }
                    img.animate().translationYBy(1000f).setDuration(300);
                }
                int flag = 0;
                // Check if any player has won
                for (int[] winPosition : winPositions) {
                    if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                            gameState[winPosition[1]] == gameState[winPosition[2]] &&
                            gameState[winPosition[0]] != 2) {
                        flag = 1;

                        // Somebody has won! - Find out who!
                        String winnerStr;

                        // game reset function be called
                        gameActive = false;
                        if (gameState[winPosition[0]] == 0) {
                            winnerStr = "X has won";
                        } else {
                            winnerStr = "O has won";
                        }
                        // Update the status bar for winner announcement
                        TextView status = root.findViewById(R.id.status);
                        Toast.makeText(GalleryFragment.super.getContext(),winnerStr,Toast.LENGTH_SHORT).show();
                        status.setText(winnerStr);
                    }
                }
                // set the status if the match draw
                if (counter == 9 && flag == 0) {
                    TextView status = root.findViewById(R.id.status);
                    Toast.makeText(GalleryFragment.super.getContext(),"Match Draw",Toast.LENGTH_SHORT).show();
                    status.setText("Match Draw");
                }
            }
        });
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView img = (ImageView) v;
                int tappedImage = Integer.parseInt(img.getTag().toString());

                // game reset function will be called
                // if someone wins or the boxes are full
                if (!gameActive) {
                    gameActive = true;
                    activePlayer = 0;
                    counter=0;
                    for (int i = 0; i < gameState.length; i++) {
                        gameState[i] = 2;
                    }
                    // remove all the images from the boxes inside the grid
                    ((ImageView) root.findViewById(R.id.imageView0)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView1)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView2)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView3)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView4)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView5)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView6)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView7)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView8)).setImageResource(0);

                    TextView status = root.findViewById(R.id.status);
                    status.setText("X's Turn - Tap to play");
                }

                // if the tapped image is empty
                if (gameState[tappedImage] == 2) {
                    // increase the counter
                    // after every tap
                    counter++;

                    // check if its the last box
                    if (counter == 9) {
                        // reset the game
                        gameActive = false;
                    }

                    // mark this position
                    gameState[tappedImage] = activePlayer;

                    // this will give a motion
                    // effect to the image
                    img.setTranslationY(-1000f);

                    // change the active player
                    // from 0 to 1 or 1 to 0
                    if (activePlayer == 0) {
                        // set the image of x
                        img.setImageResource(R.drawable.x);
                        activePlayer = 1;
                        TextView status = root.findViewById(R.id.status);

                        // change the status
                        status.setText("O's Turn - Tap to play");
                    } else {
                        // set the image of o
                        img.setImageResource(R.drawable.o);
                        activePlayer = 0;
                        TextView status = root.findViewById(R.id.status);

                        // change the status
                        status.setText("X's Turn - Tap to play");
                    }
                    img.animate().translationYBy(1000f).setDuration(300);
                }
                int flag = 0;
                // Check if any player has won
                for (int[] winPosition : winPositions) {
                    if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                            gameState[winPosition[1]] == gameState[winPosition[2]] &&
                            gameState[winPosition[0]] != 2) {
                        flag = 1;

                        // Somebody has won! - Find out who!
                        String winnerStr;

                        // game reset function be called
                        gameActive = false;
                        if (gameState[winPosition[0]] == 0) {
                            winnerStr = "X has won";
                        } else {
                            winnerStr = "O has won";
                        }
                        // Update the status bar for winner announcement
                        TextView status = root.findViewById(R.id.status);
                        Toast.makeText(GalleryFragment.super.getContext(),winnerStr,Toast.LENGTH_SHORT).show();
                        status.setText(winnerStr);
                    }
                }
                // set the status if the match draw
                if (counter == 9 && flag == 0) {
                    TextView status = root.findViewById(R.id.status);
                    status.setText("Match Draw");
                    Toast.makeText(GalleryFragment.super.getContext(),"Match Draw",Toast.LENGTH_SHORT).show();
                }
            }
        });
        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView img = (ImageView) v;
                int tappedImage = Integer.parseInt(img.getTag().toString());

                // game reset function will be called
                // if someone wins or the boxes are full
                if (!gameActive) {
                    gameActive = true;
                    activePlayer = 0;
                    counter=0;
                    for (int i = 0; i < gameState.length; i++) {
                        gameState[i] = 2;
                    }
                    // remove all the images from the boxes inside the grid
                    ((ImageView) root.findViewById(R.id.imageView0)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView1)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView2)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView3)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView4)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView5)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView6)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView7)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView8)).setImageResource(0);

                    TextView status = root.findViewById(R.id.status);
                    status.setText("X's Turn - Tap to play");
                }

                // if the tapped image is empty
                if (gameState[tappedImage] == 2) {
                    // increase the counter
                    // after every tap
                    counter++;

                    // check if its the last box
                    if (counter == 9) {
                        // reset the game
                        gameActive = false;
                    }

                    // mark this position
                    gameState[tappedImage] = activePlayer;

                    // this will give a motion
                    // effect to the image
                    img.setTranslationY(-1000f);

                    // change the active player
                    // from 0 to 1 or 1 to 0
                    if (activePlayer == 0) {
                        // set the image of x
                        img.setImageResource(R.drawable.x);
                        activePlayer = 1;
                        TextView status = root.findViewById(R.id.status);

                        // change the status
                        status.setText("O's Turn - Tap to play");
                    } else {
                        // set the image of o
                        img.setImageResource(R.drawable.o);
                        activePlayer = 0;
                        TextView status = root.findViewById(R.id.status);

                        // change the status
                        status.setText("X's Turn - Tap to play");
                    }
                    img.animate().translationYBy(1000f).setDuration(300);
                }
                int flag = 0;
                // Check if any player has won
                for (int[] winPosition : winPositions) {
                    if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                            gameState[winPosition[1]] == gameState[winPosition[2]] &&
                            gameState[winPosition[0]] != 2) {
                        flag = 1;

                        // Somebody has won! - Find out who!
                        String winnerStr;

                        // game reset function be called
                        gameActive = false;
                        if (gameState[winPosition[0]] == 0) {
                            winnerStr = "X has won";
                        } else {
                            winnerStr = "O has won";
                        }
                        // Update the status bar for winner announcement
                        TextView status = root.findViewById(R.id.status);
                        Toast.makeText(GalleryFragment.super.getContext(),winnerStr,Toast.LENGTH_SHORT).show();
                        status.setText(winnerStr);
                    }
                }
                // set the status if the match draw
                if (counter == 9 && flag == 0) {
                    TextView status = root.findViewById(R.id.status);
                    status.setText("Match Draw");
                    Toast.makeText(GalleryFragment.super.getContext(),"Match Draw",Toast.LENGTH_SHORT).show();
                }
            }
        });
        i5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView img = (ImageView) v;
                int tappedImage = Integer.parseInt(img.getTag().toString());

                // game reset function will be called
                // if someone wins or the boxes are full
                if (!gameActive) {
                    gameActive = true;
                    activePlayer = 0;
                    counter=0;
                    for (int i = 0; i < gameState.length; i++) {
                        gameState[i] = 2;
                    }
                    // remove all the images from the boxes inside the grid
                    ((ImageView) root.findViewById(R.id.imageView0)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView1)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView2)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView3)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView4)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView5)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView6)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView7)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView8)).setImageResource(0);

                    TextView status = root.findViewById(R.id.status);
                    status.setText("X's Turn - Tap to play");
                }

                // if the tapped image is empty
                if (gameState[tappedImage] == 2) {
                    // increase the counter
                    // after every tap
                    counter++;

                    // check if its the last box
                    if (counter == 9) {
                        // reset the game
                        gameActive = false;
                    }

                    // mark this position
                    gameState[tappedImage] = activePlayer;

                    // this will give a motion
                    // effect to the image
                    img.setTranslationY(-1000f);

                    // change the active player
                    // from 0 to 1 or 1 to 0
                    if (activePlayer == 0) {
                        // set the image of x
                        img.setImageResource(R.drawable.x);
                        activePlayer = 1;
                        TextView status = root.findViewById(R.id.status);

                        // change the status
                        status.setText("O's Turn - Tap to play");
                    } else {
                        // set the image of o
                        img.setImageResource(R.drawable.o);
                        activePlayer = 0;
                        TextView status = root.findViewById(R.id.status);

                        // change the status
                        status.setText("X's Turn - Tap to play");
                    }
                    img.animate().translationYBy(1000f).setDuration(300);
                }
                int flag = 0;
                // Check if any player has won
                for (int[] winPosition : winPositions) {
                    if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                            gameState[winPosition[1]] == gameState[winPosition[2]] &&
                            gameState[winPosition[0]] != 2) {
                        flag = 1;

                        // Somebody has won! - Find out who!
                        String winnerStr;

                        // game reset function be called
                        gameActive = false;
                        if (gameState[winPosition[0]] == 0) {
                            winnerStr = "X has won";
                        } else {
                            winnerStr = "O has won";
                        }
                        // Update the status bar for winner announcement
                        TextView status = root.findViewById(R.id.status);
                        Toast.makeText(GalleryFragment.super.getContext(),winnerStr,Toast.LENGTH_SHORT).show();
                        status.setText(winnerStr);
                    }
                }
                // set the status if the match draw
                if (counter == 9 && flag == 0) {
                    TextView status = root.findViewById(R.id.status);
                    status.setText("Match Draw");
                    Toast.makeText(GalleryFragment.super.getContext(),"Match Draw",Toast.LENGTH_SHORT).show();
                }
            }
        });
        i6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView img = (ImageView) v;
                int tappedImage = Integer.parseInt(img.getTag().toString());

                // game reset function will be called
                // if someone wins or the boxes are full
                if (!gameActive) {
                    gameActive = true;
                    activePlayer = 0;
                    counter=0;
                    for (int i = 0; i < gameState.length; i++) {
                        gameState[i] = 2;
                    }
                    // remove all the images from the boxes inside the grid
                    ((ImageView) root.findViewById(R.id.imageView0)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView1)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView2)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView3)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView4)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView5)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView6)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView7)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView8)).setImageResource(0);

                    TextView status = root.findViewById(R.id.status);
                    status.setText("X's Turn - Tap to play");
                }

                // if the tapped image is empty
                if (gameState[tappedImage] == 2) {
                    // increase the counter
                    // after every tap
                    counter++;

                    // check if its the last box
                    if (counter == 9) {
                        // reset the game
                        gameActive = false;
                    }

                    // mark this position
                    gameState[tappedImage] = activePlayer;

                    // this will give a motion
                    // effect to the image
                    img.setTranslationY(-1000f);

                    // change the active player
                    // from 0 to 1 or 1 to 0
                    if (activePlayer == 0) {
                        // set the image of x
                        img.setImageResource(R.drawable.x);
                        activePlayer = 1;
                        TextView status = root.findViewById(R.id.status);

                        // change the status
                        status.setText("O's Turn - Tap to play");
                    } else {
                        // set the image of o
                        img.setImageResource(R.drawable.o);
                        activePlayer = 0;
                        TextView status = root.findViewById(R.id.status);

                        // change the status
                        status.setText("X's Turn - Tap to play");
                    }
                    img.animate().translationYBy(1000f).setDuration(300);
                }
                int flag = 0;
                // Check if any player has won
                for (int[] winPosition : winPositions) {
                    if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                            gameState[winPosition[1]] == gameState[winPosition[2]] &&
                            gameState[winPosition[0]] != 2) {
                        flag = 1;

                        // Somebody has won! - Find out who!
                        String winnerStr;

                        // game reset function be called
                        gameActive = false;
                        if (gameState[winPosition[0]] == 0) {
                            winnerStr = "X has won";
                        } else {
                            winnerStr = "O has won";
                        }
                        // Update the status bar for winner announcement
                        TextView status = root.findViewById(R.id.status);
                        status.setText(winnerStr);
                        Toast.makeText(GalleryFragment.super.getContext(),winnerStr,Toast.LENGTH_SHORT).show();
                    }
                }
                // set the status if the match draw
                if (counter == 9 && flag == 0) {
                    TextView status = root.findViewById(R.id.status);
                    status.setText("Match Draw");
                    Toast.makeText(GalleryFragment.super.getContext(),"Match Draw",Toast.LENGTH_SHORT).show();
                }
            }
        });
        i7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView img = (ImageView) v;
                int tappedImage = Integer.parseInt(img.getTag().toString());

                // game reset function will be called
                // if someone wins or the boxes are full
                if (!gameActive) {
                    gameActive = true;
                    activePlayer = 0;
                    counter=0;
                    for (int i = 0; i < gameState.length; i++) {
                        gameState[i] = 2;
                    }
                    // remove all the images from the boxes inside the grid
                    ((ImageView) root.findViewById(R.id.imageView0)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView1)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView2)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView3)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView4)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView5)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView6)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView7)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView8)).setImageResource(0);

                    TextView status = root.findViewById(R.id.status);
                    status.setText("X's Turn - Tap to play");
                }

                // if the tapped image is empty
                if (gameState[tappedImage] == 2) {
                    // increase the counter
                    // after every tap
                    counter++;

                    // check if its the last box
                    if (counter == 9) {
                        // reset the game
                        gameActive = false;
                    }

                    // mark this position
                    gameState[tappedImage] = activePlayer;

                    // this will give a motion
                    // effect to the image
                    img.setTranslationY(-1000f);

                    // change the active player
                    // from 0 to 1 or 1 to 0
                    if (activePlayer == 0) {
                        // set the image of x
                        img.setImageResource(R.drawable.x);
                        activePlayer = 1;
                        TextView status = root.findViewById(R.id.status);

                        // change the status
                        status.setText("O's Turn - Tap to play");
                    } else {
                        // set the image of o
                        img.setImageResource(R.drawable.o);
                        activePlayer = 0;
                        TextView status = root.findViewById(R.id.status);

                        // change the status
                        status.setText("X's Turn - Tap to play");
                    }
                    img.animate().translationYBy(1000f).setDuration(300);
                }
                int flag = 0;
                // Check if any player has won
                for (int[] winPosition : winPositions) {
                    if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                            gameState[winPosition[1]] == gameState[winPosition[2]] &&
                            gameState[winPosition[0]] != 2) {
                        flag = 1;

                        // Somebody has won! - Find out who!
                        String winnerStr;

                        // game reset function be called
                        gameActive = false;
                        if (gameState[winPosition[0]] == 0) {
                            winnerStr = "X has won";
                        } else {
                            winnerStr = "O has won";
                        }
                        // Update the status bar for winner announcement
                        TextView status = root.findViewById(R.id.status);
                        Toast.makeText(GalleryFragment.super.getContext(),winnerStr,Toast.LENGTH_SHORT).show();
                        status.setText(winnerStr);
                    }
                }
                // set the status if the match draw
                if (counter == 9 && flag == 0) {
                    TextView status = root.findViewById(R.id.status);
                    status.setText("Match Draw");
                    Toast.makeText(GalleryFragment.super.getContext(),"Match Draw",Toast.LENGTH_SHORT).show();
                }
            }
        });
        i8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView img = (ImageView) v;
                int tappedImage = Integer.parseInt(img.getTag().toString());

                // game reset function will be called
                // if someone wins or the boxes are full
                if (!gameActive) {
                    gameActive = true;
                    activePlayer = 0;
                    counter=0;
                    for (int i = 0; i < gameState.length; i++) {
                        gameState[i] = 2;
                    }
                    // remove all the images from the boxes inside the grid
                    ((ImageView) root.findViewById(R.id.imageView0)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView1)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView2)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView3)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView4)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView5)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView6)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView7)).setImageResource(0);
                    ((ImageView) root.findViewById(R.id.imageView8)).setImageResource(0);

                    TextView status = root.findViewById(R.id.status);
                    status.setText("X's Turn - Tap to play");
                }

                // if the tapped image is empty
                if (gameState[tappedImage] == 2) {
                    // increase the counter
                    // after every tap
                    counter++;

                    // check if its the last box
                    if (counter == 9) {
                        // reset the game
                        gameActive = false;
                    }

                    // mark this position
                    gameState[tappedImage] = activePlayer;

                    // this will give a motion
                    // effect to the image
                    img.setTranslationY(-1000f);

                    // change the active player
                    // from 0 to 1 or 1 to 0
                    if (activePlayer == 0) {
                        // set the image of x
                        img.setImageResource(R.drawable.x);
                        activePlayer = 1;
                        TextView status = root.findViewById(R.id.status);

                        // change the status
                        status.setText("O's Turn - Tap to play");
                    } else {
                        // set the image of o
                        img.setImageResource(R.drawable.o);
                        activePlayer = 0;
                        TextView status = root.findViewById(R.id.status);

                        // change the status
                        status.setText("X's Turn - Tap to play");
                    }
                    img.animate().translationYBy(1000f).setDuration(300);
                }
                int flag = 0;
                // Check if any player has won
                for (int[] winPosition : winPositions) {
                    if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                            gameState[winPosition[1]] == gameState[winPosition[2]] &&
                            gameState[winPosition[0]] != 2) {
                        flag = 1;

                        // Somebody has won! - Find out who!
                        String winnerStr;

                        // game reset function be called
                        gameActive = false;
                        if (gameState[winPosition[0]] == 0) {
                            winnerStr = "X has won";
                        } else {
                            winnerStr = "O has won";
                        }
                        // Update the status bar for winner announcement
                        TextView status = root.findViewById(R.id.status);
                        Toast.makeText(GalleryFragment.super.getContext(),winnerStr,Toast.LENGTH_SHORT).show();
                        status.setText(winnerStr);
                    }
                }
                // set the status if the match draw
                if (counter == 9 && flag == 0) {
                    TextView status = root.findViewById(R.id.status);
                    status.setText("Match Draw");
                    Toast.makeText(GalleryFragment.super.getContext(),"Match Draw",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }
}