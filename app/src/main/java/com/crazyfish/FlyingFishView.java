package com.crazyfish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.crazyfish.R;

public class FlyingFishView extends View
{
    private final Bitmap[] fish = new Bitmap[2];
    private final int fishX = 10;
    private int fishY;
    private int fishSpeed;
    private int yellowX;
    private int yellowY;
    private final Paint yellowPaint = new Paint();
    private int greenX;
    private int greenY;
    private final Paint greenPaint = new Paint();
    private int reedX;
    private int reedY;
    private final Paint reedPaint = new Paint();
    private int score , lifeCounterOfFish;
    private final Bitmap backgroundImage;
    private final Paint scorePaint = new Paint();
    private final Bitmap[] life = new Bitmap[2];
    private boolean touch = false;

    public FlyingFishView(Context context) {
        super(context);
        fish[0] = BitmapFactory.decodeResource(getResources(),R.drawable.fish);
        fish[1] = BitmapFactory.decodeResource(getResources(),R.drawable.fish);

        backgroundImage = BitmapFactory.decodeResource(getResources(),R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        reedPaint.setColor(Color.RED);
        reedPaint.setAntiAlias(false);


        scorePaint.setColor(Color.DKGRAY);
        scorePaint.setTextSize(50);
        scorePaint.setTypeface(Typeface.DEFAULT);
        scorePaint.setAntiAlias(true);

        life[0]= BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1]= BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);
        fishY = 550;
        score = 0 ;
        lifeCounterOfFish = 3;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int canvasHeight = getHeight();
        int canvasWidth = getWidth();
        canvas.drawBitmap(backgroundImage,0,0,null);


        int minFishY = fish[0].getHeight();
        int mixFishY = canvasHeight - fish[0].getHeight() * 3;
        fishY = fishY + fishSpeed;

        if (fishY < minFishY)
        {
            fishY = minFishY;
        }
        if (fishY > mixFishY)
        {
            fishY = mixFishY;
        }
        fishSpeed = fishSpeed +2;
        if (touch)
        {
            canvas.drawBitmap(fish[1], fishX,fishY,null);
            touch = false;
        }
        else
        {
            canvas.drawBitmap(fish[0],fishX,fishY,null);
        }


        int yellowSpeed = 20;
        yellowX = yellowX - yellowSpeed;
        if (hitBallChecker(yellowX,yellowY))
        {
            score = score +10 ;
            yellowX = - 100;
        }
        if (yellowX < 0 )
        {
            yellowX = canvasWidth + 21 ;
            yellowY = (int) Math.floor(Math.random()*(mixFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(yellowX,yellowY,25,yellowPaint);

        int greenSpeed = 25;
        greenX = greenX - greenSpeed;
        if (hitBallChecker(greenX,greenY))
        {
            score = score +20 ;
            greenX = - 100;
        }
        if (greenX < 0 )
        {
            greenX = canvasWidth + 21 ;
            greenY = (int) Math.floor(Math.random()*(mixFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(greenX,greenY,25,greenPaint);

        int reedSpeed = 35;
        reedX = reedX - reedSpeed;
        if (hitBallChecker(reedX,reedY))
        {
            reedX = - 100;
            lifeCounterOfFish--;

            if (lifeCounterOfFish == 0)
            {
                @SuppressLint("DrawAllocation") Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("Score", score);

                getContext().startActivity(gameOverIntent);
            }
        }
        if (reedX < 0 )
        {
            reedX = canvasWidth + 21 ;
            reedY = (int) Math.floor(Math.random()*(mixFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(reedX,reedY,30,reedPaint);

        canvas.drawText("Score : "+ score,20 , 60 , scorePaint);

        for (int i=0; i<3; i++)
        {
            int x = (int) (500 + life[0].getWidth() * 1.5 * i);
            int y = 10;
            if (i < lifeCounterOfFish)
            {
                canvas.drawBitmap(life[0],x ,y ,null);
            }
            else
            {
                canvas.drawBitmap(life[1],x, y,null);
            }
        }
    }

    private boolean hitBallChecker(int x, int y)
    {
        return fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            touch = true;
            fishSpeed = -22;
        }
        return  true;
        }
}
